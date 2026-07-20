package skyline;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;





/**
 * Helicopter machine gun bullet.
 * 
 * @author www.gametutorial.net
 */

public class BulletBoss {
    
    // For creating new bullets.
    public static final long timeBetweenNewBossBullets = Framework.secInNanosec / 2;
    public static long timeOfLastCreatedBossBullet = 0;
    
    // Damage that is made to an enemy helicopter when it is hit with a bullet.
    public static int damagePower = 10;
    public boolean trackOn;
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
    public BulletBoss(int xCoordinate, int yCoordinate, long level, int direction)
    {
    	
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        if(level == 1)
        {
        	bulletSpeed = 3;
        }
        if(level == 2)
        {
        	bulletSpeed = 4;
        }
        if(level == 3)
        {
        	bulletSpeed = 5;
        }
        if(level == 4)
        {
        	bulletSpeed = 5;
        }
        switch (direction) {
		case 1:
			setDirectionAndSpeed(1, -0.3);//ขึ้น
			break;
		case 2:
			setDirectionAndSpeed(1, 0.3);
			break;
		case 3:
			setDirectionAndSpeed(1, 0);
			break;
		case 4:
			setDirectionAndSpeed(1, -0.12);
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
    
    public BulletBoss(int xCoordinate, int yCoordinate, long level, int direction, boolean trackOn)
    {
    	this.trackOn = trackOn;
    	System.out.println(trackOn);
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        if(level == 1)
        {
        	bulletSpeed = 3;
        }
        if(level == 2)
        {
        	bulletSpeed = 4;
        }
        if(level == 3)
        {
        	bulletSpeed = 5;
        }
        if(level == 4)
        {
        	bulletSpeed = 6;
        }
        switch (direction) {
		case 1:
			setDirectionAndSpeed(1, -0.3);//ขึ้น
			break;
		case 2:
			setDirectionAndSpeed(1, 0.3);
			break;
		case 3:
			setDirectionAndSpeed(1, 0);
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
    private void setDirectionAndSpeed(double dX, double dY)
    {
        // Unit direction vector of the bullet.
        double directionVx = dX;
        double directionVy = dY;
         
        // Set speed.
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
    public void Update(double x, double y)
    {
    	if(trackOn == true)
    	{
    		
    		 xCoordinate -= movingXspeed+0.5;
    		 if(yCoordinate > y)
    		 {
    			 yCoordinate -= movingYspeed+3;
    			 
    		 }
    		 else
    			 yCoordinate += movingYspeed+3 ;
    	}
    	else
    	{
    		xCoordinate -= movingXspeed;
   	     	yCoordinate += movingYspeed;
    	}
       
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
