package server.interfaces;


public interface Server extends Runnable {
	public void removeLobby(Lobby lobby);
	public void shutDown();
}
