package Sim;

import java.util.ArrayList;

// This class implements a simple router

public class Router extends SimEnt{

	private RouteTableEntry [] _routingTable;
	private int _interfaces;
	private int _now=0;
	public int routerId;
	private ArrayList<HomeAgents> CareOf;

	// When created, number of interfaces are defined
	
 	Router(int interfaces, int id, ArrayList<HomeAgents> network)
	{
		_routingTable = new RouteTableEntry[interfaces];
		_interfaces=interfaces;
		routerId = id;
		CareOf =network;
	}
	
 	
	// This method connects links to the router and also informs the 
	// router of the host connects to the other end of the link
	public void connectInterface(int interfaceNumber, SimEnt link, SimEnt node, largerNetwork Network)
	{
		if (interfaceNumber<_interfaces)
		{
			_routingTable[interfaceNumber] = new RouteTableEntry(link, node);
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
			if (_routingTable[i] != null)
				
				
				if (((Node) _routingTable[i].node()).getAddr().networkId() == networkAddress)
				{
					routerInterface = _routingTable[i].link();
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
	
	
	public void routerAdvertisement() {
		for (int i =0; i <_routingTable.length; i++) {
			if (_routingTable[i] !=null){
				SimEnt currentObj = _routingTable[i].node();
			send(currentObj, new routerAdvertisement(routerId), (double) _now);
			
			}
		    }
	}
	
	// When messages are received at the router this method is called
	
	public void recv(SimEnt source, Event event)
	{
		if (event instanceof Message)
		{
			for (int i=0; i< CareOf.size(); i++) {
				if(((Message) event).destination() == CareOf.get(i).getId()){
					if(CareOf.get(i).getHomeAgent() == CareOf.get(i).getCareOf()) {
			
			System.out.println("Router handles packet with seq: " + ((Message) event).seq()+" from node: "+((Message) event).source().networkId()+"." + ((Message) event).source().nodeId() );
			SimEnt sendNext = getInterface(((Message) event).destination().networkId());
			System.out.println("Router sends to node: " + ((Message) event).destination().networkId()+"." + ((Message) event).destination().nodeId());		
			send (sendNext, event, _now);
					}
					else {
						
						
						//Send to Foreign Router
						
					}
				
			}
	
		}
		}
		if (event instanceof routerSolicitation) {
			System.out.println("Router Solicitation Recieved");
			send (((routerSolicitation) event).get_node(),  ((Event) new routerAdvertisement(routerId)), (double)_now);
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