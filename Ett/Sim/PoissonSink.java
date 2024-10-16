

package Sim;

import java.util.ArrayList;
import java.util.List;

public class PoissonSink extends Node {
	private NetworkAddr _id;
	protected SimEnt _peer;
	List<Double> values = new ArrayList<>();
	String fileName = "poissonDoubles.csv";
	WriteDoublesToCSV blargh = new WriteDoublesToCSV();


public PoissonSink(int network, int node) {
	super(network, node);
	_id = new NetworkAddr(network, node);
	}



public void recv(SimEnt src, Event ev)
{
	System.out.println("in recieve");
	
		if (ev instanceof Message)
		{
			System.out.println("Node "+_id.networkId()+ "." + _id.nodeId() +" receives message with seq: "+((Message) ev).seq() + " at time "+SimEngine.getTime());
			 values.add(SimEngine.getTime());
			 blargh.writeDoublesToCSV(values, fileName);
			 System.out.println("babababa");
		}
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





}