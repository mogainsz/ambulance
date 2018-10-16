import javax.swing.*;
import javax.swing.Timer;

import java.awt.*;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.time.Duration;
import java.time.Instant;

import javax.swing.border.*;

import java.util.Arrays;

import java.util.*;


import javax.swing.table.DefaultTableModel;

public class AmbulanceSimulation{
	private JButton stop, start;// creating the buttons
	private JLabel durationLabel, keyLabel, ambKeyLabel, patientKeyLabel, stationKeyLabel, hospitalKeyLabel, simulationLabel;
	private JScrollPane ambulancesPane;
	private ArrayList<Patient> currentPatients;
	public ArrayList<Ambulance> newAmbulance, currentAmbulances;
	private JTextField duration;
	private JTable ambulancesTable;
	private Object[][] rowDataAmbulances;
	private inputFile thePatients, theAmbulances;
	private DefaultTableModel modelA;
	private JFrame mainMenu, simulation;
	private ArrayList<oval> keys = new ArrayList<oval>();
	private ArrayList<oval> stations = new ArrayList<oval>();
	private ArrayList<oval> ambulances = new ArrayList<oval>();
	private ArrayList<oval> patients = new ArrayList<oval>();
	private ArrayList<Thread> threads = new ArrayList<Thread>();
	private ArrayList<SwingWorker> sw = new ArrayList<SwingWorker>();
	
	private int counter = 0;
	
	private int theTime;
	
	public AmbulanceSimulation(){
	
	mainMenu = new JFrame("Ambulance Simulation App"); 
	mainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	simulation = new JFrame("Simulation");
	
	
	//Create all the panels
	JPanel ambulanceListP = new JPanel();
	JPanel simulationP = new JPanel();
	keyPanel key = new keyPanel();
	
	
	//------------------------------------------Read and return patient data from patients.csv file----------------------------------------------------\\
	thePatients = new inputFile("patients.csv");
	currentPatients = thePatients.readPatients();
	

	
	//------------------------------------------Read and return Ambulance data from ambulances.csv file----------------------------------------------------\\
	theAmbulances = new inputFile("ambulances.csv");
	currentAmbulances = theAmbulances.readAmbulances();
	rowDataAmbulances = theAmbulances.returnAmbulanceData();
	
	Object columnNamesAmbulances[] = { "ID", "Location", "Status", "Patient"};
    
    modelA = new DefaultTableModel(rowDataAmbulances, columnNamesAmbulances);
    
    //Disable cell editing
	ambulancesTable = new JTable(modelA){
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public boolean isCellEditable(int row, int column){
			return false;
		}
	};
	
	
	
	simPanel simulateP = new simPanel(stations, ambulances, patients, currentAmbulances, currentPatients);

	
	
	Border border = new LineBorder(Color.BLACK, 3); // border of the buttons
	Border panelBorder = new LineBorder(Color.black, 3); // border of the panels
	Color buttonColor = Color.DARK_GRAY; // color of the buttons
	Color panelColor = new Color(105,105,105); //color of the panel
	UIManager.put("OptionPane.border", panelBorder);
	UIManager.put("OptionPane.messageForeground", Color.white);
	UIManager.put("Panel.background", panelColor);
	
	

    /***************************************************************************************************************************************
	 * 												Setting up the Ambulances list screen												  *
	 **************************************************************************************************************************************/
    	ambulanceListP.setBackground(panelColor);
    	ambulanceListP.setLayout(null);
    	
    	
    	
    	
    	start = new JButton("Start");
    	start.setFont(new Font("Calibri (Body)", Font.BOLD, 16));
    	start.setBounds(10, 275, 185, 60);
    	start.setBackground(buttonColor);
    	start.setForeground(Color.WHITE);
    	start.setBorder(border);
    	start.addActionListener(new ActionListener() { 
    		  public void actionPerformed(ActionEvent e) { 
    			  simulation.setVisible(true);
    			  stop.setEnabled(true);
    			  start.setEnabled(false);
    			  
    			  for(Ambulance a : currentAmbulances){
    				  
    				  startSimulation sim = new startSimulation(currentAmbulances, currentPatients, a ,simulateP, rowDataAmbulances, modelA, ambulancesTable, Integer.parseInt(duration.getText()));
    				  Thread l = new Thread(sim);
    				 
    				  l.start();
    				  threads.add(l);
    				 
    			  }
    			 
    			 
    			 
    			
    			 
    			  SwingWorker<Void, Void> h = new SwingWorker<Void, Void> (){

    					@Override
    					protected Void doInBackground() throws Exception {
    						while(!(isCancelled())){
    						if(counter < Integer.parseInt(duration.getText())){
    							Thread.sleep(1000);
    							counter += 1;
    							//System.out.print(counter);
    						}
    						
    						if(counter == Integer.parseInt(duration.getText())){
    				    		for(Thread i: threads){
    				    			i.stop();
    				    			start.setEnabled(true);
    				    			stop.setEnabled(false);
    				    			
    				    		}
    				    		this.cancel(true);
    				    		counter = 0;
    				    	}
    						}
    						
    						return null;
    					}
    					  
    				  };
    			
    				h.execute();
    				sw.add(h);

    		  } 
    		});
    	
    	
    	
    	
    	stop = new JButton("Stop");
    	stop.setEnabled(false);
    	stop.setFont(new Font("Calibri (Body)", Font.BOLD, 16));
    	stop.setBounds(303, 275, 185, 60);
    	stop.setBackground(buttonColor);
    	stop.setForeground(Color.WHITE);
    	stop.setBorder(border);
    	stop.addActionListener(new ActionListener() { 
  		  @SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent e) { 
  			
  			  for(Thread l: threads){
  				  l.stop();
  				 
  				 
  			  }
  			  
  			  for(SwingWorker n:sw){
  				  n.cancel(true);
  			  }
  			 
  			stop.setEnabled(false);
  			 start.setEnabled(true);
  			 
  			counter = 0;
  		  } 
  		});
  	
  	
    
    	
        duration = new JTextField("60");
        duration.setFont(new Font("Calibri (Body)", Font.BOLD, 16));
        duration.setBounds(173, 235, 315, 35);
        duration.setBorder(border);
    	
