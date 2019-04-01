package client;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

public class DBHandler {

	public DBHandler() {
		try {
			createTable();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws Exception {
		try {
			DatabaseInfo databaseInfo = new DatabaseInfo();
			String driver = databaseInfo.getDrive();
			String url = databaseInfo.getUrl();
			String username = databaseInfo.getUser();
			String password = databaseInfo.getPassword();
			Class.forName(driver);

			Connection connection = DriverManager.getConnection(url, username, password);
			System.out.println("[Database Connected]");
			return connection;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	public static void createTable() throws Exception {
		try {
			Connection connection = getConnection();
			PreparedStatement create = connection.prepareStatement(
					"CREATE TABLE IF NOT EXISTS user(id int NOT NULL AUTO_INCREMENT PRIMARY KEY, account varchar(255), password varchar(255) )");
			create.executeUpdate();
			PreparedStatement create2 = connection.prepareStatement(
					"ALTER TABLE user ADD friendList varchar(255)");
			create2.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			System.out.println("[Table Created]");
		}
	}

	public static boolean accountCheck(String account) throws Exception{
		boolean result = false;
		try {
			PreparedStatement statement = getConnection().prepareStatement("SELECT account FROM user WHERE account = '" + account + "'");
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static boolean loginCheck(String account, String password) throws Exception{
		boolean result = false;
		try {
			PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM user WHERE account = '" + account + "' AND password = '" + password + "'");
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static void signUpInfo(String account, String password) {
		String var1 = account;
		String var2 = password;
		try {
			Connection conn = getConnection();
			PreparedStatement insert = conn
					.prepareStatement("INSERT INTO user(account, password) VALUES ('" + var1 + "', '" + var2 + "')");

			insert.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			System.out.println("[Build Completed]");
		}
		
	}
	
	public static void updateFriendList(String user ,Map<String, ChatBox> map) {
		try {
			Connection conn = getConnection();
//			PreparedStatement updateFList = conn.prepareStatement("INSERT INTO user(friendList) VALUES(?) WHERE account = '" + user + "'");
			PreparedStatement updateFList = conn.prepareStatement("UPDATE user SET friendList = ? WHERE account = '" + user + "'");
			updateFList.setObject(1, map);
//			updateFList.setString(2, user);
			updateFList.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Map<String, ChatBox> readFriendList(String user) {
		Map<String, ChatBox> rs = null;
		try {
			
			PreparedStatement readFList = getConnection().prepareStatement("SELECT friendList FROM user WHERE account = '" + user + "'");
			ResultSet resultSet = readFList.executeQuery();
			if(resultSet.next()) {
				if(resultSet.getObject(1) != null) {
					byte[] st = (byte[]) resultSet.getObject(1);
				    ByteArrayInputStream baip = new ByteArrayInputStream(st);
				    ObjectInputStream ois = new ObjectInputStream(baip);
				    rs = (Map<String, ChatBox>) ois.readObject();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return rs;
	}
}

