package client;

//Extends WrappedObj because User need to implements Serializable
public class User extends WrappedObj{
	public User(String username) {
		this.username = username;
	}
	public String getUsername() {
		return username;
	};
	private String username;
	private String idNumber;
}
