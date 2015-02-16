package launch;

import java.io.IOException;

import control.TCPClientController;

public class TCPClient {
	public static void main(String[] args) throws IOException, Exception {
		TCPClientController control = new TCPClientController();
		control.init();
	}

}
