package com.sbnz.SIEMCenter2.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

@Entity
@Timestamp("dateTriggered")
@Role(Role.Type.EVENT)
public class AlarmTriggered {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;

	@Column(nullable = false)
	private String message;
	@Column(nullable = false)
	private int type;
	@Column
	private String userId;
	@Column
	private String machineId;
	@Column
	private String ip;
	@Column(nullable = false)
	private Date dateTriggered;
	

	
	public Date getDateTriggered() {
		return dateTriggered;
	}

	public void setDateTriggered(Date dateTriggered) {
		this.dateTriggered = dateTriggered;
	}

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


	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public AlarmTriggered() {
		super();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMachineId() {
		return machineId;
	}

	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public AlarmTriggered(String userId, String message, int type, Date date) {
		super();
		this.userId = userId;
		this.message = message;
		this.type = type;
		this.dateTriggered = date;
		//this.machineId = machineId;
		//this.ip = ip;
	}
	public AlarmTriggered(String userId,String machineId, String ip, String message, int type, Date date) {
		super();
		this.userId = userId;
		this.message = message;
		this.type = type;
		this.machineId = machineId;
		this.ip = ip;
		this.dateTriggered = date;
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
