package simulator;
import user.User;
import user.User.UserState;
import user.User.UserType;
import utils.Util;
public class NormalState extends State{
	private static int NUMBER_OF_USERS = 4;
	private Thread[] users;
	@Override
	public void createLogs() {
		users = new Thread[NUMBER_OF_USERS];
		for (int i = 0; i < NUMBER_OF_USERS; i++){
			users[i] = new Thread(){
				public void run(){
					while(!interrupted()){
						while(true){
							User user = new User(Util.generateRandomUser(), UserType.NORMAL_USER, UserState.UNAUTHORIZED);
							user.doActions();
						}
					}
				}
			};
			users[i].start();
		}
		
	}
	


	@Override
	public void endState() {
		for (Thread t : users){
			t.stop();
		}
		
	}
	
}
