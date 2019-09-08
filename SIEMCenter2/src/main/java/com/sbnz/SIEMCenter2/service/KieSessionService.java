package com.sbnz.SIEMCenter2.service;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KieSessionService {
	@Autowired
	private AlarmTriggeredService alarmService;
	
	static KieSession session;
	@Autowired
	public KieSessionService(KieContainer kContainer){
		//session = kContainer.newKieSession();
		//System.out.println("usao");
		//session.setGlobal("alarmService", this.alarmService);
	}
}
