package client;

import java.io.Serializable;

public class WrappedObj implements Serializable{
	public WrappedObj(User user) {
		this.user = user;
		objectType = USER;
	}
	public WrappedObj(Message msg) {
		this.msg = msg;
		objectType = MESSAGE;
	}
	public WrappedObj() {
		
	}
	
	public User user;
    public Message msg;
    public int objectType;
    public final static int USER = 0;
    public final static int MESSAGE = 1;
    private final static long serialVersionUID = 192294423276324203l;
}
