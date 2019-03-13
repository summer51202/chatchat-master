package chatchat;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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







public class loginPage extends JFrame implements ActionListener{

	public loginPage() {
		setSize(400, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setTitle("Welcome To Chatchat");
		
		//Display window in the central of screen.
		Toolkit toolkit = getToolkit();
		Dimension size = toolkit.getScreenSize();
		setLocation(size.width/2 - getWidth()/2, size.height/2 - getHeight()/2);
		
		icon = new JLabel("CHATCHAT");
		icon.setFont(new java.awt.Font("CHATCHAT", 1, 50));
		icon.setLocation(50, 50);
		icon.setSize(400, 100);
		add(icon);
		
		username = new JTextField("<Your Username>");
		username.setForeground(new java.awt.Color(204, 204, 204));
		username.setHorizontalAlignment(SwingConstants.CENTER);
		username.setLocation(100, 200);
		username.setSize(200, 50);
		add(username);
		username.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if(username.getForeground()!= Color.BLACK){
                    if(username.getText().equals("<Your Username>")){
                        username.setText("");
                    }
                }
                username.setForeground(Color.BLACK);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
            	if(username.getText().isEmpty()==true){
                    username.setText("<Your Username>");
                    username.setCaretPosition(0);
                    username.setForeground(new java.awt.Color(204,204,204));
                }
            }
        });
		
		password = new JTextField("<Your Password>");
		password.setForeground(new java.awt.Color(204, 204, 204));
		password.setHorizontalAlignment(SwingConstants.CENTER);
		password.setLocation(100, 250);
		password.setSize(200, 50);
		add(password);
		password.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if(password.getForeground()!= Color.BLACK){
                    if(password.getText().equals("<Your Password>")){
                        password.setText("");
                    }
                }
                password.setForeground(Color.BLACK);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
            	if(password.getText().isEmpty()==true){
                    password.setText("<Your Password>");
                    password.setCaretPosition(0);
                    password.setForeground(new java.awt.Color(204,204,204));
                }
            }
        });
		
		login = new JButton("Log In");
		login.setLocation(150, 325);
		login.setSize(100, 25);
		login.addActionListener(this);
		add(login);
		
		signUp = new JButton("Sign Up");
		signUp.setLocation(150, 350);
		signUp.setSize(100, 25);
		signUp.addActionListener(this);
		add(signUp);
		
	}	
	
	public void showUp() {
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		

	}
	
	static loginPage loginPage;
	
	private JLabel icon = null;
	private JTextField username = null;
	private JTextField password = null;
	private JButton login;
	private JButton signUp;
}
