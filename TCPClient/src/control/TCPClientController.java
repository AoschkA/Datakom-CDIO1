package control;
import java.io.*;
import java.net.*;

import boundary.TUI;
import exceptions.NoInputException;
import exceptions.WrongInputException;

public class TCPClientController {
	Socket clientSocket;
	DataOutputStream outToServer;
	BufferedReader inFromServer;
	TUI UI;
	boolean run=true, showWeight=false, extrainput=false,vent=true; 

	public void init() throws IOException, Exception {
		createUI("TUI");
		checkConnection();
		runClient();
	}
	public void createUI(String UIformat) {
		if (UIformat.equals("TUI")) {
			UI = new TUI();
		} 
	}
	
	// Connects to server
	public void connectToServer() throws IOException, NoRouteToHostException, ConnectException, NoInputException {
		String IP = UI.getIP();
		if (IP.isEmpty())
			throw new NoInputException();
		int port = UI.getPort();
		clientSocket = new Socket(IP, port); 
		outToServer = 
				new DataOutputStream(clientSocket.getOutputStream()); 
		inFromServer = 
				new BufferedReader(new
						InputStreamReader(clientSocket.getInputStream())); 
	}
	
	// Exceptionhandling on userinput in the main menu (runMenu())
	public void checkMenuInput() throws IOException, InterruptedException {
		try{	
			runMenu();
		} catch (WrongInputException e) {
			checkMenuInput();
		}
	}
	
	// Exceptionhandling on creation of connection to server
	public void checkConnection() throws IOException {
		try {
			connectToServer();
		} catch (NoInputException e) {
			UI.noInput();
			checkConnection();
		} catch (ConnectException e) {
			UI.connectionError();
			checkConnection();
		} catch (NoRouteToHostException e) {
			UI.connectionError();
			checkConnection();
		} catch (UnknownHostException e) {
			UI.connectionError();
			checkConnection();
		}
	}
	// Controls the user input, and executes the following funktions, matching the input command
	public void runMenu() throws IOException, WrongInputException, InterruptedException {
		int option = UI.getMenuOption();
		switch (option) {
		case 0: 	int input = getMessageInput();
					String usermessage1 = UI.getUserMessage(1);
					String usermessage2 = UI.getUserMessage(2);
					String usermessage3 = UI.getUserMessage(3);
					outToServer.writeBytes("RM20 "+input+" "+"\""+usermessage1+"\""+"\""+usermessage2+"\""+"\""+usermessage3+"\""+'\r'+'\n');
					extrainput=true;
					break;
		case 1: 	outToServer.writeBytes("Z"+ '\r'+'\n');		
					break;
		case 2: 	outToServer.writeBytes("T"+ '\r'+'\n');
					break;
		case 3: 	String display=UI.getDisplay();
					outToServer.writeBytes("D "+display + '\r'+'\n');
					break;
		case 4: 	UI.showQuitMessage();
					run=false;
					clientSocket.close(); 
					break;
		case 5: 	outToServer.writeBytes("S"+'\r'+'\n');	
					break;
		case 6: 
					outToServer.writeBytes("DW"+'\r'+'\n');	
					break;
		default:	throw new WrongInputException("Ukendt Input"); 
		}
	}
	
	// Runs client after connection is found
	public void runClient() throws IOException, InterruptedException{
		System.out.println("Velkommen til vægt 10000 v800.67");
		while(run) {
			checkMenuInput();
			if (run) {
				UI.showMessage(inFromServer.readLine());
					if (extrainput) {
						UI.showMessage("Please wait");
							// checks for server input every second
							while(vent){
								Thread.sleep(1000);
								String answer = inFromServer.readLine();
									if(!answer.equals(null)){
										UI.showMessage(answer);
										break;
									}
							}	
					}
			}
		}
	}
	// controls if message is Integer or Alfanum
	public int getMessageInput() throws IOException {
		try {
		String input = UI.getMessageOption();
		if (input.equals("I")) {
			return 4;
		} else if (input.equals("A")) {
			return 8;
		} else {
			throw new WrongInputException("Ukendt Input");
		}
		} catch (WrongInputException e) {
			UI.unknownMessage();
			getMessageInput();
		}
		return -1;
	}
}


