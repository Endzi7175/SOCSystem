package user;

import java.util.Date;
import java.util.Random;

import log.ActionFactory;
import log.LogEntry;
import log.Logger;

public class User {
	private String username;
	private UserType userType;
	private UserState state;
	private String ipAddress;
	private static Random r = new Random();
	public void doActions(){
		String fileName = userType == UserType.NORMAL_USER ? "normal.log" : "attack.log";
		if (this.userType == UserType.NORMAL_USER){
		while(true){
			LogEntry log = ActionFactory.getNextAction(this);
			Logger.write(log, fileName);
			if (this.state == UserState.LOGOUT){
				System.out.println(username + " se izlogovao.");
				break;
			}
		}
		}else{
			int x = r.nextInt(4) + 1;
			switch(x){
			case 1 :
				performFrequentAttacks(fileName);
				break;
			case 2 :
				performSuccessLoginWithProfileChangeAfter5UnsuccessfulLogins(fileName);
				break;
			case 3 :
				performDosAttack(fileName);
				break;
			case 4 :
				perfromErrorInApplication(fileName);
				break;
			}


		}
	}
	
	public void performSuccessLoginWithProfileChangeAfter5UnsuccessfulLogins(String fileName){
		for (int i = 0; i < 6; i++){
			LogEntry l = new LogEntry(1, "Neuspesna prijava na sistem", "1", "0", this.ipAddress, this.username, new Date(), "1");
			Logger.write(l, fileName);
			try {
				Thread.sleep(1000 * (r.nextInt(5) + 1));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		LogEntry l = new LogEntry(1, "Uspesna prijava na sistem", "1", "0", this.ipAddress, this.username, new Date(), "1");
		Logger.write(l, fileName);
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		l = new LogEntry(1, "Izmena profila", "1", "0", this.ipAddress, this.username, new Date(), "1");
		Logger.write(l, fileName);
	}
	public void performFrequentAttacks(String fileName){
		for (int i = 0; i < 50; i++){
			int x = r.nextInt(3) + 1;
			LogEntry l = null;
			switch(x){
			case 1 :
				l = new LogEntry(1, "poruka", "1", "0", this.ipAddress, this.username, new Date(), "1");
				Logger.write(l, fileName);
				break;
			case 2 :
				l = new LogEntry(1,"Payment", "payment", "0", this.ipAddress, this.username, new Date(), "1");
				Logger.write(l, fileName);

				break;
			case 3:
				l = new LogEntry(1,"Neuspesna prijava na sistem", "security related", "0", this.ipAddress, this.username, new Date(), "1");

			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void performDosAttack(String fileName){
		for (int i = 0; i < 50; i++){
			LogEntry l = new LogEntry(1, "poruka", "1", "0", this.ipAddress, this.username, new Date(), "1");
			Logger.write(l, fileName);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public void perfromErrorInApplication(String fileName){
		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		LogEntry l = new LogEntry(1, "Greska u aplikaciji", "1", "1", this.ipAddress, this.username, new Date(), "1");
		Logger.write(l, fileName);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	public String getUsername(){
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public UserType getUserType() {
		return userType;
	}


	public void setUserType(UserType userType) {
		this.userType = userType;
	}


	public UserState getState() {
		return state;
	}


	public void setState(UserState state) {
		this.state = state;
	}

	
	public String getIpAddress() {
		return ipAddress;
	}


	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}


	public User(String username, UserType userType, UserState state, String ipAddress) {
		super();
		this.username = username;
		this.userType = userType;
		this.state = state;
		this.ipAddress = ipAddress;
	}


	public enum UserType{
		NORMAL_USER,
		HACKER
	}
	public enum UserState{
		UNAUTHORIZED,
		AUTHORIZED,
		LOGOUT
	}
}
