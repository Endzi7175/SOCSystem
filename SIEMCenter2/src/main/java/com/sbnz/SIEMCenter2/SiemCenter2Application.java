package com.sbnz.SIEMCenter2;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;
import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.sbnz.SIEMCenter2.model.AlarmTriggered;
import com.sbnz.SIEMCenter2.model.LogEntry;
import com.sbnz.SIEMCenter2.service.AlarmTriggeredService;
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
		  Date date = new Date(2019, 5, 19, 20, 0);
	        LogEntry le = new LogEntry(1, "Neuspesna prijava", "asd", 1, "192.168.0.1", "1", date);
	        LogEntry le1 = new LogEntry(2, "Neuspesna prijavaa", "asd", 2, "192.168.0.1", "1", date);
	        LogEntry le2 = new LogEntry(3, "Neuspesna prijava", "asd", 3, "192.168.0.1", "1", date);
	        List<LogEntry> entries = new ArrayList<>();
	        entries.add(le);
	        entries.add(le1);
	        entries.add(le2);
	        
	        kieService.insertLogEntries(entries);
			//kieSession.insert(new LogEntry(1, "Neuspesna prijava", "1", 1, "128.212.", "1", new Date()));
		
	}

}
