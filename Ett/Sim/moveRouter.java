package Sim;

public class moveRouter implements Event {
	private Node sourceNode;
	private Router targetRouter;
	
	
	moveRouter(Node from, Router to){
		this.setLeaving(from);
		this.setEntering(to);
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

}
