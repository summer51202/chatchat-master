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
			serverInput = new ObjectInputStream(socket.getInputStream());
			
//Receiving from client		
			while(true) {
				WrappedObj wrappedObj = (WrappedObj)serverInput.readObject();
				MyServerSocket.writeTo(wrappedObj, this);
			}
//			clientInput.close();
//			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void enlist(WrappedObj obj) {
		MyServerSocket.onlineList.put(obj.operation.getUser().getUsername(), this);
		this.user = obj.operation.getUser();
		System.out.println("[" + obj.operation.getUser().getUsername() + " is online]");
	}
	
	public void write(WrappedObj obj) {
		try {
			this.serverOutput.writeObject(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public User user;
	private Socket socket;
	private ObjectInputStream serverInput;
	private ObjectOutputStream serverOutput;
}
