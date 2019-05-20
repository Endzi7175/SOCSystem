package com.sbnz.SIEMCenter2.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbnz.SIEMCenter2.model.AlarmTriggered;
import com.sbnz.SIEMCenter2.model.LogEntry;

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
	
	public void insertNewRule(String rule) {
		
	}
	
}
