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
	
	public void setFile(String fileName){
		try {
			File file = new File(fileName);
			Set<PosixFilePermission> perms = new HashSet<PosixFilePermission>();
	        //add owners permission
	        perms.add(PosixFilePermission.OWNER_READ);
	        perms.add(PosixFilePermission.OWNER_WRITE);
	        //perms.add(PosixFilePermission.OWNER_EXECUTE);
			if (!file.exists()){
				Files.createFile(Paths.get(fileName));
			}
			Files.setPosixFilePermissions(Paths.get(fileName), perms);
			this.writer = new PrintWriter(new FileWriter(fileName, true));
		} catch (IOException e) {
			System.out.println("Greska prilikom otvaranja fajla.");
		}
	}
	public void write(String message, String username, char logType, String category, String ipAddress, int informationSystem){
		DateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm:ss.SS");
		Date date = new Date();
		String timeStamp = dateFormat.format(date);
		writer.append(timeStamp + "|" + username + "|" + ipAddress + "|" + logType + "|" + category + "|" + message + "|" + informationSystem + "\n");
		writer.flush();
		
	}
	public PrintWriter getWriter() {
		return writer;
	}
	public void setWriter(PrintWriter writer) {
		this.writer = writer;
	}
	
	
}
