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

	@PostMapping(produces =MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void addnewPost(@RequestBody List<LogEntry> logs){
		
		kieService.insertLogEntries(logs);
		for (LogEntry log : logs){
			logService.save(log);
		}
	}
	
}
