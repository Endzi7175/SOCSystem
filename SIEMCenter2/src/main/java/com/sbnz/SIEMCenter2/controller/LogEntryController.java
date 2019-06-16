package com.sbnz.SIEMCenter2.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbnz.SIEMCenter2.model.LogEntry;
import com.sbnz.SIEMCenter2.service.KieService;
import com.sbnz.SIEMCenter2.service.LogEntryService;

@RestController
@RequestMapping(value="api/logs")
public class LogEntryController {
	@Autowired
	KieService kieService;
	@Autowired
	KieSession kieSession;
	@Autowired 
	LogEntryService logService;
	@GetMapping(produces =MediaType.APPLICATION_JSON_VALUE)
	public void addnew(){
		Date date = new Date(2019, 5, 19, 20, 0);
		List<LogEntry > list = new ArrayList<>();
		//LogEntry log = new LogEntry(1, "Neuspesna prijava na sistem", "asd", 1, "192.168.0.1", "1", date);
		//kieSession.insert(log);
		//kieSession.fireAllRules();
	}
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/1")
	public void addneww(){
		Date date = new Date(2019, 5, 19, 20, 0);
		List<LogEntry > list = new ArrayList<>();
		//LogEntry log = new LogEntry(1, "Neuspesna prijava", "asd", 0, "192.168.0.1", "0", date);
		//kieSession.insert(log);
		kieSession.fireAllRules();
		
		//kieSession.
	}
	@PostMapping(produces =MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void addnewPost(@RequestBody List<LogEntry> logs){
		
		kieService.insertLogEntries(logs);
		for (LogEntry log : logs){
			logService.save(log);
		}
	}
	
}
