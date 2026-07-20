package skyline;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Enemy helicopter.
 * 
 * @author www.gametutorial.net
 */

public class EnemyHelicopter {

	// For creating new enemies.
	private static int timeOfEnemyLevel;
	private final static long timeBetweenNewEnemiesInit = Framework.secInNanosec * 3;;
	public static long timeBetweenNewEnemies = timeBetweenNewEnemiesInit;
	public static long timeOfLastCreatedEnemy = 0;

	// Health of the helicopter.
	public int health;
	public static int timerBullet = 1;
	private int offsetEnemyXMachineGun;
	private int offsetEnemyYMachineGun;
	private int item;
	public int machineGunEnemyXcoordinate;
	public int machineGunEnemyYcoordinate;
	public boolean enemyShoot = false;
	static Timer timer = new Timer();

	// Position of the helicopter on the screen.
	public int xCoordinate;
	public int yCoordinate;
   
	// Moving speed and direction.
	private static final double movingXspeedInit = -1;
	private static double movingXspeed = movingXspeedInit;

	// Images of enemy helicopter. Images are loaded and set in Game class in
	// LoadContent() method.
	public static BufferedImage enemyBodyImg;
	public static BufferedImage enemyDefendImg;
	public static BufferedImage enemyUpImg;
	public static BufferedImage enemyHealImg;
	

	/**
	 * Initialize enemy helicopter.
	 * 
	 * @param xCoordinate
	 *            Starting x coordinate of helicopter.
	 * @param yCoordinate
	 *            Starting y coordinate of helicopter.
	 * @param helicopterBodyImg
	 *            Image of helicopter body.
	 * @param helicopterFrontPropellerAnimImg
	 *            Image of front helicopter propeller.
	 * @param helicopterRearPropellerAnimImg
	 *            Image of rear helicopter propeller.
	 */
	public void Initialize(int xCoordinate, int yCoordinate, int level, int item) {
		
		
		// Sets enemy position.
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
		this.item = item;
		if(level == 1)
		{
			health = 150;
			timerBullet = 1000;
		}
		if(level == 2)
		{
			health = 250;
			timerBullet = 600;
		}
		if(level == 3)
		{
			health = 350;
			timerBullet = 400;
		}
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				enemyShoot = true;
			}
		}, 1, timerBullet);

		this.offsetEnemyXMachineGun = enemyBodyImg.getWidth() - 80;
		this.offsetEnemyYMachineGun = enemyBodyImg.getHeight() - 10;
		this.machineGunEnemyXcoordinate = this.xCoordinate + this.offsetEnemyXMachineGun;
		this.machineGunEnemyYcoordinate = this.yCoordinate + this.offsetEnemyYMachineGun;

		// Moving speed and direction of enemy.
		EnemyHelicopter.movingXspeed = -1.4;
	}
	public int GetItem() {
		return item;
	}

	/**
	 * It sets speed and time between enemies to the initial properties.
	 */
	public static void restartEnemy() {
		timeBetweenNewEnemies = timeBetweenNewEnemiesInit;
		timeOfLastCreatedEnemy = 0;
		EnemyHelicopter.movingXspeed = movingXspeedInit;
//		timer.cancel();
	}

	/**
	 * It increase enemy speed and decrease time between new enemies.
	 */
	public static void speedUp() {
//		if(timeBetweenNewEnemies > Framework.secInNanosec)
//			timeBetweenNewEnemies -= Framework.secInNanosec / 100;
//
//		 EnemyHelicopter.movingXspeed -= 0.25;
	}
	

	/**
	 * Checks if the enemy is left the screen.
	 * 
	 * @return true if the enemy is left the screen, false otherwise.
	 */
	public boolean isLeftScreen() {
		if (xCoordinate < 0 - enemyBodyImg.getWidth()) // When the entire helicopter is out of the screen.
			return true;
		else
			return false;
	}

	/**
	 * Updates position of helicopter, animations.
	 */
	public void Update() {
		// Move enemy on x coordinate.
		xCoordinate += movingXspeed;
		enemyShoot = false;
		// Move the machine gun with helicopter.
		this.machineGunEnemyXcoordinate = this.xCoordinate + this.offsetEnemyXMachineGun;
		this.machineGunEnemyYcoordinate = this.yCoordinate + this.offsetEnemyYMachineGun;
	}

	public boolean EnemyShooting(long gameTime) {

		return enemyShoot;

	}

	/**
	 * Draws helicopter to the screen.
	 * 
	 * @param g2d
	 *            Graphics2D
	 */
	public void Draw(Graphics2D g2d) {

		if(item == 1) {
			g2d.drawImage(enemyUpImg, xCoordinate, yCoordinate, null);
		}
		else if(item == 2) {
			g2d.drawImage(enemyDefendImg, xCoordinate, yCoordinate, null);
		}
		else if(item == 3) {
			g2d.drawImage(enemyHealImg, xCoordinate, yCoordinate, null);
		}
		
		else
			g2d.drawImage(enemyBodyImg, xCoordinate, yCoordinate, null);



	}

}
