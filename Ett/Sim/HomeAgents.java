package Sim;

public class HomeAgents {
	private NetworkAddr Id;
	private Node _node;
	private int homeAgent;
	private int careOf;

	HomeAgents(NetworkAddr address, Node node, int H, int C){
		this.setId(address);
		this.set_node(node);
		this.setHomeAgent(H);
		this.setCareOf(C);
		
	}

		
	public void Update(int currentRouterId) {
		this.careOf = currentRouterId;
	
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


	public Node get_node() {
		return _node;
	}


	public void set_node(Node _node) {
		this._node = _node;
	}
	
}
