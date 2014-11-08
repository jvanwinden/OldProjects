package client;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class HubWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5159176626150139221L;
	private static final int port = 60606;
	private static final String host = "82.170.206.79";

	private DefaultListModel<String> lobbyData;
	private JList<String> lobbyList;

	public HubWindow() {
		setupUI();
	}

	private void setupUI() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(400, 400);
		setResizable(true);
		setTitle("Chat");
		
		lobbyData = new DefaultListModel<>();
		lobbyList = new JList<>(lobbyData);
		JLabel label = new JLabel("Active lobbies: ");
		JButton joinNew = new JButton("Join specific lobby");
		JButton refresh = new JButton("Refresh");
		JPanel southpanel = new JPanel();
		joinNew.addActionListener(e -> {
			startChatting(-1);
		});
		refresh.addActionListener(e -> {
			new Thread(() -> {
				listLobbies();
			}).start();
		});

		lobbyList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					String[] things = lobbyList.getSelectedValue().split(":");
					int lobby = Math.abs(Integer.parseInt(things[0]));
					startChatting(lobby);
				}
			}
		});
		
		southpanel.add(joinNew);
		southpanel.add(refresh);

		add(label, BorderLayout.NORTH);
		add(lobbyList, BorderLayout.CENTER);
		add(southpanel, BorderLayout.SOUTH);
	}

	private void listLobbies() {
		lobbyData.clear();
		try (Socket connection = new Socket(host, port);
				DataOutputStream out = new DataOutputStream(
						connection.getOutputStream());
				DataInputStream in = new DataInputStream(
						connection.getInputStream())) {
			out.writeInt(-1);
			String result = in.readUTF();
			parse(result);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void parse(String raw) {
		String[] lobbies = raw.split(":");
		for(int i = 1; i < lobbies.length; i++) {
			String result = "";
			String[] users = lobbies[i].split(" ");
			result += users[0] + ": ";
			for(int j = 1; j < users.length; j++) {
				result += users[j];
				if(j != users.length - 1) {
					result += ", ";
				}
			}
			lobbyData.addElement(result);
		}
	}
	
	private void startChatting(int lobby) {
		SwingUtilities.invokeLater(() -> {
			ChatWindow chat = new ChatWindow(lobby);
			chat.setVisible(true);
		});
	}
}
