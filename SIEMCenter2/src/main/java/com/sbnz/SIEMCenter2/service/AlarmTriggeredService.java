package com.sbnz.SIEMCenter2.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.sbnz.SIEMCenter2.model.AlarmTriggered;
import com.sbnz.SIEMCenter2.repository.AlarmTriggeredRepository;

@Service
public class AlarmTriggeredService {
	
	public AlarmTriggeredService(AlarmTriggeredRepository alarmRepo){
		this.alarmRepo = alarmRepo;
	}


	@Autowired
	private AlarmTriggeredRepository alarmRepo;

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	public AlarmTriggered save(AlarmTriggered alarm){
		AlarmTriggered alarmTriggered = alarmRepo.save(alarm);
		simpMessagingTemplate.convertAndSend("/alarm", alarmTriggered);
		return alarmTriggered;
	}
	public AlarmTriggered findByType(int type){
		return alarmRepo.findByType(type);
	}
	public List<AlarmTriggered> fidnAll() {
		// TODO Auto-generated method stub
		return alarmRepo.findAll();
	}
	
}