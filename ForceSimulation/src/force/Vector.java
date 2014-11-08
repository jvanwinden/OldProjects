package force;

import java.awt.*;

public class Vector {
	 private int x;
	 private int y;

	 public Vector(int x, int y) {
		  this.x = x;
		  this.y = y;
	 }

	 public void draw(Graphics g, int sx, int sy) {
		  g.setColor(Color.BLUE);
		  g.drawLine(x, y, sx, sy);
	 }
}
