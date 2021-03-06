package com.sbnz.SIEMCenter2.controller;

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
import com.sbnz.SIEMCenter2.repository.LogEntryRepository;
import com.sbnz.SIEMCenter2.service.KieService;
import com.sbnz.SIEMCenter2.service.LogEntryService;
import com.sbnz.SIEMCenter2.service.SearchQueryBuilder;

@RestController
@RequestMapping(value="api/logs")
public class LogEntryController {
	@Autowired
	KieService kieService;

	@Autowired 
	LogEntryService logService;

	@Autowired
	SearchQueryBuilder searchQueryBuilder;
	@Autowired
	LogEntryRepository logRepo;
	@PostMapping(produces =MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void addnewPost(@RequestBody List<LogEntry> logs){
		
		kieService.insertLogEntries(logs);
		for (LogEntry log : logs){
			logService.save(log);
		}
	}
	@RequestMapping(value="/searchDB")
	public List<LogEntry> getAllFromDatabase(@RequestBody String queryParams[]){
	
		return searchQueryBuilder.getAll(queryParams);
	}
	@RequestMapping(value="/searchMemory")
	public List<LogEntry> getAllFromSessionMemory(@RequestBody String queryParams[]){
		return kieService.getAllFromMemory(queryParams);
	}
	@RequestMapping(value="/save")
	public List<LogEntry> save(){

		for (int i = 0; i < 15; i ++){
			logRepo.save(new LogEntry("1", "asd", "asd", "1", "111.111.111.111", "1", new Date(), "1"));
		}
		return null;
	}
	@GetMapping
	public Iterable<LogEntry> getAll(){
		return logRepo.findAll();
	}
	
}
