import java.util.Random;

public class AttackState extends State{
	private static Random rnd = new Random();
	@Override
	public void createLogs(Logger logger) {

		System.out.println("Generating logs in attack state...");
		//unsuccessfulAuthWithDifferentIpWithin10sec(logger);
		//unsuccessfulAuthWithin5days(logger);
		//generateErrorLog(logger);
		AttackThread t = new AttackThread(logger);
		t.start();
		AttackThread2 t2 = new AttackThread2(logger);
		t2.start();
		AttackThread3 t3 = new AttackThread3(logger);
		t3.start();
		
		
	}
	private static void unsuccessfulAuthWithin5days(Logger logger){
		String ipAddress = generateRandomIpAddress();
		String user = generateRandomUser();
		for (int i = 0; i < 20; i++){
			logger.write("Neuspesna prijava", user, 'S', 'H', ipAddress, generateInformationSystemCode());
			try {
				logger.getWriter().flush();
				Thread.sleep(rnd.nextInt(3) + 1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
	}
	private static void unsuccessfulAuthWithDifferentIpWithin10sec(Logger logger){
		String user = generateRandomUser();
		for (int i = 0; i < 15; i++){
			logger.write("Neuspesna prijava", user, 'S', 'H', generateRandomIpAddress(), generateInformationSystemCode());
			logger.getWriter().flush();
			try {
				Thread.sleep(rnd.nextInt(3) + 1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private static void generateErrorLog(Logger logger){
		logger.write("Greska u programu", generateRandomUser(), 'E', 'H', generateRandomIpAddress(), generateInformationSystemCode());
		logger.getWriter().flush();
		try {
			Thread.sleep(rnd.nextInt(10) + 1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.write("Greska u programu", generateRandomUser(), 'E', 'H', generateRandomIpAddress(), generateInformationSystemCode());
		logger.getWriter().flush();
		try {
			Thread.sleep(rnd.nextInt(10) + 1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.write("Greska u programu", generateRandomUser(), 'E', 'H', generateRandomIpAddress(), generateInformationSystemCode());
		
	}
	private class AttackThread extends Thread{
		private Logger logger;
		public AttackThread(Logger logger){
			this.logger = logger;
		}
		public void run(){
			unsuccessfulAuthWithin5days(this.logger);
			
		}
	}
	private class AttackThread2 extends Thread{
		private Logger logger;
		public AttackThread2(Logger logger){
			this.logger = logger;
		}
		public void run(){
			unsuccessfulAuthWithDifferentIpWithin10sec(this.logger);
			
		}
	}
	private class AttackThread3 extends Thread{
		private Logger logger;
		public AttackThread3(Logger logger){
			this.logger = logger;
		}
		public void run(){
			generateErrorLog(this.logger);
			
		}
	}
	
	
}
