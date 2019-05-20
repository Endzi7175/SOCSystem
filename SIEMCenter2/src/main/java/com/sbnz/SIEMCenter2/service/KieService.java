package com.sbnz.SIEMCenter2.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
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
	
	@Autowired
	AlarmTriggeredService alarmTriggeredService;
	
	

	public KieService() 
	{
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks
				 .newKieContainer(ks.newReleaseId("com.sbnz.drools", "log-rules", "0.0.1-SNAPSHOT"));
		KieScanner kScanner = ks.newKieScanner(kContainer);
		kScanner.start(10_000);
		kieSession = kContainer.newKieSession();
		alarms = new ArrayList<AlarmTriggered>();
		kieSession.setGlobal("alarms", alarms);
		
	}
	
	public void insertLogEntries(List<LogEntry> entries) {

        Date date = new Date(2019, 5, 19, 20, 0);
        for(LogEntry entry : entries) {
        	kieSession.insert(entry);
        }
      
        List<AlarmTriggered> oldAlarms = (ArrayList<AlarmTriggered>)kieSession.getGlobal("alarms");
		int x = kieSession.fireAllRules();
		alarms = (ArrayList<AlarmTriggered>)kieSession.getGlobal("alarms");
		for(AlarmTriggered alrm : oldAlarms) {
			alarms.remove(alrm);
		}
		kieSession.setGlobal("alarms", alarms);
        for (AlarmTriggered alarm : alarms){
        	alarmTriggeredService.save(alarm);
        	System.out.println(alarm.getMessage());
        }

		kieSession.dispose();
	}
	
	public void insertNewRule(String rule) {
		
	}
	
}
