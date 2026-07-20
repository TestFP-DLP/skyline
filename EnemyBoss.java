package skyline;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.newdawn.slick.Sound;

/**
 * Enemy helicopter.
 * 
 * @author www.gametutorial.net
 */

public class EnemyBoss {

	// For creating new enemies.
	private static final long timeBetweenNewEnemiesInit = Framework.secInNanosec * 3;
	public static long timeBetweenNewEnemies = timeBetweenNewEnemiesInit;
	public static long timeOfLastCreatedEnemy = 0;
	public static Sound bossWarning;
	
	private int bossStage;

	// Health of the helicopter.
	public int health;

	// Position of the helicopter on the screen.
	public int xCoordinate;
	public int yCoordinate;
	public int bossDirection = 1;
	private int offsetBossXMachineGun;
	private int offsetBossYMachineGun;

	public int machineGunBossXcoordinate;
	public int machineGunBossYcoordinate;

	private int offsetBossXMachineGun1;
	private int offsetBossYMachineGun1;
	
	public int machineGunBossXcoordinate1;
	public int machineGunBossYcoordinate1;

	private int offsetBossXMachineGun2;
	private int offsetBossYMachineGun2;

	public int machineGunBossXcoordinate2;
	public int machineGunBossYcoordinate2;
    public static boolean hitFade = false;
	public static boolean lastBoss = false;
	public boolean active;
	private long gametime;
	private long timeToPlay;
	private double movingXspeed;
	private double movingYspeed;
	boolean shoot = true;
	public static BufferedImage BossSt1;
	public static BufferedImage BossSt1hit;
	public static BufferedImage BossSt2;
	public static BufferedImage BossSt2hit;
	public static BufferedImage BossSt3;
	public static BufferedImage BossSt3hit;
	public static BufferedImage BossSt4;
	public static BufferedImage BossSt4hit;

	
	public EnemyBoss(int level) {
		
		this.bossStage = level;
		active = true;
	}

