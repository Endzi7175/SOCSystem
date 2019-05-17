package com.sbnz.SIEMAgent.Log;

import com.sun.jna.platform.win32.Advapi32Util;
import jdk.jfr.internal.LogLevel;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class LogEntry {

    private int informationSystemType;
    private String message;
    private String category;
    private LOGLevel logLevel;
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
            logLevel = LOGLevel.fromInt(Integer.parseInt(tokens[3]));
            category = tokens[4];
            message = tokens[5];
            informationSystemType = Integer.parseInt(tokens[6]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public LogEntry(Advapi32Util.EventLogRecord record) {
        String ip = null;
        try(final DatagramSocket socket = new DatagramSocket()){
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            ip = socket.getLocalAddress().getHostAddress();
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
        this.informationSystemType = 0; //FILOVATI
        this.ipAddress = ip;
        this.message = String.join("|", record.getStrings());
        this.logLevel = LOGLevel.Security;

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

    public LOGLevel getLogLevel() {
        return logLevel;
    }

    public  static enum  LOGLevel{
        Security, Error, Warrning;

        public int toInt(){
            return Arrays.asList(LogLevel.values()).indexOf(this);
        }
        public static LOGLevel fromInt(int value){
            if(value>=LogLevel.values().length){
                throw new IllegalArgumentException("Unknown value for log lvl");
            }
            return LOGLevel.values()[value];
        }
    }
}
