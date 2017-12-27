import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
/**
 * This class generates Quarries from a given file
 */
public class QuarryGenerator {
	
	public ArrayList<Quarry> quarryList;
	private Random rand = new Random();
	
	/**
	 * This is the constructor for a QuarryGenerator
	 */
	public QuarryGenerator()
	{
		quarryList = new ArrayList<Quarry>();
		try {
			Scanner read = new Scanner (new File ("QuarryList.txt"));
			do {
				String line = read.nextLine();
				String[] split = line.split(",");
				
				String name = split[0];
				int weight = Integer.parseInt(split[1]);
				int hp = Integer.parseInt(split[2]);
				int speed = Integer.parseInt(split[3]);
				int diameter = 0;
				if (weight < 10){
					diameter = 50;
					speed = 5;
				}
				if (weight == 75){
					diameter = 70;
					speed = 3;
				}
				if (weight == 200){
					diameter = 75;
					speed = 2;
				}
				Quarry quarry = new Quarry (new Point (0, 0), diameter, diameter, hp, speed/2, name, weight);
				quarryList.add(quarry);
			}	while (read.hasNext());
			read.close();
		}	catch (FileNotFoundException e) {
			System.out.println("File was not found.");
		}
	}
	
	/**
	 * This method takes a Quarry from the Arraylist, generates a copy with a new direction and spawning location
	 * and returns the copy as the new Quarry
	 * @return a new Quarry
	 */
	public Quarry generateQuarry()
	{
		Point location = new Point (0, 0);
		int direction = 0;
		int spawnLoc = rand.nextInt(2);
		if (spawnLoc == 0)
		{
			location = new Point (-30, rand.nextInt(450));
			direction = rand.nextInt(3)+2;
		}
		if (spawnLoc == 1)
		{
			location = new Point (640, rand.nextInt(450));
			direction = rand.nextInt(3)+6;
		}
		Quarry q = quarryList.get(rand.nextInt(quarryList.size()));
		Quarry newQ = new Quarry (location, q.getWidth(), q.getHeight(), q.getHp(), q.getSpeed(), q.getName(), q.getWeight());
		newQ.setDirection(direction);
		return newQ;
	}
}
