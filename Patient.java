
public class Patient {
	private int ID;
	private int xLocation;
	private int yLocation;
	private String status;
	private String ambulance;
	
	public Patient(){}
	
	public Patient(int ID, int xLocation, int yLocation, String status, String ambulance){
		this.ID = ID;
		this.xLocation = xLocation;
		this.yLocation = yLocation;
		this.status = status;
		this.ambulance = ambulance;
	}
	
	public String toString(){
		return String.format("%3s	(%s, %s)	%s	%s", this.ID, this.xLocation, this.yLocation, this.status, this.ambulance);
	}
	
	public int getID(){
		return this.ID;
	}
	
	public int getXLocation(){
		return this.xLocation;
	}
	
	public int getYLocation(){
		return this.yLocation;
	}
	
	public String getStatus(){
		return this.status;
	}
	
	public String getAmbulance(){
		return this.ambulance;
	}
	
	public void setID(int ID){
		this.ID = ID;
	}
	
	public void setXLocation(int xLocation){
		this.xLocation = xLocation;
	}
	
	public void setYLocation(int yLocation){
		this.yLocation = yLocation;
	}
	
	public void setStatus(String status){
		this.status = status;
	}
	
	public void setAmbulance(String ambulance){
		this.ambulance = ambulance;
	}
	

}
