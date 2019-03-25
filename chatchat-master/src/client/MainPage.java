package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;




public class MainPage extends JFrame implements ActionListener{
	public MainPage(User user) {
		
//Connecting & Verify your identity to server		
		this.user = user;
		try {
			socket = new Socket(server_IP, 6666);
			clientOutput = new ObjectOutputStream(socket.getOutputStream());
			functionUnit = new FunctionUnit(this, socket);
			functionUnit.start();
			WrappedObj wrappedObj = new WrappedObj(user);
			clientOutput.writeObject(wrappedObj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//Initialize GUI		
		setSize(400, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setTitle("Welcome To Chatchat " + this.user.getUsername());
		
//CENTRALIZE THE WINDOW
		Toolkit toolkit = getToolkit();
		Dimension size = toolkit.getScreenSize();
		setLocation(size.width/2 - getWidth()/2, size.height/2 - getHeight()/2);
		
//Set Typing field		
		typeInTextField = new JTextField("<Type here>");
		typeInTextField.setForeground(new java.awt.Color(204, 204, 204));
		typeInTextField.setLocation(100, 350);
		typeInTextField.setSize(200, 50);
		add(typeInTextField);
		typeInTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if(typeInTextField.getForeground()!= Color.BLACK){
                    if(typeInTextField.getText().equals("<Type here>")){
                    	typeInTextField.setText("");
                    }
                }
                typeInTextField.setForeground(Color.BLACK);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
            	if(typeInTextField.getText().isEmpty()==true){
            		typeInTextField.setText("<Type here>");
            		typeInTextField.setCaretPosition(0);
            		typeInTextField.setForeground(new java.awt.Color(204,204,204));
                }
            }
        });
		
//Message sending		
		typeInTextField.addKeyListener(new KeyListener() {
	
			public void keyTyped(KeyEvent arg0) {	
			}
			
			public void keyReleased(KeyEvent arg0) {
			}
			
			public void keyPressed(KeyEvent keyEvent) {
				if (keyEvent.getKeyCode() == keyEvent.VK_ENTER) {
					if(!typeInTextField.getText().equals("")) {
						try {
							Message msg = new Message(typeInTextField.getText());
							WrappedObj wrappedObj = new WrappedObj(msg);
							clientOutput.writeObject(wrappedObj);
//							clientOutput.writeBytes(typeInTextField.getText() + "\n");
							System.out.println("MY MSG: " + typeInTextField.getText());
//							displayTextArea.append("my msg: " + typeInTextField.getText() + "\n");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					typeInTextField.setText("");
				}
				
			}
		});

//Set Displaying area
		displayTextArea = new JTextArea();
		displayTextArea.setLocation(100, 100);
		displayTextArea.setSize(200, 200);
		displayTextArea.setEditable(false);
		add(displayTextArea);

		
	}
	
	public void showUp() {
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		
	}
	
	public static JTextField typeInTextField;
	public static JTextArea displayTextArea;
	public static User user;
	
	private ObjectOutputStream clientOutput;
//	private DataOutputStream clientOutput;
    private Socket socket;
    private FunctionUnit functionUnit;
    
    final private String server_IP = "192.168.50.214";
}
