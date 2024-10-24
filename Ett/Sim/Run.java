package Sim;

import java.util.ArrayList;

// An example of how to build a topology and starting the simulation engine

public class Run {
	public static void main (String [] args)
	{
 		//Creates two links
 		Link link1 = new Link();
		Link link2 = new Link();
		Link routerLink = new Link();
		ArrayList<HomeAgents> Net = new ArrayList<HomeAgents>();
		
		
		// Create two end hosts that will be
		// communicating via the router

		//Connect links to hosts

		// Creates as router and connect
		// links to it. Information about 
		// the host connected to the other
		// side of the link is also provided
		// Note. A switch is created in same way using the Switch class
		Router routeNode = new 	Router(7, 1, Net);
		Router routeNode2 = new Router(7,2, Net);
		
		Node  trafficGen = new Node(1,1,0,null, 100);
		Node  trafficSink = new Node(2,2,2,routeNode2, 100);
		routeNode.setHomeAgentRouter(trafficSink, 1337, routeNode2, 3);
		

		trafficGen.setPeer(link1);
		trafficSink.setPeer(link2);
		
		
		
		routeNode.connectInterface(0, routerLink, routeNode2);
		routeNode2.connectInterface(0, routerLink, routeNode);	
		routeNode.connectInterface(6, link1, trafficGen);
		routeNode.connectInterface(2, link2, trafficSink);
		trafficGen.setHomeAgentNode(routeNode.routerId);
		trafficSink.setHomeAgentNode(routeNode2.routerId);
		
		//Test for top level moving
		routeNode.publishRouting();
		routeNode.moveInterface(trafficSink.getAddr(), 3);
		routeNode.publishRouting();
		
		// Generate some traffic
		// host1 will send 4 messages with time interval 5 to network 2, node 1. Sequence starts with number 1
		trafficGen.StartSending(2, 2, 10, 15, 0); 
	
		
		// host2 will send 4 messages with time inter50val 5 to network 1, node 1. Sequence starts with number 10
		//trafficSink.StartSending(1, 1, 5, 5, 10); 
		
		// Start the simulation engine and of we go!
		Thread t=new Thread(SimEngine.instance());
	
		t.start();
		try
		{
			t.join();
			System.out.println("got here");
		}
		catch (Exception e)
		{
			System.out.println("The motor seems to have a problem, time for service?");
		}		



	}
}
