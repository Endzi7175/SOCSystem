package log;
import java.util.Date;
import java.util.Random;

import user.User;
import user.User.UserType;
import user.User.UserState;
public class ActionFactory {
	private static Random random = new Random();
	public static LogEntry getNextAction(User user) {
		if (user.getUserType().equals(UserType.NORMAL_USER)){
			switch(user.getState()){
				case UNAUTHORIZED:
					int delay = random.nextInt(3000) + 1000;
					try {
						Thread.sleep(delay);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					int loginChance = random.nextInt(5);
					if (loginChance == 0){
						return new LogEntry(0, "Neuspesna prijava na sistem", "0", "0", user.getIpAddress(), user.getUsername(), new Date(), "1");
					}else{
						user.setState(UserState.AUTHORIZED);
						return new LogEntry(0, "Uspesna prijava", "0", "0", user.getIpAddress(), user.getUsername(), new Date(), "1");
					}
				case AUTHORIZED:
					int nextAction = random.nextInt(5);
					switch (nextAction){
						case 0:
							try {
								Thread.sleep(random.nextInt(5000) + 1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							return new LogEntry(0, "Izmena profila", "0", "0", user.getIpAddress(), user.getUsername(), new Date(), "1");
						case 1:
							try {
								Thread.sleep(random.nextInt(7000) + 1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							return new LogEntry(0, "Kupovina", "0", "0", user.getIpAddress(), user.getUsername(), new Date(), "1");
						case 2:
							try {
								Thread.sleep(random.nextInt(6000) + 1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							return new LogEntry(0, "Slanje poruke", "0", "1", user.getIpAddress(), user.getUsername(), new Date(), "1");
						case 3:
							try {
								Thread.sleep(random.nextInt(3000) + 1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							return new LogEntry(0, "Pristup nedozvoljenom fajlu", "0", "0", user.getIpAddress(), user.getUsername(), new Date(), "1");
						case 4:
							try {
								Thread.sleep(random.nextInt(5000) + 1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							user.setState(UserState.LOGOUT);
							return new LogEntry(0, "Odjava sa sistema", "0", "0", user.getIpAddress(), user.getUsername(), new Date(), "1");

							
					}
				case LOGOUT :
					return null;
			}
		}else{ //HAKER
			switch(user.getState()){
			 	case UNAUTHORIZED :
			 		//veci delay, haker hoce da sakrije napad
			 		int delay = random.nextInt(10000) + 1000;
					try {
						Thread.sleep(delay);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					//1 - 30 login sansa
					int loginChance = random.nextInt(50);
					if (loginChance > 0){
						return new LogEntry(0, "Neuspesna prijava na sistem", "0", "0", user.getIpAddress(), user.getUsername(), new Date(), "1");
					}else{
						user.setState(UserState.AUTHORIZED);
						return new LogEntry(0, "Uspesna prijava na sistem", "0", "0", user.getIpAddress(), user.getUsername(), new Date(), "1");
					}
			 	case  AUTHORIZED:
			 		int nextAction = random.nextInt(3);
					switch (nextAction){
						case 0:
							try {
								Thread.sleep(random.nextInt(5000) + 1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							return new LogEntry(0, "Izmena profila", "0", "0", user.getIpAddress(), user.getUsername(), new Date(), "1");
						case 1:
							try {
								Thread.sleep(random.nextInt(7000) + 1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							return new LogEntry(0, "Kupovina", "0", "0", user.getIpAddress(), user.getUsername(), new Date(), "1");
						
						case 2:
							try {
								Thread.sleep(random.nextInt(3000) + 1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							return new LogEntry(0, "Pristup nedozvoljenom fajlu", "0", "0", user.getIpAddress(), user.getUsername(), new Date(), "1");
						
					}
			}
		}
		return null;
		
	}

}
