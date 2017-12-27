import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
/**
 * This class is a representation of a Bullet, extends from the Entity class
 */
public class Bullet extends Entity {
	
	/**
	 * Here are the declarations for the Bullet class
	 */
	private Image image;
	private BufferedImage img;
	
	/**
	 * This is a constructor for a Bullet
	 * @param p is the location
	 * @param w is the width
	 * @param h is the height
	 * @param hp is the health
	 * @param sp is the speed
	 * @param dir is the direction
	 */
	public Bullet (Point p, int w, int h, int hp, int sp, int dir) {
		super (p, w, h, hp, sp, dir);
	}

	/**
	 * This method draws the Bullet
	 * @param g is the graphics
	 * @param p is the location
	 * @param w is the width
	 * @param h is the height
	 * @param dir is the direction
	 */
	@Override
	void draw(Graphics g, Point p, int w, int h, int dir) {
		grabImage();
		g.setColor(Color.white);
		int x = (int) p.getX();
		int y = (int) p.getY();
		g.drawImage(img, x, y, w, h, null);
	}
	
	/**
	 * This method tests for collision between the Bullet and an Entity
	 * @param e is the entity
	 * @return true if there is a collision; false if there is not a collision
	 */
	public boolean testCollision(Entity e) {
		Rectangle object1 = new Rectangle ((int)getLocation().getX(), (int)getLocation().getY(), getWidth(), getHeight());
		Rectangle object2 = new Rectangle ((int)e.getLocation().getX(), (int)e.getLocation().getY(), e.getWidth(), e.getHeight());
		if (object1.intersects(object2))
			return true;
		else
			return false;
	}

	/**
	 * This method determines and returns the image for a Bullet
	 * @return the image
	 */
	@Override
	Image grabImage() {
		img = null;
		try {
		    img = ImageIO.read(new File("pokeball.png"));
		} catch (IOException e) {
			try {
			    img = ImageIO.read(new File("pokeball.png"));
			} catch (IOException ex) {}
		}
		image = img;
		return image;
	}

}
