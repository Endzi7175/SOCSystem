package com.sbnz.SIEMCenter2;



import java.util.ArrayList;

import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.runtime.KieContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;


import com.google.common.io.Files;
import com.sbnz.SIEMCenter2.model.AlarmTriggered;
import com.sbnz.SIEMCenter2.model.Condition;
import com.sbnz.SIEMCenter2.model.Condition.BooleanTrailingOperator;
import com.sbnz.SIEMCenter2.model.LogEntry;
import com.sbnz.SIEMCenter2.model.Rule;
import com.sbnz.SIEMCenter2.service.AlarmTriggeredService;

import com.sbnz.SIEMCenter2.repository.MaliciousIpRepository;

import com.sbnz.SIEMCenter2.service.KieService;


@SpringBootApplication
public class SiemCenter2Application implements CommandLineRunner {
	@Autowired
	public ApplicationContext context;
	
	@Autowired
	public KieService kieService;

	public static void main(String[] args) {
		SpringApplication.run(SiemCenter2Application.class, args);
	


	}

	@Override
	public void run(String... args) throws Exception {
//		  Date date = new Date(2019, 5, 19, 20, 0);
//	        LogEntry le = new LogEntry(1, "Neuspesna prijava", "asd", 1, "192.168.0.1", "1", date);
//	        LogEntry le1 = new LogEntry(2, "Neuspesna prijavaa", "asd", 2, "192.168.0.1", "1", date);
//	        LogEntry le2 = new LogEntry(3, "Neuspesna prijava", "asd", 3, "192.168.0.1", "1", date);
//	        List<LogEntry> entries = new ArrayList<>();
//	        entries.add(le);
//	        entries.add(le1);
//	        entries.add(le2);
//	        
//	        kieService.insertLogEntries(entries);
			//kieSession.insert(new LogEntry(1, "Neuspesna prijava", "1", 1, "128.212.", "1", new Date()));
		 Condition condition = new Condition();
		 condition.field = "message";
		 condition.value = "Pera";
		 condition.comapreOperator = Condition.ComapreOperator.EQUAL_TO;
		 condition.trailingOperator = BooleanTrailingOperator.AND;
		 
		 Rule rule = new Rule();
		 rule.setConditions(new ArrayList<>());
		 rule.conditions.add(condition);
		 
		 condition = new Condition();
		 condition.field = "logLevel";
		 condition.value = 5;
		 condition.comapreOperator = Condition.ComapreOperator.EQUAL_TO;
		 condition.trailingOperator = BooleanTrailingOperator.AND;
		 
		 rule.conditions.add(condition);
		 
		 kieService.insertNewRule(rule);

	}
	@Bean
	public KieContainer KieContainer(){
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks
				 .newKieContainer(ks.newReleaseId("com.sbnz.drools", "log-rules", "0.0.1-SNAPSHOT"));
				//.newKieContainer(ks.newReleaseId("sbnz.integracija" , "drools-spring-kjar", "0.0.1-SNAPSHOT"));
		KieScanner kScanner = ks.newKieScanner(kContainer);
		kScanner.start(10_000);
		return kContainer;
	}

}
