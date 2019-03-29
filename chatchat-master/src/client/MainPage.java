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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class MainPage extends JFrame implements ActionListener {
	public MainPage(User user) {
		mainPage = this;
		MainPage.user = user;
		try {
			functionUnit = new FunctionUnit();
			functionUnit.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Initialize GUI with GridBagLayout & CardLayout
		setSize(400, 600);
		setLayout(new GridBagLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Welcome To Chatchat " + MainPage.user.getUsername());

		cardLayout = new CardLayout();
		chatCardLayout = new CardLayout();
		displayPanel = new JPanel(cardLayout);
		worldChPanel = new JPanel(new GridBagLayout());
		friendListPanel = new JPanel(new GridBagLayout());
		groupListPanel = new JPanel();
		chatPanel = new JPanel(chatCardLayout);
//		chatPanel.add(new JPanel(), "Default Panel");
		
		// INIT DISPLAY PANEL
		
		displayPanel.add(worldChPanel, "World Channel");
		displayPanel.add(friendListPanel, "Friend List");
		displayPanel.add(groupListPanel, "Group List");
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

		chatRoomButton = new JButton("Group List");
		chatRoomButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cardLayout.show(displayPanel, "Group List");
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

		//INIT WORLD CHANNEL PANEL
		
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
					typeInTextField.setText("<Type here>");
					typeInTextField.setForeground(new java.awt.Color(204, 204, 204));
				}
			}
		});

		// Set message sending
		typeInTextField.addKeyListener(new KeyListener() {

			public void keyTyped(KeyEvent arg0) {
			}

			public void keyReleased(KeyEvent arg0) {
			}

			public void keyPressed(KeyEvent keyEvent) {
				if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!typeInTextField.getText().equals("")) {
						try {
							Message msg = new Message(typeInTextField.getText(), user.getUsername());
							WrappedObj wrappedObj = new WrappedObj(msg);
							functionUnit.writeObj(wrappedObj);
							System.out.println("MY MSG: " + typeInTextField.getText());
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

		sendButton = new JButton("ԅ(¯﹃¯ԅ)安安");
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				if (!typeInTextField.getText().equals("")) {
					try {
						Message msg = new Message(typeInTextField.getText(), user.getUsername());
						WrappedObj wrappedObj = new WrappedObj(msg);
						functionUnit.writeObj(wrappedObj);
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
		
		// INIT FRIEND LIST PANEL
		
		// Set frienfListModel
		friendListModel = new DefaultListModel<String>();
		friendListModel.addElement("test");
		friendListModel.addElement("test");
		friendListModel.addElement("test");
		friendListModel.addElement("test");
		friendListModel.addElement("test");
		friendListModel.addElement("test");
		JList<String> friendList = new JList<String>(friendListModel);
		fScroll = new JScrollPane(friendList);
		friendList.setFixedCellHeight(75);
		friendList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				if (evt.getClickCount() == 2) {
					
					// Double-clicked detecting to jump to chatBox
					cardLayout.show(displayPanel, "Chat");
					chatCardLayout.show(chatPanel, friendList.getSelectedValue());
				}
			}
		});
		addComponent(fScroll, friendListPanel, 0, 1, 2, 1, 1, 8, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
		
		addFriendTextField = new JTextField("<Search here>");
		addFriendTextField.setForeground(new java.awt.Color(204, 204, 204));
		addFriendTextField.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				if (addFriendTextField.getForeground() != Color.BLACK) {
					if (addFriendTextField.getText().equals("<Search here>")) {
						addFriendTextField.setText("");
					}
				}
				addFriendTextField.setForeground(Color.BLACK);
			}

			public void keyReleased(java.awt.event.KeyEvent evt) {
				if (addFriendTextField.getText().isEmpty() == true) {
					addFriendTextField.setText("<Search here>");
					addFriendTextField.setForeground(new java.awt.Color(204, 204, 204));
				}
			}
		});
		
		addFriendTextField.addKeyListener(new KeyListener() {

			public void keyTyped(KeyEvent arg0) {
			}

			public void keyReleased(KeyEvent arg0) {
			}

			public void keyPressed(KeyEvent keyEvent) {
				if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!addFriendTextField.getText().equals("") && !addFriendTextField.getText().equals(MainPage.user.getUsername())) {
						try {
							String friend = addFriendTextField.getText();
							if(DBHandler.accountCheck(friend)) {
								functionUnit.addFriend(friend);
								JOptionPane.showMessageDialog(friendListPanel, "Adding successful!", "", JOptionPane.INFORMATION_MESSAGE);
							} else {
								JOptionPane.showMessageDialog(friendListPanel, "This user is not exist!", "Failed", JOptionPane.ERROR_MESSAGE);
							}
						} catch (Exception e) {
							e.printStackTrace();
						} 
					}
					addFriendTextField.setText("<Search here>");
					addFriendTextField.setForeground(new java.awt.Color(204, 204, 204));
				}
			}
		});
		addComponent(addFriendTextField, friendListPanel, 0, 0, 1, 1, 4, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
		
		addFriendButton = new JButton("ԅ(¯﹃¯ԅ)");
		addFriendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				if (!addFriendTextField.getText().equals("") && !addFriendTextField.getText().equals(MainPage.user.getUsername())) {
					try {
						String friend = addFriendTextField.getText();
						if(DBHandler.accountCheck(friend)) {
							functionUnit.addFriend(friend);
							JOptionPane.showMessageDialog(friendListPanel, "Adding successful!", "", JOptionPane.INFORMATION_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(friendListPanel, "This user is not exist!", "Failed", JOptionPane.ERROR_MESSAGE);
						}
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}
				addFriendTextField.setText("<Search here>");
				addFriendTextField.setForeground(new java.awt.Color(204, 204, 204));
			}
		});
		addComponent(addFriendButton, friendListPanel, 1, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
		
		// CENTRALIZE THE WINDOW
		Toolkit toolkit = getToolkit();
		Dimension size = toolkit.getScreenSize();
		setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 2);
	}

	// Method to add Components in GridBagLayout
	public static void addComponent(Component component, Container container, int row, int column, int width, int height,
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

	public static MainPage mainPage;
	public static JPanel controlPanel, displayPanel, worldChPanel, friendListPanel, groupListPanel, chatPanel;
	public static JTextField typeInTextField, addFriendTextField;
	public static JTextArea displayTextArea;
	public static JButton worldChButton, friendListButton, chatRoomButton, chatButton, sendButton, addFriendButton;
	public static CardLayout cardLayout, chatCardLayout;
	public static User user;
	public static DefaultListModel<String> friendListModel;
	public static DefaultListModel<ChatBox> chatRoomListModel;
	public static JScrollPane fScroll, rScroll;
	public static GridBagConstraints constraints = new GridBagConstraints();
	public static FunctionUnit functionUnit;
}
