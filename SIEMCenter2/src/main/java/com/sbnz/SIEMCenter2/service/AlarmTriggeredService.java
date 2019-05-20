package com.sbnz.SIEMCenter2.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbnz.SIEMCenter2.model.AlarmTriggered;
import com.sbnz.SIEMCenter2.repository.AlarmTriggeredRepository;

@Service
public class AlarmTriggeredService {

	@Autowired
	private AlarmTriggeredRepository alarmRepo;
	
	public AlarmTriggered save(AlarmTriggered alarm){
		return alarmRepo.save(alarm);
	}
	
}