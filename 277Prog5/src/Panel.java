import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.util.Random;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
/**
 * This is the Panel class, extends JPanel and implements Runnable, KeyListener, MouseListener, and MouseMotionListener.
 * The goal of this program is to capture at least 10 pokemons by shooting pokeballs.
 * You are only given 25 pokeballs!
 * @author Rachel Dang
 */
@SuppressWarnings("serial")
public class Panel extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener{
	/**
	 * Here are the declarations for the Panel class
	 */
	private Random rand = new Random();
	private Hunter hunter;
	private QuarryGenerator qGen;
	private ArrayList<Obstacle> obstacles;
	private ArrayList<Quarry> quarries;
	private String bulletCount = "POKEBALLS REMAINING: ";
	private int hits = 0;
	
	/**
	 * This method creates a random number of obstacles between 9 and 14
	 */
	public void createObstacles()
	{
		//Creates Obstacles
		obstacles = new ArrayList <Obstacle>();
		for (int i=0; i<rand.nextInt(5)+9; i++)
		{
			Obstacle obstacle = new Obstacle (new Point(rand.nextInt(550)+50, rand.nextInt(350)+30), rand.nextInt(3), null);	
			//Checks to see if newly created obstacle collides with previously made obstacles and hunter
			for (int j=0; j<obstacles.size(); j++)
			{
				while (obstacle.testCollisions(obstacles.get(j)) == true)
					obstacle = new Obstacle (new Point(rand.nextInt(550)+50, rand.nextInt(350)+30), rand.nextInt(3), null);
			}
			obstacles.add(obstacle);
		}
	}
	
	/**
	 * This method creates a Hunter and checks to see if spawn location collides with the obstacles
	 */
	public void createHunter()
	{
		//Creates a Hunter and to see if spawn will collide with obstacles
		for (int i=0; i<obstacles.size(); i++)
		{
			hunter = new Hunter(new Point (rand.nextInt(600)+20,rand.nextInt(400)+20), 50, 50, 50, 2);
			for (int j=0; j<obstacles.size(); j++)
			{
				while (obstacles.get(i).testCollisions(hunter)== true)
					hunter = new Hunter(new Point (rand.nextInt(600)+20,rand.nextInt(300)+20), 50, 50, 50, 2);
			}
		}
	}
	
	/**
	 * This method creates a total of 3 Quarries and checks to see if they collide with one another
	 */
	public void createQuarries()
	{
		quarries = new ArrayList<Quarry>();
		qGen = new QuarryGenerator();
		for (int i=0; i<3; i++)
		{
			Quarry q = qGen.generateQuarry();
			for (int j=0; j<obstacles.size(); j++)
			{
				while (obstacles.get(i).testCollisions(q)== true)
					q = qGen.generateQuarry();
			}
			quarries.add(q);
		}
	}
	
	/**
	 * This is a constructor for the Panel class
	 */
	public Panel()
	{
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.setBackground(Color.black);
		
		play("theme.wav");
		createObstacles();
		createHunter();
		createQuarries();
	}
	
	/**
	 * This method displays the pop-up screen at the end of the game
	 */
	public void endGame(Graphics g)
	{
		g.setColor(Color.white);
		g.fillRect(90, 200, 500, 200);
		g.setColor(Color.gray);
		g.drawRect(100, 210, 480, 180);
		if (hits>=10){
			g.drawString("CONGRATULATIONS! YOU'VE CAPTURED LOTS OF POKEMON TO TRAIN.", 110, 230);
		}
		if (hits<10){
			g.drawString("YOU LOSE... YOU FAILED TO CATCH THEM ALL \t :-(", 110, 230);
		}
		double percent = (double) hits / 25 * 100;
		g.drawString("HERE ARE YOUR STATS: ", 110, 250);
		g.drawString("POKEMONS CAPTURED: "+hits, 140, 270);
		g.drawString("FAILED ATTEMPTS: "+(25-hits), 140, 290);
		g.drawString("ACCURACY: "+percent+"%", 140, 310);
	}
	