	public void Initialize(int xCoordinate, int yCoordinate) {
		
		if (bossStage == 1) {
			health = 1500;
			this.offsetBossXMachineGun = BossSt1.getWidth() - 392;
			this.offsetBossYMachineGun = BossSt1.getHeight() - 28;
			
			this.machineGunBossXcoordinate = this.xCoordinate + this.offsetBossXMachineGun;
			this.machineGunBossYcoordinate = this.yCoordinate + this.offsetBossYMachineGun;
			
			this.machineGunBossXcoordinate1 = this.xCoordinate + this.offsetBossXMachineGun;
			this.machineGunBossYcoordinate1 = this.yCoordinate + this.offsetBossYMachineGun;
			
			this.machineGunBossXcoordinate2 = this.xCoordinate + this.offsetBossXMachineGun;
			this.machineGunBossYcoordinate2 = this.yCoordinate + this.offsetBossYMachineGun;
		}
		if (bossStage == 2) {
			health = 2000;
			this.offsetBossXMachineGun = BossSt2.getWidth() - 432;
			this.offsetBossYMachineGun = BossSt2.getHeight() - 101;

			this.offsetBossXMachineGun1 = BossSt2.getWidth() - 432;
			this.offsetBossYMachineGun1 = BossSt2.getHeight() - 54;

			this.offsetBossXMachineGun2 = BossSt2.getWidth() - 459;
			this.offsetBossYMachineGun2 = BossSt2.getHeight() - 72;
			
			this.machineGunBossXcoordinate = this.xCoordinate + this.offsetBossXMachineGun;
			this.machineGunBossYcoordinate = this.yCoordinate + this.offsetBossYMachineGun;
			
			this.machineGunBossXcoordinate1 = this.xCoordinate + this.offsetBossXMachineGun1;
			this.machineGunBossYcoordinate1 = this.yCoordinate + this.offsetBossYMachineGun1;
			
			this.machineGunBossXcoordinate2 = this.xCoordinate + this.offsetBossXMachineGun2;
			this.machineGunBossYcoordinate2 = this.yCoordinate + this.offsetBossYMachineGun2;
		}
		if (bossStage == 3) {
			health = 2500;
			this.offsetBossXMachineGun = BossSt3.getWidth() - 476;
			this.offsetBossYMachineGun = BossSt3.getHeight() - 20;

			this.offsetBossXMachineGun1 = BossSt3.getWidth() - 424;
			this.offsetBossYMachineGun1 = BossSt3.getHeight() - 148;

			this.offsetBossXMachineGun2 = BossSt3.getWidth() - 400;
			this.offsetBossYMachineGun2 = BossSt3.getHeight() - 25;
			
			this.machineGunBossXcoordinate = this.xCoordinate + this.offsetBossXMachineGun;
			this.machineGunBossYcoordinate = this.yCoordinate + this.offsetBossYMachineGun;
			
			this.machineGunBossXcoordinate1 = this.xCoordinate + this.offsetBossXMachineGun1;
			this.machineGunBossYcoordinate1 = this.yCoordinate + this.offsetBossYMachineGun1;
			
			this.machineGunBossXcoordinate2 = this.xCoordinate + this.offsetBossXMachineGun2;
			this.machineGunBossYcoordinate2 = this.yCoordinate + this.offsetBossYMachineGun2;
		}
		if (bossStage == 4) {
			health = 3200;
			lastBoss = true;
			this.offsetBossXMachineGun = BossSt1.getWidth() - 494;
			this.offsetBossYMachineGun = BossSt1.getHeight() - 43;

			this.offsetBossXMachineGun1 = BossSt1.getWidth() - 450;
			this.offsetBossYMachineGun1 = BossSt1.getHeight() - 37;
			
			this.machineGunBossXcoordinate = this.xCoordinate + this.offsetBossXMachineGun;
			this.machineGunBossYcoordinate = this.yCoordinate + this.offsetBossYMachineGun;
			
			this.machineGunBossXcoordinate1 = this.xCoordinate + this.offsetBossXMachineGun1;
			this.machineGunBossYcoordinate1 = this.yCoordinate + this.offsetBossYMachineGun1;
		}

		// Sets enemy position.
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;

		// Moving speed and direction of enemy.
		movingXspeed = -2;
		movingYspeed = 1;
	}

	/**
	 * It sets speed and time between enemies to the initial properties.
	 */
	public static void restartEnemy() {
		timeBetweenNewEnemies = timeBetweenNewEnemiesInit;
		timeOfLastCreatedEnemy = 0;
	}

	/**
	 * It increase enemy speed and decrease time between new enemies.
	 */
	public static void speedUp() {

	}

	/**
	 * Checks if the enemy is left the screen.
	 * 
	 * @return true if the enemy is left the screen, false otherwise.
	 */
	public boolean bossShooting(long gameTime) {
		
//		this.gametime = (int) (gametime / Framework.milisecInNanosec / 1000);
//		timeToPlay = (int) (this.gametime + 100);
//		if(gametime - timeToPlay == 0)
//		{
//			active = false;
//		}
//		else
//			gametime++;
//		System.out.println(gametime + " " + timeToPlay);
		
		
        // Checks if left mouse button is down && if it is the time for a new bullet.
        if( shoot && ((gameTime - BulletBoss.timeOfLastCreatedBossBullet) >= BulletBoss.timeBetweenNewBossBullets))
        {
        	
        	return true;
        	 
        } 
         else	 
         {
        	 
        	 return false; 
         }
            
       

	}

