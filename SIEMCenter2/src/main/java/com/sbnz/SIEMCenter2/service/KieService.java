package com.sbnz.SIEMCenter2.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

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
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.sbnz.SIEMCenter2.model.LogEntry;
import com.sbnz.SIEMCenter2.model.Rule;
import com.sbnz.SIEMCenter2.repository.MaliciousIpRepository;


@Service
public class KieService {
	private KieSession kieSession;
	private KieContainer kieCointainer;
	private Thread sessionThread;
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
	@Bean(destroyMethod = "shutdown")
    public ListeningExecutorService executor() {
        return MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());
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
		kieSession.setGlobal("userService", this.userService);
		kieSession.setGlobal("ipRepo", this.ipRepo);
		/*sessionThread = new Thread(){
			public void run(){
				
				kieSession.fireUntilHalt();
				
				

			}
		};*/
		executor().submit(() -> {
			kieSession.insert(new LogEntry("1", "Neuspesna prijava na sistem", "security related", "0", "124.142.345.123", "1", new Date(),"1"));
			kieSession.insert(new LogEntry("1", "Neuspesna prijava na sistem", "security related", "0", "124.142.345.123", "1", new Date(),"1"));
			kieSession.insert(new LogEntry("1", "Neuspesna prijava na sistem", "security related", "0", "124.142.345.123", "1", new Date(),"1"));
			kieSession.insert(new LogEntry("1", "Neuspesna prijava na sistem", "security related", "0", "124.142.345.123", "1", new Date(),"1"));
			kieSession.insert(new LogEntry("1", "Neuspesna prijava na sistem", "security related", "0", "124.142.345.123", "1", new Date(),"1"));
			kieSession.insert(new LogEntry("1", "Neuspesna prijava na sistem", "security related", "0", "124.142.345.123", "1", new Date(),"1"));
			kieSession.insert(new LogEntry("1", "Neuspesna prijava na sistem", "security related", "0", "124.142.345.123", "1", new Date(),"1"));
			kieSession.insert(new LogEntry("1", "Neuspesna prijava na sistem", "security related", "0", "124.142.345.123", "1", new Date(),"1"));
			kieSession.insert(new LogEntry("1", "Neuspesna prijava na sistem", "security related", "0", "124.142.345.123", "1", new Date(),"1"));
			kieSession.insert(new LogEntry("1", "Neuspesna prijava na sistem", "security related", "0", "124.142.345.123", "1", new Date(),"1"));
			kieSession.insert(new LogEntry("1", "Neuspesna prijava na sistem", "security related", "0", "124.142.345.123", "1", new Date(),"1"));
			kieSession.insert(new LogEntry("1", "Neuspesna prijava na sistem", "security related", "0", "124.142.345.123", "1", new Date(),"1"));
			kieSession.insert(new LogEntry("1", "Neuspesna prijava na sistem", "security related", "0", "124.142.345.123", "1", new Date(),"1"));
			kieSession.insert(new LogEntry("1", "Neuspesna prijava na sistem", "security related", "0", "124.142.345.123", "1", new Date(),"1"));
			kieSession.insert(new LogEntry("1", "Neuspesna prijava na sistem", "security related", "0", "124.142.345.123","1", new Date(),"1"));

            kieSession.fireUntilHalt();
			
        });
		//sessionThread.start();
		System.out.println("startovao i prosao until halt");
		LogEntry log = new LogEntry("1", "Neuspesna prijava na sistem", "security related", "0", "124.142.345.123", "1", new Date(),"1");
		LogEntry log1 = new LogEntry("1", "Virus", "security related", "0", "124.142.345.123", "1", new Date(),"1");
		LogEntry log2= new LogEntry("1", "Proba", "security related", "0", "124.142.345.123", "1", new Date(),"1");

		kieSession.insert(log);
		kieSession.insert(log1);
		kieSession.insert(log2);
		
	}
	
	public void insertLogEntries(List<LogEntry> entries) {
		//System.out.println(entries.size());
	  for(LogEntry entry : entries) {
		  entry.setTimestamp(new Date());
		  kieSession.insert(entry);
        } 
	  //kieSession.fireUntilHalt();

	}
	
	public void insertNewRule(Rule rule, String interval, int count, String alarmMessage ) throws IOException {

		 File initialFile = Paths.get("src","main","resources","templates","template.drl").toFile();
		 InputStream targetStream = new FileInputStream(initialFile);
		 String gitDir = Paths.get(Paths.get("").toAbsolutePath().toString()).toFile().getParentFile().toString();
		 Path dir = Paths.get( gitDir,"log-rules","src","main","resources","sbnz","rules");
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
			e.printStackTrace();
		}
		 
		
	}

	public List<LogEntry> getAllFromMemory(String[] queryParams) {
		 for (int i = 0; i < queryParams.length; i++){
			 if (queryParams[i].equals("")){
				 queryParams[i] = ".*";
			 }
		 }
		 List<LogEntry> results = new ArrayList<LogEntry>();
		 QueryResults qr = kieSession
				 .getQueryResults("getLogsWithMessage", 
						 queryParams[0], 
						 queryParams[1], 
						 queryParams[2], 
						 queryParams[3], 
						 queryParams[4], 
						 queryParams[5],
						 queryParams[6]);
		 for (Iterator<QueryResultsRow> iterator=qr.iterator(); iterator.hasNext(); ) {
			    QueryResultsRow row=iterator.next();
			    LogEntry l=(LogEntry)row.get("$l");
			    results.add(l);
		 }
		return results;
	}
	
}
