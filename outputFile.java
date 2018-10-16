import java.io.*;
import java.util.*;
public class outputFile {
	String fileName;
	ArrayList<Patient> p;
	ArrayList<Ambulance>a;
	FileWriter outFile = null;
	String comma = ",";
	String quote = "\"";
	
	public outputFile(String fileName, ArrayList<Patient> p){
		this.fileName = fileName;
		this.p = p;
	}
	
	
	public void updatePatients(){
		try{
		outFile = new FileWriter(this.fileName);
		outFile.write("\"id\"");outFile.append(comma);
		outFile.write("\"x.location\"");outFile.append(comma);
		outFile.write("\"y.location\"");outFile.append(comma);
		outFile.write("\"status\"");outFile.append(comma);
		outFile.write("\"ambulance\"");outFile.append("\n");
		for(Patient patient: p){
			outFile.append(String.valueOf(patient.getID()));
			outFile.append(comma);
			outFile.append(String.valueOf(patient.getXLocation()));
			outFile.append(comma);
			outFile.append(String.valueOf(patient.getYLocation()));
			outFile.append(comma);
			outFile.append(quote + patient.getStatus() + quote);
			outFile.append(comma);
			if(patient.getAmbulance().equals("")){
				outFile.append(patient.getAmbulance());
			}
			else{
				outFile.append(quote + patient.getAmbulance() + quote);
			}
			outFile.append("\n");
			
		}
		
		}
		
		catch(Exception e){
			System.out.print("An Error occured");
		}
		
		finally{
			try{
			outFile.close();}
		
		catch(IOException e){
			System.out.print("Error while closing file");
		}
		}
	}
	
	
	
}
