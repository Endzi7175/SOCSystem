package com.sbnz.SIEMCenter2.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;
@Role(Role.Type.EVENT)
@Timestamp("timeStamp")
@Expires("2h30m")
@Entity
public class LogEntry implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	private static final long serialVersionUID = 1L;
	@Column(nullable = false)
	private int informationSystemType;
	@Column(nullable = false)
    private String message;
	@Column(nullable = false)
    private String category;
	@Column(nullable = false)
    private int logLevel;
	@Column(nullable = false)
    private String ipAddress;
	@Column(nullable = false)
    private String userId;
	@Column(nullable = false)
    private Date timestamp;
	
    private static DateFormat sdf = new SimpleDateFormat("MM-DD hh:mm:ss.SS");



	public LogEntry(int informationSystemType, String message, String category, int logLevel, String ipAddress,
			String userId, Date timestamp) {
		super();
		this.informationSystemType = informationSystemType;
		this.message = message;
		this.category = category;
		this.logLevel = logLevel;
		this.ipAddress = ipAddress;
		this.userId = userId;
		this.timestamp = timestamp;
	}

	public LogEntry(String line) {
    	String[] tokens = line.split("\\|");
        try {
            timestamp = sdf.parse(tokens[0]);
            userId = tokens[1];
            ipAddress = tokens[2];
            logLevel = Integer.parseInt(tokens[3]);
            category = tokens[4];
            message = tokens[5];
            informationSystemType = Integer.parseInt(tokens[6]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "LogEntry{" +
                "informationSystemType=" + informationSystemType +
                ", message='" + message + '\'' +
                ", severity=" + category +
                ", logType=" + logLevel +
                ", ipAddress='" + ipAddress + '\'' +
                ", username='" + userId + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

    
	public LogEntry() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getInformationSystemType() {
		return informationSystemType;
	}

	public void setInformationSystemType(int informationSystemType) {
		this.informationSystemType = informationSystemType;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setLogLevel(int logLevel) {
		this.logLevel = logLevel;
	}

	public Date getTimeStamp(){
        return timestamp;
    }

    public String getMessage() {
        return  message;
    }

    public String getCategory() {
        return  category;
    }

    public int getLogLevel() {
        return logLevel;
    }
}
