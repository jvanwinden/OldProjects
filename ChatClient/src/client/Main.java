package client;

import javax.swing.SwingUtilities;

public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			HubWindow hub = new HubWindow();
			hub.setVisible(true);
		});
	}
}