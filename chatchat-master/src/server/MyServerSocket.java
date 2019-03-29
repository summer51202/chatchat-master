package server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
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
			case (WrappedObj.OPERATION):
				
//OnlineInfo broadcast	
				
				ArrayList<String> receiver = new ArrayList<String>();
				receiver.add("World");
				Message onlineRemind = new Message(obj.operation.getUser().getUsername() + " is online!", "", receiver);
				WrappedObj wrappedObjUser = new WrappedObj(onlineRemind);
				Iterator iterator = MyServerSocket.onlineList.entrySet().iterator();
				while(iterator.hasNext()) {
					Map.Entry mapEntry = (Map.Entry) iterator.next();
					Chat chatTo = (Chat)mapEntry.getValue();
					chatTo.write(wrappedObjUser);
				}
					
				chat.enlist(obj);
			break;
			case (WrappedObj.MESSAGE):
				
//Message directing
				
				switch (obj.msg.msgType) {
				case (Message.WORLD):
					System.out.println(obj.msg.getSender() + " : " + obj.msg.getContext());
				
					Message msgToWorld = new Message(obj.msg.getSender() + " : " + obj.msg.getContext(), "");
					WrappedObj wrappedObjWorld = new WrappedObj(msgToWorld);
					
					Iterator iterator2 = onlineList.entrySet().iterator();
					while(iterator2.hasNext()) {
					Map.Entry mapEntry = (Map.Entry) iterator2.next();
					Chat chatToWorld = (Chat)mapEntry.getValue();
					chatToWorld.write(wrappedObjWorld);
				}
					break;
				case (Message.PERSONAL):
					Message msgToPerson = new Message(obj.msg.getSender() + " : " + obj.msg.getContext(), "", obj.msg.getReceiver());
					WrappedObj wrappedObjPerson = new WrappedObj(msgToPerson);
					Chat chatToPerson = onlineList.get(obj.msg.getReceiver());
					chatToPerson.write(wrappedObjPerson);
					break;
				case (Message.GROUP):
					Message msgToGroup = new Message(obj.msg.getSender() + " : " + obj.msg.getContext(), "");
					break;
				}
			break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Map<String, Chat> onlineList;
}
