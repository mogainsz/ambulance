import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

public class startSimulation extends SwingWorker<ArrayList<Ambulance>, Ambulance> {
	private ArrayList<Ambulance> currentAmbulances;
	private ArrayList<Patient> currentPatients;
	private simPanel simulateP;

	private Object[][] rowDataAmbulances;
	private DefaultTableModel modelA;
	private JTable ambulancesTable;
	private Point greenFields = new Point(10, 0);
	private Point blueLane = new Point(30, 80);
	private Point redVill = new Point(90, 20);
	private Point hospital = new Point(50,50);
	private Point shortest = new Point();

	private Ambulance a;
	
	private double counter = 0;
	private Point aPosition;
	private int time = 0;
	

	
	

	
	public startSimulation(ArrayList<Ambulance> amb, ArrayList<Patient> pat,Ambulance a,simPanel panel, Object[][] rowData, DefaultTableModel model, JTable table, int time){
		this.currentAmbulances = amb;
		this.currentPatients = pat;
		this.simulateP = panel;
		this.rowDataAmbulances = rowData;
		this.modelA = model;
		this.ambulancesTable = table;
		this.a = a;
		this.aPosition = new Point(a.getXLocation(), a.getYLocation());
		this.time = time;
		
	}
	
	@Override
	protected ArrayList<Ambulance> doInBackground() throws Exception {
					
					
					shortest = greenFields;
					
					int ambCount = currentAmbulances.size()/3;
					long greenCount = 0;
					long blueCount = 0;
					long redCount = 0;
						
					Patient pat = currentPatients.stream().filter(x -> x.getStatus().equals("Pending")).findFirst().get();
					Point patPosition = new Point(pat.getXLocation(), pat.getYLocation());
					
					while(!(this.isCancelled())){

							for(Patient p: currentPatients){
								if(p.getStatus().equals("Pending")){
									Point pPosition = new Point(p.getXLocation(), p.getYLocation());
									if(getDistance(aPosition, patPosition) > getDistance(aPosition, pPosition)){
										
										pat = p;
									}
								}
							}
							
						blueCount = currentAmbulances.stream().filter(x -> x.getXLocation() == blueLane.getX() && x.getYLocation() == blueLane.getY()).count();
						greenCount = currentAmbulances.stream().filter(x -> x.getXLocation() == greenFields.getX() && x.getYLocation() == greenFields.getY()).count();
						redCount = currentAmbulances.stream().filter(x -> x.getXLocation() == redVill.getX() && x.getYLocation() == redVill.getY()).count();
						
						if(a.getStatus().equals("At Station")){
							if(pat.getStatus().equals("Pending")){
							a.setStatus("Responding");
							a.setPatient(String.valueOf(pat.getID()));
							pat.setStatus("Assigned");
							pat.setAmbulance(a.getID());
							
							Thread.sleep(1000);
							publish(a);
							}
							
							else{
								pat = currentPatients.stream().filter(x -> x.getStatus().equals("Pending")).findFirst().get();
								patPosition = new Point(pat.getXLocation(), pat.getYLocation());
							}
						}
						
						
						if(a.getStatus().equals("Responding")){
							
							Patient CurrP = currentPatients.stream().filter(x -> x.getAmbulance().equals(a.getID())).findFirst().get();
							Rectangle bounds = new Rectangle(CurrP.getXLocation(), CurrP.getYLocation(), 5, 5);
							if(a.getXLocation() > CurrP.getXLocation()){
								a.leftX(4);
							}
							
							if(a.getXLocation() < CurrP.getXLocation()){
								a.rightX(4);
							}
							
							if(a.getYLocation() > CurrP.getYLocation()){
								a.upY(4);
							}
							
							if(a.getYLocation() < CurrP.getYLocation()){
								a.downY(4);
							}
							
							if(bounds.contains(new Point(a.getXLocation(), a.getYLocation()))){
								CurrP.setStatus("");
								a.setXLocation(CurrP.getXLocation()); a.setYLocation(CurrP.getYLocation());
								a.setStatus("At Scene");
							
							}
							
							Thread.sleep(1000);
							publish(a);
							
						}
						
						
						if(a.getStatus().equals("At Scene")){
							Patient CurrP = currentPatients.stream().filter(x -> x.getAmbulance().equals(a.getID())).findFirst().get();
							Thread.sleep(4000);
							a.setStatus("Transporting");
							CurrP.setStatus("Transporting");
						}
						
						
						if(a.getStatus().equals("Transporting")){
							
							Rectangle bounds = new Rectangle((int)hospital.getX(), (int)hospital.getY(), 5, 5);

							if(a.getXLocation() > hospital.getX()){
								a.leftX(3);
							}
							
							if(a.getXLocation() < hospital.getX()){
								a.rightX(3);
							}
							
							if(a.getYLocation() > hospital.getY()){
								a.upY(3);
							}
							
							if(a.getYLocation() < hospital.getY()){
								a.downY(3);
							}
							
							
							if(bounds.contains(new Point(a.getXLocation(), a.getYLocation()))){
								a.setXLocation((int)hospital.getX());a.setYLocation((int)hospital.getY());
								Patient currP = currentPatients.stream().filter(x -> x.getAmbulance().equals(a.getID())).findFirst().get();
								currP.setAmbulance("");
								currP.setStatus("Completed");
								a.setStatus("At Destination");
								a.setPatient("");
								
								
							}
							
							Thread.sleep(1000);
							publish(a);
							
							
						}
						
						if(a.getStatus().equals("At Destination")){
							Thread.sleep(2000);
							a.setStatus("Returning");
							
							
							
						}
						
						
						
						
						if(a.getStatus().equals("Returning")){
							
							aPosition = new Point(a.getXLocation(), a.getYLocation());
							if(getDistance(aPosition, greenFields) > getDistance(aPosition, blueLane)){
								
								shortest = blueLane;
							}
							
							else if(getDistance(aPosition, greenFields) > getDistance(aPosition, redVill)){
								
								shortest = redVill;
							}
							
							
							if(greenCount >= ambCount){
								shortest = blueLane;
								
								
							}
							
							if(blueCount >= ambCount){
								shortest = redVill;
								
							}
							
							if(redCount >= ambCount){
								shortest = greenFields;
								
							}
							
							Rectangle bounds = new Rectangle((int)shortest.getX(), (int)shortest.getY(), 5, 5);
							//System.out.print(shortest);
							
							
							if(a.getXLocation() > shortest.getX()){
								a.leftX(4);
							}
							
							else if(a.getXLocation() < shortest.getX()){
								a.rightX(4);
							}
							
							if(a.getYLocation() > shortest.getY()){
								a.upY(4);
							}
							
							if(a.getYLocation() < shortest.getY()){
								a.downY(4);
							}
							
							if(bounds.contains(new Point(a.getXLocation(), a.getYLocation()))){
								a.setXLocation((int)shortest.getX());a.setYLocation((int)shortest.getY());
								a.setStatus("At Station");
								
							}
							
							
							
							Thread.sleep(1000);
							
							publish(a);
						}
						
						
						
						
						
	
				}
					
					
					return null;
					
					
				}
	
				
				
				protected void process(List<Ambulance> chunks){
						
					
					  outputFileA newAmb = new outputFileA("Ambulances-2.csv", currentAmbulances);
					  newAmb.updateAmbulance();
					  inputFile load = new inputFile("Ambulances-2.csv");
					  load.readAmbulances();
					  rowDataAmbulances = load.returnAmbulanceData();
					  Object columnNamesAmbulances[] = { "ID", "Location", "Status", "Patient"};
					  
					  modelA = new DefaultTableModel(rowDataAmbulances, columnNamesAmbulances);
					  ambulancesTable.setModel(modelA);
					  

					  outputFile newPat = new outputFile("Patients-2.csv", currentPatients);
					  newPat.updatePatients();
				
					  simulateP.repaint();
					  
					
					  
					  
					  
				}
				
				
				

public double getDistance(Point from, Point to){
	return Math.hypot(from.getX()-to.getX(), from.getY()-to.getY());
}

public void resetTime(){
	this.counter = 0;
}


}





