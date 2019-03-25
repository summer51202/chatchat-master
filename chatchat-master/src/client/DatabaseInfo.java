package client;

public class DatabaseInfo {
	public String getDrive() {
		return driver;
	} 
	public String getUrl() {
		return url;
	} 
	public String getUser() {
		return username;
	} 
	public String getPassword() {
		return password;
	} 
	
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/testDB?serverTimezone=UTC";
	private String username = "root";
	private String password = "joke90566";
}
