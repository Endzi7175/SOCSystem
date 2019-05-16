package utils;

import java.util.Random;

public class Util {
	public static String generateRandomIpAddress(){
		Random r = new Random();
		return r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256);
	}
	
	public static String generateRandomUser(){
		Random r = new Random();
		return "User" + r.nextInt(999);
	}
	public static int generateInformationSystemCode(){
		Random r = new Random();
		return r.nextInt(3);
	}
}
