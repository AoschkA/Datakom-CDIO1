package boundary;

import java.io.IOException;

public interface ITCPClient {
	
	String getIP() throws IOException;
	
	int getPort() throws IOException; 
	
	void noConnection() throws IOException;
	
	String getWeight() throws IOException;
	
	String getUsername() throws IOException;
	
	String getDisplay() throws IOException;
	
	void showQuitMessage() throws IOException;

	int getMenuOption() throws IOException;
	
	void connectionError();
	
	void noInput();
	
	String getMessageOption() throws IOException;
	
	String getUserMessage(int messagenumber) throws IOException;
	
	void unknownMessage();
	
	void showMessage(String message);

}
