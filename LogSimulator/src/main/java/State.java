import java.util.Random;

public abstract class State {
	
	
	public abstract void createLogs(Logger logger);
	
	
	protected static String generateRandomIpAddress(){
		Random r = new Random();
		return r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256);
	}
	
	protected static String generateRandomUser(){
		Random r = new Random();
		return "User" + r.nextInt(999);
	}
	protected static int generateInformationSystemCode(){
		Random r = new Random();
		return r.nextInt(3);
	}

}
