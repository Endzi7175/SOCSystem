package simulator;

import user.User;
import user.User.UserState;
import user.User.UserType;
import utils.Util;

public class AttackState extends State{
	private static int NUMBER_OF_HACKERS = 4;
	private Thread[] hackers;
	@Override
	public void createLogs() {
		hackers = new Thread[NUMBER_OF_HACKERS];
		for (int i = 0; i < NUMBER_OF_HACKERS; i++){
			hackers[i] = new Thread(){
				public void run(){
					while(!interrupted()){
						while(true){
							User user = new User(Util.generateRandomUser(), UserType.HACKER, UserState.UNAUTHORIZED);
							user.doActions();
						}
					}
				}
			};
			hackers[i].start();
		}
		
	}
	


	@Override
	public void endState() {
		for (Thread t : hackers){
			t.stop();
		}
		
	}
	
}
