package Sim;

import java.util.ArrayList;

public class HomeAgents extends SimEnt{
	private NetworkAddr Id;
	private Node targetNode;
	private Router homeAgent;
	private Router careOf;

	HomeAgents(NetworkAddr address, Node node, Router H, Router C, ArrayList<HomeAgents> HAList){
		this.setId(address);
		this.setTargetNode(node);
		this.setHomeAgent(H);
		this.setCareOf(C);
		HAList.add(this);
		
	}

	public NetworkAddr getId() {
		return Id;
	}

	public void setId(NetworkAddr id) {
		Id = id;
	}

	public Router getHomeAgent() {
		return homeAgent;
	}

	public void setHomeAgent(Router homeAgent) {
		this.homeAgent = homeAgent;
	}

	public Router getCareOf() {
		return careOf;
	}

	public void setCareOf( Router CareOf) {
		this.careOf = CareOf;
	}
	
	public Node getTargetNode() {
		return targetNode;
	}

	public void setTargetNode(Node targetNode) {
		this.targetNode = targetNode;
	}

	@Override
	public void recv(SimEnt source, Event event) {
		// TODO Auto-generated method stub
		if (event instanceof Message) {
			
			NetworkAddr destination = ((Message) event).destination();
			int seq=((Message)event).seq();
			System.out.println("HA tag");
			send(careOf, new Message(this.getId(), new NetworkAddr(destination.networkId(),destination.nodeId()),seq), 0);
		}
		
		if (event instanceof bindingUpdate) {
			this.setCareOf(((bindingUpdate)event).getNewCareOf());
			send(careOf, new bindingAck(true),0); 
			System.out.println("Sent binding Acknowledgement");
		}
	}

	
}