	/**
	 * Updates position of helicopter, animations.
	 */
	public void Update() {
		if (xCoordinate > Framework.frameWidth - BossSt1.getWidth())
		{
			xCoordinate += movingXspeed;
		}
		else {
			shoot = true;
			bossWarning.stop();
			switch (bossDirection) {
			case 1:
				if (yCoordinate > (Framework.frameHeight - (Framework.frameHeight - 50))) 
				{
					yCoordinate -= movingYspeed;
				} else 
				{
					bossDirection = 2;
				}
				break;
			case 2:
				if (yCoordinate < Framework.frameHeight - (BossSt1.getHeight()+80)) {
					yCoordinate += movingYspeed;
				} else {
					bossDirection = 1;
				}
				break;

			default:
				break;
			}
		}
		switch (bossStage) {
		case 1:
			this.machineGunBossXcoordinate = this.xCoordinate + this.offsetBossXMachineGun;
	        this.machineGunBossYcoordinate = this.yCoordinate + this.offsetBossYMachineGun;
	        
	        this.machineGunBossXcoordinate1 = this.xCoordinate + this.offsetBossXMachineGun;
	        this.machineGunBossYcoordinate1 = this.yCoordinate + this.offsetBossYMachineGun;
	        
	        this.machineGunBossXcoordinate2 = this.xCoordinate + this.offsetBossXMachineGun;
	        this.machineGunBossYcoordinate2 = this.yCoordinate + this.offsetBossYMachineGun;
			break;
		case 2:
			this.machineGunBossXcoordinate = this.xCoordinate + this.offsetBossXMachineGun;
			this.machineGunBossYcoordinate = this.yCoordinate + this.offsetBossYMachineGun;
			
			this.machineGunBossXcoordinate1 = this.xCoordinate + this.offsetBossXMachineGun1;
			this.machineGunBossYcoordinate1 = this.yCoordinate + this.offsetBossYMachineGun1;
			
			this.machineGunBossXcoordinate2 = this.xCoordinate + this.offsetBossXMachineGun2;
			this.machineGunBossYcoordinate2 = this.yCoordinate + this.offsetBossYMachineGun2;
			break;
		case 3:
			this.machineGunBossXcoordinate = this.xCoordinate + this.offsetBossXMachineGun;
			this.machineGunBossYcoordinate = this.yCoordinate + this.offsetBossYMachineGun;
			
			this.machineGunBossXcoordinate1 = this.xCoordinate + this.offsetBossXMachineGun1;
			this.machineGunBossYcoordinate1 = this.yCoordinate + this.offsetBossYMachineGun1;
			
			this.machineGunBossXcoordinate2 = this.xCoordinate + this.offsetBossXMachineGun2;
			this.machineGunBossYcoordinate2 = this.yCoordinate + this.offsetBossYMachineGun2;
			
			break;
		case 4:
			this.machineGunBossXcoordinate = this.xCoordinate + this.offsetBossXMachineGun;
			this.machineGunBossYcoordinate = this.yCoordinate + this.offsetBossYMachineGun;
			
			this.machineGunBossXcoordinate1 = this.xCoordinate + this.offsetBossXMachineGun1;
			this.machineGunBossYcoordinate1 = this.yCoordinate + this.offsetBossYMachineGun1;
			break;

		default:
			break;
		}
        
        
	}

	/**
	 * Draws helicopter to the screen.
	 * 
	 * @param g2d
	 *            Graphics2D
	 */
	public void Draw(Graphics2D g2d) {
		switch (bossStage) {
		case 1:
			if(hitFade == false)
				g2d.drawImage(BossSt1, xCoordinate, yCoordinate, null);
			else
			{
				g2d.drawImage(BossSt1hit, xCoordinate, yCoordinate, null);
				hitFade = false;
			}
			break;
		case 2:
			if(hitFade == false)
				g2d.drawImage(BossSt2, xCoordinate, yCoordinate, null);
			else
			{
				g2d.drawImage(BossSt2hit, xCoordinate, yCoordinate, null);
				hitFade = false;
			}
			break;
		case 3:
			if(hitFade == false)
				g2d.drawImage(BossSt3, xCoordinate, yCoordinate, null);
			else
			{
				g2d.drawImage(BossSt3hit, xCoordinate, yCoordinate, null);
				hitFade = false;
			}
			break;
		case 4:
			if(hitFade == false)
				g2d.drawImage(BossSt4, xCoordinate, yCoordinate, null);
			else
			{
				g2d.drawImage(BossSt4, xCoordinate, yCoordinate, null);
				hitFade = false;
			}
			break;

		default:
			break;
		}
	}

}
