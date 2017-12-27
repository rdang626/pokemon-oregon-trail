import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
/**
 * This is a representation of a Quarry, extends the Entity class
 */
public class Quarry extends Entity{
	/**
	 * Here are the declarations for the Quarry class
	 */
	private String name;
	private int weight;
	private Image image;
	private BufferedImage img;
	
	/**
	 * This is a constructor for a Quarry
	 * @param p is the location
	 * @param w is the width
	 * @param h is the height
	 * @param hp is the health
	 * @param sp is the speed
	 * @param n is the name
	 * @param wt is the weight
	 */
	public Quarry(Point p, int w, int h, int hp, int sp, String n, int wt)
	{
		super (p, w, h, hp, sp, 1);
		name = n;
		weight = w;
		toggleMoving();
	}
	
	/**
	 * This method returns the name of the Quarry
	 * @return name
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * This method returns the weight of the Quarry
	 * @return weight
	 */
	public int getWeight()
	{
		return weight;
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
			int f = 0;
	
			if (name.equals("Squirrel"))
				f = 125;
			else if (name.equals("Snake"))
				f = 147;
			else if (name.equals("Bear"))
				f = 25;
			else if (name.equals("Deer"))
				f = 100;
			else if (name.equals("Bison"))
				f = 50;
			
			if (d == 5)
				image = img.getSubimage(0, f, 23 ,22);
			else if (d == 1)
				image = img.getSubimage(50, f, 23 ,22);
			else if (d == 7)
				image = img.getSubimage(95, f, 23 ,22);
			else if (d == 3)
				image = img.getSubimage(145, f, 23 ,22);
			else if (d == 6)
				image = img.getSubimage(190, f, 23 ,22);
			else if (d == 4)
				image = img.getSubimage(240, f, 23 ,22);
			else if (d == 8)
				image = img.getSubimage(290, f, 23 ,22);
			else if (d == 2)
				image = img.getSubimage(335, f, 23 ,22);
		}
		return image;
	}
	
	/**
	 * This method draws the Quarry
	 * @param g is the graphics
	 * @param p is the location
	 * @param w is the width
	 * @param h is the height
	 * @param dir is the direction
	 */
	@Override
	void draw(Graphics g, Point p, int w, int h, int dir) {
		image = grabImage();
		g.setColor(Color.lightGray);
		g.drawImage(image,(int)p.getLocation().getX(), (int)p.getLocation().getY(), w, h, null);

	}
}
