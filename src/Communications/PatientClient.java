package Communications;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.sql.rowset.serial.SerialDatalink;

import clinicinterface.Appointment;
import clinicinterface.ClinicInterface;
import clinicinterface.Doctor;
import clinicinterface.Patient;
import clinicinterface.PendingAppointment;


public class PatientClient {

	public static DatagramSocket clientSocket;
	public static InetAddress IPAddress;
	public static byte[] sendData;
	public static byte[] receiveData;
	public static String Data="";

	public PatientClient() throws SocketException, UnknownHostException{
		clientSocket = new DatagramSocket();
		IPAddress = InetAddress.getByName("localhost");
		sendData = new byte[2048];
		receiveData = new byte[2048];

	}

	public String Login(String UserName,String Password) throws IOException{
		Data = "1," + UserName + "," + Password;
		sendData = Data.getBytes();

		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
		clientSocket.send(sendPacket);

		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		clientSocket.receive(receivePacket);

		String ServerResponse = new String(receivePacket.getData());
		System.out.println("RECEIVED "+ServerResponse);
		return ServerResponse;

	}


	public String Register(Patient P) throws IOException{

		System.out.println(P.toString());

		Data = "2," + P.getFirstName()+","+P.getOtherNames()+","+P.getAddress()+","+P.getContactNo()+","+
				P.getEmail()+","+P.getAllergies()+","+ P.getBloodGroup()+","+P.getOtherDetails()+","+P.getUsername()+","+P.getPassword();
		sendData = Data.getBytes();

		System.out.println("REGISTERING  "+ Data);

		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
		clientSocket.send(sendPacket);

		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		clientSocket.receive(receivePacket);

		String ServerResponse = new String(receivePacket.getData());
		return ServerResponse;

	}

	public String ObtainProfile(String UID) throws IOException{
		System.out.println("REQUESTED PROFILE");
		Data = "4," +UID;
		sendData = Data.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
		clientSocket.send(sendPacket);

		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		clientSocket.receive(receivePacket);

		String ServerResponse = new String(receivePacket.getData());
		System.out.println(ServerResponse);
		return ServerResponse;

	}
	
	
	public String ObtainPatientUsingID(String UID)throws IOException{
		System.out.println("REQUESTED PROFILE");
		Data = "41," +UID;
		sendData = Data.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
		clientSocket.send(sendPacket);

		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		clientSocket.receive(receivePacket);

		String ServerResponse = new String(receivePacket.getData());
		System.out.println(ServerResponse);
		return ServerResponse;
		
	}

	public String ObtainDoctorProfile(String UID) throws IOException{
		System.out.println("REQUESTED PROFILE");
		Data = "9," +UID;
		sendData = Data.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
		clientSocket.send(sendPacket);

		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		clientSocket.receive(receivePacket);

		String ServerResponse = new String(receivePacket.getData());
		System.out.println(ServerResponse);

		return ServerResponse;

	}


	public ArrayList<Doctor> getAllDoctorsList() throws IOException{

		ArrayList<Doctor> doctorList=new ArrayList<>();
		System.out.println("REQUESTED DOCTORS LIST");
		String Data = "7,";
		sendData=new byte[2048];

		sendData=Data.getBytes();

		System.out.println(sendData.toString());
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
		clientSocket.send(sendPacket);

		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		clientSocket.receive(receivePacket);

		String ServerResponse = new String(receivePacket.getData());
		System.out.println("DOCTOR LIST: "+ServerResponse);

		ServerResponse=ServerResponse.trim();
		String [] items= ServerResponse.split("-");
		for(String i:items)
			System.out.println(i);

		System.out.println("ITEMS :"+ items.length);
		for(String docStr:items){
			String [] IItems= docStr.split(",");
			Doctor doctor=new Doctor(IItems[0], IItems[1],  IItems[2],  IItems[3],  IItems[4],
					IItems[5], IItems[6],  IItems[7],  IItems[8]);
			doctorList.add(doctor);
			System.out.println("OBTAINED :"+doctor.toString());
		}

		return doctorList;
	}

	public String RequestAppointment(String PID,String DID, String date,String Reason) throws IOException{

		System.out.println("REQUESTING APPOINTMENT");
		Data = "10," +PID+","+DID+","+date+","+Reason;

		sendData = Data.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
		clientSocket.send(sendPacket);
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		clientSocket.receive(receivePacket);

		String ServerResponse = new String(receivePacket.getData());
		System.out.println(ServerResponse);
		return ServerResponse;
	}

	public Doctor getDoctorById(String DID) throws IOException{

		System.out.println("REQUESTING DOCTOR");
		Data = "11,"+DID;

		sendData = Data.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
		clientSocket.send(sendPacket);
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		clientSocket.receive(receivePacket);

		String ServerResponse = new String(receivePacket.getData());
		String docStr=ServerResponse.trim();

		String [] IItems= docStr.split(",");
		Doctor doctor=new Doctor(IItems[0], IItems[1],  IItems[2],  IItems[3],  IItems[4],
				IItems[5], IItems[6],  IItems[7],  IItems[8]);

		return doctor;

	}

	public ArrayList<Appointment> getAppointmentList(String DID) throws IOException{
		
		ArrayList<Appointment> list=new ArrayList<>();
		
		System.out.println("REQUESTING APPOINTMENT");
		Data = "12,"+DID;

		sendData = Data.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
		clientSocket.send(sendPacket);
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		clientSocket.receive(receivePacket);

		String ServerResponse = new String(receivePacket.getData());
		System.out.println(ServerResponse);
		String ResponseString=ServerResponse.trim();
		String [] items= ResponseString.split("!");
		System.out.println("ITEMS :"+ items.length);
		for(String appStr:items){
			String [] IItems= appStr.trim().split(",");
			Appointment appointment=new Appointment(IItems[0], IItems[1], IItems[2], IItems[3], IItems[4], IItems[5], IItems[6]);
			System.out.println("OBTAINED : "+appointment.toString());
			list.add(appointment);
		}

		return list;
	}

	public String getID(String Uname,int Code) throws IOException{
		System.out.println("Requesting ID");
		Data = "100,"+Uname+","+Code;

		sendData = Data.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
		clientSocket.send(sendPacket);
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		clientSocket.receive(receivePacket);

		String ServerResponse = new String(receivePacket.getData());
		System.out.println(ServerResponse);
		return ServerResponse;
	}
	
	
	public ArrayList<Patient> getMyPatientsDetails(String DID) throws IOException{
		
		ArrayList<Patient> PList=new ArrayList<>();
		System.out.println("Requesting ID");
		Data = "13,"+DID;

		sendData = Data.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
		clientSocket.send(sendPacket);
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		clientSocket.receive(receivePacket);

		String ServerResponse = new String(receivePacket.getData());
		System.out.println(ServerResponse);
		
		String ResponseString=ServerResponse.trim();
		String [] items= ResponseString.split("!");
		System.out.println("ITEMS :"+ items.length);
		for(String appStr:items){
			String [] IItems= appStr.trim().split(",");
			Patient patient=new Patient(IItems[0], IItems[1], IItems[2], IItems[3], IItems[4], IItems[5], IItems[6],IItems[7],IItems[8],IItems[9],IItems[10]);
			System.out.println("OBTAINED : "+patient.toString());
			PList.add(patient);
		}

		return PList;
	}


	public void CloseConnection(){
		clientSocket.close();
	}
}
