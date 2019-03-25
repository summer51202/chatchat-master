package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class FunctionUnit extends Thread {
	public FunctionUnit(MainPage mainPage, Socket socket) {
		this.mainPage = mainPage;
		this.socket = socket;
	}

	public void run() {
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while(true) {
				if (bufferedReader.ready()) {
					String msg = bufferedReader.readLine();
					System.out.println("msg: " + msg);
					mainPage.displayTextArea.append(mainPage.user.getUsername() + " : " + msg + "\n");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private MainPage mainPage;
	private BufferedReader bufferedReader;
	private Socket socket;
}
