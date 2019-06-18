package com.sbnz.SIEMCenter2;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sbnz.SIEMCenter2.model.AlarmTriggered;
import com.sbnz.SIEMCenter2.model.LogEntry;
import com.sbnz.SIEMCenter2.model.MaliciousIpAddress;
import com.sbnz.SIEMCenter2.model.User;
import com.sbnz.SIEMCenter2.model.User.SecurityStatus;
import com.sbnz.SIEMCenter2.repository.MaliciousIpRepository;
import com.sbnz.SIEMCenter2.service.AlarmTriggeredService;
import com.sbnz.SIEMCenter2.service.KieService;
import com.sbnz.SIEMCenter2.service.UserService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class KieSessionTest1 {

	@Autowired
	KieService kieService;
	@Autowired
	AlarmTriggeredService alarmService;
	@Autowired
	MaliciousIpRepository ipRepo;
	@Autowired
	UserService userService;
	
	@Test
	public void test_neuspesnePrijaveNaIstojMasini(){
		KieSession session = kieService.getKieSession();
		for (int i = 0; i < 9; i++){
			LogEntry log = new LogEntry("1", "Neuspesna prijava na sistem", "security related", "0", "212.234.212.102", i+"", new Date(), "1");
			session.insert(log);
		}
		int rulesFired = session.fireAllRules();
		assertEquals(0, rulesFired);
		
		LogEntry log = new LogEntry("1", "Neuspesna prijava na sistem", "security related", "0", "212.234.212.102", "1", new Date(), "1");
		session.insert(log);
		
		rulesFired = session.fireAllRules();
		assertEquals(1, rulesFired);
	}
	@Test
	public void test_neuspesniPokusajiPrijaveNaSistemSaIstimKorisnickimImenom() throws ParseException, InterruptedException{
		KieSession session = kieService.getKieSession();
		for (int i = 0; i < 9; i++){
			LogEntry log = new LogEntry("1", "Neuspesna prijava na sistem", "security related", "0", "212.234.212.102", "1", new Date(), i+"");
			session.insert(log);
		}
		int rulesFired = session.fireAllRules();
		assertEquals(0, rulesFired);
		
		LogEntry log = new LogEntry("1", "Neuspesna prijava na sistem", "security related", "0", "212.234.212.102", "1", new Date(), "1");
		session.insert(log);
		
		rulesFired = session.fireAllRules();
		assertEquals(1, rulesFired);
	}
	@Test
	public void test_pojavaLogaCijiJeTipError(){
		KieSession session = kieService.getKieSession();
		//log level 1 = error
		String logLevel = "1";
		LogEntry log = new LogEntry("1", "Greska u programu", "security related", logLevel, "212.234.212.102", "1", new Date(), "1");
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
		LogEntry logActiveUser1 = new LogEntry("1", "Uspesna prijava na sistem", "security related", "0", "212.234.212.102", "1", dateLastLogin, "1");
		LogEntry logActiveUser2 = new LogEntry("1", "Neuspesna prijava na sistem", "security related", "0", "212.234.212.102", "1", dateUnsuccessfulLogin, "1");
		
		session.insert(logActiveUser1);
		session.insert(logActiveUser2);
		
		int rulesFired = session.fireAllRules();
		assertEquals(0, rulesFired);
		
		//Inactive user last login 91 days ago (Alarm fired)
		LogEntry logInactiveUser1 = new LogEntry("1", "Uspesna prijava na sistem", "security related", "0", "212.234.212.102", "2", dateLastLogin2, "1");
		LogEntry logInactiveUser2 = new LogEntry("1", "Neuspesna prijava na sistem", "security related", "0", "212.234.212.102", "2", dateUnsuccessfulLogin, "1");
		
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
			LogEntry log = new LogEntry(i + "", "Neuspesna prijava na sistem", "security related", "0", "213.234.212.102", i+"", loginDate, i+"");
			session.insert(log);
			cal.add(Calendar.HOUR, interval);
		}
		int rulesFired = session.fireAllRules();
		assertEquals(0, rulesFired);

		LogEntry log = new LogEntry("1", "Neuspesna prijava na sistem", "security related", "0", "213.234.212.102", "1", cal.getTime(), "1");
		session.insert(log);
		
		rulesFired = session.fireAllRules();
		assertEquals(1, rulesFired);
	}
	@Test
	public void test_pojavaLogaUKomeAntivirusRegistrujePretnjuADaURokuOdSatVremenaNijeUklonjenVirus(){
		KieSession session = kieService.getKieSession();
		Date virusAppearDate = new Date();
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, -58);
		virusAppearDate= c.getTime();
		
		LogEntry log = new LogEntry("1", "Virus", "security related", "0", "212.234.212.102", "1", virusAppearDate, "1");


		session.insert(session.getSessionClock());
		session.insert(log);
		


		int rulesFired = session.fireAllRules();
		assertEquals(0, rulesFired);
		
		c.add(Calendar.MINUTE, -3);
		virusAppearDate = c.getTime();
		
		LogEntry log2 = new LogEntry("1", "Virus", "security related", "0", "212.234.212.102", "1", virusAppearDate, "1");

		session.insert(log2);

		rulesFired = session.fireAllRules();
		assertEquals(1, rulesFired);		
	}
	@Test
	public void test_pojavaLogaUKomeAntivirusRegistrujePretnjuADaURokuOdSatVremenaJesteUklonjenVirus(){
		KieSession session = kieService.getKieSession();
		Date virusAppearDate = new Date();
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, -61);
		virusAppearDate= c.getTime();
		
		LogEntry log = new LogEntry("1", "Virus", "security related", "0", "212.234.212.102", "1", virusAppearDate, "1");


		session.insert(session.getSessionClock());
		session.insert(log);
		
		c.add(Calendar.MINUTE, 59);
		Date virusRemoveDate = c.getTime();

		LogEntry log2 = new LogEntry("1", "Uklonjen virus", "security related", "0", "212.234.212.102", "1", virusRemoveDate, "1");
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
			LogEntry log = new LogEntry("1", "Virus", "security related", "0", "212.234.212.102", "1", virusAppearDate, "1");
			session.insert(log);
			cal.add(Calendar.HOUR, interval);
		}
		
		int rulesFired = session.fireAllRules();
		assertEquals(1, rulesFired);
		
		
		//> 10 days machineId 2
		for (int i = 0; i < 6; i++){
			Date virusAppearDate = cal.getTime();
			LogEntry log = new LogEntry("1", "Virus", "security related", "0", "212.234.212.102", "1", virusAppearDate, "2");
			session.insert(log);
		}
		cal.add(Calendar.DAY_OF_YEAR, 10);
		cal.add(Calendar.MINUTE, 1);
		Date tenDaysAfter = cal.getTime();
		LogEntry log = new LogEntry("1", "Virus", "security related", "0", "212.234.212.102", "1", tenDaysAfter, "2");
		session.insert(log);
		rulesFired = session.fireAllRules();
		assertEquals(0, rulesFired);
		
	}
	@Test
	public void test_pojavaLogaUKomeSeNalaziIpAdresaSaListeMalicioznihIpAdresa(){
		KieSession session = kieService.getKieSession();
		session.insert(new MaliciousIpAddress("222.222.222.222"));
		
		LogEntry log = new LogEntry("1", "Neuspesna prijava na sistem", "security related", "0", "222.222.222.222", "1", new Date(), "1");
		session.insert(log);
		
		int rulesFired = session.fireAllRules();
		assertEquals(1, rulesFired);

	}
	@Test
	public void test_DosNapad(){
		KieSession session = kieService.getKieSession();
		for (int i = 0; i < 11; i++){
			LogEntry log = new LogEntry("1", "Placanje", "payment", "0", "222.222.222.222", "1", new Date(), "1");
			session.insert(log);
		}
		for (int i = 0; i < 35; i++){
			LogEntry log = new LogEntry("1", "Izmena profila", "security related", "0", "222.222.222.222", "1", new Date(), "1");
			session.insert(log);
		}
		for (int i = 0; i < 4; i++){
			LogEntry log = new LogEntry("1", "Neuspesna prijava na sistem", "security related", "0", "222.222.222.222",i+"", new Date(), i+"");
			session.insert(log);
		}

		int rulesFired = session.fireAllRules();
		assertEquals(1, rulesFired);
		List<AlarmTriggered> alarms = alarmService.findByType(AlarmTriggered.DOS_NAPAD);
		assertNotNull(alarms);
		assertTrue(alarms.size() > 0);
	}
	@Test
	public void test_paymentNapad(){
		KieSession session = kieService.getKieSession();
		
		for (int i = 0; i < 12; i++){
			LogEntry log = new LogEntry("1", "Placanje", "payment", "0", "222.222.222.222", "1", new Date(), "1");
			session.insert(log);
		}
		for (int i = 0; i < 34; i++){
			LogEntry log = new LogEntry("1", "nesto sedmo", "security related", "0", "222.222.222.222", "1", new Date(), "1");
			session.insert(log);
		}
		for (int i = 0; i < 4; i++){
			LogEntry log = new LogEntry("1", "Neuspesna prijava na sistem", "security related", "0", "222.222.222.222",i+"", new Date(), i+"");
			session.insert(log);
		}
		int rulesFired = session.fireAllRules();
		assertEquals(1, rulesFired);
		List<AlarmTriggered> alarms = alarmService.findByType(AlarmTriggered.PAYMENT_NAPAD);
		assertNotNull(alarms);
		assertTrue(alarms.size() > 0);
	}
	@Test
	public void test_bruteForceNapad(){
		KieSession session = kieService.getKieSession();
		for (int i = 0; i < 10; i++){
			LogEntry log = new LogEntry("1", "Placanje", "payment", "0", "222.222.222.222", "1", new Date(), "1");
			session.insert(log);
		}
		for (int i = 0; i < 32; i++){
			LogEntry log = new LogEntry("1", "Izmena profila", "security related", "0", "222.222.222.222", "1", new Date(), "1");
			session.insert(log);
		}
		for (int i = 0; i < 8; i++){
			LogEntry log = new LogEntry("1", "Neuspesna prijava na sistem", "security related", "0", "222.222.222.222",i+"", new Date(), i+"");
			session.insert(log);
		}
		int rulesFired = session.fireAllRules();
		assertEquals(1, rulesFired);
		List<AlarmTriggered> alarms = alarmService.findByType(AlarmTriggered.BRUTEFORCE_NAPAD);
		assertNotNull(alarms);
		assertTrue(alarms.size() > 0);
	}
	@Test
	public void test_30IliViseNeuspesnihPrijavaSaIsteIpAdrese(){
		KieSession session = kieService.getKieSession();
		session.setGlobal("ipRepo", ipRepo);
		Calendar cal = Calendar.getInstance();
		
		for (int i = 0; i < 30; i++){
			LogEntry log = new LogEntry("1", "Neuspesna prijava na sistem", "security related", "0", "124.142.345.123", i+"1", cal.getTime(), i+ "1");
			session.insert(log);
		}
		cal.add(Calendar.HOUR, 25);
		LogEntry log = new LogEntry("1", "Neuspesna prijava na sistem", "security related", "0", "124.142.345.123", "1", cal.getTime(), "1");
		session.insert(log);
		session.fireAllRules();
		MaliciousIpAddress maliciousIp = ipRepo.findByIp("124.142.345.123");
		assertNull(maliciousIp);
		
		cal.add(Calendar.HOUR,-2);
		LogEntry log2 = new LogEntry("1", "Neuspesna prijava na sistem", "security related", "0", "124.142.345.123", "1", cal.getTime(), "1");
		session.insert(log2);
		session.fireAllRules();
		maliciousIp = ipRepo.findByIp("124.142.345.123");
		assertNotNull(maliciousIp);

		
	}
	@Test
	public void test_UpsesnaPrijavaPracenaIzmenomPodataka(){
		KieSession session = kieService.getKieSession();
		Calendar cal = Calendar.getInstance();
		LogEntry logProfileChange = new LogEntry("1", "Izmena profila", "security related", "0", "123.123.123.123", "1", cal.getTime(), "1");
		cal.add(Calendar.MINUTE, -1);
		LogEntry logSuccessLogin = new LogEntry("1", "Uspesna prijava na sistem", "security related", "0", "123.123.123.123", "1", cal.getTime(), "1");
		
		for (int i = 0; i < 5; i++){
			cal.add(Calendar.SECOND, -13);
			LogEntry loglogin = new LogEntry("1", "Neuspesna prijava na sistem", "security related", "0", "123.123.123.123", i+"1", cal.getTime(), "1");
			session.insert(loglogin);
		}
		session.insert(logSuccessLogin);
		session.insert(logProfileChange);
		int rulesFired = session.fireAllRules();
		assertEquals(1, rulesFired);
	}
	@Test
	public void test_korisnikKojiNijeBioAsociranSaAlarmimaUProteklih90Dana(){
		KieSession session = kieService.getKieSession();
		Calendar cal = Calendar.getInstance();
		session.setGlobal("userService", userService);
		User admin = new User("1", "a@a.com", "password", true, SecurityStatus.MODERATE, 9, 17);
		
		cal.add(Calendar.DAY_OF_YEAR, -91);
		Date date = cal.getTime();
		
		
		AlarmTriggered alarm = new AlarmTriggered("1","Neuspesni pokusaji prijave na sistem sa istim korisnickim imenom", AlarmTriggered.VISE_OD_2_PRIJAVE_ISTI_KORISNIK, date);
		
		session.insert(alarm);
		session.insert(admin);
		int rulesFired = session.fireAllRules();
		assertEquals(1, rulesFired);
		
		User user = userService.findOne("1");
		assertEquals(user.getStatus(), User.SecurityStatus.LOW);
		//alarm2 triggered 89 days ago
	
	}
	@Test
	public void test_korisnikPovezanSaAlarmomKojiJeIzazvaoVirusUProteklih6Meseci(){
		KieSession session = kieService.getKieSession();
		Calendar cal = Calendar.getInstance();
		session.setGlobal("userService", userService);
		User admin = new User("1", "a@a.com", "password", true, SecurityStatus.LOW, 9, 17);
		
		cal.add(Calendar.DAY_OF_YEAR, -181);
		Date date = cal.getTime();	
		
		AlarmTriggered alarm = new AlarmTriggered("1","Neuspesni pokusaji prijave na sistem sa istim korisnickim imenom", AlarmTriggered.VIRUS, date);
		
		session.insert(alarm);
		session.insert(admin);
		int rulesFired = session.fireAllRules();
		assertEquals(0, rulesFired);
		

		//alarm2 triggered 89 days ago
		cal.add(Calendar.DAY_OF_YEAR, 2);
		Date date2 = cal.getTime();
		AlarmTriggered alarm2 = new AlarmTriggered("1","Neuspesni pokusaji prijave na sistem sa istim korisnickim imenom", AlarmTriggered.VIRUS, date2);
		
		session.insert(alarm2);
		rulesFired = session.fireAllRules();
		assertEquals(1, rulesFired);
		
		User user = userService.findOne("1");
		assertEquals(user.getStatus(), User.SecurityStatus.MODERATE);
		
	}
	@Test
	public void test_KorisnickiNalogJeImaoViseOd15NeuspesnihPokusajaPrijavljivanjaUPoslednjih90dana_MODERATE(){
		KieSession session = kieService.getKieSession();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -90);
		
		session.setGlobal("userService", userService);
		User admin = new User("1", "a@a.com", "password", true, SecurityStatus.LOW, 9, 17);
		session.insert(admin);
		for (int i = 0; i < 16; i++){
			LogEntry log = new LogEntry("1", "Neuspesna prijava na sistem", "security related", "0", "222.222.222.222", "1", cal.getTime(), i+"");
			session.insert(log);
			cal.add(Calendar.DAY_OF_YEAR, 5);
		}
		int rulesFired = session.fireAllRules();
		assertEquals(2, rulesFired);
		User user = userService.findOne("1");
		assertEquals(user.getStatus(), User.SecurityStatus.MODERATE);
	}
	@Test
	public void test_korisnikPovezanSaAlarmomUProteklih30danaIImaAdministratorskePrivilegije_HIGH(){
		KieSession session = kieService.getKieSession();
		Calendar cal = Calendar.getInstance();
		session.setGlobal("userService", userService);
		User admin = new User("1", "a@a.com", "password", true, SecurityStatus.LOW, 9, 17);
		
		cal.add(Calendar.DAY_OF_YEAR, -31);
		Date date = cal.getTime();
		
		
		AlarmTriggered alarm = new AlarmTriggered("1","Neuspesni pokusaji prijave na sistem sa istim korisnickim imenom", AlarmTriggered.VISE_OD_2_PRIJAVE_ISTI_KORISNIK, date);
		
		session.insert(alarm);
		session.insert(admin);
		int rulesFired = session.fireAllRules();
		assertEquals(0, rulesFired);
		
		
		//alarm2 triggered 89 days ago
		cal.add(Calendar.DAY_OF_YEAR, 2);
		Date date2 = cal.getTime();
		AlarmTriggered alarm2 = new AlarmTriggered("1","Neuspesni pokusaji prijave na sistem sa istim korisnickim imenom", AlarmTriggered.VISE_OD_2_PRIJAVE_ISTI_KORISNIK, date2);
		
		session.insert(alarm2);
		rulesFired = session.fireAllRules();
		assertEquals(1, rulesFired);	
		
		User user = userService.findOne("1");
		assertEquals(user.getStatus(), User.SecurityStatus.HIGH);
	}
	@Test
	public void test_korisnikUlogovanVanRadnogVremena_HIGH(){
		KieSession session = kieService.getKieSession();
		Calendar cal = Calendar.getInstance();
		session.setGlobal("userService", userService);
		User admin = new User("1", "a@a.com", "password", true, SecurityStatus.LOW, 9, 17);
		
	
		
		LogEntry log = new LogEntry("1", "Neuspesna prijava na sistem", "security related", "0", "124.142.345.123", "1", cal.getTime(),"1");
		cal.add(Calendar.MINUTE, 3);
		LogEntry log2 = new LogEntry("1", "Neuspesna prijava na sistem", "security related", "0", "124.142.345.123", "1", cal.getTime(),"1");
		cal.add(Calendar.MINUTE, 3);

		LogEntry log3 = new LogEntry("1", "Uspesna prijava na sistem", "security related", "0", "124.142.345.123", "1", cal.getTime(),"1");
		
		session.insert(log);
		session.insert(log2);
		session.insert(log3);
		
		
		session.insert(admin);
		int rulesFired = session.fireAllRules();
		assertEquals(1, rulesFired);	
		
		User user = userService.findOne("1");
		assertEquals(user.getStatus(), User.SecurityStatus.HIGH);
		
	}
	@Test
	public void test_KorisnikSePrijavioSaMaliciozneIpAdrese(){
		KieSession session = kieService.getKieSession();
		session.setGlobal("userService", userService);
		User user = new User("1", "a@a.com", "password", true, SecurityStatus.LOW, 9, 17);

		MaliciousIpAddress m = new MaliciousIpAddress("222.222.222.222");
		LogEntry log = new LogEntry("1", "Uspesna prijava na sistem", "security related", "0", "222.222.222.222", "1", new Date(),"1");
		session.insert(m);
		session.insert(log);
		session.insert(user);
		int rulesFired = session.fireAllRules();
		assertEquals(2, rulesFired);
		
		user = userService.findOne("1");
		assertEquals( User.SecurityStatus.EXTREME, user.getStatus());
	}
	@Test
	public void test_korisnikAsociranSaAlarmomAntivirusaKojiJeIzmenioProfilNakon2NeuspesnePrijaveNaSistem(){
		KieSession session = kieService.getKieSession();
		session.setGlobal("userService", userService);
		User user = new User("1", "a@a.com", "password", true, SecurityStatus.LOW, 9, 17);
		
		AlarmTriggered alarm = new AlarmTriggered("1","Virus", AlarmTriggered.VIRUS, new Date());
		Calendar cal = Calendar.getInstance();
		LogEntry log = new LogEntry("1", "Neuspesna prijava na sistem", "security related", "0", "222.222.222.222", "1", cal.getTime(),"1");
		cal.add(Calendar.MINUTE, 5);
		LogEntry log1 = new LogEntry("1", "Neuspesna prijava na sistem", "security related", "0", "222.222.222.222", "1", cal.getTime(),"1");
		cal.add(Calendar.MINUTE, 5);
		LogEntry log2 = new LogEntry("1", "Uspesna prijava na sistem", "security related", "0", "222.222.222.222", "1", cal.getTime(),"1");
		cal.add(Calendar.MINUTE, 5);
		LogEntry log3 = new LogEntry("1", "Izmena profila", "security related", "0", "222.222.222.222", "1", cal.getTime(),"1");
		
		session.insert(user);
		session.insert(alarm);
		session.insert(log);
		session.insert(log1);
		session.insert(log2);
		session.insert(log3);
		
		int rulesFired = session.fireAllRules();
		assertEquals(2, rulesFired);
		
		user = userService.findOne("1");
		assertEquals( User.SecurityStatus.EXTREME, user.getStatus());
		
	}
	@Test
	public void test_uspesnaPrijavaNaRazliciteDeloveInformacionihSistemaSaRazliciteIpAddrese(){
		KieSession session = kieService.getKieSession();

		Calendar cal = Calendar.getInstance();
		//cal.add(Calendar.DAY_OF_YEAR, -5);
		//15 jednakih intervala po 8 sati (ukupno 5 dana)
		
		for (int i = 0; i < 2; i++){
			Date loginDate = cal.getTime();
			LogEntry log = new LogEntry(i+"1", "Uspesna prijava na sistem", "security related", "0", i+"13.234.212.102", "1", loginDate, "1");
			session.insert(log);
			
		}
		cal.add(Calendar.SECOND, 11);
		LogEntry log = new LogEntry( "5", "Uspesna prijava na sistem", "security related", "0", "13.234.212.103", "1",cal.getTime(), "5");
		session.insert(log);
		int rulesFired = session.fireAllRules();
		assertEquals(0, rulesFired);

		cal.add(Calendar.SECOND, -2);
		LogEntry log2 = new LogEntry("5", "Uspesna prijava na sistem", "security related", "0", "213.234.212.102", "1", cal.getTime(), "1");
		session.insert(log2);
		
		rulesFired = session.fireAllRules();
		assertEquals(1, rulesFired);
	}

}
