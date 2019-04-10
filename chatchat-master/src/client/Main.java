package client;

import java.sql.*;
import java.sql.DriverManager;
import java.util.ArrayList;

import javax.swing.UIManager;
//1.Add scrollPane in MainPage
//2.將chatBox加入Map中，再將Map序列化，如此當Map反序列化時，chatBox會被new出來嗎?(addKey、ActionListener好像不會) 貌似就是這樣，所以需要另外保存聊天紀錄=> initBox
//3.Add scrollPane in chatBox
//4.不知名的出現aaa的聊天紀錄
public class Main {

	public static void main(String[] args) throws Exception {
		//Nimbus appearance applied
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("[Ready]");
		LoginPage loginPage = new LoginPage();
		loginPage.showUp();

	
	}

	public static ArrayList<String> get() throws Exception {
		try {
			Connection conn = getConnection();
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM user");

			ResultSet result = statement.executeQuery();

			ArrayList<String> array = new ArrayList<String>();
			while (result.next()) {
				System.out.print(result.getInt("id"));
				System.out.print(" ");
				System.out.print(result.getString("account"));
				System.out.print(" ");
				System.out.println(result.getString("password"));

				array.add(result.getString("account"));
			}
			System.out.println("Select Completed.");
			return array;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	public static void insertInfo() throws Exception {
		final String var1 = "aaa";
		final String var2 = "bbb";
		try {
			Connection conn = getConnection();
			PreparedStatement insert = conn
					.prepareStatement("INSERT INTO user(account, password) VALUES ('" + var1 + "', '" + var2 + "')");

			insert.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			System.out.println("Insert Completed.");
		}
	}

	public static void createTable() throws Exception {
		try {
			Connection conn = getConnection();
			PreparedStatement create = conn.prepareStatement(
					"CREATE TABLE IF NOT EXISTS user(id int NOT NULL AUTO_INCREMENT PRIMARY KEY, account varchar(255), password varchar(255))");
			create.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			System.out.println("Table created.");
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

			Connection conn = DriverManager.getConnection(url, username, password);
			System.out.println("connected");
			return conn;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

}
