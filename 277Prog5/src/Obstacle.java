import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
/**
 * This is a reprentation of an Obstacle, extends the Entity class
 */
public class Obstacle extends Entity{
	/**
	 * Here are the declarations for the Obstacle class
	 */
	private int type;
	private Image image;
	private BufferedImage img;
	
	/**
	 * This is a constructor for an Obstacle
	 * @param p is the location
	 * @param t is the type of Obstacle
	 * @param i is the image (passed in to draw an inactive Quarry)
	 */
	public Obstacle(Point p, int t, Image i)
	{
		super (p, 0, 0, 0, 0, 0);
		type = t;
		if (t == 0)
		{
			setWidth(30);
			setHeight(30);
		}
		if (t == 1)
		{
			setWidth(50);
			setHeight(70);
		}
		if (t == 2)
		{
			setWidth(70);
			setHeight(30);
		}
		image = i;
	}
	
	/**
	 * This method returns the type of the Obtacle
	 * @return type
	 */
	public int getType()
	{
		return type;
	}
	
	/**
	 * This method tests for collisions between the Obstacle and an Entity
	 * @param e is the Entity
	 * @return true if there is a collison; false if there is not a collision
	 */
	public boolean testCollisions(Entity e)
	{
		Rectangle object1 = new Rectangle ((int)getLocation().getX(), (int)getLocation().getY(), getWidth(), getHeight());
		Rectangle object2 = new Rectangle ((int)e.getLocation().getX(), (int)e.getLocation().getY(), e.getWidth(), e.getHeight());
		if (object1.intersects(object2))
			return true;
		else
			return false;
	}

	/**
	 * This method draws the Obstacle
	 * @param g is the graphics
	 * @param p is the location
	 * @param w is the width
	 * @param h is the height
	 * @param dir is the direction
	 */
	@Override
	void draw(Graphics g, Point p, int w, int h, int dir) {
		if (type == 3)
			g.drawImage(image, (int)p.getLocation().getX(), (int)p.getLocation().getY(), getWidth(), getHeight(), null);
		else {
			image = grabImage();
			g.drawImage(image, (int)p.getLocation().getX(), (int)p.getLocation().getY(), getWidth(), getHeight(), null);
		}
	}

	/**
	 * This method determines and returns the image for a Obstacle
	 * @return the image
	 */
	@Override
	Image grabImage() {
		File file =  new File ("shrub.png");
		img = null;
		if (type == 0)
			file = new File ("bush.png");
		else if (type == 1)
			file = new File ("tree.png");
		else if (type == 2)
			file = new File ("shrub.png");

		try {
		    img = ImageIO.read(file);
		} catch (IOException e) {
			try {
			    img = ImageIO.read(file);
			} catch (IOException ex) {
			}
		}
		if (type!=4)
			image = img;
		return image;
	}
}
