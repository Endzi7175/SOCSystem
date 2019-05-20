package com.sbnz.SIEMCenter2.service;

import java.util.Date;

import org.kie.api.internal.utils.KieService;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbnz.SIEMCenter2.model.LogEntry;
import com.sbnz.SIEMCenter2.repository.LogEntryRepository;

@Service
public class LogEntryService  {
	@Autowired
	private KieContainer kieContainer;
	@Autowired
	KieSessionService kieService;
	@Autowired
	private LogEntryRepository logRepo;
	public LogEntry save(LogEntry le){
		Date date = new Date(2019, 5, 19, 20, 0);
        //LogEntry l = new LogEntry(1, "Neuspesna prijava", "asd", 1, "192.168.0.1", "1", date);
        LogEntry le1 = new LogEntry(2, "Neuspesna prijavaa", "asd", 2, "192.168.0.1", "1", date);
        ////LogEntry le2 = new LogEntry(3, "Neuspesna prijava", "asd", 3, "192.168.0.1", "1", date);
        //KieSession ks = kieService.session; 
		//ks.insert(l);
		//ks.insert(le1);
		//ks.insert(le2);
		KieSessionService.session.insert(le);
		//KieSessionService.session.insert(le1);

		int fireCount  = KieSessionService.session.fireAllRules();
		System.out.println(fireCount);
		//KieSessionService.session.dispose();
		return logRepo.save(le);
	}
}
