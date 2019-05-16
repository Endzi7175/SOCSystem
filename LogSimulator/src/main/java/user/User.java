package user;

import log.ActionFactory;
import log.LogEntry;
import log.Logger;

public class User {
	private String username;
	private UserType userType;
	private UserState state;
	public void doActions(){
		String fileName = userType == UserType.NORMAL_USER ? "normal.log" : "attack.log";
		while(true){
			LogEntry log = ActionFactory.getNextAction(this);
			Logger.write(log, fileName);
			if (this.state == UserState.LOGOUT){
				System.out.println(username + " se izlogovao.");
				break;
			}
		}
	}
	
	
	public String getUsername() {
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


	public User(String username, UserType userType, UserState state) {
		super();
		this.username = username;
		this.userType = userType;
		this.state = state;
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
