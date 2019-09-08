package com.sbnz.SIEMCenter2.service;

import org.kie.api.runtime.KieContainer;
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
	@Autowired
	public Iterable<LogEntry> findAll(){
		return logRepo.findAll();
	}
	public LogEntry save(LogEntry le){
		
		//KieSessionService.session.dispose();
		return logRepo.save(le);
	}

}
