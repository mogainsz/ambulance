import java.awt.Point;

public class Ambulance {
	
	private String ID;
	private int xLocation;
	private int yLocation;
	private String status;
	private String patient;
	
	public Ambulance(){}
	
	public Ambulance(String ID, int xLocation, int yLocation, String status, String patient){
		this.ID = ID;
		this.xLocation = xLocation;
		this.yLocation = yLocation;
		this.status = status;
		this.patient = patient;
	}
	
	public String toString(){
		return String.format("%3s	(%s, %s)	%s	%s", this.ID, this.xLocation, this.yLocation, this.status, this.patient);
	}
	
	public String getID(){
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
	
	public String getPatient(){
		return this.patient;
	}
	
	public void setID(String ID){
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
	
	public void setPatient(String patient){
		this.patient = patient;
	}
	


	
	public void leftX(int step){
		this.xLocation -= step;
	}
	
	public void upY(int step){
		this.yLocation -= step;
	}
	
	public void downY(int step){
		this.yLocation += step;
	}
	
	public void rightX(int step){
		this.xLocation += step;
	}
	
}
