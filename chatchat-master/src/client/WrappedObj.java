package client;

import java.io.Serializable;

public class WrappedObj implements Serializable{
	public WrappedObj(Operation operation) {
		this.operation = operation;
		objectType = OPERATION;;
	}
	public WrappedObj(Message msg) {
		this.msg = msg;
		objectType = MESSAGE;
	}
	public WrappedObj() {
		
	}
	
	public Operation operation;
    public Message msg;
    public int objectType;
    public final static int OPERATION = 0;
    public final static int MESSAGE = 1;
    private final static long serialVersionUID = 192294423276324203l;
}
