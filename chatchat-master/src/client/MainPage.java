package client;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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

public class MainPage extends JFrame implements ActionListener {
	public MainPage(User user) {

		// Connecting & Verify identity to server
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

		// Initialize GUI with GridBagLayout
		setSize(400, 600);
		setLayout(new GridBagLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Welcome To Chatchat " + MainPage.user.getUsername());

		displayPanel = new JPanel();
		worldChPanel = new JPanel(new GridBagLayout());
		friendListPanel = new JPanel();
		chatRoomPanel = new JPanel();
		chatPanel = new JPanel();
		cardLayout = new CardLayout();

		displayPanel.setLayout(cardLayout);
		displayPanel.add(worldChPanel, "World Channel");
		displayPanel.add(friendListPanel, "Friend List");
		displayPanel.add(chatRoomPanel, "Chat Room");
		displayPanel.add(chatPanel, "Chat");
		addComponent(displayPanel, this, 0, 1, 4, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);

		worldChButton = new JButton("World Ch.");
		worldChButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cardLayout.show(displayPanel, "World Channel");
			}
		});
		addComponent(worldChButton, this, 0, 0, 1, 1, 1, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL);

		friendListButton = new JButton("Friend List");
		friendListButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cardLayout.show(displayPanel, "Friend List");
			}
		});
		addComponent(friendListButton, this, 1, 0, 1, 1, 1, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL);

		chatRoomButton = new JButton("Chat Room");
		chatRoomButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cardLayout.show(displayPanel, "Chat Room");
			}
		});
		addComponent(chatRoomButton, this, 2, 0, 1, 1, 1, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL);

		chatButton = new JButton("Chat");
		chatButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cardLayout.show(displayPanel, "Chat");
			}
		});
		addComponent(chatButton, this, 3, 0, 1, 1, 2, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL);

		// Set Typing field
		typeInTextField = new JTextField("<Type here>");
		typeInTextField.setForeground(new java.awt.Color(204, 204, 204));
		typeInTextField.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				if (typeInTextField.getForeground() != Color.BLACK) {
					if (typeInTextField.getText().equals("<Type here>")) {
						typeInTextField.setText("");
					}
				}
				typeInTextField.setForeground(Color.BLACK);
			}

			public void keyReleased(java.awt.event.KeyEvent evt) {
				if (typeInTextField.getText().isEmpty() == true) {
					// typeInTextField.setCaretPosition(0);
					typeInTextField.setText("<Type here>");
					typeInTextField.setForeground(new java.awt.Color(204, 204, 204));
				}
			}
		});

		// Message sending
		typeInTextField.addKeyListener(new KeyListener() {

			public void keyTyped(KeyEvent arg0) {
			}

			public void keyReleased(KeyEvent arg0) {
			}

			public void keyPressed(KeyEvent keyEvent) {
				if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!typeInTextField.getText().equals("")) {
						try {
							Message msg = new Message(typeInTextField.getText(), MainPage.user.getUsername());
							WrappedObj wrappedObj = new WrappedObj(msg);
							clientOutput.writeObject(wrappedObj);
							// clientOutput.writeBytes(typeInTextField.getText() + "\n");
							System.out.println("MY MSG: " + typeInTextField.getText());
							// displayTextArea.append("my msg: " + typeInTextField.getText() + "\n");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					typeInTextField.setText("<Type here>");
					typeInTextField.setForeground(new java.awt.Color(204, 204, 204));
				}

			}
		});
		addComponent(typeInTextField, worldChPanel, 0, 1, 1, 1, 4, 1, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH);

		sendButton = new JButton("ԅ(¯﹃¯ԅ)");
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				if (!typeInTextField.getText().equals("")) {
					try {
						Message msg = new Message(typeInTextField.getText(), MainPage.user.getUsername());
						WrappedObj wrappedObj = new WrappedObj(msg);
						clientOutput.writeObject(wrappedObj);
						System.out.println("MY MSG: " + typeInTextField.getText());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				typeInTextField.setText("<Type here>");
				typeInTextField.setForeground(new java.awt.Color(204, 204, 204));
			}
		});
		addComponent(sendButton, worldChPanel, 1, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);

		// Set Displaying area
		displayTextArea = new JTextArea();
		displayTextArea.setEditable(false);
		addComponent(displayTextArea, worldChPanel, 0, 0, 2, 1, 1, 6, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH);

		// CENTRALIZE THE WINDOW
		Toolkit toolkit = getToolkit();
		Dimension size = toolkit.getScreenSize();
		setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 2);
	}

	// Method to add Components in GridBagLayout
	private void addComponent(Component component, Container container, int row, int column, int width, int height,
			double weightx, double weighty, int anchor, int fill) {
		constraints.gridx = row;
		constraints.gridy = column;
		constraints.gridwidth = width;
		constraints.gridheight = height;
		constraints.weightx = weightx;
		constraints.weighty = weighty;
		constraints.anchor = anchor;
		constraints.fill = fill;
		container.add(component, constraints);
	}

	public void showUp() {
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {

	}

	public static JPanel controlPanel, displayPanel, worldChPanel, friendListPanel, chatRoomPanel, chatPanel;
	public static JTextField typeInTextField;
	public static JTextArea displayTextArea;
	public static JButton worldChButton, friendListButton, chatRoomButton, chatButton, sendButton;
	public static CardLayout cardLayout;
	public static User user;

	private ObjectOutputStream clientOutput;
	private ObjectInputStream clientInput;
	private Socket socket;
	private FunctionUnit functionUnit;
	private GridBagConstraints constraints = new GridBagConstraints();

	final private String server_IP = "192.168.1.102";
}
