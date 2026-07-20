package skyline;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;





/**
 * Helicopter machine gun bullet.
 * 
 * @author www.gametutorial.net
 */

public class BulletEnemy {
    
    // For creating new bullets.
    public static final long timeBetweenNewEnemyBullets = Framework.secInNanosec / 1;
    public static long timeOfLastCreatedBullet = 0;
    
    // Damage that is made to an enemy helicopter when it is hit with a bullet.
    public static int damagePower = 10;
    
    // Position of the bullet on the screen. Must be of type double because movingXspeed and movingYspeed will not be a whole number.
    public double xCoordinate;
    public double yCoordinate;
    
    // Moving speed and direction.
    private static int bulletSpeed = 4;
    private double movingXspeed;
    private double movingYspeed;
    
    // Images of helicopter machine gun bullet. Image is loaded and set in Game class in LoadContent() method.
    public static BufferedImage bulletImg;
    
    
    /**
     * Creates new machine gun bullet.
     * 
     * @param xCoordinate From which x coordinate was bullet fired?
     * @param yCoordinate From which y coordinate was bullet fired?
     * @param mousePosition Position of the mouse at the time of the shot.
     */
    public BulletEnemy(int xCoordinate, int yCoordinate, int level, int direction)
    {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        if(level == 1)
        {
        	bulletSpeed = 4;
        }
        if(level == 2)
        {
        	bulletSpeed = 5;
        }
        if(level == 3)
        {
        	bulletSpeed = 6;
        }
        switch (direction) {
		case 1:
			setDirectionAndSpeed(1, -0.1);//ขึ้น
			break;
		case 2:
			setDirectionAndSpeed(1, 0.1);
			break;
		case 3:
			setDirectionAndSpeed(1, 0);
			break;
		case 4:
			setDirectionAndSpeed(1, -0.);
			break;
		case 5:
			setDirectionAndSpeed(1, -0.6);
			break;
		case 6:
			setDirectionAndSpeed(1, -0.3);
			break;
		case 7:
			setDirectionAndSpeed(1, 0);
			break;
		case 8:
			setDirectionAndSpeed(1, 0.3);
			break;
		case 9:
			setDirectionAndSpeed(1, 0.6);
			break;
		case 10:
			setDirectionAndSpeed(1, 0.12);
			break;
		
		default:
			break;
		}
    }
    
    
    /**
     * Calculate the speed on a x and y coordinate.
     * 
     * @param mousePosition 
     */
    private void setDirectionAndSpeed(double xCoordinate, double yCoordinate)
    {
        // Unit direction vector of the bullet.
        double directionVx = xCoordinate;
        double directionVy = yCoordinate;

        this.movingXspeed = bulletSpeed * directionVx;
        this.movingYspeed = bulletSpeed * directionVy;
    }
    
    
    /**
     * Checks if the bullet is left the screen.
     * 
     * @return true if the bullet left the screen, false otherwise.
     */
    public boolean isItLeftScreen()
    {
        if(xCoordinate > 0 && xCoordinate < Framework.frameWidth &&
           yCoordinate > 0 && yCoordinate < Framework.frameHeight)
            return false;
        else
            return true;
    }
    
    
    /**
     * Moves the bullet.
     */
    public void Update()
    {
        xCoordinate -= movingXspeed;
        yCoordinate += movingYspeed;
    }
    
    
    /**
     * Draws the bullet to the screen.
     * 
     * @param g2d Graphics2D
     */
    public void Draw(Graphics2D g2d)
    {
        g2d.drawImage(bulletImg, (int)xCoordinate, (int)yCoordinate, null);
    }
}
