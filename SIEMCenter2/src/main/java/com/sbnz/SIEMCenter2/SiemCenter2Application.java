package com.sbnz.SIEMCenter2;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.sbnz.SIEMCenter2.model.AlarmTriggered;
import com.sbnz.SIEMCenter2.model.LogEntry;

@SpringBootApplication
public class SiemCenter2Application {
	@Autowired
	public ApplicationContext context;
	public static void main(String[] args) {
		SpringApplication.run(SiemCenter2Application.class, args);
		
		
        Date date = new Date(2019, 5, 19, 20, 0);
        LogEntry le = new LogEntry(1, "Neuspesna prijava", "asd", 1, "192.168.0.1", "1", date);
        LogEntry le1 = new LogEntry(2, "Neuspesna prijavaa", "asd", 2, "192.168.0.1", "1", date);
        LogEntry le2 = new LogEntry(3, "Neuspesna prijava", "asd", 3, "192.168.0.1", "1", date);

        

		InvocationRequest request = new DefaultInvocationRequest();
		request.setPomFile( new File( "D:\\4.godina\\Bezbednost\\Projekat\\SOCSystem\\log-rules\\pom.xml" ) );
		request.setGoals( Collections.singletonList( "install" ) );
		
		Invoker invoker = new DefaultInvoker();
		//System.out.println(System.getenv("M2_HOME"));
		try {
			invoker.execute( request );
		} catch (MavenInvocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
