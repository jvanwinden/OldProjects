package server.implementation;

import javax.swing.*;

public class Main {

	 public static void main(String[] args) {
		  SwingUtilities.invokeLater(() -> {
				ServerUI ui = new ServerUI();
				ui.setVisible(true);
		  });
	 }
}
