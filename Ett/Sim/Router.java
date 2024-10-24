package Sim;

import java.util.ArrayList;

// This class implements a simple router

public class Router extends SimEnt{

	private RouteTableEntry [] _routingTable;
	private int _interfaces;
	private int _now=0;
	public int routerId;
	private ArrayList<HomeAgents> careOf;
	
	
	//public void setHomeAgentRouter(Node node, int NA, Router
	//targetRouter, int buffer) {
	// HA mounting variables
	
	private Node mountHATargetNode;
	private int mountHANetworkAddr;
	private Router mountHATargetRouter;
	private int mountHABuffer;
	

	// When created, number of interfaces are defined
	
 	Router(int interfaces, int id, ArrayList<HomeAgents> network)
	{
		_routingTable = new RouteTableEntry[interfaces];
		_interfaces=interfaces;
		routerId = id;
		careOf =network;
	}
	
 	
	// This method connects links to the router and also informs the 
	// router of the host connects to the other end of the link
	public void connectInterface(int interfaceNumber, SimEnt link, SimEnt node)
	{
		if (interfaceNumber<_interfaces)
		{
			_routingTable[interfaceNumber] = new RouteTableEntry(link, node);
			
			
			//REMOVE MEANT TO BE REPLACED WITH ROUTER SOLICITATION
			if(node.getClass()==Node.class) {
				((Node) node).setCurrentRouter(this);
			}
		}
		else
			System.out.println("Trying to connect to port not in router");
		
		((Link) link).setConnector(this);
	}

	// This method searches for an entry in the routing table that matches
	// the network number in the destination field of a messages. The link
	// represents that network number is returned
	
	public void moveInterface(NetworkAddr oldConnection, int newConnection) {
		
		System.out.println("started moving");
		if(_routingTable[newConnection] == null) {
			
			System.out.println("if true");
			int oldId = oldConnection.networkId();	
			
			RouteTableEntry tempObjectStorage =_routingTable[oldId];
			
			_routingTable[oldId] = null;
			
			_routingTable[newConnection] = tempObjectStorage;
			    }
		else {
			System.out.println("Spot's taken!");
		}
	}
	
	public void setHomeAgentRouter(Node node, int NA, Router targetRouter, int buffer) {
		//HomeAgents Entry = new HomeAgents(new NetworkAddr(NA,NA), node,this, targetRouter, careOf, buffer);
		this.mountHABuffer=buffer;
		this.mountHANetworkAddr= NA;
		this.mountHATargetNode=node;
		this.mountHATargetRouter= targetRouter;
	}
	
	public void publishRouting() {
		System.out.println("--------------------------");
		
		for (int i =0; i <_routingTable.length; i++) {		
			System.out.println(_routingTable[i]);
		    }
		
		System.out.println("--------------------------");
	}
	
	private SimEnt getInterface(int networkAddress)
	{
		SimEnt routerInterface=null;
		for(int i=0; i<_interfaces; i++)
			if (_routingTable[i] != null && (_routingTable[i].node().getClass() == Node.class)){
				
				if (((Node) _routingTable[i].node()).getAddr().networkId() == networkAddress)
				{
					routerInterface = _routingTable[i].link();
				}
				
			}
		if (routerInterface ==null) {
			for(int j= 0; j< careOf.size();j++) {
				if (careOf.get(j).getTargetNode().getAddr().networkId() ==networkAddress) {
					routerInterface = careOf.get(j);
					
				}
				
			}
			
		}
				
		return routerInterface;
	}
	
	public int getInterfaceNumber(int networkAddress)
	{
		SimEnt routerInterface=null;
		for(int i=0; i<_interfaces; i++)
			if (_routingTable[i] != null)
			{
				if (((Node) _routingTable[i].node()).getAddr().networkId() == networkAddress)
				{
					routerInterface = _routingTable[i].link();
				}
				return i;
			}
		return 404;
	}
	
	
	public void clearInterfaceEntry(NetworkAddr targetNode) {
		
		for(int i=0; i<_interfaces; i++)
			if (_routingTable[i] != null && _routingTable[i].node().getClass() == Node.class )
			{
				if (((Node) _routingTable[i].node()).getAddr() == targetNode)
				{
					 _routingTable[i] = null;
					 System.out.println("interface " + i + " cleared");
				}
			}
	}
	
	public RouteTableEntry[] getRoutingTable() {
		return _routingTable;
	}
	
	
	public void routerAdvertisement() {
		for (int i =0; i <_routingTable.length; i++) {
			if (_routingTable[i] !=null){
				SimEnt currentObj = _routingTable[i].node();
			send(currentObj, new routerAdvertisement(routerId), (double) _now);
			
			}
		    }
	}
	
