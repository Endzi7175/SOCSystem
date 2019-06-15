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
	@Column(nullable = false)
	private int type;

	
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
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public AlarmTriggered() {
		super();
	}

	public AlarmTriggered(String customerId, String message, int type) {
		super();
		this.customerId = customerId;
		this.message = message;
		this.type = type;
	}
	
	public static int VISE_OD_2_PRIJAVE_ISTI_KORISNIK = 0;
	public static int VISE_OD_4_PRIJAVE_ISTA_MASINA = 1;
	public static int POKUSAJ_PRIJAVE_NEAKTIVAN_KORISNIK = 2;
	public static int ERROR = 3;
	public static int VIRUS = 4;
	public static int VISE_OD_15_PRIJAVA_RAZLICITI_SISTEMI = 5;
	public static int POJAVLJIVANJE_VIRUSA = 6;
	public static int DOS_NAPAD = 7;
	public static int PAYMENT_NAPAD = 8;
	public static int BRUTEFORCE_NAPAD = 9;
	
}