        durationLabel = new JLabel("Duration(seconds): ");
        durationLabel.setBounds(15,218, 2000, 65);
        durationLabel.setFont(new Font("Calibri (Body)", Font.BOLD, 16));
        durationLabel.setForeground(Color.white);
        
		ambulancesTable.getTableHeader().setFont(new Font("Calibri (Body)", Font.BOLD, 14));
		ambulancesTable.getTableHeader().setBackground(buttonColor);
		ambulancesTable.getTableHeader().setForeground(Color.white);
		ambulancesTable.getTableHeader().setBorder(new LineBorder(Color.black, 1));
		ambulancesTable.setFont(new Font("Calibri (Body)", Font.BOLD, 12));
    
		simulationLabel = new JLabel("Ambulance Simulation");
		simulationLabel.setBounds(125,1, 2000, 50);
		simulationLabel.setFont(new Font("Calibri (Body)", Font.PLAIN, 26));
		simulationLabel.setForeground(Color.white);
		    
		ambulancesPane = new JScrollPane(ambulancesTable);
		ambulancesPane.setBounds(10, 45, 478, 185);
		ambulancesPane.setBorder(border);
		ambulanceListP.add(ambulancesPane);
		ambulanceListP.add(start);
		ambulanceListP.add(stop);
		ambulanceListP.add(simulationLabel);
		ambulanceListP.add(duration);
		ambulanceListP.add(durationLabel);
		
		ambulanceListP.setBorder(panelBorder);
		
		ambulancesPane.getVerticalScrollBar().setBackground(new Color(53,125,145));
		mainMenu.setContentPane(ambulanceListP);
		mainMenu.setSize(510, 385);
		mainMenu.setLocationRelativeTo(null);
		mainMenu.setVisible(true);
		
		
		 /***************************************************************************************************************************************
		 * 												Setting up the Ambulances simulation screen												*
		 **************************************************************************************************************************************/
		
