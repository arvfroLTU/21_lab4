package Sim;

public class bindingAck implements Event {
	private boolean Acknowledged;

	bindingAck(boolean maybe){
		this.Acknowledged = maybe;
		
	}

	@Override
	public void entering(SimEnt locale) {
		// TODO Auto-generated method stub
		
	}
}
