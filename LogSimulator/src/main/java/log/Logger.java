package log;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Logger {
	private PrintWriter writer;
	public static String NORMAL_LOGS = "normal.log";
	public static String ATTACK_LOGS = "attack.log";
 	public static void write(String message, String username, char logType, char severity, String ipAddress, int informationSystem, String fileName){
		DateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm:ss.SS");
		Date date = new Date();
		String timeStamp = dateFormat.format(date);
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		pw.append(timeStamp + "|" + username + "|" + ipAddress + "|" + logType + "|" + severity + "|" + message + "|" + informationSystem + "\n");
		System.out.print(timeStamp + "|" + username + "|" + ipAddress + "|" + logType + "|" + severity + "|" + message + "|" + informationSystem + "\n");
		pw.flush();
		pw.close();
		
	}
	public PrintWriter getWriter() {
		return writer;
	}
	public void setWriter(PrintWriter writer) {
		this.writer = writer;
	}
	public static void write(LogEntry log, String fileName) {
		DateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm:ss.SS");
		Date date = new Date();
		String timeStamp = dateFormat.format(date);
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(fileName, true));
		} catch (IOException e) {
			e.printStackTrace();
		}
		pw.append(timeStamp + "|" + log.getUserId() + "|" + log.getIpAddress() + "|" + log.getLogLevel() + "|" + log.getCategory() + "|" + log.getMessage() + "|" + log.getInformationSystemType() + "\n");
		System.out.print(timeStamp + "|" + log.getUserId() + "|" + log.getIpAddress() + "|" + log.getLogLevel() + "|" + log.getCategory() + "|" + log.getMessage() + "|" + log.getInformationSystemType() + "\n");
		pw.flush();
		pw.close();
	}
	
	
}
