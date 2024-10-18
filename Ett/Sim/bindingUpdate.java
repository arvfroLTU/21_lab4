package Sim;


public class bindingUpdate implements Event{
	
	private Router newCareOf;
	
	bindingUpdate(Router targetRouter){
		
		this.newCareOf =  targetRouter;
	}

	@Override
	public void entering(SimEnt locale) {
		// TODO Auto-generated method stub
		
	}

	public Router getNewCareOf() {
		return newCareOf;
	}

	public void setNewCareOf(Router newCareOf) {
		this.newCareOf = newCareOf;
	}

}
