package client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class FunctionUnit extends Thread {
	public FunctionUnit() {
		friendListMap = new HashMap<String, ChatBox>();
	}

	public void run() {
		try {
			
			// Connecting & Verify identity to server
			socket = new Socket(server_IP, 6666);
			clientOutput = new ObjectOutputStream(socket.getOutputStream());
			clientInput = new ObjectInputStream(socket.getInputStream());
			Operation operation = new Operation(MainPage.user, Operation.ONLINE);
			WrappedObj wrappedObj = new WrappedObj(operation);
			clientOutput.writeObject(wrappedObj);
			
			//Keep receiving Msg
			while(true) {
				WrappedObj receiveObj = (WrappedObj)clientInput.readObject();
				switch (receiveObj.objectType) {
				case (WrappedObj.MESSAGE):
					Message receiveMsg = receiveObj.msg;
					MainPage.displayTextArea.append(receiveMsg.getContext() + "\n");
					System.out.println("[Message received]");
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void writeObj(WrappedObj obj) {
		try {
			clientOutput.writeObject(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addFriend(String friendName) {
		ArrayList<String> member = new ArrayList<String>();
		member.add(friendName);
		ChatBox chatBox = new ChatBox(member);
		FunctionUnit.friendListMap.put(friendName, chatBox);
		MainPage.friendListModel.addElement(friendName);
		MainPage.chatPanel.add(chatBox.panel, chatBox.getRoomName());
		User friend = new User(friendName);
		Operation operation = new Operation(friend, Operation.ADDFRIEND);
		WrappedObj wrappedObj = new WrappedObj(operation);
		writeObj(wrappedObj);
	}
	
	public static Map<String, ChatBox> friendListMap; 
	public static Map<String, ChatBox> chatRoomListMap;
	
	private ObjectInputStream clientInput;
	private ObjectOutputStream clientOutput;
	private Socket socket;
	final private String server_IP = "192.168.1.106";
	
	

}
