package com.sbnz.SIEMAgent.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogEntry {

    private int informationSystemType;
    private String message;
    private int category;
    private int logLevel;
    private String ipAddress;
    private String userId;
    private Date timestamp;
    private static DateFormat sdf = new SimpleDateFormat("MM-DD hh:mm:ss.SS");



	public LogEntry(String line) {
    	String[] tokens = line.split("\\|");
        try {
            timestamp = sdf.parse(tokens[0]);
            userId = tokens[1];
            ipAddress = tokens[2];
            logLevel = tokens[3].charAt(0);
            category = tokens[4].charAt(0);
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


	public Date getTimeStamp(){
        return timestamp;
    }
}
