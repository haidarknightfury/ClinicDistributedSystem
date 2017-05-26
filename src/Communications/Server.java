package Communications;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.security.cert.PKIXRevocationChecker.Option;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import clinicinterface.Appointment;
import clinicinterface.ClinicInterface;
import clinicinterface.Doctor;
import clinicinterface.Patient;
import clinicinterface.PendingAppointment;


public class Server {

	public static ExecutorService executor;
	public static Connections Conn;

	//THREAD PER REQUEST MODEL

	public static void main(String args[]) throws Exception {
		DatagramSocket serverSocket = new DatagramSocket(9876);

		byte[] receiveData;
		byte[] sendData = new byte[2048];

		ArrayList<Patient> patientList = new ArrayList<>();
		ArrayList<Doctor> doctorList=new ArrayList<>();
		String Response="";

		int NumProcessors=Runtime.getRuntime().availableProcessors();
		executor=Executors.newFixedThreadPool(NumProcessors);

		System.out.println("Server ready and waiting for clients to connect..");
		Conn=new Connections();

		/*
		 * 1- LOGIN
		 * 2- REGISTERING PATIENT
		 * 3- RETRIEVE ALL PATIENTS
		 * 4- RETRIEVE PATIENT DETAILS
		 * 
		 * 5- RETRIEVE DOCTOR DETAILS
		 * 6- REGISTERING DOCTOR
		 * 7- RETRIEVE ALL DOCTORS
		 * 8- INSERT APPOINTMENT
		 * 9- INSERT HISTORY
		 *10-REQUEST APPOINTMENT
		 * 
		 */
		while (true) {
			receiveData = new byte[2048];
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			serverSocket.receive(receivePacket);
			executor.submit(new ProcessThread(receivePacket, serverSocket));
		}

	}

	public static class ProcessThread implements Runnable{
		public DatagramPacket receivePacket;
		public DatagramSocket ServerSocket;
		public String Response;
		public byte[] sendData = new byte[2048];

		public ProcessThread(DatagramPacket PK,DatagramSocket DS){
			this.receivePacket=PK;
			this.ServerSocket=DS;
		}

