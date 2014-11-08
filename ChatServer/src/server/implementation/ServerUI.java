package server.implementation;

import server.interfaces.Loggable;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ServerUI extends JFrame implements Loggable {

	 /**
	  *
	  */
	 private static final long serialVersionUID = -8174032924353178590L;
	 private ChatServer server = null;

	 private JTextArea logText;
	 private JButton startStop;
	 private boolean running = false;

	 public ServerUI() {
		  setupUI();
	 }

	 private void setupUI() {
		  setSize(500, 500);
		  setTitle("Server");
		  setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		  logText = new JTextArea();
		  logText.setEditable(false);
		  startStop = new JButton("Start");
		  startStop.addActionListener(e -> {
				if (!running && startStop.getText().equals("Start")) {
					 startServer();
				} else if (running && startStop.getText().equals("Stop")
						  && server != null) {
					 stopServer();
				}
		  });
		  JScrollPane pane = new JScrollPane(logText);

		  add(startStop, BorderLayout.NORTH);
		  add(pane, BorderLayout.CENTER);
	 }

	 private void startServer() {
		  try {
				server = new ChatServer(this);
				new Thread(server).start();
				running = true;
				startStop.setText("Stop");
				log("Server started");
		  } catch (IOException e) {
				e.printStackTrace();
				stopServer();
		  }
	 }

	 private void stopServer() {
		  server.shutDown();
		  server = null;
		  running = false;
		  startStop.setText("Start");
		  log("Server stopped");
	 }

	 public void log(String msg) {
		  logText.append(msg + "\n");
		  logText.setCaretPosition(logText.getDocument().getLength());
	 }

}
