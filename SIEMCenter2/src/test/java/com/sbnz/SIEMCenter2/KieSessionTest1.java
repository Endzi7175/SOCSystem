package com.sbnz.SIEMCenter2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sbnz.SIEMCenter2.model.AlarmTriggered;
import com.sbnz.SIEMCenter2.model.LogEntry;
import com.sbnz.SIEMCenter2.model.MaliciousIpAddress;
import com.sbnz.SIEMCenter2.repository.MaliciousIpRepository;
import com.sbnz.SIEMCenter2.service.AlarmTriggeredService;
import com.sbnz.SIEMCenter2.service.KieService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class KieSessionTest1 {

	@Autowired
	KieService kieService;
	@Autowired
	AlarmTriggeredService alarmService;
	@Autowired
	MaliciousIpRepository ipRepo;
	@Test
	public void test_neuspesnePrijaveNaIstojMasini(){
		KieSession session = kieService.getKieSession();
		for (int i = 0; i < 9; i++){
			LogEntry log = new LogEntry(1, "Neuspesna prijava na sistem", "security related", 0, "212.234.212.102", i+"", new Date(), "1");
			session.insert(log);
		}
		int rulesFired = session.fireAllRules();
		assertEquals(0, rulesFired);
		
		LogEntry log = new LogEntry(1, "Neuspesna prijava na sistem", "security related", 0, "212.234.212.102", "1", new Date(), "1");
		session.insert(log);
		
		rulesFired = session.fireAllRules();
		assertEquals(1, rulesFired);
	}
	@Test
	public void test_neuspesniPokusajiPrijaveNaSistemSaIstimKorisnickimImenom() throws ParseException, InterruptedException{
		KieSession session = kieService.getKieSession();
		for (int i = 0; i < 9; i++){
			LogEntry log = new LogEntry(1, "Neuspesna prijava na sistem", "security related", 0, "212.234.212.102", "1", new Date(), i+"");
			session.insert(log);
		}
		int rulesFired = session.fireAllRules();
		assertEquals(0, rulesFired);
		
		LogEntry log = new LogEntry(1, "Neuspesna prijava na sistem", "security related", 0, "212.234.212.102", "1", new Date(), "1");
		session.insert(log);
		
		rulesFired = session.fireAllRules();
		assertEquals(1, rulesFired);
	}
	@Test
	public void test_pojavaLogaCijiJeTipError(){
		KieSession session = kieService.getKieSession();
		//log level 1 = error
		int logLevel = 1;
		LogEntry log = new LogEntry(1, "Greska u programu", "security related", logLevel, "212.234.212.102", "1", new Date(), "1");
		session.insert(log);
		int rulesFired = session.fireAllRules();
		assertEquals(1, rulesFired);

	}
	@Test
	public void test_neuspesniPokusajPrijaveNeaktivnogKorisnika(){
		KieSession session = kieService.getKieSession();
		Calendar cal = Calendar.getInstance();
		Date dateUnsuccessfulLogin = cal.getTime(); 
		cal.add(Calendar.DAY_OF_YEAR, -89);
		Date dateLastLogin = cal.getTime();
		
		cal.add(Calendar.DAY_OF_YEAR, -2);
		Date dateLastLogin2 = cal.getTime();
		
		//Active user, last login was 89 days ago (No alarm fired)
		LogEntry logActiveUser1 = new LogEntry(1, "Uspesna prijava na sistem", "security related", 0, "212.234.212.102", "1", dateLastLogin, "1");
		LogEntry logActiveUser2 = new LogEntry(1, "Neuspesna prijava na sistem", "security related", 0, "212.234.212.102", "1", dateUnsuccessfulLogin, "1");
		
		session.insert(logActiveUser1);
		session.insert(logActiveUser2);
		
		int rulesFired = session.fireAllRules();
		assertEquals(0, rulesFired);
		
		//Inactive user last login 91 days ago (Alarm fired)
		LogEntry logInactiveUser1 = new LogEntry(1, "Uspesna prijava na sistem", "security related", 0, "212.234.212.102", "2", dateLastLogin2, "1");
		LogEntry logInactiveUser2 = new LogEntry(1, "Neuspesna prijava na sistem", "security related", 0, "212.234.212.102", "2", dateUnsuccessfulLogin, "1");
		
		session.insert(logInactiveUser1);
		session.insert(logInactiveUser2);
		
		rulesFired = session.fireAllRules();
		
		assertEquals(1, rulesFired);
	}
	@Test
	public void test_viseOd15NeuspesnihPrijavaNaRazliciteDeloveInformacionihSistemaSaIsteIpAdreseURokuOd5Dana(){
		KieSession session = kieService.getKieSession();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -5);
		//15 jednakih intervala po 8 sati (ukupno 5 dana)
		int interval = 24*5/15;
		for (int i = 0; i < 14; i++){
			Date loginDate = cal.getTime();
			LogEntry log = new LogEntry(i, "Neuspesna prijava na sistem", "security related", 0, "213.234.212.102", i+"", loginDate, i+"");
			session.insert(log);
			cal.add(Calendar.HOUR, interval);
		}
		int rulesFired = session.fireAllRules();
		assertEquals(0, rulesFired);

		LogEntry log = new LogEntry(1, "Neuspesna prijava na sistem", "security related", 0, "213.234.212.102", "1", cal.getTime(), "1");
		session.insert(log);
		
		rulesFired = session.fireAllRules();
		assertEquals(1, rulesFired);
	}
	@Test
	public void test_pojavaLogaUKomeAntivirusRegistrujePretnjuADaURokuOdSatVremenaNijeUklonjenVirus(){
		KieSession session = kieService.getKieSession();
		Date virusAppearDate = new Date();
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, -61);
		virusAppearDate= c.getTime();
		
		LogEntry log = new LogEntry(1, "Virus", "security related", 0, "212.234.212.102", "1", virusAppearDate, "1");


		session.insert(session.getSessionClock());
		session.insert(log);
		


		int rulesFired = session.fireAllRules();
		assertEquals(1, rulesFired);


		
		
	}
	@Test
	public void test_pojavaLogaUKomeAntivirusRegistrujePretnjuADaURokuOdSatVremenaJesteUklonjenVirus(){
		KieSession session = kieService.getKieSession();
		Date virusAppearDate = new Date();
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, -61);
		virusAppearDate= c.getTime();
		
		LogEntry log = new LogEntry(1, "Virus", "security related", 0, "212.234.212.102", "1", virusAppearDate, "1");


		session.insert(session.getSessionClock());
		session.insert(log);
		
		c.add(Calendar.MINUTE, 59);
		Date virusRemoveDate = c.getTime();

		LogEntry log2 = new LogEntry(1, "Uklonjen virus", "security related", 0, "212.234.212.102", "1", virusRemoveDate, "1");
		session.insert(log2);

		int rulesFired = session.fireAllRules();
		assertEquals(0, rulesFired);		
	}
	@Test
	public void test_uPredioduOd10DanaRegistrovano7IliVisePretnjiOdStraneAntivirusaZaIstiRacunar(){
		KieSession session = kieService.getKieSession();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -10);
		int interval = 10*24/6;
		System.out.println(interval);
		//<= 10 days MachineId 1
		for (int i = 0; i < 7; i++){
			Date virusAppearDate = cal.getTime();
			LogEntry log = new LogEntry(1, "Virus", "security related", 0, "212.234.212.102", "1", virusAppearDate, "1");
			session.insert(log);
			cal.add(Calendar.HOUR, interval);
		}
		
		int rulesFired = session.fireAllRules();
		assertEquals(1, rulesFired);
		
		
		//> 10 days machineId 2
		for (int i = 0; i < 6; i++){
			Date virusAppearDate = cal.getTime();
			LogEntry log = new LogEntry(1, "Virus", "security related", 0, "212.234.212.102", "1", virusAppearDate, "2");
			session.insert(log);
		}
		cal.add(Calendar.DAY_OF_YEAR, 10);
		cal.add(Calendar.MINUTE, 1);
		Date tenDaysAfter = cal.getTime();
		LogEntry log = new LogEntry(1, "Virus", "security related", 0, "212.234.212.102", "1", tenDaysAfter, "2");
		session.insert(log);
		rulesFired = session.fireAllRules();
		assertEquals(0, rulesFired);
		
	}
	@Test
	public void test_pojavaLogaUKomeSeNalaziIpAdresaSaListeMalicioznihIpAdresa(){
		KieSession session = kieService.getKieSession();
		session.insert(new MaliciousIpAddress("222.222.222.222"));
		
		LogEntry log = new LogEntry(1, "Neuspesna prijava na sistem", "security related", 0, "222.222.222.222", "1", new Date(), "1");
		session.insert(log);
		
		int rulesFired = session.fireAllRules();
		assertEquals(1, rulesFired);

	}
	@Test
	public void test_DosNapad(){
		KieSession session = kieService.getKieSession();
		for (int i = 0; i < 11; i++){
			LogEntry log = new LogEntry(1, "Placanje", "payment", 0, "222.222.222.222", "1", new Date(), "1");
			session.insert(log);
		}
		for (int i = 0; i < 35; i++){
			LogEntry log = new LogEntry(1, "Izmena profila", "security related", 0, "222.222.222.222", "1", new Date(), "1");
			session.insert(log);
		}
		for (int i = 0; i < 4; i++){
			LogEntry log = new LogEntry(1, "Neuspesna prijava na sistem", "security related", 0, "222.222.222.222",i+"", new Date(), i+"");
			session.insert(log);
		}

		int rulesFired = session.fireAllRules();
		assertEquals(1, rulesFired);
		AlarmTriggered alarm = alarmService.findByType(AlarmTriggered.DOS_NAPAD);
		assertNotNull(alarm);
		assertEquals(AlarmTriggered.DOS_NAPAD, alarm.getType());
	}
	@Test
	public void test_paymentNapad(){
		KieSession session = kieService.getKieSession();
		for (int i = 0; i < 12; i++){
			LogEntry log = new LogEntry(1, "Placanje", "payment", 0, "222.222.222.222", "1", new Date(), "1");
			session.insert(log);
		}
		for (int i = 0; i < 34; i++){
			LogEntry log = new LogEntry(1, "nesto sedmo", "security related", 0, "222.222.222.222", "1", new Date(), "1");
			session.insert(log);
		}
		for (int i = 0; i < 4; i++){
			LogEntry log = new LogEntry(1, "Neuspesna prijava na sistem", "security related", 0, "222.222.222.222",i+"", new Date(), i+"");
			session.insert(log);
		}
		int rulesFired = session.fireAllRules();
		assertEquals(1, rulesFired);
		AlarmTriggered alarm = alarmService.findByType(AlarmTriggered.PAYMENT_NAPAD);
		assertNotNull(alarm);
		assertEquals(AlarmTriggered.PAYMENT_NAPAD, alarm.getType());
	}
	@Test
	public void test_bruteForceNapad(){
		KieSession session = kieService.getKieSession();
		for (int i = 0; i < 10; i++){
			LogEntry log = new LogEntry(1, "Placanje", "payment", 0, "222.222.222.222", "1", new Date(), "1");
			session.insert(log);
		}
		for (int i = 0; i < 32; i++){
			LogEntry log = new LogEntry(1, "Izmena profila", "security related", 0, "222.222.222.222", "1", new Date(), "1");
			session.insert(log);
		}
		for (int i = 0; i < 8; i++){
			LogEntry log = new LogEntry(1, "Neuspesna prijava na sistem", "security related", 0, "222.222.222.222",i+"", new Date(), i+"");
			session.insert(log);
		}
		int rulesFired = session.fireAllRules();
		assertEquals(1, rulesFired);
		AlarmTriggered alarm = alarmService.findByType(AlarmTriggered.BRUTEFORCE_NAPAD);
		assertNotNull(alarm);
		assertEquals(AlarmTriggered.BRUTEFORCE_NAPAD, alarm.getType());
	}
	@Test
	public void test_30IliViseNeuspesnihPrijavaSaIsteIpAdrese(){
		KieSession session = kieService.getKieSession();
		session.setGlobal("ipRepo", ipRepo);
		for (int i = 0; i < 31; i++){
			LogEntry log = new LogEntry(1, "Neuspesna prijava na sistem", "security related", 0, "222.222.222.222", i+"1", new Date(), i+ "1");
			session.insert(log);
		}
		MaliciousIpAddress maliciousIp = ipRepo.findByIp("222.222.222.222");
		assertNotNull(maliciousIp);
	}

}
