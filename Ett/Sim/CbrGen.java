package Sim;

public class CbrGen extends Node {
	private NetworkAddr _id;
	private SimEnt _peer;
	private int _sentmsg=0;
	private int _seq = 0;
	
	private int _stopSendingAfter = 0; //messages
	private int _timeBetweenSending = 10; //time between messages
	private int _toNetwork = 0;
	private int _toHost = 0;

	public CbrGen(int network, int node) {
		super(network, node);
		_id = new NetworkAddr(network, node);
		
	}
/**
 * Sets values for interaction, then sends a single signal
 * 
 * @param network
 * @param node
 * @param number
 * @param timeInterval
 * @param startSeq
 */
		public void StartSending(int network, int node, int startSeq, int number, int timeInterval)
		{	
			System.out.println("started sending at " +SimEngine.getTime());
			this.set_stopSendingAfter(number);
			this.set_timeBetweenSending(timeInterval);
			this._toNetwork = network;
			this.set_toHost(node);
			this.set_seq(startSeq);
			
			//send(_peer, new Message(_id, Sink.getAddr() ,_seq), 0);
			send(this, new TimerEvent(),0);
		
		}	
		
		
		@Override
		public void setPeer (SimEnt peer)
		{
			_peer = peer;
			
			if(_peer instanceof Link )
			{
				 ((Link) _peer).setConnector(this);
			}
		}
		
		public NetworkAddr getAddr()
		{
			return _id;
		}
		
		
		public void recv(SimEnt src, Event ev)
		{	// generates more output, though sadly has to be called recieve, in order for the systems to work.
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
		}

		
		
		public int get_toNetwork() {
			return _toNetwork;
		}
		public void set_toNetwork(int _toNetwork) {
			this._toNetwork = _toNetwork;
		}
		public int get_toHost() {
			return _toHost;
		}
		public void set_toHost(int _toHost) {
			this._toHost = _toHost;
		}
		public int get_timeBetweenSending() {
			return _timeBetweenSending;
		}
		public void set_timeBetweenSending(int _timeBetweenSending) {
			this._timeBetweenSending = _timeBetweenSending;
		}
		public int get_stopSendingAfter() {
			return _stopSendingAfter;
		}
		public void set_stopSendingAfter(int _stopSendingAfter) {
			this._stopSendingAfter = _stopSendingAfter;
		}
		public int get_sentmsg() {
			return _sentmsg;
		}
		public void set_sentmsg(int _sentmsg) {
			this._sentmsg = _sentmsg;
		}
		public int get_seq() {
			return _seq;
		}
		public void set_seq(int _seq) {
			this._seq = _seq;
		}
		
	}
