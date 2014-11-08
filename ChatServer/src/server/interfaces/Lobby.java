package server.interfaces;

import java.net.Socket;
import java.util.List;

public interface Lobby {
	public void somethingSaid(String message, User user);
	public void removeUser(User user);
	public int getId();
	public void addSocket(Socket socket);
	public void shutDown();
	public List<User> getUsers();
}
