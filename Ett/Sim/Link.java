package Sim;

// This class implements a link without any loss, jitter or delay

public class Link extends SimEnt{
	private SimEnt _connectorA=null;
	private SimEnt _connectorB=null;
	private int _now=0;
	
	public Link()
	{
		super();	
	}
	
	// Connects the link to some simulation entity like
	// a node, switch, router etc.
	
	public void setConnector(SimEnt connectTo)
	{
		if (_connectorA == null) 
			_connectorA=connectTo;
		else if (_connectorB == null) 
			_connectorB=connectTo;
		else
			System.out.println("connections full");
	}
	
	public SimEnt getConnectorB(){
		return _connectorB;
	}
	
	public SimEnt getConnectorA(){
		return _connectorA;
	}
	
	public SimEnt getPartner(Node src)
	{
		if (getConnectorA() == (src)) {
			return getConnectorB(); 
		}
		else {
			return getConnectorA();
		}
	}

	// Called when a message enters the link
	
	public void recv(SimEnt src, Event ev)
	{
		if (ev instanceof Message)
		{
			System.out.println("Link recv msg, passes it through");
			if (src == _connectorA)
			{
				send(_connectorB, ev, _now);
			}
			else
			{
				send(_connectorA, ev, _now);
			}
		}
			else if (ev instanceof TimerEvent)
			{
				System.out.println("Link recv first Timer Event, passes it through");
				if (src == _connectorA)
				{
					send(_connectorB, ev, _now);
				}
				else
				{
					send(_connectorA, ev, _now);
				}
		}
	}	
}