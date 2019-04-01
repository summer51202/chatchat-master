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

				switch (obj.operation.operationType) {
				case (Operation.ONLINE):

					// OnlineInfo broadcast
					Message onlineRemind = new Message(obj.operation.getSender().getUsername() + " is online!", "");
					WrappedObj wrappedObjUser = new WrappedObj(onlineRemind);

					Iterator iterator = MyServerSocket.onlineList.entrySet().iterator();
					while (iterator.hasNext()) {
						Map.Entry mapEntry = (Map.Entry) iterator.next();
						Chat chatTo = (Chat) mapEntry.getValue();
						chatTo.write(wrappedObjUser);
					}
					chat.enlist(obj);
					break;
				case (Operation.ADDFRIEND):

					// Build the relation to friend
					Operation addFriend = new Operation(null, obj.operation.getSender());
					WrappedObj wrappedObjAddF = new WrappedObj(addFriend);

					Chat chatAddF = onlineList.get(obj.operation.getFriend().getUsername());
					chatAddF.write(wrappedObjAddF);
					break;
				case (Operation.BUILDGROUP):

					break;
				}

				break;
			case (WrappedObj.MESSAGE):

				// Message directing

				switch (obj.msg.msgType) {
				case (Message.WORLD):
					System.out.println(obj.msg.getSender() + " : " + obj.msg.getContext());

					Message msgToWorld = new Message(obj.msg.getSender() + " : " + obj.msg.getContext(), "");
					WrappedObj wrappedObjWorld = new WrappedObj(msgToWorld);

					Iterator iterator2 = onlineList.entrySet().iterator();
					while (iterator2.hasNext()) {
						Map.Entry mapEntry = (Map.Entry) iterator2.next();
						Chat chatToWorld = (Chat) mapEntry.getValue();
						chatToWorld.write(wrappedObjWorld);
					}
					break;
				case (Message.PERSONAL):
					
					// Msg to receiver
					Message msgToPerson1 = new Message(obj.msg.getSender() + " : " + obj.msg.getContext(),
							obj.msg.getSender(), "");
					WrappedObj wrappedObjPerson1 = new WrappedObj(msgToPerson1);
					Chat chatToPerson1 = onlineList.get(obj.msg.getReceiver());
					chatToPerson1.write(wrappedObjPerson1);
					
					// Msg to sender
					Message msgToPerson2 = new Message(obj.msg.getSender() + " : " + obj.msg.getContext(),
							obj.msg.getReceiver(), "");
					WrappedObj wrappedObjPerson2 = new WrappedObj(msgToPerson2);
					Chat chatToPerson2 = onlineList.get(obj.msg.getSender());
					chatToPerson2.write(wrappedObjPerson2);
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
