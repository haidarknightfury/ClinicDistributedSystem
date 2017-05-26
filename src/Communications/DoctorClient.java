package Communications;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class DoctorClient {
	public static void main(String args[]) throws Exception {
		BufferedReader IN = new BufferedReader(new InputStreamReader(System.in));
		DatagramSocket clientSocket = new DatagramSocket();
		InetAddress IPAddress = InetAddress.getByName("localhost");
		byte[] sendData = new byte[1024];
		byte[] receiveData = new byte[1024];
		String Data="";

		int Option = 0;
		while (Option != 5) {
			System.out.print("FROM DOCTOR CLIENT:ENTER NUMBER\n" +"1- REGISTER\n" +"2- VIEW DETAILS\n" +
					         "3- DELETE ACCOUNT\n" +"4- VIEW CLIENT DETAILS\n"+"5- EXIT");
			String sentence = IN.readLine();
			Option=Integer.parseInt(sentence);
			switch (Option) {
			case 1:
				System.out.println("ENTER FIRST NAME");
				String FirstName = IN.readLine();
				System.out.println("ENTER OTHER NAME");
				String OtherNames = IN.readLine();
				System.out.println("ENTER SPECIALISATION");
				String Specs = IN.readLine();
				System.out.println("ENTER CONTACT NUMBER");
				String contactNo = IN.readLine();
				Data = "4," + FirstName + "," + OtherNames + "," +Specs + "," +contactNo;

				break;
			case 2:
				System.out.println("ENTER FIRST NAME");
				String FName = IN.readLine();
				System.out.println("ENTER OTHER NAME");
				String ONames = IN.readLine();
				Data = "5," + FName + "," + ONames;
				break;


			case 3:
				System.out.println("ENTER FIRST NAME");
				String fName = IN.readLine();
				System.out.println("ENTER OTHER NAME");
				String oNames = IN.readLine();
				Data = "6," + fName + "," + oNames;

			case 4:
				System.out.println("ENTER CLIENT FIRST NAME");
				String CfName = IN.readLine();
				System.out.println("ENTER OTHER NAME");
				String CoNames = IN.readLine();
				Data = "7," + CfName + "," + CoNames;
				break;
				
			case 5:
				clientSocket.close();
				break;
				
			}
			sendData = Data.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
			clientSocket.send(sendPacket);


			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			clientSocket.receive(receivePacket);

			String ModifiedSentence = new String(receivePacket.getData());
			System.out.println("FROM SERVER: " + ModifiedSentence);

		}



	}

}
