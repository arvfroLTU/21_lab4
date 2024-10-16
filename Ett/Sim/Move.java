package Sim;

public class Move implements Event{

	private NetworkAddr oldConnection;  
	private int newConnection;
	
	
	Move (NetworkAddr from, int to)
	{
		oldConnection = from;
		newConnection= to;
	}
/*
	public  void setMoveEvent(NetworkAddr old, int noo){
		this.setOldConnection(old);
		this.setNewConnection(noo);
	}
	*/
	
	public void entering(SimEnt locale)
	{
	}


	public NetworkAddr getOldConnection() {
		return this.oldConnection;
	}


	public void setOldConnection(NetworkAddr oldConnection) {
		this.oldConnection = oldConnection;
	}


	public int getNewConnection() {
		return this.newConnection;
	}

	public void setNewConnection(int newConnection) {
		this.newConnection = newConnection;
	}
}
