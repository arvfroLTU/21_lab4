package Sim;

public class routerSolicitation implements Event{
	private SimEnt _node;
	
routerSolicitation(SimEnt node){
	this._node = node;
}
	public void entering(SimEnt locale)
	{
	}
	public SimEnt get_node() {
		return _node;
	}
	public void set_node(SimEnt _node) {
		this._node = _node;
	}
}
