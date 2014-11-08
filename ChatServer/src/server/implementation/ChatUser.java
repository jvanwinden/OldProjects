package server.implementation;

import server.interfaces.Lobby;
import server.interfaces.User;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatUser implements User {

	 private final String name;
	 private DataInputStream in;
	 private DataOutputStream out;
	 private Socket socket;
	 private Lobby lobby;

	 public ChatUser(Socket socket, Lobby lobby) throws IOException {
		  try {
				this.lobby = lobby;
				this.socket = socket;
				this.in = new DataInputStream(socket.getInputStream());
				this.out = new DataOutputStream(socket.getOutputStream());
				String tempName = in.readUTF();
				if (tempName.length() > 10) {
					 name = tempName.substring(0, 10);
				} else {
					 name = tempName;
				}

		  } catch (IOException e) {
				disconnect();
				throw new IOException(e);
		  }

	 }

	 @Override
	 public void run() {
		  try {
				while (true) {
					 String input = in.readUTF();
					 lobby.somethingSaid(input, this);
				}
		  } catch (IOException e) {
				disconnect();
		  }
		  lobby.removeUser(this);
	 }

	 @Override
	 public void sendMessage(String message) {
		  try {
				out.writeUTF(message);
		  } catch (IOException e) {
				e.printStackTrace();
				disconnect();
		  }
	 }

	 @Override
	 public String getName() {
		  return name;
	 }

	 @Override
	 public void disconnect() {
		  close(in);
		  close(out);
		  close(socket);
	 }

	 private void close(Closeable c) {
		  try {
				c.close();
		  } catch (IOException e) {
				e.printStackTrace();
		  }
	 }
}
