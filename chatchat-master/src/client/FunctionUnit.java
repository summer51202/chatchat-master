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
			Operation operation = new Operation(MainPage.user);
			WrappedObj wrappedObj = new WrappedObj(operation);
			clientOutput.writeObject(wrappedObj);

			// Keep receiving Msg
			while (true) {
				WrappedObj receiveObj = (WrappedObj) clientInput.readObject();
				switch (receiveObj.objectType) {

				case (WrappedObj.OPERATION):
					
					Operation receiveOp = receiveObj.operation;
					switch (receiveObj.operation.operationType) {
					case (Operation.ADDFRIEND):
						
						//Build friend's chatBox
						ArrayList<String> member = new ArrayList<String>();
						member.add(receiveOp.getFriend().getUsername());
						ChatBox chatBox = new ChatBox(member);
						FunctionUnit.friendListMap.put(receiveOp.getFriend().getUsername(), chatBox);
						MainPage.friendListModel.addElement(receiveOp.getFriend().getUsername());
						MainPage.chatPanel.add(chatBox.panel, chatBox.getRoomName());
						
						DBHandler.updateFriendList(MainPage.user.getUsername(), friendListMap);
						break;
					case (Operation.BUILDGROUP):

						break;
					}
					break;
				case (WrappedObj.MESSAGE):

					Message receiveMsg = receiveObj.msg;
					switch (receiveObj.msg.msgType) {
					case (Message.WORLD):
						MainPage.displayTextArea.append(receiveMsg.getContext() + "\n");
						break;
					case (Message.PERSONAL):
						ChatBox box = FunctionUnit.friendListMap.get(receiveMsg.getSender());
						box.boxTextArea.append(receiveMsg.getContext() + "\n");
						break;
					case (Message.GROUP):

						break;
					}

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
		
		//Build friend's chatBox
		ArrayList<String> member = new ArrayList<String>();
		member.add(friendName);
		ChatBox chatBox = new ChatBox(member);
		friendListMap.put(friendName, chatBox);
		MainPage.friendListModel.addElement(friendName);
		MainPage.chatPanel.add(chatBox.panel, chatBox.getRoomName());
		
		//Let the friend know by server
		User friend = new User(friendName);
		Operation operation = new Operation(MainPage.user, friend);
		WrappedObj wrappedObj = new WrappedObj(operation);
		writeObj(wrappedObj);
		
		DBHandler.updateFriendList(MainPage.user.getUsername(), friendListMap);
	}

	public static Map<String, ChatBox> friendListMap;
	public static Map<String, ChatBox> chatRoomListMap;

	private ObjectInputStream clientInput;
	private ObjectOutputStream clientOutput;
	private Socket socket;
	final private String server_IP = "192.168.1.134";

}