	/**
	 * This method draws the different objects onto the panel
	 * @param g is the graphics imported
	 */
	public void paintComponent(Graphics g)
	{
		//While the game is still ongoing
		if (hunter.bulletsLeft()>=0)
		{
			super.paintComponent(g);
			
			//Display bullet count
			g.setColor(Color.white);
			g.drawString(bulletCount+hunter.bulletsLeft(), 10, 20);

			//Update Obstacles
			for (int i=0; i<obstacles.size(); i++){
				obstacles.get(i).update(g);
			}
			
			//Test for Collisions
			testCollisions();		
			
			//Update Hunter
			hunter.update(g);
			
			//Update Quarries
			for (int i=0; i<quarries.size(); i++) {
				quarries.get(i).update(g);
			}
			while (quarries.size()<3)
				quarries.add(qGen.generateQuarry());
		}
		
		//Game stops when player runs out of bullets
		else
			endGame(g);
	}
	
	/**
	 * This is a thread that runs the program and repaints the objects as they are changing positions
	 */
	public void run() {
		while (true)
		{
			repaint();
			try {
				Thread.sleep(16);
			}	catch (InterruptedException e) {}
		}
	}
	
	/**
	 * This method tests the collision bounds between the Entity and the frame of the game
	 * @param e is the Entity
	 */
	public void testFrame(Entity e)
	{
		int left = 0;
		int up = 0;
		int right = 645;
		int down = 420;
			
		if (e.getLocation().getX()<=left){
			e.setDirection(rand.nextInt(3)+2);
			e.move();
			e.move();
			e.stopMoving();
		}
		else if (e.getLocation().getY()<=up){
			e.setDirection(rand.nextInt(3)+4);
			e.move();
			e.move();
			e.stopMoving();
		}
		else if (e.getLocation().getX()>=right){
			e.setDirection(rand.nextInt(3)+6);
			e.move();
			e.move();
			e.stopMoving();
		}
		else if (e.getLocation().getY()>=down){
			e.setDirection(rand.nextInt(3)+1);
			e.move();
			e.move();
			e.stopMoving();
		}
	}

	/**
	 * This method plays a sound file
	 * @param filename is the name of the sound file
	 */
	public static void play(String filename){
		try {
			Clip clip = AudioSystem.getClip();
			clip.open( AudioSystem.getAudioInputStream( new File(filename)));
			clip.start();
			
			}catch(LineUnavailableException e)	{
				System.out.println("Audio Error");
			}catch(IOException e)	{
				System.out.println("File Not Found");
			}catch(UnsupportedAudioFileException e)	{
				System.out.println("Wrong File Type.");
		}
	}
	
	/**
	 * This method tests the collision bounds between an Entity and the Obstacles
	 * @param e is the Entity
	 */
	public void testObstacle(Entity e)
	{
		int d = (int) e.getDirection();
		for (int i=0; i<obstacles.size(); i++)
		{				
			if (obstacles.get(i).testCollisions(e) == true)
			{
				e.setOpDirection(d);
				
				if (e instanceof Quarry)
				{
					for (int k=0; k<3; k++)
						e.move();
					if (obstacles.get(i).testCollisions(e) == false)
					{
						int r = rand.nextInt(2);
						if (r == 0)
							e.spinCW();
						else
							e.spinCCW();
					}
				}
				if (e instanceof Hunter)
				{
					for (int k=0; k<3; k++)
						e.move();
					e.stopMoving();
					if (obstacles.get(i).testCollisions(e) == true)
						e.toggleMoving();
					e.setOpDirection(d);
				}
			}
		}
	}
	
