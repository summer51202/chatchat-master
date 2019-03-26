package client;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;




public class MainPage extends JFrame implements ActionListener{
	public MainPage(User user) {
		
//Connecting & Verify your identity to server		
		MainPage.user = user;
		try {
			socket = new Socket(server_IP, 6666);
			clientOutput = new ObjectOutputStream(socket.getOutputStream());
			clientInput = new ObjectInputStream(socket.getInputStream());
			functionUnit = new FunctionUnit(clientInput);
			functionUnit.start();
			WrappedObj wrappedObj = new WrappedObj(user);
			clientOutput.writeObject(wrappedObj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//Initialize GUI		
		setSize(400, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Welcome To Chatchat " + MainPage.user.getUsername());
		
//CENTRALIZE THE WINDOW
		Toolkit toolkit = getToolkit();
		Dimension size = toolkit.getScreenSize();
		setLocation(size.width/2 - getWidth()/2, size.height/2 - getHeight()/2);
		
		
		controlPanel = new JPanel();
		displayPanel = new JPanel();
		worldChPanel = new JPanel();
		friendListPanel = new JPanel();
		chatRoomPanel = new JPanel();
		chatPanel = new JPanel();
		cardLayout = new CardLayout();
		displayPanel.setLayout(cardLayout);
		worldChPanel.setLayout(new BorderLayout());
		
		displayPanel.add(worldChPanel, "World Channel");
		displayPanel.add(friendListPanel, "Friend List");
		displayPanel.add(chatRoomPanel, "Chat Room");
		displayPanel.add(chatPanel, "Chat");
		
		worldChButton = new JButton("World Ch.");
		friendListButton = new JButton("Friend List");
		chatRoomButton = new JButton("Chat Room");
		chatButton = new JButton("Chat");
		
		worldChButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cardLayout.show(displayPanel, "World Channel");
			}
		});
		friendListButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cardLayout.show(displayPanel, "Friend List");
			}
		});
		chatRoomButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cardLayout.show(displayPanel, "Chat Room");
			}
		});
		chatButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cardLayout.show(displayPanel, "Chat");
			}
		});
		
		controlPanel.add(worldChButton);
		controlPanel.add(friendListButton);
		controlPanel.add(chatRoomButton);
		controlPanel.add(chatButton);
		add(displayPanel);
		add(controlPanel, BorderLayout.NORTH);
		
		
//Set Typing field		
		typeInTextField = new JTextField("<Type here>");
		typeInTextField.setForeground(new java.awt.Color(204, 204, 204));
//		typeInTextField.setLocation(100, 350);
//		typeInTextField.setSize(200, 200);
		worldChPanel.add(typeInTextField, BorderLayout.SOUTH);
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
				if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
					if(!typeInTextField.getText().equals("")) {
						try {
							Message msg = new Message(typeInTextField.getText(), MainPage.user.getUsername());
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
		worldChPanel.add(displayTextArea, BorderLayout.CENTER);

		
	}
	
	public void showUp() {
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {

	}
	
	public static JPanel controlPanel, displayPanel, worldChPanel, friendListPanel, chatRoomPanel, chatPanel;
	public static JTextField typeInTextField;
	public static JTextArea displayTextArea;
	public static JButton worldChButton, friendListButton, chatRoomButton, chatButton;
	public static CardLayout cardLayout;
	public static User user;
	
	private ObjectOutputStream clientOutput;
	private ObjectInputStream clientInput;
    private Socket socket;
    private FunctionUnit functionUnit;
    
    final private String server_IP = "192.168.50.214";
}
