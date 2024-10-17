package Sim;

// An example of how to build a topology and starting the simulation engine

public class Run {
	
	
	public static void main (String [] args)
	{
		
		largerNetwork Net = new largerNetwork();
 		//Creates two links
 		Link link1 = new Link();
		Link link2 = new Link();
		//Link routerLink = new Link();
		
		
		
		// Create two end hosts that will be
		// communicating via the router
		Node  trafficGen = new Node(1,1);
		Node  trafficSink = new Node(2,2);

		//Connect links to hosts
		trafficGen.setPeer(link1);
		trafficSink.setPeer(link2);

		// Creates as router and connect
		// links to it. Information about 
		// the host connected to the other
		// side of the link is also provided
		// Note. A switch is created in same way using the Switch class
		Router routeNode = new 	Router(11, 1, Net.getNetwork());
		//Router routeNode2 = new Router(11,2, Net.getNetwork());
			
		
		routeNode.connectInterface(0, link1, trafficGen);
		routeNode.connectInterface(1, link2, trafficSink);
	
		
		//Test for top level moving
		routeNode.publishRouting();
		routeNode.moveInterface(trafficSink.getAddr(), 5);
		routeNode.publishRouting();
		
		// Generate some traffic
		// host1 will send 4 messages with time interval 5 to network 2, node 1. Sequence starts with number 1
		trafficGen.StartSending(2, 2, 5, 15, 0); 
	
		
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
