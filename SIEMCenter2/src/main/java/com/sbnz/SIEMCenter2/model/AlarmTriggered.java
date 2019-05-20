package com.sbnz.SIEMCenter2.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AlarmTriggered {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	@Column(nullable = false)
	private String customerId;
	@Column(nullable = false)
	private String message;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	public AlarmTriggered() {
		super();
	}

	public AlarmTriggered(String customerId, String message) {
		super();
		this.customerId = customerId;
		this.message = message;
	}
	
}
