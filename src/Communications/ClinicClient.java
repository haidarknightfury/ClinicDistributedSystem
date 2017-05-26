package Communications;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import clinicinterface.ClinicInterface;

public class ClinicClient {
	
	public static ClinicInterface CI;
	
	public ClinicClient(){
		try{
			Registry r=LocateRegistry.getRegistry("127.0.0.1",1099);
		    CI=(ClinicInterface)r.lookup("ClinicService");
			System.out.println("LOGIN :"+ CI.Login("walder", "1234"));
			
		}
		catch(RemoteException|NotBoundException e){
			System.out.println(e.toString());
		}
	}
	
	public ClinicInterface getClinicInterface(){
		return CI;
	}

}