		oval ambulanceKey = new oval(10, 30, 10, 10, new Color(151, 171, 117));
		oval patientKey = new oval(10, 50, 10, 10, new Color(55, 96, 146));
		oval stationKey = new oval(10, 70, 10, 10, new Color(165, 97, 96));
		oval hospitalKey = new oval(10, 90, 10, 10, new Color(96, 74, 123));
		keys.add(ambulanceKey);keys.add(patientKey);keys.add(stationKey);keys.add(hospitalKey);
		
		oval greenfields = new oval((10*326)/100, 0, 10, 10, new Color(165, 97, 96));
		oval bluelane = new oval((30*326)/100, (80*290)/100, 10, 10, new Color(165, 97, 96));
		oval redvill = new oval((90*326)/100, (20*290)/100, 10, 10, new Color(165, 97, 96));
		stations.add(greenfields); stations.add(bluelane); stations.add(redvill);
		
		keyLabel = new JLabel("Key");
		keyLabel.setBounds(10,0, 2000, 30);
		keyLabel.setFont(new Font("Calibri (Body)", Font.BOLD, 14));
		
		ambKeyLabel = new JLabel("Ambulance");
		ambKeyLabel.setBounds(25,20, 2000, 30);
		ambKeyLabel.setFont(new Font("Calibri (Body)", Font.BOLD, 12));
		
		
		
		patientKeyLabel = new JLabel("Patient");
		patientKeyLabel.setBounds(25,40, 2000, 30);
		patientKeyLabel.setFont(new Font("Calibri (Body)", Font.BOLD, 12));
		
		stationKeyLabel = new JLabel("Station");
		stationKeyLabel.setBounds(25,60, 2000, 30);
		stationKeyLabel.setFont(new Font("Calibri (Body)", Font.BOLD, 12));
		
		hospitalKeyLabel = new JLabel("Hospital");
		hospitalKeyLabel.setBounds(25,80, 2000, 30);
		hospitalKeyLabel.setFont(new Font("Calibri (Body)", Font.BOLD, 12));
	
		simulationLabel = new JLabel("Ambulance Simulation");
		simulationLabel.setBounds(125,1, 2000, 50);
		simulationLabel.setFont(new Font("Calibri (Body)", Font.PLAIN, 26));
		simulationLabel.setForeground(Color.white);
		    
		
		simulationP.setBackground(panelColor);
		simulationP.setLayout(null);
		
		simulateP.setBackground(Color.WHITE);
		
		simulateP.setBounds(15,  45,326, 290);
		simulationP.setBorder(panelBorder);
		simulateP.setBorder(panelBorder);
		simulationP.add(simulateP);
		
		key.setBorder(panelBorder);
		key.setBounds(350,  45,135, 290);
		key.setLayout(null);
		key.setBackground(Color.white);
		simulationP.add(key);
		key.add(keyLabel);
		key.add(ambKeyLabel);
		key.add(patientKeyLabel);
		key.add(stationKeyLabel);
		key.add(hospitalKeyLabel);
		simulationP.add(simulationLabel);
		simulation.setSize(510, 385);
		simulation.setContentPane(simulationP);
		
	
		
		
		
	}
	
	
	/***************************************************************************************************************************************
	 * 											Few methods for checking and manipulating the data										   *
	 **************************************************************************************************************************************/
	
	
	class keyPanel extends JPanel{
		
	    @Override
	    public void paintComponent(Graphics g) {
	    	super.paintComponent(g);
	    	for(oval l: keys){
	    		Graphics2D g2d = (Graphics2D) g;
		    	g2d.setColor(l.getColor());
		    	g2d.fillOval(l.getX(), l.getY(), l.getWidth(), l.getHeight());
	    	}
	    	
	    	
	    }
	}
	

	


public static void main(String[] args) {
	SwingUtilities.invokeLater(new Runnable(){
		public void run(){
			new AmbulanceSimulation();
		}
		
	
	});
    
    
 }
}




