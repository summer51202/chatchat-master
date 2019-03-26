package client;

import java.io.ObjectInputStream;


public class FunctionUnit extends Thread {
	public FunctionUnit(ObjectInputStream clientInput) {
		this.clientInput = clientInput;
	}

	public void run() {
		try {
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

	private ObjectInputStream clientInput;

}
