package force;

import java.awt.*;

public class Ball {
	 private int x;
	 private int y;
	 private float sx;
	 private float sy;

	 public Ball(int x, int y) {
		  this.x = x;
		  this.y = y;
	 }

	 public void applyForce(int x, int y) {
		  sx += x / 20;
		  sy += y / 20;
	 }

	 public void draw(Graphics g) {
		  g.setColor(Color.BLACK);
		  int width = 10;
		  int height = 10;
		  g.fillArc(x, y, width, height, 0, 360);
	 }

	 public void update() {
		  if (x + 5 > MainApplet.WIDTH || x - 5 < 0) {
				sx = -sx;
		  } else if (y + 5 > MainApplet.HEIGHT || x - 5 < 0) {
				sy = -sy;
		  }
		  sy += 9.8 / 20;

		  x += sx;
		  y += sy;
	 }

	 public int getX() {
		  return x + 5;
	 }

	 public int getY() {
		  return y + 5;
	 }
}
