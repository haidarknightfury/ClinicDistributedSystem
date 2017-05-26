package Communications;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import clinicinterface.Appointment;
import clinicinterface.ClinicInterface;
import clinicinterface.Doctor;
import clinicinterface.Patient;
import clinicinterface.PendingAppointment;


public class Connections {
	public static Connection connection;
	public static Statement statement;

	public Connections() throws ClassNotFoundException{
		try{
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:clinic.db");
			statement = connection.createStatement();

			statement.executeUpdate(CreatePatientTable());
			statement.executeUpdate(CreateDoctorTable());
			statement.executeUpdate(CreateAppointmentTable());
			statement.executeUpdate(CreatePendingAppointmentTable());

			System.out.println("Connection to SQLite has been established.");
		}
		catch(SQLException e){  System.err.println(e.getMessage()); }       
	}

	public void DropTable() throws SQLException{
		String dropStatement="DROP TABLE appointment";
		statement.execute(dropStatement);
		//dropStatement="DROP TABLE patient";
		//statement.execute(dropStatement);

	}


	public void ClearContents() throws SQLException{
		String clsStatement="DELETE FROM doctor";
		statement.execute(clsStatement);

		clsStatement="DELETE FROM appointment";
		statement.execute(clsStatement);
	}

	public int Login(String UserName,String Password) throws SQLException{
		
		System.out.println("OBTAINED :"+ UserName);
		System.out.println("OBTAINED :"+ Password);

		UserName=UserName.trim();
		Password=Password.trim();

		ResultSet resultSet = statement.executeQuery("SELECT * from patient WHERE Username = '"+UserName+"' AND Password = '"+Password+"'");
		if(resultSet.next()){
			return 1;
		}

		ResultSet resultSet2=statement.executeQuery("SELECT * from doctor WHERE Username = '"+UserName+"' AND Password = '"+Password+"'");
		if(resultSet2.next()){
			return 2;
		}
		return 0;
	}

	public ArrayList<Patient> GetAllPatients() throws SQLException{
		ResultSet resultSet = statement.executeQuery("SELECT * from patient");
		ArrayList<Patient>patients=new ArrayList<>();
		while(resultSet.next())
		{
			String PID=resultSet.getString("PID");
			String FirstName=resultSet.getString("FirstName");
			String OtherNames=resultSet.getString("OtherNames");
			String Address=resultSet.getString("Address");
			String ContactNumber=resultSet.getString("ContactNumber");
			String Email=resultSet.getString("Email");
			String Allergies=resultSet.getString("Allergies");
			String BloodGroup=resultSet.getString("BloodGroup");
			String OtherDetails=resultSet.getString("OtherDetails");
			String Username=resultSet.getString("Username");
			String Password=resultSet.getString("Password");

			Patient patient=new Patient(FirstName,OtherNames,Address,ContactNumber,Email,Allergies,BloodGroup,OtherDetails,Username,Password);
			patients.add(patient);

			System.out.println(patient);
		}

		return patients;
	}

	public Patient getPatient(String Uname) throws SQLException{
		String query="SELECT * from patient WHERE Username ='"+Uname.trim()+"'";
		System.out.println(query);
		ResultSet resultSet = statement.executeQuery(query);
		Patient patient=null;
		while(resultSet.next())
		{
			String PID=resultSet.getString("PID");
			String FirstName=resultSet.getString("FirstName");
			String OtherNames=resultSet.getString("OtherNames");
			String Address=resultSet.getString("Address");
			String ContactNumber=resultSet.getString("ContactNumber");
			String Email=resultSet.getString("Email");
			String Allergies=resultSet.getString("Allergies");
			String BloodGroup=resultSet.getString("BloodGroup");
			String OtherDetails=resultSet.getString("OtherDetails");
			String Username=resultSet.getString("Username");
			String Password=resultSet.getString("Password");

			patient=new Patient(FirstName,OtherNames,Address,ContactNumber,Email,Allergies,BloodGroup,OtherDetails,Username,Password);
			System.out.println(patient);

		}
		return patient;
	}

