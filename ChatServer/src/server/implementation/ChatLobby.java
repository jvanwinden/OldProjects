package server.implementation;

import server.interfaces.Lobby;
import server.interfaces.Loggable;
import server.interfaces.Server;
import server.interfaces.User;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatLobby implements Lobby {

	 private final int id;
	 private List<User> users;
	 private Server server;
	 private Loggable log;

	 public ChatLobby(int id, Server server, Loggable log) {
		  this.users = new ArrayList<>();
		  this.log = log;
		  this.id = id;
		  this.server = server;
	 }

	 @Override
	 public void addSocket(Socket socket) {
		  try {
				User newUser = new ChatUser(socket, this);
				new Thread(newUser).start();
				for (User user : users) {
					 user.sendMessage(newUser.getName() + " joined");
					 newUser.sendMessage(user.getName() + " joined");
				}
				newUser.sendMessage("You joined");
				users.add(newUser);
				log.log("User " + newUser.getName() + " successfully joined lobby " + getId());
		  } catch (IOException e) {
				e.printStackTrace();
		  }
	 }

	 @Override
	 public synchronized int getId() {
		  return id;
	 }

	 @Override
	 public synchronized void somethingSaid(String msg, User chattingUser) {
		  log.log(chattingUser.getName() + ": " + msg + " in lobby " + id);
		  for (User user : users) {
				user.sendMessage(chattingUser.getName() + ": " + msg);
		  }
	 }

	 @Override
	 public synchronized void removeUser(User user) {
		  log.log(user.getName() + " removed from lobby " + getId());
		  users.remove(user);
		  if (users.size() == 0) {
				server.removeLobby(this);
		  }
		  for (User user1 : users) {
				user1.sendMessage(user.getName() + " left lobby");
		  }
	 }

	 @Override
	 public List<User> getUsers() {
		  return users;
	 }

	 @Override
	 public void shutDown() {
		  for (User user : users) {
				user.disconnect();
		  }
		  users.clear();
	 }
}
