package client;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1388544483060101676L;
	private static final String host = "82.170.206.79";
	private static final int port = 60606;
	private static final String endl = "\n";
	private Socket socket;
	private DataInputStream in;
	private DataOutputStream out;
	private boolean connected = false;

	private JTextField textField;
	private JTextArea chatText;

	public ChatWindow(int lobby) {
		setupUI();
		setupNetwork(lobby);
	}

	private final void setupUI() {
		setSize(400, 400);
		setResizable(true);
		getContentPane().setLayout(new BorderLayout(5, 5));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				disconnect();
			}
		});

		textField = new JTextField();

		textField.addActionListener(event -> {
			String message = textField.getText();
			textField.setText("");
			try {
				out.writeUTF(message);
			} catch (IOException e) {
				disconnect();
			}
		});
		textField.setEditable(false);
		textField.setCaretPosition(0);
		textField.setFocusable(true);
		getContentPane().add(textField, BorderLayout.PAGE_START);

		chatText = new JTextArea();
		chatText.setEditable(false);
		chatText.setVisible(true);

		JScrollPane scroll = new JScrollPane(chatText);
		getContentPane().add(scroll);
	}

	private final void setupNetwork(int lobbyId) {
		new Thread(
				() -> {
					chatText.append("Trying to connect...");
					try {
						int lobby = lobbyId;

						socket = new Socket(host, port);
						in = new DataInputStream(socket.getInputStream());
						out = new DataOutputStream(socket.getOutputStream());

						if (lobby == -1) {
							lobby = getLobby();
						}
						String username = getUsername();
						setTitle("You are in lobby " + lobby + " as "
								+ username);
						out.writeInt(lobby);
						out.writeUTF(username);
						connected = true;
						textField.setEditable(true);
						textField.getCaret().setVisible(true);
						chatText.append(endl + "Connected");
						new Thread(() -> {
							while (connected) {
								try {
									String input = in.readUTF();
									chatText.append(endl + input);
									chatText.setCaretPosition(chatText
											.getDocument().getLength());
								} catch (IOException e) {
									chatText.append(endl + "Connection lost");
									disconnect();
								}
							}
						}).start();
					} catch (Exception e) {
						chatText.append(endl
								+ "Something went wrong while connecting. Please restart to try again");
						disconnect();
					}
				}).start();

	}

	private int getLobby() {
		String input = JOptionPane.showInputDialog("Enter a lobby number");
		try {
			int lobbyid = Integer.parseInt(input);
			if (lobbyid != Math.abs(lobbyid)) {
				throw new NumberFormatException();
			}
			return lobbyid;
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null,
					"You must enter a positive number");
			return getLobby();
		}
	}

	private String getUsername() {
		String input = JOptionPane.showInputDialog("Enter a username");
		if (input.contains(":")) {
			JOptionPane.showMessageDialog(null,
					"The username cannot contain semicolons");
			return getUsername();
		} else if (input.contains(" ")) {
			JOptionPane.showMessageDialog(null,
					"The username cannot contain whitespaces");
			return getUsername();
		} else if (input.equals("")) {
			JOptionPane.showMessageDialog(null, "The username cannot be empty");
			return getUsername();
		} else {
			return input;
		}
	}

	private void disconnect() {
		textField.setText("");
		connected = false;
		textField.setEditable(false);
		try {
			in.close();
		} catch (Exception e) {
		}
		try {
			out.close();
		} catch (Exception e) {
		}
		try {
			socket.close();
		} catch (Exception e) {
		}
	}
}
