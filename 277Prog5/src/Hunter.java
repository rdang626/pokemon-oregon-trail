import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
/**
 * This is a representation of a Hunter, extends the Entity class
 */
public class Hunter extends Entity {
	/**
	 * Here are the declarations for the Hunter class
	 */
	private Bullet bullet;
	private Image image;
	private BufferedImage img;
	private int bulletCount = 25;
	
	/**
	 * This is a constructor for the Hunter
	 * @param p is the location
	 * @param w is the width
	 * @param h is the height
	 * @param hp is the health
	 * @param sp is the speed
	 */
	public Hunter (Point p, int w, int h, int hp, int sp)
	{
		super (p, w, h, hp, sp, 7);
		bullet = new Bullet (new Point(700, 700), 0, 0, 0, 0, sp);
	}
	
	/**
	 * This method allows the Hunter to fire a bullet
	 */
	public void fireBullet()
	{
		bullet = new Bullet (new Point ((int)getLocation().getX()+(getWidth()/3), (int)getLocation().getY()+(getHeight()/3)), 10, 10, 0, 5, getDirection());
		bullet.toggleMoving();
		bulletCount--;
	}
	
	/**
	 * This method tests to see if the bullet has hit an Entity
	 * @param e is the Entity
	 * @return true if the Entity is hit; false if the Entity is not hit
	 */
	public boolean testHit(Entity e)
	{
		return bullet.testCollision(e);
	}
	
	/**
	 * This method removes the bullet by recreating it to an isolated location
	 */
	public void removeBullet()
	{
		bullet.stopMoving();
		bullet = new Bullet(new Point(700, 700), 0, 0, 0, 0, 0);
	}
	
	/**
	 * This method returns the bullet count
	 * @return bulletCount
	 */
	public int bulletsLeft()
	{
		return bulletCount;
	}

	/**
	 * This method draws the Hunter
	 * @param g is the graphics
	 * @param p is the location
	 * @param w is the width
	 * @param h is the height
	 * @param dir is the direction
	 */
	@Override
	void draw(Graphics g, Point p, int w, int h, int dir) {
		grabImage();
		if (bullet.isMoving()==true)
			bullet.update(g);
		int x = (int) p.getX();
		int y = (int) p.getY();
		
		g.setColor(Color.orange);
		g.drawImage(image,(int)p.getLocation().getX(), (int)p.getLocation().getY(), w, h, null);
		g.setColor(Color.red);
		
		x = x+(w/2);
		y = y+(w/2);
	}

	/**
	 * This method determines and returns the image for a Hunter
	 * @return the image
	 */
	@Override
	Image grabImage() {
		img = null;
		try {
		    img = ImageIO.read(new File("24x2.png"));
		} catch (IOException e) {
			try {
			    img = ImageIO.read(new File("24x2.png"));
			} catch (IOException ex) {
				
			}
		}
		int d = getDirection();
		{
			if (d == 5)
				image = img.getSubimage(0, 0, 25 ,25);
			if (d == 1)
				image = img.getSubimage(50, 0, 25 ,25);
			if (d == 7)
				image = img.getSubimage(95, 0, 25 ,25);
			if (d == 3)
				image = img.getSubimage(145, 0, 25 ,25);
			if (d == 6)
				image = img.getSubimage(192, 0, 25 ,25);
			if (d == 4)
				image = img.getSubimage(240, 0, 25 ,25);
			if (d == 8)
				image = img.getSubimage(290, 0, 25 ,25);
			if (d == 2)
				image = img.getSubimage(335, 0, 25 ,25);
		}
		return image;
	}
}
