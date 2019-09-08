package com.sbnz.SIEMAgent.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;



public class LogEntry {

    private int informationSystemType;
    private String message;
    private String category;
    private int logLevel;
    private String ipAddress;
    private String userId;
    private Date timestamp;
    private static DateFormat sdf = new SimpleDateFormat("MM-DD hh:mm:ss.SS");
	private String machineId;

    public int getInformationSystemType() {
        return informationSystemType;
    }

    public void setInformationSystemType(int informationSystemType) {
        this.informationSystemType = informationSystemType;
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

    public static DateFormat getSdf() {
        return sdf;
    }

    public static void setSdf(DateFormat sdf) {
        LogEntry.sdf = sdf;
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
            machineId = tokens[7];
            
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


	public String getMachineId() {
		return machineId;
	}

	public void setMachineId(String machineId) {
		this.machineId = machineId;
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