	public Patient getPatientUsingID(String ID) throws SQLException{
		String query="SELECT * from patient WHERE PID ='"+ID.trim()+"'";
		System.out.println(query);
		ResultSet resultSet = statement.executeQuery(query);
		Patient patient=null;
		while(resultSet.next())
		{
			String PID=resultSet.getString("PID");
			String FirstName=resultSet.getString("FirstName");
			String OtherNames=resultSet.getString("OtherNames");
			String Address=resultSet.getString("Address");
			String ContactNumber=resultSet.getString("ContactNumber");
			String Email=resultSet.getString("Email");
			String Allergies=resultSet.getString("Allergies");
			String BloodGroup=resultSet.getString("BloodGroup");
			String OtherDetails=resultSet.getString("OtherDetails");
			String Username=resultSet.getString("Username");
			String Password=resultSet.getString("Password");

			patient=new Patient(PID,FirstName,OtherNames,Address,ContactNumber,Email,Allergies,BloodGroup,OtherDetails,Username,Password);
			System.out.println(patient);

		}
		return patient;
	}

	public Doctor getDoctor(String Uname) throws SQLException{
		ResultSet resultSet = statement.executeQuery("SELECT * from doctor WHERE Username = '"+Uname.trim()+"'");
		Doctor doc=null;
		while(resultSet.next())
		{
			String PID=resultSet.getString("DID");
			String FirstName=resultSet.getString("FirstName");
			String OtherNames=resultSet.getString("OtherNames");
			String Specialization=resultSet.getString("Specialization");
			String ContactNumber=resultSet.getString("ContactNumber");
			String Address=resultSet.getString("Address");
			String Email=resultSet.getString("Email");
			String Username=resultSet.getString("Username");
			String Password=resultSet.getString("Password");
			
			doc=new Doctor(PID,FirstName,OtherNames,Specialization,ContactNumber,Address,Email,Username,Password);
			System.out.println(doc);
		}
		return doc;
	}

	public ArrayList<Doctor> GetAllDoctors() throws SQLException{
		ResultSet resultSet = statement.executeQuery("SELECT * from doctor");
		ArrayList<Doctor>doctors=new ArrayList<>();
		while(resultSet.next())
		{
			String DID=resultSet.getString("DID");
			String FirstName=resultSet.getString("FirstName");
			String OtherNames=resultSet.getString("OtherNames");
			String Specialization=resultSet.getString("Specialization");
			String ContactNumber=resultSet.getString("ContactNumber");
			String Email=resultSet.getString("Email");
			String Address=resultSet.getString("Address");
			String Username=resultSet.getString("Username");
			String Password=resultSet.getString("Password");

			Doctor doc=new Doctor(DID,FirstName,OtherNames,Specialization,ContactNumber,Address,Email,Username,Password);
			doctors.add(doc);
			System.out.println(doc.toString());
		}

		return doctors;
	}

