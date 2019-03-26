package server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Iterator;
import java.util.Map;

import client.*;

public class Chat implements Runnable{
	public Chat(Socket socket) {
		this.socket = socket;	
	}
	
	public void run() {
		try {
			serverOutput = new ObjectOutputStream(socket.getOutputStream());
			serverInput = new ObjectInputStream(socket.getInputStream());
			
//Receive user identity			
//			WrappedObj userObj = (WrappedObj)serverInput.readObject();
//			MyServerSocket.onlineList.put(userObj.user.getUsername(), this);
//			this.user = userObj.user;
//			System.out.println("[" + userObj.user.getUsername() + " is online]");
			while(true) {
				WrappedObj wrappedObj = (WrappedObj)serverInput.readObject();
				MyServerSocket.writeTo(wrappedObj, this);
//				serverOutput.writeObject(wrappedObj);
//				String clientText = clientInput.readObject();
//				serverOutput.writeBytes(clientText + "\n");
//				System.out.println("From Client: " + wrappedObj.msg.getContext());
//				serverOutput.flush();
			}
//			clientInput.close();
//			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void enlist(WrappedObj obj) {
		MyServerSocket.onlineList.put(obj.user.getUsername(), this);
		this.user = obj.user;
		System.out.println("[" + obj.user.getUsername() + " is online]");
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