	/**
	 * This method tests for all the collisions using previous smaller test methods
	 */
	public void testCollisions()
	{	
		//HUNTER VS FRAME && HUNTER VS OBSTACLES
		testFrame(hunter);
		testObstacle(hunter);
		
		//BULLET VS OBSTACLES
		for (int i=0; i<obstacles.size(); i++)
		{
			if (hunter.testHit(obstacles.get(i))==true)
				hunter.removeBullet();
		}
		
		//QUARRY VS BULLETS
		for (int i=0; i<quarries.size(); i++)
		{			
			for (int j=0; j<10; j++)
				testObstacle(quarries.get(i));
			if (hunter.testHit(quarries.get(i))==true)
			{
				if (quarries.get(i).getName().equals("Squirrel"))
					play("174.wav");
				else if (quarries.get(i).getName().equals("Snake"))
					play("039.wav");
				else if (quarries.get(i).getName().equals("Bear"))
					play("133.wav");
				else if (quarries.get(i).getName().equals("Deer"))
					play("037.wav");
				else if (quarries.get(i).getName().equals("Bison"))
					play("155.wav");
				
				Image q = quarries.get(i).grabImage();
				quarries.get(i).stopMoving();
				Obstacle newOb = new Obstacle (quarries.get(i).getLocation(), 3, q);
				newOb.setWidth(quarries.get(i).getWidth());
				newOb.setHeight(quarries.get(i).getHeight());
				
				quarries.remove(i);
				obstacles.add(newOb);
				hits++;
			}
		}
		
		//QUARRY VS FRAME
		for (int i=0; i<quarries.size(); i++)
		{
			int left = -125;
			int up = -125;
			int right = 720;
			int down = 565;
				
			if (quarries.get(i).getLocation().getX()<=left){
				quarries.remove(i);
			}
			else if (quarries.get(i).getLocation().getY()<=up){
				quarries.remove(i);
			}
			else if (quarries.get(i).getLocation().getX()>=right){
				quarries.remove(i);
			}
			else if (quarries.get(i).getLocation().getY()>=down){
				quarries.remove(i);
			}
		}
	
	}
	
	/**
	 * This method takes in the mouse's point location and determines where the hunter should be facing
	 * when the arrow is not pressed
	 * @param e is the MouseEvent
	 */
	public void mouseMoved(MouseEvent e) {
		Point move = new Point (e.getX(), e.getY());
		Point location = hunter.getLocation();
		int x = (int) location.getX();
		int y = (int) location.getY();
		
		double angle = Math.atan2(move.getY()-y, move.getX()-x) - (Math.PI/2);
		double angle2 = Math.toDegrees(angle) + 180;
		
		if (angle2 >= -65 && angle2 < -20)
			hunter.setDirection(8);
		else if (angle2 >= -20 && angle2 < 25)
			hunter.setDirection(1);
		else if (angle2 >= 25 && angle2 < 70)
			hunter.setDirection(2);
		else if (angle2 >= 70 && angle2 < 115)
			hunter.setDirection(3);
		else if (angle2 >= 115 && angle2 < 160)
			hunter.setDirection(4);
		else if (angle2 >= 160 && angle2 < 205)
			hunter.setDirection(5);
		else if (angle2 >= 205 && angle2 < 250)
			hunter.setDirection(6);
		else if (angle2 > 250 && angle2 > -65)
			hunter.setDirection(7);
	}

	/**
	 * This method allows the Hunter to fire a bullet
	 * @param e is the MouseEvent
	 */
	public void mouseClicked(MouseEvent e) {
		hunter.fireBullet();
		play("throw.wav");
	}
	
	/**
	 * This method allows the Panel to be focused
	 */
	public boolean isFocusTraversable() {
	    return true;
	  }
	
	/**
	 * This method allows the user to execute different tasks by pressing specific keys
	 * @param e is the KeyEvent
	 */
	public void keyPressed(KeyEvent e) {		
		if (e.getKeyCode() == KeyEvent.VK_ENTER && hunter.isMoving() == false){
			hunter.toggleMoving();
			hunter.move();
		}		
		else if (e.getKeyCode() == KeyEvent.VK_ENTER && hunter.isMoving() == true){
			hunter.stopMoving();
		}	
		else
		{
			if (e.getKeyCode() == KeyEvent.VK_COMMA){
				hunter.spinCCW();
			}
			
			if (e.getKeyCode() == KeyEvent.VK_PERIOD){
				hunter.spinCW();
			}
			
			if (e.getKeyCode() == KeyEvent.VK_SPACE){
				hunter.fireBullet();
				play("throw.wav");
			}
		}
	}

	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
