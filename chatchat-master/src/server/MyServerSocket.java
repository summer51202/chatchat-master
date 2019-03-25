package server;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

import client.*;


public class MyServerSocket {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
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
	
	public static Map<String, Chat> onlineList;
}