		@Override
		public void run() {

			String sentence = new String(receivePacket.getData());
			System.out.println("INTIAL REQUEST "+sentence );

			try{

				Response="";
				String[] Options = sentence.split(",");
				switch (Integer.parseInt(Options[0])) {

				case 1:
					String UserName=Options[1];
					String Password=Options[2];

					int ResponseCode=Conn.Login(UserName, Password);
					if(ResponseCode==1){ 
						Response="Patient";
					}
					else if(ResponseCode==2){
						Response="Doctor";
					}
					else {
						Response="Wrong Credentials";
					}
					break;

					//PATIENT OPERATIONS//

				case 2:
					Patient patient=(new Patient(Options[1], Options[2],Options[3],Options[4],Options[5],Options[6],Options[7],Options[8],Options[9],Options[10]));
					System.out.println("INSERTING PATIENT "+ patient.toString());
					Conn.InsertPatient(patient);
					Response = "Patient Successfully Registered";
					break;

				case 3:
					ArrayList<Patient> PatientList= Conn.GetAllPatients();
					for(Patient p :PatientList){
						Response +=p.getFirstName()+","+p.getOtherNames()+","+p.getAddress()+","+p.getContactNo()+","+p.getEmail()+","+
								p.getEmail()+","+p.getAllergies()+","+p.getBloodGroup()+","+p.getOtherDetails()+","+p.getUsername()+","+p.getPassword()+"|";
					}
					break;

				case 4:
					Patient p= Conn.getPatient(Options[1]);
					System.out.println("OBTAINED REQUEST : VIEW "+ Options[1]);
					Response= p.getFirstName()+","+p.getOtherNames()+","+p.getAddress()+","+p.getContactNo()+","+
							p.getEmail()+","+p.getAllergies()+","+p.getBloodGroup()+","+p.getOtherDetails()+","+p.getUsername()+","+p.getPassword();
					break;

				case 41: 
					Patient p1= Conn.getPatientUsingID(Options[1]);
					System.out.println("OBTAINED REQUEST : VIEW "+ Options[1]);
					Response= p1.getFirstName()+","+p1.getOtherNames()+","+p1.getAddress()+","+p1.getContactNo()+","+
							p1.getEmail()+","+p1.getAllergies()+","+p1.getBloodGroup()+","+p1.getOtherDetails()+","+p1.getUsername()+","+p1.getPassword();
					break;

					//DOCTOR OPERATIONS
				case 5:
					Doctor doc= Conn.getDoctor(Options[1]);
					System.out.println("OBTAINED REQUEST : VIEW "+ Options[1]);
					Response= doc.getFirstName()+","+doc.getOtherNames()+","+doc.getSpecialization()+","+doc.getContactNumber()+","+doc.getAddress()+","+
							doc.getEmail()+","+doc.getUsername()+","+doc.getPassword();
					break;	


				case 6: 
					Doctor doctor=(new Doctor(Options[1], Options[2],Options[3],Options[4],Options[5],Options[6],Options[7],Options[8],Options[9]));
					System.out.println("INSERTING PATIENT "+ doctor.toString());
					Conn.InsertDoctor(doctor);
					Response = "Doctor Successfully Registered";
					break;

				case 7:
					ArrayList<Doctor> doctorsList= Conn.GetAllDoctors();
					for(Doctor d: doctorsList){
						Response +=d.getID()+","+d.getFirstName()+","+d.getOtherNames()+","+d.getSpecialization()+","+d.getContactNumber()+","+d.getAddress()+","+
								d.getEmail()+","+d.getUsername()+","+d.getPassword()+"-";
					}
					break;

				case 8: 
					Appointment ap=new Appointment(Options[1], Options[2], Options[3], Options[4], Options[5], Options[6], Options[7]);
					Conn.InsertAppointment(ap);
					break;

				case 9: 
					Doctor d= Conn.getDoctor(Options[1]);
					System.out.println("OBTAINED REQUEST : VIEW "+ Options[1]);
					Response =d.getID()+","+d.getFirstName()+","+d.getOtherNames()+","+d.getSpecialization()+","+d.getContactNumber()+","+d.getAddress()+","+
							d.getEmail()+","+d.getUsername()+","+d.getPassword();
					break;

				case 10:
					Conn.InsertPendingAppointment(Options[1], Options[2], Options[3], Options[4]);
					Response="SUCCESSFULLY REQUESTED APPOINTMENT";
					break;

				case 11:
					System.out.println("OBTAIN DOCTOR WITH ID : "+Options[1]);
					Doctor newDoc= Conn.getDoctorDetails(Options[1]);
					System.out.println(newDoc.toString());
					Response= newDoc.getFirstName()+","+newDoc.getOtherNames()+","+newDoc.getSpecialization()+","+newDoc.getContactNumber()+","+newDoc.getAddress()+","+
							newDoc.getEmail()+","+newDoc.getUsername()+","+newDoc.getPassword();
					break;

				case 12:
					System.out.println("APPOINTMENT FOR DOCTOR :"+ Options[1]);
					ArrayList<Appointment> app=Conn.GetAppointments(Options[1]);
					for(Appointment A:app){
						System.out.println(A.toString());
						Response+=A.getAppointmentDate()+","+A.getAppointmentStartTime()+","+A.getAppointmentEndTime()+","+
								A.getDocId()+","+A.getPatientId()+","+A.getHistory()+","+A.getPrescription()+"!";
					}

					break;

				case 13:
					System.out.println("OBTAINING MY PATIENTS");
					ArrayList<Patient> patients=Conn.ObtainMyPatients(Options[1]);
					for(Patient pat:patients){
						Response +=pat.getPid()+","+pat.getFirstName()+","+pat.getOtherNames()+","+pat.getAddress()+","+pat.getContactNo()+","+pat.getEmail()+","+
								pat.getEmail()+","+pat.getAllergies()+","+pat.getBloodGroup()+","+pat.getOtherDetails()+","+pat.getUsername()+","+pat.getPassword()+"!";

					}
					System.out.println(Response);
					break;

				case 100:
					System.out.println("REQUESTING ID :"+ Options [1]+" , "+Options[2]);
					String ID= Conn.GetID(Options[1], Integer.parseInt(Options[2].trim()));
					Response=ID;
					break;

				default:break;
				}

				sendData = Response.getBytes();
				InetAddress IPAddress = receivePacket.getAddress();
				int port = receivePacket.getPort();
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
				ServerSocket.send(sendPacket);

			}

			catch (Exception e) {
				System.out.println(e.toString());
			}
		}
	}
}
