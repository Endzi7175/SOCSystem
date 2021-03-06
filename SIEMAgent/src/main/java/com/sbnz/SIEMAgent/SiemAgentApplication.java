package com.sbnz.SIEMAgent;




import java.nio.file.Paths;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

import com.sbnz.SIEMAgent.FileWatcher.WatchAgent;


@SpringBootApplication
@EnableAsync
@ComponentScan
public class SiemAgentApplication implements CommandLineRunner {
	
	  private final org.slf4j.Logger logger = LoggerFactory.getLogger(SiemAgentApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SiemAgentApplication.class, args);
	}

	@Autowired
	WatchAgent agent;


	@Override
	public void run(String... args) {

		agent.loadWatchers();
		//try {
			//agent.registerNewPath(Paths.get("D:\\4.godina\\Bezbednost\\Projekat\\SOCSystem\\LogSimulator"),0);
		//} catch (WatchAgent.WatchAgentException e) {
			//e.printStackTrace();
		//}

	}



}
