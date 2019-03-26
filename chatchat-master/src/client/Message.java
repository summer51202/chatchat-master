package client;

//Extends WrappedObj because Message need to implements Serializable
public class Message extends WrappedObj{
	public Message(String context, String sender) {
		this.context = context;
		this.sender = sender;
	}
	public String getContext() {
		return context;
	}
	public String getSender() {
		return sender;
	}
	private String context;
	private String sender;
}
