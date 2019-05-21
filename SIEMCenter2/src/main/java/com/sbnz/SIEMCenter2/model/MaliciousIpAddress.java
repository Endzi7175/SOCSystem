package com.sbnz.SIEMCenter2.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class MaliciousIpAddress {
	@Column(nullable = false)
	private String ip;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;

	public MaliciousIpAddress() {
		super();
	}

	public MaliciousIpAddress(String ip) {
		super();
		this.ip = ip;
		
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
