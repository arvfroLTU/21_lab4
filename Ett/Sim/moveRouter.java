package Sim;

public class moveRouter implements Event {
	private Node sourceNode;
	private Router targetRouter;
	private int reconnectionDelay;
	
	
	moveRouter(Node from, Router to, int delay){
		this.setLeaving(from);
		this.setEntering(to);
		this.reconnectionDelay = delay;
	}


	@Override
	public void entering(SimEnt locale) {
		// TODO Auto-generated method stub
		
	}


	public Node getLeaving() {
		return sourceNode;
	}


	public void setLeaving(Node leaving) {
		this.sourceNode = leaving;
	}


	public Router getEntering() {
		return targetRouter;
	}


	public void setEntering(Router entering) {
		this.targetRouter = entering;
	}


	public int getReconnectionDelay() {
		return reconnectionDelay;
	}


	public void setReconnectionDelay(int reconnectionDelay) {
		this.reconnectionDelay = reconnectionDelay;
	}

}
