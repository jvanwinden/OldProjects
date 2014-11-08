package force;

import java.awt.*;

public class ForceVector {
	 private int startX;
	 private int startY;
	 private int x;
	 private int y;

	 public ForceVector(int x, int y) {
		  this.startX = x;
		  this.startY = y;
		  this.x = x;
		  this.y = y;
	 }

	 public void applyForce(int x, int y) {
		  this.x += x - startX;
		  this.y += y - startY;
	 }

	 public void draw(Graphics g) {
		  g.setColor(Color.RED);
		  g.drawLine(startX, startY, x, y);
	 }

	 public int getX() {
		  return x - startX;
	 }

	 public int getY() {
		  return y - startY;
	 }
}
