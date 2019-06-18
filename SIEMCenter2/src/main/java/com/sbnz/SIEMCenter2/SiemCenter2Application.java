package com.sbnz.SIEMCenter2;



import java.util.*;

import com.sbnz.SIEMCenter2.model.AlarmTriggered;
import com.sbnz.SIEMCenter2.service.AlarmTriggeredService;
import org.aspectj.weaver.patterns.TypePatternQuestions.Question;
import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.sbnz.SIEMCenter2.model.Condition;
import com.sbnz.SIEMCenter2.model.Condition.BooleanTrailingOperator;
import com.sbnz.SIEMCenter2.model.LogEntry;
import com.sbnz.SIEMCenter2.model.Rule;
import com.sbnz.SIEMCenter2.service.KieService;


@SpringBootApplication
public class SiemCenter2Application implements CommandLineRunner {
	@Autowired
	public ApplicationContext context;

	@Autowired
	public KieService kieService;

	@Autowired
	AlarmTriggeredService alarmTriggeredService;

	public static void main(String[] args) {
		SpringApplication.run(SiemCenter2Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

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
		 
		 kieService.startKieService();
		 int id=0;
		 List<String> imena = Arrays.asList( "pera","zika","mika","seka");
		 List<String> ips = Arrays.asList( "192.168.0.1","192.168.11.1","192.168.0.152","192.168.0.73");
		 List<String> machine = Arrays.asList( "PC-123","PC-111","PC-100","PC-222");
		List<String> messages = Arrays.asList( "FATALNA GRESKA","Nista strasno","Strmovito","Poruka113", "Lorem ipsum", "MA kakvi ovo nista ne valaj", "Pera zika 123123");
		 Random r = new Random();
		/* while(true){
			 AlarmTriggered alarmTriggered = new AlarmTriggered();
			 alarmTriggered.setId((long)id++);
			 alarmTriggered.setUserId(imena.get(r.nextInt(4)));
			 alarmTriggered.setIp(ips.get(r.nextInt(4)));
			 alarmTriggered.setMachineId(machine.get(r.nextInt(4)));
			 alarmTriggered.setDateTriggered(new Date());
			 alarmTriggered.setType(r.nextInt(10));
			 alarmTriggered.setMessage(messages.get(r.nextInt(messages.size())));
			 alarmTriggeredService.save(alarmTriggered);
			 Thread.sleep(3000-r.nextInt(3000));
		 }*/
		 //kieService.insertNewRule(rule);


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
