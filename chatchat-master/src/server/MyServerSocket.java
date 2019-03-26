package server;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import client.*;


public class MyServerSocket {

	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(6666);
			System.out.println("[Server Started]");
			onlineList = new HashMap<String, Chat>();
			
			while (true) {
				Socket clientSocket = serverSocket.accept();
				Chat chat = new Chat(clientSocket);
				Thread chatThread = new Thread(chat);
				chatThread.start();
				System.out.println("[Client connected]");
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("[Disconnected]");
		}
	}
	
	public static void writeTo(WrappedObj obj, Chat chat) {
		try {
			switch (obj.objectType) {
			case (WrappedObj.USER):
				
//OnlineInfo broadcast	

				Message onlineRemind = new Message(obj.user.getUsername() + " is online!", "");
				WrappedObj wrappedObj = new WrappedObj(onlineRemind);
				Iterator iterator = MyServerSocket.onlineList.entrySet().iterator();
				while(iterator.hasNext()) {
					Map.Entry mapEntry = (Map.Entry) iterator.next();
					Chat chatTo = (Chat)mapEntry.getValue();
					chatTo.write(wrappedObj);
				}
					
				chat.enlist(obj);
			break;
			case (WrappedObj.MESSAGE):
				
//Message broadcast	
				
				System.out.println(obj.msg.getSender() + " : " + obj.msg.getContext());
				Message msg = new Message(obj.msg.getSender() + " : " + obj.msg.getContext(), "");
				WrappedObj wrappedObj2 = new WrappedObj(msg);
				Iterator iterator2 = MyServerSocket.onlineList.entrySet().iterator();
				while(iterator2.hasNext()) {
					Map.Entry mapEntry = (Map.Entry) iterator2.next();
					Chat chatTo = (Chat)mapEntry.getValue();
					chatTo.write(wrappedObj2);
				}
			break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Map<String, Chat> onlineList;
}
