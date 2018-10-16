import java.io.*;
import java.util.*;
public class inputFile {
	private String fileName;
	BufferedReader file = null;
	String patients[];
	String aPatient = "";
	ArrayList<Patient> allPatients;
	Patient p;
	
	String ambulances[];
	String anAmbulance = "";
	Ambulance a;
	ArrayList<Ambulance> allAmbulances;

	
	
	public inputFile(String fileName){
		this.fileName = fileName;
		
	}
	
	public ArrayList<Patient> readPatients(){
		allPatients = new ArrayList<Patient>();
		try{
		
			file = new BufferedReader(new FileReader(this.fileName));
			file.readLine();
			while ((aPatient = file.readLine()) != null) {
                patients = aPatient.split(",", 5);
                p = new Patient(Integer.parseInt(patients[0]), Integer.parseInt(patients[1]), Integer.parseInt(patients[2]), patients[3].replace("\"", ""), patients[4].replace("\"", ""));
                allPatients.add(p); 
         }
		
	}
		
		catch (IOException e){
			System.out.print("File not found");
		}
		
		
		return allPatients;
	}
	
	
	public Object[][] returnPatientData(){
		Object[][] data = new Object[allPatients.size()][4];
		for(int i =0; i < allPatients.size(); i++){
			data[i][0] = allPatients.get(i).getID();
			data[i][1] = "(" + allPatients.get(i).getXLocation() + ", " + allPatients.get(i).getYLocation()+")";
			data[i][2] = allPatients.get(i).getStatus();
			data[i][3] = allPatients.get(i).getAmbulance();
			if (data[i][3].equals("")){
				data[i][3] = "-";
			};
	}
		return data;
	
}
	
	
	
	public ArrayList<Ambulance> readAmbulances(){
		allAmbulances = new ArrayList<Ambulance>();
		try{
		
			file = new BufferedReader(new FileReader(this.fileName));
			file.readLine();
			while ((anAmbulance = file.readLine()) != null) {
                ambulances = anAmbulance.split(",", 5);
                a = new Ambulance(ambulances[0].replace("\"", ""), Integer.parseInt(ambulances[1]), Integer.parseInt(ambulances[2]), ambulances[3].replace("\"", ""), ambulances[4]);
                allAmbulances.add(a); 
         }
		
	}
		
		catch (IOException e){
			System.out.print("Error");
		}
		
		return allAmbulances;
	}
	
	
	public Object[][] returnAmbulanceData(){
		Object[][] data = new Object[allAmbulances.size()][4];
		for(int i =0; i < allAmbulances.size(); i++){
			data[i][0] = allAmbulances.get(i).getID();
			data[i][1] = "(" + allAmbulances.get(i).getXLocation() + ", " + allAmbulances.get(i).getYLocation()+")";
			data[i][2] = allAmbulances.get(i).getStatus();
			data[i][3] = allAmbulances.get(i).getPatient();
			if (data[i][3].equals("")){
				data[i][3] = "-";
			};
	}
		return data;
	
}
	
}
