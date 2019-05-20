package com.sbnz.SIEMCenter2.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbnz.SIEMCenter2.model.LogEntry;
import com.sbnz.SIEMCenter2.service.LogEntryService;

@RestController
@RequestMapping(value="api/logs")
public class LogEntryController {
	@Autowired 
	LogEntryService logService;
	@GetMapping(produces =MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LogEntry> addnew(){
		Date date = new Date(2019, 5, 19, 20, 0);
		return new ResponseEntity<LogEntry>(logService.save(new LogEntry(1, "Neuspesna prijava", "asd", 1, "192.168.0.1", "1", date)),  HttpStatus.OK);

	}
}
