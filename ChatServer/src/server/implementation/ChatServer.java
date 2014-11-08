package server.implementation;

import server.interfaces.Lobby;
import server.interfaces.Loggable;
import server.interfaces.Server;
import server.interfaces.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class ChatServer implements Server {

	 private static final int PORT = 60606;
	 private ServerSocket serverSocket;
	 private List<Lobby> lobbies;
	 private Loggable log;

	 public ChatServer(Loggable log) throws IOException {
		  serverSocket = new ServerSocket(PORT);
		  lobbies = new ArrayList<>();
		  this.log = log;
	 }

	 @Override
	 public void run() {
		  while (true) {
				try {
					 Socket connection = serverSocket.accept();
					 DataInputStream in = new DataInputStream(connection.getInputStream());
					 int id = in.readInt();
					 if (id == -1) {
						  String result = format();
						  DataOutputStream out = new DataOutputStream(connection.getOutputStream());
						  out.writeUTF(result);
						  in.close();
						  out.close();
						  connection.close();
					 } else {
						  Lobby destination = findLobby(id);
						  destination.addSocket(connection);
					 }
				} catch (SocketException e) {
					 // socket was closed and server will be shut down
					 break;
				} catch (IOException e) {
					 e.printStackTrace();
				}
		  }
	 }

	 private String format() {
		  String result = "Lobbies";
		  for (Lobby lobby : lobbies) {
				result += ":" + lobby.getId();

				for (User user : lobby.getUsers()) {
					 result += " " + user.getName();
				}
		  }
		  return result;
	 }

	 private Lobby findLobby(int id) {
		  for (Lobby lobby : lobbies) {
				if (lobby.getId() == id) {
					 return lobby;
				}
		  }
		  Lobby l = new ChatLobby(id, this, log);
		  lobbies.add(l);
		  return l;
	 }

	 @Override
	 public synchronized void removeLobby(Lobby l) {
		  lobbies.remove(l);
	 }

	 public void shutDown() {
		  try {
				serverSocket.close();
		  } catch (IOException e) {
				e.printStackTrace();
		  }

		  for (Lobby l : lobbies) {
				l.shutDown();
		  }
		  lobbies.clear();
	 }
}
