package Sim;

// This class implements a node (host) it has an address, a peer that it communicates with
// and it count messages send and received.

public class Node extends SimEnt {
	private NetworkAddr _id;
	private SimEnt _peer;
	private int _sentmsg=0;
	private int _seq = 0;
	
	private double _stopSendingAfter = 0; //messages
	private int _timeBetweenSending = 10; //time between messages
	private int _toNetwork = 0;
	private int _toHost = 0;
	public int flag = 0;
	public int _homeAgentId;
	private int _currentAgentId;

	
	public Node (int network, int node)
	{
		super();
		_id = new NetworkAddr(network, node);
	}	
	
	
	// Sets the peer to communicate with. This node is single homed
	
	public void setPeer (SimEnt peer)
	{
		_peer = peer;
		
		if(_peer instanceof Link )
		{
			 ((Link) _peer).setConnector(this);
			 
		}
	
	}
	
	public void setHomeAgent (int homeAgent)
	{

		this._homeAgentId = homeAgent;
	
	}
	
	
	public NetworkAddr getAddr()
	{
		return _id;
	}

	
	
	public void StartSending(int network, int node, int number, int timeInterval, int startSeq)
	{
		_stopSendingAfter = number;
		_timeBetweenSending = timeInterval;
		_toNetwork = network;
		_toHost = node;
		_seq = startSeq;
		send(this, new TimerEvent(),0);	
	}
	
	
	///////////////////////////////////////////////////////////////////////
	
	double switchingTime = 5.0;
	
	
   /////////////////////////////////////////////////////////////////////
	
	
	// This method is called upon that an event destined for this node triggers.
	
	public void recv(SimEnt src, Event ev)
	{
		/**
		if (SimEngine.getTime()> switchingTime && flag == 0) {
			flag++;
			moveMe(3);
		}
		*/
		
		if (ev instanceof TimerEvent)
		{			
			if (_stopSendingAfter > _sentmsg)
			{
				_sentmsg++;
				 send(_peer, new Message(_id, new NetworkAddr(_toNetwork, _toHost),_seq),0);
				send(this, new TimerEvent(),_timeBetweenSending);
				System.out.println("Node "+_id.networkId()+ "." + _id.nodeId() +" sent message with seq: "+_seq + " at time "+SimEngine.getTime());
				_seq++;
				
			}
		}
		if (ev instanceof Message)
		{
			System.out.println("Node "+_id.networkId()+ "." + _id.nodeId() +" receives message with seq: "+((Message) ev).seq() + " at time "+SimEngine.getTime());
			
		}
		
		if (ev instanceof routerAdvertisement) {
			System.out.println("Router Advertisement recieved");
			_currentAgentId= ((routerAdvertisement) ev).get_Router();
		}
	}

}
	
