package client;

import java.util.ArrayList;



//Extends WrappedObj because Message need to implements Serializable
public class Message extends WrappedObj{
	public Message(String context, String sender) {
		this.context = context;
		this.sender = sender;
		msgType = Message.WORLD;
	}
	public Message(String context, String sender, ArrayList<String> receiverList) {
		this.context = context;
		this.sender = sender;
		this.receiverList = receiverList;
		msgType = Message.GROUP;
	}
	public Message(String context, String sender, String receiver) {
		this.context = context;
		this.sender = sender;
		this.receiver = receiver;
		msgType = Message.PERSONAL;
	}
	public String getContext() {
		return context;
	}
	public String getSender() {
		return sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public ArrayList<String> getReceiverList() {
		return receiverList;
	}
	
	private String context;
	private String sender;
	private ArrayList<String> receiverList;
	private String receiver; 
	
	public int msgType;
	public final static int WORLD = 0;
	public final static int	PERSONAL = 1;
	public final static int GROUP = 2;
}
