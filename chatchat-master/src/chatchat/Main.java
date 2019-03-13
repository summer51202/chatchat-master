package chatchat;

import java.sql.*;
import java.sql.DriverManager;
import java.util.ArrayList;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class Main {

	public static void main(String[] args) throws Exception {
		//Nimbus appearance applied
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("[Ready]");
		loginPage loginPage = new loginPage();
		loginPage.showUp();

		// createTable();
		// insertInfo();
		// get();
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
					"CREATE TABLE IF NOT EXISTS user(id int NOT NULL AUTO_INCREMENT PRIMARY KEY, account varchar(255), password varchar(255) )");
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
			String driver = "com.mysql.cj.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/testDB?serverTimezone=UTC";
			String username = "root";
			String password = "joke90566";
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
