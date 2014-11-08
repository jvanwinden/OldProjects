package force;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class MainApplet extends Applet implements Runnable {


	 public static final int WIDTH = 400;
	 public static final int HEIGHT = 400;
	 /**
	  *
	  */
	 private static final long serialVersionUID = -4973519695036555633L;
	 private State state;
	 private Button startSimButton = new Button("Start Simulation");
	 private Button showForceVector = new Button("Show vector");
	 private Button reset = new Button("Reset");
	 private Ball ball;
	 private ForceVector totalForce;
	 private List<Vector> vectors;


	 @Override
	 public void init() {
		  setSize(WIDTH, HEIGHT);
		  setFocusable(true);

		  add(reset);
		  add(startSimButton);
		  add(showForceVector);
		  addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					 vectors.add(new Vector(e.getX(), e.getY()));
					 totalForce.applyForce(e.getX(), e.getY());
				}
		  });

		  startSimButton.setVisible(true);
		  showForceVector.setVisible(true);
	 }

	 @Override
	 public void start() {
		  state = State.SETTING;
		  ball = new Ball(200, 200);
		  totalForce = new ForceVector(ball.getX(), ball.getY());
		  vectors = new ArrayList<>();

		  Thread thread = new Thread(this);
		  thread.start();
	 }

	 @Override
	 public void run() {
		  while (true) {
				repaint();
				try {
					 Thread.sleep(20);
				} catch (InterruptedException e) {
					 e.printStackTrace();
				}
		  }
	 }

	 @Override
	 public void paint(Graphics g) {
		  switch (state) {
				case SETTING:
					 g.setColor(Color.green);
					 g.fillRect(0, 0, WIDTH, HEIGHT);

					 for (Vector vector : vectors) {
						  vector.draw(g, ball.getX(), ball.getY());
					 }
					 ball.draw(g);

					 break;
				case FORCEARROW:
					 g.setColor(Color.green);
					 g.fillRect(0, 0, WIDTH, HEIGHT);
					 totalForce.draw(g);
					 ball.draw(g);
					 break;
				case SIMULATING:
					 g.setColor(Color.green);
					 g.fillRect(0, 0, WIDTH, HEIGHT);
					 ball.update();
					 ball.draw(g);

					 break;
		  }
	 }

	 @Override
	 public boolean action(Event evt, Object what) {
		  if (state != State.SIMULATING) {
				if (evt.target == startSimButton) {
					 ball.applyForce(totalForce.getX(), totalForce.getY());
					 state = State.SIMULATING;
				} else if (evt.target == showForceVector) {
					 state = State.FORCEARROW;
				}
		  }

		  if (evt.target == reset) {
				state = State.SETTING;
				ball = new Ball(200, 200);
				totalForce = new ForceVector(ball.getX(), ball.getY());
				vectors = new ArrayList<>();
		  }

		  return true;

	 }

}
