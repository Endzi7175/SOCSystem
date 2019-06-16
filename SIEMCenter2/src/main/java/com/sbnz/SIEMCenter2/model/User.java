package com.sbnz.SIEMCenter2.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private String id;
	@Column(nullable = false)
	private String email;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private boolean isAdmin;
	@Column(nullable = false)
	private SecurityStatus status;
	@Column
	private int wokringHoursStart;
	@Column
	private int workingHoursEnd;
	
	
	
	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public boolean getIsAdmin() {
		return isAdmin;
	}



	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}



	public SecurityStatus getStatus() {
		return status;
	}



	public void setStatus(SecurityStatus status) {
		this.status = status;
	}



	public int getWokringHoursStart() {
		return wokringHoursStart;
	}



	public void setWokringHoursStart(int wokringHoursStart) {
		this.wokringHoursStart = wokringHoursStart;
	}



	public int getWorkingHoursEnd() {
		return workingHoursEnd;
	}



	public void setWorkingHoursEnd(int workingHoursEnd) {
		this.workingHoursEnd = workingHoursEnd;
	}



	public User() {
		super();
	}



	public User(String id, String email, String password, boolean isAdmin, SecurityStatus status, int wokringHoursStart,
			int workingHoursEnd) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.isAdmin = isAdmin;
		this.status = status;
		this.wokringHoursStart = wokringHoursStart;
		this.workingHoursEnd = workingHoursEnd;
	}



	public enum SecurityStatus{
		LOW, MODERATE, HIGH, EXTREME
	}
}
