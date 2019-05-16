package simulator;
import java.util.Scanner;

public class Simulator {
	State state;
	public void changeState(State s){
		this.state = s;
	}
	

	public State getState() {
		return state;
	}


	public void setState(State state) {
		this.state = state;
	}


	public static void main(String[] args){
		Scanner keyboard = new Scanner(System.in);
		Simulator sim = new Simulator();
		int stateCode = 1;
		do{
			System.out.println("Choose state: \n1 - Normal state\n2 - Attack state\n9 - exit");
			stateCode = keyboard.nextInt();
			if (stateCode == 1){
				if (sim.getState()!=null){
					sim.getState().endState();
				}
				sim.changeState(new NormalState());
				sim.getState().createLogs();

			}else if(stateCode == 2){
				if (sim.getState()!=null){
					sim.getState().endState();
				}
				sim.changeState(new AttackState());
				sim.getState().createLogs();
			}else{
				System.out.println("Invalid option!");
			}
		}while(stateCode != 9);
		keyboard.close();
	}
}
