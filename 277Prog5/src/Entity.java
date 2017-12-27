import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
/**
 * This abstract class is a representation of an Entity.
 * @author Rachel Dang
 *
 */
public abstract class Entity {
	/**
	 * Here are the declarations for the class Entity
	 */
	private Rectangle location;
	private int hp;
	private int speed;
	private int direction;
	private boolean moving = false;
	
	/**
	 * This is a constructor for the class Entity
	 * @param p is the location
	 * @param w is the width
	 * @param h is the height
	 * @param hp is the health
	 * @param sp is the speed
	 * @param dir is the direction
	 */
	public Entity(Point p, int w, int h, int hp, int sp, int dir) {
		location = new Rectangle (p, new Dimension (w, h));
		this.hp = hp;
		speed = sp;
		direction = dir;
	}
	
	/**
	 * This method returns the location of the Entity
	 * @return location
	 */
	public Point getLocation() {
		return location.getLocation();
	}
	
	/**
	 * This method returns the width of the Entity
	 * @return the width
	 */
	public int getWidth() {
		return (int)location.getWidth();
	}
	
	/**
	 * This method returns the height of the Entity
	 * @return the height
	 */
	public int getHeight() {
		return (int)location.getHeight();
	}
	
	/**
	 * This method sets the width of an Entity
	 * @param w is the new width
	 */
	public void setWidth(int w)	{
		location.width = w;
	}
	
	/**
	 * This method sets the height of an Entity
	 * @param h is the new height
	 */
	public void setHeight(int h) {
		location.height = h;
	}
	
	/**
	 * This method returns the health of an Entity
	 * @return hp is the health
	 */
	public int getHp() {
		return hp;
	}
	
	/**
	 * This method returns the speed of an Entity
	 * @return speed
	 */
	public int getSpeed() {
		return speed;
	}
	
	/**
	 * This method sets the speed of an Entity
	 * @param s is the new speed
	 */
	public void setSpeed(int s)	{
		speed = s;
	}
	
	/**
	 * This method returns the direction of the Entity
	 * @return direction
	 */
	public int getDirection() {
		return direction;
	}
	
	/**
	 * This method returns the true if the Entity has no hp and false if the Entity still has hp
	 * @return hp
	 */
	public boolean isDead() {
		if (hp == 0)
			return true;
		else
			return false;
	}
	
	/**
	 * This method checks if the Entity is in motion
	 * @return true if yes, false if no
	 */
	public boolean isMoving() {
		if (moving == true)
			return true;
		else
			return false;
	}
	
	/**
	 * This method allows the Entity to take a hit
	 */
	public void takeHit() {
		stopMoving();
		hp = 0;
		speed = 0;
	}
	
	/**
	 * This method spins the Entity in the clockwise direction
	 */
	public void spinCW() {
		if (direction != 8)
			direction++;
		else
			direction = 1;
	}
	
	/**
	 * This method spins the Entity in the counterclockwise direction
	 */
	public void spinCCW() {
		if (direction != 1)
			direction--;
		else
			direction = 8;
	}
	
	/**
	 * This method sets the direction of the Entity
	 * @param d is the new direction
	 */
	public void setDirection(int d) {
		direction = d;
	}
	
	/**
	 * This method sets the opposite direction of the Entity
	 * @param d is the initial direction
	 */
	public void setOpDirection(int d) {
		for (int i=0; i<4; i++)
			spinCW();
	}
	
	/**
	 * This method allows the target to be in motion
	 */
	public void toggleMoving() {
		moving = true;
	}
	
	/**
	 * This method allows the target to not be in motion
	 */
	public void stopMoving() {
		moving = false;
	}
	
	/**
	 * This method updates the Entity
	 * @param g is the graphics
	 */
	public void update(Graphics g) {
		move();
		draw(g, getLocation(), getWidth(), getHeight(), getDirection());
	}

	/**
	 * This method allows the Entity to move in different directions
	 */
	public void move() {
		if (moving == true) {
			int x = (int) location.getX();
			int y = (int) location.getY();
			
			if (direction == 1) {
				location.setLocation(x, y-speed);
			}
			if (direction == 2) {
				location.setLocation(x+speed, y-speed);
			}
			if (direction == 3) {
				location.setLocation(x+speed, y);
			}
			if (direction == 4) {
				location.setLocation(x+speed, y+speed);
			}
			if (direction == 5) {
				location.setLocation(x, y+speed);
			}
			if (direction == 6) {
				location.setLocation(x-speed, y+speed);
			}
			if (direction == 7) {
				location.setLocation(x-speed, y);
			}
			if (direction == 8) {
				location.setLocation(x-speed, y-speed);
			}
		}
	}
	
	/**
	 * This method draws the Entity
	 * @param g is the graphics
	 * @param p is the location
	 * @param w is the width
	 * @param h is the height
	 * @param dir is the direction
	 */
	abstract void draw(Graphics g, Point p, int w, int h, int dir);
	
	/**
	 * This method determines and returns the image for an Entity
	 * @return the image
	 */
	abstract Image grabImage();
}
