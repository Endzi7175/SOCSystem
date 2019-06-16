package com.sbnz.SIEMCenter2.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;
import org.drools.template.ObjectDataCompiler;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.sbnz.SIEMCenter2.model.LogEntry;
import com.sbnz.SIEMCenter2.model.Rule;
import com.sbnz.SIEMCenter2.repository.MaliciousIpRepository;


@Service
public class KieService {
	private KieSession kieSession;
	private KieContainer kieCointainer;
	@Autowired
	AlarmTriggeredService alarmService;
	@Autowired
	UserService userService;
	@Autowired
	LogEntryService logService;
	@Autowired
	MaliciousIpRepository ipRepo;
	@Bean
	@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
	public KieSession getKieSession(){
		KieServices ks = KieServices.Factory.get();
		KieBaseConfiguration config = KieServices.Factory.get().newKieBaseConfiguration();

		config.setOption( EventProcessingOption.STREAM );
		
		KieContainer kContainer = ks
				 .newKieContainer(ks.newReleaseId("com.sbnz.drools", "log-rules", "0.0.1-SNAPSHOT"));
		KieBase kBase = kContainer.newKieBase(config);
		
		this.kieCointainer = kContainer;
		KieScanner kScanner = ks.newKieScanner(kContainer);
		kScanner.start(10_000);
		KieSession kieSession = kBase.newKieSession();
		kieSession.setGlobal("alarmService", this.alarmService);
		return kieSession;
	}

	public KieService() 
	{
		KieServices ks = KieServices.Factory.get();
		KieBaseConfiguration config = KieServices.Factory.get().newKieBaseConfiguration();

		config.setOption( EventProcessingOption.STREAM );
		
		KieContainer kContainer = ks
				 .newKieContainer(ks.newReleaseId("com.sbnz.drools", "log-rules", "0.0.1-SNAPSHOT"));
		KieBase kBase = kContainer.newKieBase(config);
		
		this.kieCointainer = kContainer;
		KieScanner kScanner = ks.newKieScanner(kContainer);
		kScanner.start(10_000);
		kieSession = kBase.newKieSession();
		
	}
	public void startKieService(){
		kieSession.setGlobal("alarmService", this.alarmService);
		System.out.println(this.alarmService != null);
		kieSession.setGlobal("userService", this.userService);
		kieSession.setGlobal("ipRepo", this.ipRepo);
		(new Thread(){
			public void run(){
				kieSession.fireUntilHalt();
			}
		}).start();
	}
	
	public void insertLogEntries(List<LogEntry> entries) {
		
        
	  for(LogEntry entry : entries) {
        	kieSession.insert(entry);
        } 
        	  
        
        System.out.println("usao");
        //kieSession.halt();
     
		
		
		//kieSession.dispose();
	}
	
	public void insertNewRule(Rule rule, String interval, int count, String alarmMessage ) throws IOException {

		 File initialFile = Paths.get("src","main","resources","templates","template.drl").toFile();
		 InputStream targetStream = new FileInputStream(initialFile);
		 String gitDir = Paths.get("").toFile().getParent();
		 Path dir = Paths.get(gitDir, "log-rules","src","main","resources","sbnz","rules");

		 int i = 0;
		 while(Paths.get(dir.toString(), i + ".drl").toFile().exists()) {
			 i++;
		 }
		 ObjectDataCompiler objectDataCompiler = new ObjectDataCompiler();
		 
		 Map<String, Object> data = new HashMap<>();
		 data.put("ruleNum", i);
		 data.put("rule", rule);
		 data.put("count", count);
		 data.put("interval", interval);
		 data.put("alarmMessage", alarmMessage);
		 
		 String drl = objectDataCompiler.compile(Collections.singletonList(data), targetStream);
		 
		 PrintWriter out = new PrintWriter(Paths.get(dir.toString(), i +".drl").toString());
		 out.print(drl);
		 out.close();
		 targetStream.close();
		 
		 InvocationRequest request = new DefaultInvocationRequest();
		 request.setPomFile(Paths.get(gitDir,"log-rules","pom.xml").toFile());
		 request.setGoals(Collections.singletonList("install"));
		 Invoker invoker = new DefaultInvoker();
	 
		 try {
			invoker.execute(request);
		} catch (MavenInvocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		
	}
	
}
