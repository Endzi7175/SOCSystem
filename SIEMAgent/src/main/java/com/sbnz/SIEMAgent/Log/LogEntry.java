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
    private String username;
    private Date timestamp;
    private static DateFormat sdf = new SimpleDateFormat("MM-DD hh:mm:ss.SS");

    public LogEntry(String line) {
    	String[] tokens = line.split("\\|");
        try {
            timestamp = sdf.parse(tokens[0]);
            username = tokens[1];
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
                ", username='" + username + '\'' +
                ", timestamp=" + timestamp +
                '}';
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
