package server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import client.*;

public class Chat implements Runnable{
	public Chat(Socket socket) {
		this.socket = socket;	
	}
	
	public void run() {
		try {
			serverOutput = new ObjectOutputStream(socket.getOutputStream());
			clientInput = new ObjectInputStream(socket.getInputStream());
			
			WrappedObj userObj = (WrappedObj)clientInput.readObject();
			MyServerSocket.onlineList.put(userObj.user.getUsername(), this);
			System.out.println("[" + userObj.user.getUsername() + " is online]");
			while(true) {
				WrappedObj msgObj = (WrappedObj)clientInput.readObject();
				serverOutput.writeObject(msgObj);
//				String clientText = clientInput.readObject();
//				serverOutput.writeBytes(clientText + "\n");
				System.out.println("From Client: " + msgObj.msg.getContext());
//				serverOutput.flush();
			}
//			clientInput.close();
//			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private Socket socket;
//	private BufferedReader clientInput;
	private ObjectInputStream clientInput;
	private ObjectOutputStream serverOutput;
}
