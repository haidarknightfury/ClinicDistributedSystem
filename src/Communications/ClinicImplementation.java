package Communications;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;

import clinicinterface.Appointment;
import clinicinterface.ClinicInterface;
import clinicinterface.Doctor;
import clinicinterface.Patient;
import clinicinterface.PendingAppointment;

public class ClinicImplementation extends UnicastRemoteObject implements ClinicInterface{
	
	Connections Conn;

	protected ClinicImplementation() throws RemoteException {
		super();
		try {
			Conn=new Connections();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void ConfirmAppointment(String arg0, String arg1, String arg2, String arg3, String arg4)
			throws RemoteException {
		try {
			Conn.ConfirmAppointment(arg0, arg1, arg2, arg3, arg4);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public ArrayList<Appointment> GetAllAppointments() throws RemoteException {
		try {
			return Conn.GetAllAppointments();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ArrayList<Doctor> GetAllDoctors() throws RemoteException {
		try {
			return Conn.GetAllDoctors();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ArrayList<Patient> GetAllPatients() throws RemoteException {
		try {
			return Conn.GetAllPatients();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ArrayList<Appointment> GetAppointments(String arg0) throws RemoteException {
		try {
			return Conn.GetAppointments(arg0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ArrayList<Appointment> GetAppointments(String arg0, String arg1) throws RemoteException {
		try {
			return Conn.GetAppointments(arg0,arg1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String GetID(String arg0, int arg1) throws RemoteException {
	    return Conn.GetID(arg0, arg1);
	}

	@Override
	public void InsertPendingAppointment(String arg0, String arg1, String arg2, String arg3) throws RemoteException {
		 Conn.InsertPendingAppointment(arg0, arg1, arg2, arg3);
	}

	@Override
	public ArrayList<PendingAppointment> ListOfPendingAppointments(String arg0) throws RemoteException {
		try {
			return Conn.ListOfPendingAppointments(arg0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int Login(String arg0, String arg1) throws RemoteException {
		try {
			return Conn.Login(arg0, arg1);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public void ObtainMyDoctors(String arg0) throws RemoteException {
		Conn.ObtainMyDoctors(arg0);
	}

	@Override
	public ArrayList<Patient> ObtainMyPatients(String arg0) throws RemoteException {
		return Conn.ObtainMyPatients(arg0);
	}

	@Override
	public void UpdateHistory(String arg0, String arg1, String arg2, String arg3, String arg4) throws RemoteException {
		try {
			Conn.UpdateHistory(arg0, arg1, arg2, arg3, arg4);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Doctor getDoctor(String arg0) throws RemoteException {
		try {
			return Conn.getDoctor(arg0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Doctor getDoctorDetails(String arg0) throws RemoteException {
		try {
			return Conn.getDoctorDetails(arg0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Patient getPatient(String arg0) throws RemoteException {
		try {
			return Conn.getPatient(arg0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Patient getPatientUsingID(String arg0) throws RemoteException {
		try {
			return Conn.getPatientUsingID(arg0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}

	@Override
	public void InsertPatient(Patient arg0) throws RemoteException {
		Conn.InsertPatient(arg0);
	}

	@Override
	public void InsertAppointment(Appointment arg0) throws RemoteException {
	     Conn.InsertAppointment(arg0);
	}

}
