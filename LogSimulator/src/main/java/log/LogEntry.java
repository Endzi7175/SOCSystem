package log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogEntry {

    private int informationSystemType;
    private String message;
    private String category;
    private String logLevel;
    private String ipAddress;
    private String userId;
    private Date timestamp;
    private static DateFormat sdf = new SimpleDateFormat("MM-DD hh:mm:ss.SS");
    private String machineId;

    
    
    public LogEntry(int informationSystemType, String message, String category, String logLevel, String ipAddress,
			String username, Date timestamp, String machineId) {
		super();
		this.informationSystemType = informationSystemType;
		this.message = message;
		this.category = category;
		this.logLevel = logLevel;
		this.ipAddress = ipAddress;
		this.userId = username;
		this.timestamp = timestamp;
		this.machineId = machineId;
	}
    public LogEntry(String line) {
    	String[] tokens = line.split("\\|");
        try {
            timestamp = sdf.parse(tokens[0]);
            userId = tokens[1];
            ipAddress = tokens[2];
            //logLevel = tokens[3].charAt(0);
            //category = tokens[4].charAt(0);
            message = tokens[5];
            informationSystemType = Integer.parseInt(tokens[6]);
            machineId = tokens[6];
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



	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getLogLevel() {
		return logLevel;
	}
	public void setLogLevel(String logLevel) {
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
