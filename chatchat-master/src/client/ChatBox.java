package client;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatBox implements Serializable {

	public ChatBox(ArrayList<String> memberList) {
		this.memberList = memberList;
		roomName = MainPage.user.getUsername() + ", ";
		System.out.println("[" + memberList.size() + "]");
		for (int i = 0; i < memberList.size(); ++i) {
			if (i == memberList.size() - 1) {
				roomName = roomName.concat(memberList.get(i));
				System.out.println("[" + roomName + "]");
			} else {
				roomName = roomName.concat(memberList.get(i) + ", ");
			}
		}

//		panel = new JPanel(new GridBagLayout());

		boxTextArea = new JTextArea();
		boxTextArea.setEditable(false);
		MainPage.addComponent(boxTextArea, panel, 0, 0, 2, 1, 1, 6, GridBagConstraints.CENTER, GridBagConstraints.BOTH);

		// Set Typing field
		boxTextField = new JTextField("<Type here>");
		boxTextField.setForeground(new java.awt.Color(204, 204, 204));
		boxTextField.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				if (boxTextField.getForeground() != Color.BLACK) {
					if (boxTextField.getText().equals("<Type here>")) {
						boxTextField.setText("");
					}
				}
				boxTextField.setForeground(Color.BLACK);
			}

			public void keyReleased(java.awt.event.KeyEvent evt) {
				if (boxTextField.getText().isEmpty() == true) {
					boxTextField.setText("<Type here>");
					boxTextField.setForeground(new java.awt.Color(204, 204, 204));
				}
			}
		});

		// Set message sending
		boxTextField.addKeyListener(new KeyListener() {

			public void keyTyped(KeyEvent arg0) {
			}

			public void keyReleased(KeyEvent arg0) {
			}

			public void keyPressed(KeyEvent keyEvent) {
				if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
					System.out.println("[Enter]");
					if (!boxTextField.getText().equals("")) {
						try {
							String txt = boxTextField.getText();

							// If this box is personal message box
							if (ChatBox.this.memberList.size() == 1) {
								msg = new Message(txt, MainPage.user.getUsername(), ChatBox.this.memberList.get(0));
							} else {
								msg = new Message(txt, MainPage.user.getUsername(), ChatBox.this.memberList);
							}
							WrappedObj wrappedObj = new WrappedObj(msg);
							MainPage.functionUnit.writeObj(wrappedObj);
							System.out.println("MY MSG: " + boxTextField.getText());
							// displayTextArea.append("my msg: " + typeInTextField.getText() + "\n");
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							boxTextField.setText("<Type here>");
							boxTextField.setForeground(new java.awt.Color(204, 204, 204));
						}
					}
				}

			}
		});
		MainPage.addComponent(boxTextField, panel, 0, 1, 1, 1, 4, 1, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH);

		boxButton = new JButton("ԅ(¯﹃¯ԅ)");
		boxButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				if (!boxTextField.getText().equals("")) {
					try {
						String txt = boxTextField.getText();

						// If this box is personal message box
						if (ChatBox.this.memberList.size() == 1) {
							msg = new Message(txt, MainPage.user.getUsername(), ChatBox.this.memberList.get(0));
						} else {
							msg = new Message(txt, MainPage.user.getUsername(), ChatBox.this.memberList);
						}
						WrappedObj wrappedObj = new WrappedObj(msg);
						MainPage.functionUnit.writeObj(wrappedObj);
						System.out.println("MY MSG: " + boxTextField.getText());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				boxTextField.setText("<Type here>");
				boxTextField.setForeground(new java.awt.Color(204, 204, 204));
			}
		});
		MainPage.addComponent(boxButton, panel, 1, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
	}

	public void initBox() {

		boxTextField.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				if (boxTextField.getForeground() != Color.BLACK) {
					if (boxTextField.getText().equals("<Type here>")) {
						boxTextField.setText("");
					}
				}
				boxTextField.setForeground(Color.BLACK);
			}

			public void keyReleased(java.awt.event.KeyEvent evt) {
				if (boxTextField.getText().isEmpty() == true) {
					boxTextField.setText("<Type here>");
					boxTextField.setForeground(new java.awt.Color(204, 204, 204));
				}
			}
		});

		// Set message sending
		boxTextField.addKeyListener(new KeyListener() {

			public void keyTyped(KeyEvent arg0) {
			}

			public void keyReleased(KeyEvent arg0) {
			}

			public void keyPressed(KeyEvent keyEvent) {
				if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
					System.out.println("[Enter]");
					if (!boxTextField.getText().equals("")) {
						try {
							String txt = boxTextField.getText();

							// If this box is personal message box
							if (ChatBox.this.memberList.size() == 1) {
								msg = new Message(txt, MainPage.user.getUsername(), ChatBox.this.memberList.get(0));
							} else {
								msg = new Message(txt, MainPage.user.getUsername(), ChatBox.this.memberList);
							}
							WrappedObj wrappedObj = new WrappedObj(msg);
							MainPage.functionUnit.writeObj(wrappedObj);
							System.out.println("MY MSG: " + boxTextField.getText());
							// displayTextArea.append("my msg: " + typeInTextField.getText() + "\n");
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							boxTextField.setText("<Type here>");
							boxTextField.setForeground(new java.awt.Color(204, 204, 204));
						}
					}
				}

			}
		});

		boxButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				if (!boxTextField.getText().equals("")) {
					try {
						String txt = boxTextField.getText();

						// If this box is personal message box
						if (ChatBox.this.memberList.size() == 1) {
							msg = new Message(txt, MainPage.user.getUsername(), ChatBox.this.memberList.get(0));
						} else {
							msg = new Message(txt, MainPage.user.getUsername(), ChatBox.this.memberList);
						}
						WrappedObj wrappedObj = new WrappedObj(msg);
						MainPage.functionUnit.writeObj(wrappedObj);
						System.out.println("MY MSG: " + boxTextField.getText());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				boxTextField.setText("<Type here>");
				boxTextField.setForeground(new java.awt.Color(204, 204, 204));
			}
		});
	}

	public String getRoomName() {
		return roomName;
	}

	public JPanel panel;
	public JTextArea boxTextArea;
	public JTextField boxTextField;
	public JButton boxButton;
	private ArrayList<String> memberList;
	private Message msg;
	private String roomName;

}
