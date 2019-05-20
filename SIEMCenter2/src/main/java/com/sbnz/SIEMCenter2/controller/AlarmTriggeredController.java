package com.sbnz.SIEMCenter2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbnz.SIEMCenter2.model.AlarmTriggered;
import com.sbnz.SIEMCenter2.service.AlarmTriggeredService;

@RestController
@RequestMapping(value="/api/alarm")
public class AlarmTriggeredController {
	@Autowired
	AlarmTriggeredService alarmService;
	@GetMapping( produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AlarmTriggered> addNewAlarm(){
		return new ResponseEntity<AlarmTriggered>(alarmService.save(new AlarmTriggered("1", "poruka")), HttpStatus.OK);
	}
}
