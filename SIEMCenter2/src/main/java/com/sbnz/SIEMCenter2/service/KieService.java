package com.sbnz.SIEMCenter2.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.template.ObjectDataCompiler;
import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbnz.SIEMCenter2.model.AlarmTriggered;
import com.sbnz.SIEMCenter2.model.Condition;
import com.sbnz.SIEMCenter2.model.LogEntry;
import com.sbnz.SIEMCenter2.model.Rule;
import com.sbnz.SIEMCenter2.model.Condition.BooleanTrailingOperator;

@Service
public class KieService {
	private KieSession kieSession;
	private  List<AlarmTriggered> alarms;
	private KieContainer kieCointainer;
	@Autowired
	AlarmTriggeredService alarmService;
	
	@Autowired
	LogEntryService logService;

	public KieService() 
	{
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks
				 .newKieContainer(ks.newReleaseId("com.sbnz.drools", "log-rules", "0.0.1-SNAPSHOT"));
		this.kieCointainer = kContainer;
		KieScanner kScanner = ks.newKieScanner(kContainer);
		kScanner.start(10_000);
		kieSession = kContainer.newKieSession();
		alarms = new ArrayList<AlarmTriggered>();
		//kieSession.setGlobal("alarms", alarms);
		
	}
	
	public void insertLogEntries(List<LogEntry> entries) {
		kieSession = this.kieCointainer.newKieSession();
		kieSession.setGlobal("alarmService", this.alarmService);
        Date date = new Date(2019, 5, 19, 20, 0);
        for (AlarmTriggered alarm : alarmService.fidnAll()){
        	kieSession.insert(alarm);
        }
        for (LogEntry ent : logService.findAll()){
        	kieSession.insert(ent);

        }
        for(LogEntry entry : entries) {
        	
        	
        	kieSession.insert(entry);
        	
        } 
     
		int x = kieSession.fireAllRules();
		System.out.println(x);
		
		kieSession.dispose();
	}
	
	public void insertNewRule(Rule rule) throws IOException {
		
		
		
		 File initialFile = new File("src/main/resources/templates/template.drl");
		 InputStream targetStream = new FileInputStream(initialFile);	 
		 Path dir = Paths.get("/home/f.micic/faks/BsepSBNZ/SOCSystem/log-rules/src/main/resources/sbnz/rules");
		 int i = 0;
		 while(Paths.get(dir.toString(), Integer.toString(i)+ ".drl").toFile().exists()) {
			 i++;
		 }
		 ObjectDataCompiler objectDataCompiler = new ObjectDataCompiler();
		 
		 Map<String, Object> data = new HashMap<String, Object>();
		 data.put("ruleNum", i);
		 data.put("rule", rule);
		 
		 String drl = objectDataCompiler.compile(Arrays.asList(data), targetStream);
		 
		 PrintWriter out = new PrintWriter(Paths.get(dir.toString(), Integer.toString(i)+".drl").toString());
		 out.print(drl);
		 out.close();
		 targetStream.close();
		
	}
	
}
