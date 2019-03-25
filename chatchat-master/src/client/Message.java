package client;

//Extends WrappedObj because Message need to implements Serializable
public class Message extends WrappedObj{
	public Message(String context) {
		this.context = context;
	}
	public String getContext() {
		return context;
	}
	private String context;
}
