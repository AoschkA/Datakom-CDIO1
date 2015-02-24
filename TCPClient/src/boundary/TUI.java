package boundary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TUI implements ITCPClient{
	BufferedReader inFromUser = 
	          new BufferedReader(new InputStreamReader(System.in));

	@Override
	public String getIP() throws IOException {
		System.out.println("Indtast server IP");
		return inFromUser.readLine();
	}

	@Override
	public int getPort() throws IOException {
		int	message = 0;
		System.out.println("Indtast server port");
		String m = inFromUser.readLine();
		try {
			message = Integer.parseInt(m);
		} catch (NumberFormatException e) {
			System.out.println("Port ikke valid");
			getPort();
		}
		return message;
		
	}

	@Override
	public void noConnection() throws IOException {
		System.out.println("No connection was found");	
	}

	@Override
	public String getWeight() throws IOException{
		System.out.println("Indtast ny vægt");
		return inFromUser.readLine();
	}

	@Override
	public String getUsername() throws IOException{
		System.out.println("Indtast nyt brugernavn");
		return inFromUser.readLine();
	}

	@Override
	public String getDisplay() throws IOException{
		System.out.println("Indtast ny displaytekst");
		return inFromUser.readLine();
	}

	@Override
	public void showQuitMessage() throws IOException{
		System.out.println("Du forlod serveren");
	}
	@Override
	public int getMenuOption() throws IOException{
		System.out.println("#########################");
		System.out.println("Hovedmenu");
		System.out.println("#########################");
		System.out.println("Serverens kommandoer: \n"+"D: Skift displaytekst \n"+"AD: Aflæs Display \n"+"T: Tarere vægt \n"+
		"A: Aflæs vægt \n"+"Z: Nulstil vægt \n"+"M: Besked til vægtserveren \n"+"Q: Forlad program \n");
		String input = inFromUser.readLine();
		switch (input) {
		case "M": return 0;
		case "D": return 3;
		case "T": return 2;
		case "A": return 5;
		case "Q": return 4;
		case "Z": return 1;
		case "AD": return 6;
		default: System.out.println("Den indtastede funktion findes ikke");
					return -1;
		}
	}
	
	public void connectionError() {
		System.out.println("Kunne ikke forbinde til serveren");
	}
	public void noInput() {
		System.out.println("Input ikke fundet");
	}

	@Override
	public String getMessageOption() throws IOException {
		System.out.println("Vælg en beskedtype \n"+"I: Integer \n"+"A: Alfanum \n");
		return inFromUser.readLine();
	}

	@Override
	public String getUserMessage(int messagenumber) throws IOException {
		System.out.println("Indtast en besked "+messagenumber);
		return inFromUser.readLine();
	}
	public void unknownMessage() {
		System.out.println("Ukendt input");
	}
	public void showMessage(String message){
		System.out.println("Besked fra serveren: "+message);
	}

}
