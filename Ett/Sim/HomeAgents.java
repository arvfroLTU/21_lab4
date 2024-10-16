package Sim;

public class HomeAgents {
	private NetworkAddr Id;
	private Node b;
	private int homeAgent;
	private int careOf;

	HomeAgents(NetworkAddr address, Node node, int H, int C){
		this.setId(address);
		this.b= node;
		this.setHomeAgent(H);
		this.setCareOf(C);
		
	}

	public NetworkAddr getId() {
		return Id;
	}

	public void setId(NetworkAddr id) {
		Id = id;
	}

	public int getHomeAgent() {
		return homeAgent;
	}

	public void setHomeAgent(int homeAgent) {
		this.homeAgent = homeAgent;
	}

	public int getCareOf() {
		return careOf;
	}

	public void setCareOf(int careOf) {
		this.careOf = careOf;
	}
	
}