	// When messages are received at the router this method is called
	
	public void recv(SimEnt source, Event ev) {
	
	SimEnt maybeHANode;
	int maybeHANodeId = 8008135;
	
	{	
		if (ev instanceof Message)
		{
			
		
			SimEnt sendNext = getInterface(((Message) ev).destination().networkId());
			if( sendNext instanceof HomeAgents) {
			 maybeHANode = ((HomeAgents) sendNext).getTargetNode();
				 maybeHANodeId = ((Node) maybeHANode).getAddr().nodeId();
			}
	
			//identifies if node has moved routers, and if  a buffer is still needed, depletes
			//amount of packets left to buffer. simulates disconnection.
			
			
			for(int j=0; j <careOf.size(); j++) {
				int q= careOf.get(j).getTargetNode().getAddr().nodeId();
				if (maybeHANodeId == careOf.get(j).getTargetNode().getAddr().nodeId()) {
					HomeAgents concernedHA =  careOf.get(j);
					
					if (concernedHA.getBffr() >0) {
						concernedHA.setBffr(concernedHA.getBffr()-1);
						if (concernedHA.getBffr() == 0);
						send(sendNext, new Release(true), 0);
					}	
				}
			
				
			}
			
			System.out.println("Router handles packet with seq: " + ((Message) ev).seq()+" from node: "+((Message) ev).source().networkId()+"." + ((Message) ev).source().nodeId() );
			// SimEnt sendNext = getInterface(((Message) ev).destination().networkId());
			System.out.println("Router sends to node: " + ((Message) ev).destination().networkId()+"." + ((Message) ev).destination().nodeId());		
			send (sendNext, ev, _now);
		}
		if (ev instanceof routerSolicitation) {
			System.out.println("Router Solicitation Recieved");
			send (((routerSolicitation) ev).get_node(),  ((Event) new routerAdvertisement(routerId)), (double)_now);
		}
		
		if (ev instanceof moveRouter) {
			
			System.out.println("started moving node, publishing target Router connection list");
			
			//Initialization
			Node oldNode= ((moveRouter) ev).getLeaving();
			NetworkAddr oldNodeAddr= ((moveRouter) ev).getLeaving().getAddr();
			Router targetRouter = ((moveRouter) ev).getEntering();
			RouteTableEntry[] newTable= targetRouter.getRoutingTable();
			
			
			targetRouter.publishRouting();
			
			//disconnect leaving node from current Link
			clearInterfaceEntry(oldNodeAddr);
			
			
			
			//establish new Link and connection with node
				Link targetLink = new Link();
				targetLink.setConnector(oldNode);
				oldNode.setPeer(targetLink); 	
				
				
				
				
			//Connect Foreign Router to Node
				for (int i =0; i< newTable.length +1; i++ ) {
					if(newTable[i] == null) {
						targetRouter.connectInterface(i, targetLink, oldNode);
						break;
					}
					
			// create new homeAgent to assigned node
					
			HomeAgents Entry = new HomeAgents(new NetworkAddr(this.mountHANetworkAddr,this.mountHANetworkAddr),
					this.mountHATargetNode, this, this.mountHATargetRouter, careOf, this.mountHABuffer);
			
					
			// if homeAgent has a buffer (hard coded here but buffer implies lost packets)	
				try {
				for(int j=0; i<careOf.size(); j++) {
					if (oldNode == careOf.get(j).getTargetNode()) {
							
						HomeAgents concernedHA= careOf.get(j);
						int  sendNotFor = concernedHA.getBffr();
						concernedHA.setSendOk(false);
							
						}
					}
				}catch(Exception e) {
					
				}
				}
				
				//bit of a roundabout way to solicit Router but i am not rewriting this code.
				send(targetRouter, new routerSolicitation(oldNode), 0);
				System.out.println("Node moved. Result");
				targetRouter.publishRouting();
				System.out.println("and the table for the Router left behind");
				this.publishRouting();
				
				for(int i=0; i<careOf.size(); i++) {
					if (oldNode == careOf.get(i).getTargetNode()) {
						
					send(careOf.get(i), new bindingUpdate(targetRouter), 0);
					System.out.println("BindingUpdate sent");
					
					}
				}
			
		}
		
		/*
		if (event instanceof Move) {
			System.out.println("Let's try changing interfaces: moving to interface " + ((Move) event).getNewConnection());
			NetworkAddr a = ((Move) event).getOldConnection();
			int b = ((Move) event).getNewConnection();
			moveInterface(a, b);
			System.out.println("interface move attempted: current interface = " + ((Move) event).getOldConnection());
			
		}*/
	}
}
}
