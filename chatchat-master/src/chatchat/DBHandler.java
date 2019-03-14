package chatchat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			System.out.println("[Table Created]");
		}
	}

	public static boolean IDCheck(String account) throws Exception{
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
	
	public static boolean loginCheck(String account, String pw) throws Exception{
		boolean result = false;
		try {
			PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM user WHERE account = '" + account + "' AND password = '" + pw + "'");
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}

