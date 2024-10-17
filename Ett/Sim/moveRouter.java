package Sim;

public class moveRouter implements Event {
	private NetworkAddr sourceNode;
	private NetworkAddr targetRouter;
	
	
	moveRouter(NetworkAddr from, NetworkAddr to){
		this.setLeaving(sourceNode);
		this.setEntering(to);
	}


	@Override
	public void entering(SimEnt locale) {
		// TODO Auto-generated method stub
		
	}


	public NetworkAddr getLeaving() {
		return sourceNode;
	}


	public void setLeaving(NetworkAddr leaving) {
		this.sourceNode = leaving;
	}


	public NetworkAddr getEntering() {
		return targetRouter;
	}


	public void setEntering(NetworkAddr entering) {
		this.targetRouter = entering;
	}

}
