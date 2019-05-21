package log;

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

    
    
    public LogEntry(int informationSystemType, String message, int category, int logLevel, String ipAddress,
			String username, Date timestamp) {
		super();
		this.informationSystemType = informationSystemType;
		this.message = message;
		this.category = category;
		this.logLevel = logLevel;
		this.ipAddress = ipAddress;
		this.userId = username;
		this.timestamp = timestamp;
	}
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

    public int getInformationSystemType() {
		return informationSystemType;
	}

	public void setInformationSystemType(int informationSystemType) {
		this.informationSystemType = informationSystemType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public int getLogLevel() {
		return logLevel;
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

    public Date getTimeStamp(){
        return timestamp;
    }
}