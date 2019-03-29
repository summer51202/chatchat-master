package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.jar.JarFile;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;









public class LoginPage extends JFrame implements ActionListener{

	public LoginPage() {
		setSize(400, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setTitle("Welcome To Chatchat");
		
		//CENTRALIZE THE WINDOW
		Toolkit toolkit = getToolkit();
		Dimension size = toolkit.getScreenSize();
		setLocation(size.width/2 - getWidth()/2, size.height/2 - getHeight()/2);
		
		icon = new JLabel("CHATCHAT");
		icon.setFont(new java.awt.Font("CHATCHAT", 1, 50));
		icon.setLocation(50, 50);
		icon.setSize(400, 100);
		add(icon);
		
		usernameTextField = new JTextField("<Your Username>");
		usernameTextField.setForeground(new java.awt.Color(204, 204, 204));
		usernameTextField.setHorizontalAlignment(SwingConstants.CENTER);
		usernameTextField.setLocation(100, 200);
		usernameTextField.setSize(200, 50);
		add(usernameTextField);
		usernameTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if(usernameTextField.getForeground()!= Color.BLACK){
                    if(usernameTextField.getText().equals("<Your Username>")){
                        usernameTextField.setText("");
                    }
                }
                usernameTextField.setForeground(Color.BLACK);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
            	if(usernameTextField.getText().isEmpty()==true){
                    usernameTextField.setText("<Your Username>");
                    usernameTextField.setCaretPosition(0);
                    usernameTextField.setForeground(new java.awt.Color(204,204,204));
                }
            }
        });
		
		passwordTextField = new JTextField("<Your Password>");
		passwordTextField.setForeground(new java.awt.Color(204, 204, 204));
		passwordTextField.setHorizontalAlignment(SwingConstants.CENTER);
		passwordTextField.setLocation(100, 250);
		passwordTextField.setSize(200, 50);
		add(passwordTextField);
		passwordTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if(passwordTextField.getForeground()!= Color.BLACK){
                    if(passwordTextField.getText().equals("<Your Password>")){
                        passwordTextField.setText("");
                    }
                }
                passwordTextField.setForeground(Color.BLACK);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
            	if(passwordTextField.getText().isEmpty()==true){
                    passwordTextField.setText("<Your Password>");
                    passwordTextField.setCaretPosition(0);
                    passwordTextField.setForeground(new java.awt.Color(204,204,204));
                }
            }
        });
		
		loginButton = new JButton("Log In");
		loginButton.setLocation(150, 325);
		loginButton.setSize(100, 25);
		loginButton.addActionListener(this);
		add(loginButton);
		
		signUpButton = new JButton("Sign Up");
		signUpButton.setLocation(150, 350);
		signUpButton.setSize(100, 25);
		signUpButton.addActionListener(this);
		add(signUpButton);
		
	}	
	
	public void showUp() {
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		String username = usernameTextField.getText();
        String password = passwordTextField.getText();
		
        if (e.getSource() == loginButton) {
        	try {
        		System.out.println("[Login]");
        		if(DBHandler.loginCheck(username, password)) {
        			System.out.println("[" + username + " Success Login]");
        			JOptionPane.showMessageDialog(this, "Login successful!", "Login", JOptionPane.INFORMATION_MESSAGE);
        			User user = new User(username);
        			MainPage mainPage = new MainPage(user);
        			mainPage.showUp();
        			dispose();
        		} else {
        			System.out.println("[" + username + " Failed Login]");
        			JOptionPane.showMessageDialog(this, "Failed, password or username incorrect!", "Failed", JOptionPane.ERROR_MESSAGE);
        			
        			usernameTextField.setText("<Your Username>");
        			usernameTextField.setForeground(new java.awt.Color(204,204,204));
        			passwordTextField.setText("<Your Password>");
        			passwordTextField.setForeground(new java.awt.Color(204,204,204));
        		}

			} catch (Exception ee) {
				ee.printStackTrace();
			}
        }
        
        if (e.getSource() == signUpButton) {
        	try {
				System.out.println("[SignUp]");
				if(!DBHandler.accountCheck(username)) {
					System.out.println("[" + username + " Success SignUp]");
					JOptionPane.showMessageDialog(this, "Sign up successful!", "SignUp", JOptionPane.INFORMATION_MESSAGE);
					DBHandler.signUpInfo(username, password);
					
					usernameTextField.setText("<Your Username>");
        			usernameTextField.setForeground(new java.awt.Color(204,204,204));
        			passwordTextField.setText("<Your Password>");
        			passwordTextField.setForeground(new java.awt.Color(204,204,204));
				} else {
					System.out.println("[" + username + " Failed SignUp]");
        			JOptionPane.showMessageDialog(this, "Failed, this username has been used!", "Failed", JOptionPane.ERROR_MESSAGE);
        			
        			usernameTextField.setText("<Your Username>");
        			usernameTextField.setForeground(new java.awt.Color(204,204,204));
        			passwordTextField.setText("<Your Password>");
        			passwordTextField.setForeground(new java.awt.Color(204,204,204));
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
        }

	}
	
	private JLabel icon = null;
	private JTextField usernameTextField = null;
	private JTextField passwordTextField = null;
	private JButton loginButton;
	private JButton signUpButton;
}
