package Communications;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClinicServer {
	public static void main(String [] args){
		try{
			
			Registry r=LocateRegistry.createRegistry(1099);
			ClinicImplementation clinicImplementation=new ClinicImplementation();
			Naming.rebind("ClinicService", clinicImplementation);
			System.out.println("Server is Ready");
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
		
	}

}
