import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class outputFileA {
	String fileName;
	ArrayList<Ambulance>a;
	FileWriter outFile = null;
	String comma = ",";
	String quote = "\"";
	
	public outputFileA(String fileName, ArrayList<Ambulance> a){
		this.fileName = fileName;
		this.a = a;
	}
	
	
	public void updateAmbulance(){
		try{
			outFile = new FileWriter(this.fileName);
			outFile.write("\"id\"");outFile.append(comma);
			outFile.write("\"x.location\"");outFile.append(comma);
			outFile.write("\"y.location\"");outFile.append(comma);
			outFile.write("\"status\"");outFile.append(comma);
			outFile.write("\"patient\"");outFile.append("\n");
		for(Ambulance ambulance: a){
			outFile.append(quote + ambulance.getID() + quote);
			outFile.append(comma);
			outFile.append(String.valueOf(ambulance.getXLocation()));
			outFile.append(comma);
			outFile.append(String.valueOf(ambulance.getYLocation()));
			outFile.append(comma);
			outFile.append(quote + ambulance.getStatus() + quote);
			outFile.append(comma);
			outFile.append(ambulance.getPatient());
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
			System.out.print("Erro while closing file");
		}
		}
	}
	
	
}
