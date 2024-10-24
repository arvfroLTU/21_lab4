package Sim;

import java.util.ArrayList;

public class HomeAgents extends SimEnt{
	private NetworkAddr Id;
	private Node targetNode;
	private Router homeAgent;
	private Router careOf;
	private int targetNodeNetworkId;
	private boolean sendOk = true;
	private double waitFor =0;
	private int bffr;
	private ArrayList<SimEnt> simEntBuffer = new ArrayList<>();
	private ArrayList<Event> eventBuffer =new ArrayList<>();

	HomeAgents(NetworkAddr address, Node node, Router H, Router C, ArrayList<HomeAgents> HAList, int buffer){
		this.setId(address);
		this.setTargetNode(node);
		this.setHomeAgent(H);
		this.setCareOf(C);
		this.setBffr(buffer);
		HAList.add(this);
		this.setTargetNodeNetworkId(targetNode.getAddr().nodeId());
		
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
		
		if(event instanceof Release) {
			for(int i= 0; i< eventBuffer.size(); i =0) {
			SimEnt src= simEntBuffer.get(i);
			Event ev = eventBuffer.get(i);
			
			if (ev instanceof Message) {
				
				NetworkAddr destination = ((Message) ev).destination();
				int seq=((Message)ev).seq();
				System.out.println("From buffer");
				send(careOf, new Message(this.getId(), new NetworkAddr(destination.networkId(),destination.nodeId()),seq), 0);
			}
			simEntBuffer.remove(i);
			eventBuffer.remove(i);
				
			}
			this.sendOk =  true;
		}
		// TODO Auto-generated method stub
		if (this.sendOk  ==false) {
			simEntBuffer.add(source);
			eventBuffer.add(event);
			System.out.println("MN not yet connected, message added to buffer");
			
			
		}else {
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

	public boolean isSendOk() {
		return sendOk;
	}

	public void setSendOk(boolean sendOk) {
		this.sendOk = sendOk;
	}

	public double getWaitFor() {
		return waitFor;
	}

	public void setWaitFor(double waitFor) {
		this.waitFor = waitFor;
	}

	public int getBffr() {
		return bffr;
	}

	public void setBffr(int bffr) {
		this.bffr = bffr;
	}


	public int getTargetNodeNetworkId() {
		return targetNodeNetworkId;
	}

	public void setTargetNodeNetworkId(int targetNodeNetworkId) {
		this.targetNodeNetworkId = targetNodeNetworkId;
	}

	
}
