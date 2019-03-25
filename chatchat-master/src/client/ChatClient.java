package client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;



public class ChatClient extends Thread{

	public ChatClient(MainPage mainPage) {
		this.mainPage = mainPage;
		try {
			socket = new Socket("192.168.1.105", 8000);
			
			serverOutput = new DataOutputStream(socket.getOutputStream());
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		try {
			while(true) {
				Scanner scanner = new Scanner(System.in);
				System.out.println("Type your msg:");
				String msg = scanner.nextLine();
				
				if(!msg.equals(""))
					serverOutput.writeBytes("RE:" + msg + "\n");
				if(msg.equals("bye"))
					break;
			}	
			
			serverOutput.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	Socket socket = null;
	DataOutputStream serverOutput = null;
	MainPage mainPage = null;
}
