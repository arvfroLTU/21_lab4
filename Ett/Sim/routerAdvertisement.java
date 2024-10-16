package Sim;

public class routerAdvertisement implements Event{
	
	private int _Router;
	
routerAdvertisement(int deliverRouter){
	set_Router(deliverRouter);
}
	
	public void entering(SimEnt locale)
	{
	}

	public int get_Router() {
		return _Router;
	}

	public void set_Router(int _Router) {
		this._Router = _Router;
	}
}