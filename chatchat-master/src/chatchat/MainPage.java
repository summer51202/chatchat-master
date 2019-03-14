package chatchat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MainPage extends JFrame implements ActionListener{
	public MainPage() {
		setSize(400, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setTitle("Welcome To Chatchat");
	}
	
	public void showUp() {
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		

	}
}