	public void InsertPendingAppointment(String DocID,String PID,String date, String reason){
		try {
			String query="INSERT INTO pendingAppointment values('"+DocID.trim()+"', '"+PID.trim()+"', '"+date.trim()+"'"+ ", '"+reason.trim()+"')";
			System.out.println(query);
			statement.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}

	public void ConfirmAppointment(String DocID,String PID,String date,String STime,String ETime) throws SQLException{
		ResultSet resultSet = statement.executeQuery("SELECT * from pendingAppointment WHERE DID="+DocID+"AND PID="+PID+"AND date="+date);
		if(resultSet!=null){
			InsertAppointment(new Appointment(date, STime, ETime, DocID, PID, "", ""));
			statement.executeUpdate("DELETE FROM pendingAppointment WHERE DID="+DocID+"AND PID="+PID+"AND date="+date);
		}
	}

	public void UpdateHistory(String DocID,String PID,String date,String History,String Presc) throws SQLException{
		statement.executeUpdate("UPDATE TABLE appointment SET History="+History+"AND Prescription="+Presc+"WHERE DID="+DocID+"AND PID="+PID+"AND date="+date);
	}

	public ArrayList<PendingAppointment> ListOfPendingAppointments(String DOCID) throws SQLException{
		ArrayList<PendingAppointment> appointments=new ArrayList<>();
		ResultSet resultSet = statement.executeQuery("SELECT * from pendingAppointment WHERE DID="+DOCID);
		while(resultSet.next())
		{
			String DID=resultSet.getString("DID");
			String PID=resultSet.getString("PID");
			String date=resultSet.getString("Date");
			String Reason=resultSet.getString("Reason");

			PendingAppointment pendingAppointment=new PendingAppointment(DID,PID,Reason,date);
			appointments.add(pendingAppointment);
		}
		return appointments;
	}

	public ArrayList<Appointment>GetAppointments(String DOCID,String dte) throws SQLException{
		ArrayList<Appointment> appointments=new ArrayList<>();
		String getAppQuery= "SELECT * from appointment WHERE DID= '"+DOCID+"' AND Date= '"+dte+"'";
		System.out.println("getAppointmentQuery : "+ getAppQuery);
		ResultSet resultSet = statement.executeQuery(getAppQuery);
		while(resultSet.next())
		{

			String DID=resultSet.getString("DID");
			String PID=resultSet.getString("PID");
			String date=resultSet.getString("Date");
			String STime=resultSet.getString("StartTime");
			String ETime=resultSet.getString("EndTime");
			String History=resultSet.getString("History");
			String Prescription= resultSet.getString("Prescription");

			Appointment appointment=new Appointment(date, STime, ETime, DID, PID, History, Prescription);
			appointments.add(appointment);
		}
		return appointments;
	}
	
	
	public ArrayList<Appointment>GetAppointments(String DOCID) throws SQLException{
		ArrayList<Appointment> appointments=new ArrayList<>();
		String getAppQuery= "SELECT * FROM appointment WHERE DID= '"+DOCID.trim()+"'";
		System.out.println("getAppointmentQuery : "+ getAppQuery);
		ResultSet resultSet = statement.executeQuery(getAppQuery);
		while(resultSet.next())
		{
			
			String DID=resultSet.getString("DID");
			String PID=resultSet.getString("PID");
			String date=resultSet.getString("Date");
			String STime=resultSet.getString("StartTime");
			String ETime=resultSet.getString("EndTime");
			String History=resultSet.getString("History");
			String Prescription= resultSet.getString("Prescription");

			Appointment appointment=new Appointment(date, STime, ETime, DID, PID, History, Prescription);
			appointments.add(appointment);
		}
		return appointments;
	}



	public ArrayList<Appointment>GetAllAppointments() throws SQLException{
		ArrayList<Appointment> appointments=new ArrayList<>();
		ResultSet resultSet = statement.executeQuery("SELECT * from appointment");
		while(resultSet.next())
		{

			String DID=resultSet.getString("DID");
			String PID=resultSet.getString("PID");
			String date=resultSet.getString("Date");
			String STime=resultSet.getString("StartTime");
			String ETime=resultSet.getString("EndTime");
			String History=resultSet.getString("History");
			String Prescription= resultSet.getString("Prescription");

			Appointment appointment=new Appointment(date, STime, ETime, DID, PID, History, Prescription);
			appointments.add(appointment);
		}
		return appointments;
	}



	public static String CreateDoctorTable(){
		String CreateStatement="CREATE TABLE  IF NOT EXISTS doctor (DID STRING, FirstName STRING, OtherNames STRING, Specialization STRING,"
				+ "ContactNumber STRING, Address STRING, Email STRING, Username STRING, Password STRING)";
		return CreateStatement;
	}

	public static String CreatePatientTable(){
		String CreateStatement="CREATE TABLE IF NOT EXISTS patient (PID STRING, FirstName STRING, OtherNames STRING, Address STRING,"
				+ "ContactNumber STRING, Email STRING, Allergies STRING,BloodGroup STRING, OtherDetails STRING,Username STRING,Password STRING)";
		return CreateStatement;
	}

	public synchronized void InsertPatient(Patient patient){
		try {

			System.out.println("PASSWORD "+ patient.getPassword());
			System.out.println("TRY  "+"INSERT INTO patient values('"+patient.getPid()+"', '"+patient.getFirstName()+"', '"+patient.getOtherNames()+"'"
					+ ", '"+patient.getAddress()+"', '"+patient.getContactNo()+"', '"+patient.getEmail()+"','"+patient.getAllergies()+"','"+patient.getBloodGroup()+"','"+patient.getOtherDetails()+"'"
					+ ",'"+patient.getUsername()+"'"+ ",'"+patient.getPassword().trim().toString()+"'"+ ")");

			String InsertQuery=("INSERT INTO patient values('"+patient.getPid()+"', '"+patient.getFirstName()+"', '"+patient.getOtherNames()+"'"
					+ ", '"+patient.getAddress()+"', '"+patient.getContactNo()+"', '"+patient.getEmail()+"','"+patient.getAllergies()+"','"+patient.getBloodGroup()+"','"+patient.getOtherDetails()+"'"
					+ ",'"+patient.getUsername()+"'"+ ",'"+patient.getPassword().trim().toString()+"'"+ ")");

			System.out.println(InsertQuery);
			statement.executeUpdate(InsertQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}

	public static String CreateAppointmentTable(){
		String CreateStatement="CREATE TABLE  IF NOT EXISTS appointment (DID STRING, PID STRING, Date STRING, StartTime STRING,EndTime STRING,History STRING,Prescription STRING)";
		return CreateStatement;
	}

	public static String CreatePendingAppointmentTable(){
		String CreateStatement="CREATE TABLE  IF NOT EXISTS pendingAppointment (DID STRING, PID STRING,Date STRING, Reason STRING)";
		return CreateStatement;
	}

	public synchronized void InsertDoctor(Doctor doctor){
		try {
			statement.executeUpdate("INSERT INTO doctor values('"+doctor.getID()+"', '"+doctor.getFirstName()+"', '"+doctor.getOtherNames()+"'"
					+ ", '"+doctor.getSpecialization()+"', '"+doctor.getContactNumber()+"','"+doctor.getAddress()+"','"+doctor.getEmail()+"','"+doctor.getUsername()+"','"+doctor.getPassword()+"')");
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}

	public synchronized void InsertAppointment(Appointment ap){
		try {
			String appStatement=("INSERT INTO appointment values('"+ap.getDocId()+"', '"+ap.getPatientId()+"', '"+ap.getAppointmentDate()+"'"+
					"', '"+ap.getAppointmentStartTime()+"'"+ ", '"+ap.getAppointmentEndTime()+"', '"+ap.getHistory()+"', '"+ap.getPrescription()+"')");
			System.out.println(appStatement);
			statement.executeUpdate("INSERT INTO appointment values('"+ap.getDocId()+"', '"+ap.getPatientId()+"', '"+ap.getAppointmentDate()+"'"+
					", '"+ap.getAppointmentStartTime()+"'"+ ", '"+ap.getAppointmentEndTime()+"', '"+ap.getHistory()+"', '"+ap.getPrescription()+"')");
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}

	public synchronized void UpdateAppointment (Appointment ap){
		try{
			String updateQuery= "UPDATE appointment" +
					" SET History = '"+ap.getHistory()+"' , Prescription = '"+ap.getPrescription()+
					"'  WHERE DID = "+  ap.getDocId() +" AND PID = '"+ap.getPatientId()+"' AND Date= '"+ ap.getAppointmentDate() +"' AND StartTime = '"+ap.getAppointmentStartTime()+"'";
			System.out.println("UPDATE APP "+ updateQuery);
			statement.executeUpdate(updateQuery);
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
	}

	public synchronized ArrayList<Patient> ObtainMyPatients(String DID){
		ArrayList<Patient>myPatientList=new ArrayList<>();
		
		try{
			String myPatientsIDQuery= "SELECT PID"
					+ " FROM appointment"
					+ " WHERE DID = '"+ DID.trim()+"'";

			System.out.println("FQ "+ myPatientsIDQuery);
			ResultSet resultSet= statement.executeQuery(myPatientsIDQuery);
			while(resultSet.next())
			{
				String PID=resultSet.getString("PID");
				String myPatients= "SELECT *"
						+ " FROM patient WHERE"
						+ " PID = '"+PID+"'";

				System.out.println("OBTAIN MY PATIENTS "+ myPatients);
				ResultSet PRS= statement.executeQuery(myPatients);
				while(PRS.next())
				{
					String Pid=PRS.getString("PID");
					String FirstName=PRS.getString("FirstName");
					String OtherNames=PRS.getString("OtherNames");
					String Address=PRS.getString("Address");
					String ContactNumber=PRS.getString("ContactNumber");
					String Email=PRS.getString("Email");
					String Allergies=PRS.getString("Allergies");
					String BloodGroup=PRS.getString("BloodGroup");
					String OtherDetails=PRS.getString("OtherDetails");
					String Username=PRS.getString("Username");
					String Password=PRS.getString("Password");

					Patient patient=new Patient(Pid,FirstName,OtherNames,Address,ContactNumber,Email,Allergies,BloodGroup,OtherDetails,Username,Password);
					System.out.println(" MYPATIENT "+patient);
					myPatientList.add(patient);
				}
			}
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
		return myPatientList;
	}


	public synchronized void ObtainMyDoctors(String PID){
		try{
			String myDoctorsIDQuery= "SELECT DID"
					+ " FROM appointment"
					+ " WHERE PID = '"+ PID.trim()+"'";

			System.out.println("FQ "+ myDoctorsIDQuery);
			ResultSet resultSet= statement.executeQuery(myDoctorsIDQuery);

			while(resultSet.next())
			{
				String DID=resultSet.getString("DID");
				String myDoctorsQuery= "SELECT *"
						+ " FROM doctor WHERE"
						+ " DID = '"+DID+"'";

				System.out.println("OBTAIN MY DOCTORS "+ myDoctorsQuery);
				ResultSet DRS= statement.executeQuery(myDoctorsQuery);
				while(DRS.next())
				{
					String did=DRS.getString("DID");
					String FirstName=DRS.getString("FirstName");
					String OtherNames=DRS.getString("OtherNames");
					String Specialization=DRS.getString("Specialization");
					String ContactNumber=DRS.getString("ContactNumber");
					String Email=DRS.getString("Email");
					String Address=DRS.getString("Address");
					String Username=DRS.getString("Username");
					String Password=DRS.getString("Password");

					Doctor doc=new Doctor(did,FirstName,OtherNames,Specialization,ContactNumber,Address,Email,Username,Password);
					System.out.println(doc.toString());
				}
			}
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
	}

	public synchronized String GetID(String UName,int sCode){
		String id="";
		try{
			System.out.println("GETTING ID FOR "+UName);
			if(sCode==0){
				String query="SELECT PID FROM patient WHERE Username = '"+UName.trim()+"'";
				System.out.println(query);
				ResultSet RS= statement.executeQuery(query);
				while(RS.next()){
					id=RS.getString(1);
					return id;
				}
			}
			else{
				String query="SELECT DID FROM doctor WHERE Username = '"+UName.trim()+"'";
				ResultSet RS= statement.executeQuery(query);
				while(RS.next()){
					id=RS.getString(1);
					return id;
				}
			}

		}
		catch(Exception E){
               System.out.println(E.toString());
		}
		return id;
	}
	
	
	public Doctor getDoctorDetails(String DocID) throws SQLException{
		
		String query="SELECT * FROM doctor WHERE DID = '"+DocID.trim()+"'";
		System.out.println(query);
		ResultSet resultSet = statement.executeQuery(query);
		Doctor doc=null;
		while(resultSet.next())
		{
			String DID=resultSet.getString("DID");
			String FirstName=resultSet.getString("FirstName");
			String OtherNames=resultSet.getString("OtherNames");
			String Specialization=resultSet.getString("Specialization");
			String ContactNumber=resultSet.getString("ContactNumber");
			String Address=resultSet.getString("Address");
			String Email=resultSet.getString("Email");
			String Username=resultSet.getString("Username");
			String Password=resultSet.getString("Password");

			doc=new Doctor(DID,FirstName,OtherNames,Specialization,ContactNumber,Address,Email,Username,Password);
			System.out.println("DOCTOR OBTAINED : "+doc);
		}
		return doc;
	}


	public static void main(String [] args) throws ClassNotFoundException, SQLException{
		Connections conn=new Connections();
		//conn.InsertPatient(new Patient("Dargaye","Haidar", "Curepipe", "59798148", "h.gmail.com", "none", "B+", "none","haidar", "1234"));
		//conn.InsertDoctor(new Doctor("D001", "House", "Shaw", "Psychiatrist", "57983424", "PL", "house@mail.com", "house", "1234"));
		System.out.println(conn.Login("house", "1234"));
		conn.GetAllPatients();
		conn.GetAllDoctors();

		//conn.InsertAppointment(new Appointment("12-05-2017","08:05","09:15","1","2","mild fever","panadol"));
		//conn.UpdateAppointment(new Appointment("12-05-2017","08:05","09:15","1","2","mild fever","panadol"));


		//conn.InsertPatient(new Patient("P001","Stark","Ned", "North", "59798148", "ned.gmail.com", "none", "B+", "none","ned", "1234"));



		ArrayList<Appointment> applist=conn.GetAppointments("D007", "12-05-2017");
		for(Appointment A:applist){
			System.out.println("APP " +A.toString());
		}


		ArrayList<Appointment> Allapplist=conn.GetAllAppointments();
		for(Appointment A:Allapplist){
			//System.out.println("APP " +A.toString());
		}

		conn.ObtainMyPatients("D008");
		conn.ObtainMyDoctors("P001");
		
		System.out.println(conn.GetID("bran", 1));
	
		//conn.InsertDoctor(new Doctor("D008", "Walder", "Frey", "Cardiologue", "573873424", "Curepipe", "walder@mail.com", "walder", "1234"));
        //conn.InsertPendingAppointment("D007", "P001", "12-07-2019", "running nose");
		
		conn.getDoctorDetails("D008");
		
		//conn.InsertAppointment(new Appointment("12-08-2017", "09:00", "09:30", "D008", "P001", "NONE", "Panadol"));
        
	}

}
