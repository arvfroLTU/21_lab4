package Sim;

public class HomeAgents extends SimEnt{
	private NetworkAddr Id;
	private Node targetNode;
	private Router homeAgent;
	private Router careOf;

	HomeAgents(NetworkAddr address, Node node, Router H, Router C){
		this.setId(address);
		this.targetNode= node;
		this.setHomeAgent(H);
		this.setCareOf(C);
		
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

	@Override
	public void recv(SimEnt source, Event event) {
		// TODO Auto-generated method stub
		if (event instanceof Message) {
			
			NetworkAddr destination = ((Message) event).destination();
			System.out.println("HA tag");
			send(careOf, new Message(this.getId(), new NetworkAddr(destination.networkId(),destination.nodeId()),0), 0);
		}
		
	}
	
}
