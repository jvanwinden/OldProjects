package server.interfaces;

public interface User extends Runnable {
	public String getName();
	public void sendMessage(String message);
	public void disconnect();
}
