package com.sbnz.SIEMCenter2.model;

import javax.persistence.Column;
import javax.persistence.Entity;

//@Entity
public class XssModel {
	@Column(nullable=false)
	String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public XssModel(String message) {
		super();
		this.message = message;
	}

	public XssModel() {
		super();
	}
	
}
