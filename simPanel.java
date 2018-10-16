import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;



class simPanel extends JPanel{
		private ArrayList<oval> stations;
		private ArrayList<oval> ambulances;
		private ArrayList<oval> patients;
		private ArrayList<Ambulance> currentAmbulances;
		private ArrayList<Patient> currentPatients;
		
		public simPanel(ArrayList<oval> stations, ArrayList<oval> ambulances, ArrayList<oval> patients, ArrayList<Ambulance> currentAmbulances, ArrayList<Patient> currentPatients){
			this.stations = stations;
			this.ambulances = ambulances;
			this.patients = patients;
			this.currentAmbulances = currentAmbulances;
			this.currentPatients = currentPatients;
		}
	
	    @Override
	    public void paintComponent(Graphics g) {
	    	super.paintComponent(g);
	    	Graphics2D g2d = (Graphics2D) g;
		    g2d.setColor(new Color(96, 74, 123));
		    g2d.fillOval(326/2, 290/2, 10, 10);
	    	
		    for(oval l: stations){
		    	
		    	g2d.setColor(l.getColor());
		    	g2d.fillOval(l.getX(), l.getY(), l.getWidth(), l.getHeight());
	    	}
		    ambulances.clear();
		    for(Ambulance a : currentAmbulances){
				oval amb = new oval((a.getXLocation()*326)/100, (a.getYLocation()*290)/100, 10, 10, new Color(151, 171, 117));
				ambulances.add(amb);
			}
		    
		    
		    for(oval l: ambulances){
		    	g2d.setColor(l.getColor());
		    	g2d.fillOval(l.getX(), l.getY(), l.getWidth(), l.getHeight());
		    	
	    	}
		    
		    patients.clear();
		    for(Patient p : currentPatients){
				
				if(p.getStatus().equals("Assigned") || p.getStatus().equals("Pending")){
				oval pat = new oval((p.getXLocation()*326)/100, (p.getYLocation()*290)/100, 10, 10, new Color(55, 96, 146));
				patients.add(pat);
				}
			}
		    
		    for(oval l: patients){
		    	g2d.setColor(l.getColor());
		    	g2d.fillOval(l.getX(), l.getY(), l.getWidth(), l.getHeight());
		    	
	    	}
		    
	    }  
	    
	}