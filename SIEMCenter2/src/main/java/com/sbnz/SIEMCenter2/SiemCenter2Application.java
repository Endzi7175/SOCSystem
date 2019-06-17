package com.sbnz.SIEMCenter2;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.runtime.KieContainer;
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
