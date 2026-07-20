package skyline;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * Helicopter which is managed by player.
 * 
 * @author www.gametutorial.net
 */

public class PlayerHelicopter {
    
    // Health of the helicopter.
    private final int healthInit = 300;
    public int health;
    
    // Position of the helicopter on the screen.
    public int xCoordinate;
    public int yCoordinate;
    
    // Moving speed and also direction.
    private double movingXspeed;
    public double movingYspeed;
    private double acceleratingXspeed;
    private double acceleratingYspeed;
    private double stoppingXspeed;
    private double stoppingYspeed;
    
    // Helicopter rockets.
    private final int numberOfRocketsInit = 80;
    public int numberOfRockets;
    
    // Helicopter machinegun ammo.
    private final int numberOfAmmoInit = 1400;
    public int numberOfAmmo;
    
    // Images of helicopter and its propellers.
    public BufferedImage playerImg;
    
    
    // Offset of the helicopter rocket holder.
    private int offsetXRocketHolder;
    private int offsetYRocketHolder;
    // Position on the frame/window of the helicopter rocket holder.
    public int rocketHolderXcoordinate;
    public int rocketHolderYcoordinate;
    
    // Offset of the helicopter machine gun. We add offset to the position of the position of helicopter.
    private int offsetXMachineGun;
    private int offsetYMachineGun;
    // Position on the frame/window of the helicopter machine gun.
    public int machineGunXcoordinate;
    public int machineGunYcoordinate;
    
    
    /**
     * Creates object of player.
     * 
     * @param xCoordinate Starting x coordinate of helicopter.
     * @param yCoordinate Starting y coordinate of helicopter.
     */
    public PlayerHelicopter(int xCoordinate, int yCoordinate)
    {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        
        LoadContent();
        Initialize();
    }
    
    
    /**
     * Set variables and objects for this class.
     */
    private void Initialize()
    {
        this.health = healthInit;
        
        this.numberOfRockets = numberOfRocketsInit;
        this.numberOfAmmo = numberOfAmmoInit;
        
        this.movingXspeed = 0;
        this.movingYspeed = 0;
        
        this.offsetXRocketHolder = playerImg.getWidth()-180;
        this.offsetYRocketHolder = playerImg.getHeight() - 10;
        this.rocketHolderXcoordinate = this.xCoordinate + this.offsetXRocketHolder;
        this.rocketHolderYcoordinate = this.yCoordinate + this.offsetYRocketHolder;
        
        this.offsetXMachineGun = playerImg.getWidth() - 70;
        this.offsetYMachineGun = playerImg.getHeight() - 10;
        this.machineGunXcoordinate = this.xCoordinate + this.offsetXMachineGun;
        this.machineGunYcoordinate = this.yCoordinate + this.offsetYMachineGun;
    }
    
    /**
     * Load files for this class.
     */
    private void LoadContent()
    {
        try 
        {
            URL playerImgUrl = this.getClass().getResource("/skyline/resources/images/Player.png");
            playerImg = ImageIO.read(playerImgUrl);
        } 
        catch (IOException ex) {
            Logger.getLogger(PlayerHelicopter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    /**
     * Resets the player.
     * 
     * @param xCoordinate Starting x coordinate of helicopter.
     * @param yCoordinate Starting y coordinate of helicopter.
     */
    public void Reset(int xCoordinate, int yCoordinate)
    {
        this.health = healthInit;
        
        this.numberOfRockets = numberOfRocketsInit;
        this.numberOfAmmo = numberOfAmmoInit;
        
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        
        this.machineGunXcoordinate = this.xCoordinate + this.offsetXMachineGun;
        this.machineGunYcoordinate = this.yCoordinate + this.offsetYMachineGun;
        
        this.movingXspeed = 0;
        this.movingYspeed = 0;
    }
    
    
    /**
     * Checks if player is shooting. It also checks if player can 
     * shoot (time between bullets, does a player have any bullet left).
     * 
     * @param gameTime The current elapsed game time in nanoseconds.
     * @return true if player is shooting.
     */
    public boolean isShooting(long gameTime)
    {
    	boolean shoot = true;
        // Checks if left mouse button is down && if it is the time for a new bullet.
        if( shoot && 
            ((gameTime - Bullet.timeOfLastCreatedBullet) >= Bullet.timeBetweenNewBullets)) 
        {
            return true;
        } else
            return false;
    }
    
    
    /**
     * Checks if player is fired a rocket. It also checks if player can 
     * fire a rocket (time between rockets, does a player have any rocket left).
     * 
     * @param gameTime The current elapsed game time in nanoseconds.
     * @return true if player is fired a rocket.
     */
    public boolean isFiredRocket(long gameTime)
    {
        // Checks if right mouse button is down && if it is the time for new rocket && if he has any rocket left.
        if( Canvas.keyboardKeyState(KeyEvent.VK_SPACE) && 
            ((gameTime - Rocket.timeOfLastCreatedRocket) >= Rocket.timeBetweenNewRockets) && 
            this.numberOfRockets > 0 ) 
        {
            return true;
        } else
            return false;
    }
    
    
    /**
     * Checks if player moving helicopter and sets its moving speed if player is moving.
     */
    public void isMoving()
    {
        // Moving on the x coordinate.
        if((Canvas.keyboardKeyState(KeyEvent.VK_D) || Canvas.keyboardKeyState(KeyEvent.VK_RIGHT)) && xCoordinate+255 < Framework.frameWidth)
            movingXspeed = 8;
        else if((Canvas.keyboardKeyState(KeyEvent.VK_A) || Canvas.keyboardKeyState(KeyEvent.VK_LEFT)) && xCoordinate-10 > 0)
            movingXspeed = -8;
        else    // Stoping
            if(movingXspeed < 0)
                movingXspeed = 0;
            else if(movingXspeed > 0)
                movingXspeed = 0;
        
        // Moving on the y coordinate.
        if((Canvas.keyboardKeyState(KeyEvent.VK_W) || Canvas.keyboardKeyState(KeyEvent.VK_UP)) && yCoordinate-15 > 0)
            movingYspeed = -8;
        else if((Canvas.keyboardKeyState(KeyEvent.VK_S) || Canvas.keyboardKeyState(KeyEvent.VK_DOWN)) && yCoordinate+65 < Framework.frameHeight)
            movingYspeed = 8;
        else    // Stoping
            if(movingYspeed < 0)
                movingYspeed = 0;
            else if(movingYspeed > 0)
                movingYspeed = 0;
    }
    
    
    /**
     * Updates position of helicopter, animations.
     */
    public void Update(int level)
    {
        // Move helicopter and its propellers.
        xCoordinate += movingXspeed;
        yCoordinate += movingYspeed;        
        
        if(level == 4)
        	Bullet.timeBetweenNewBullets = Framework.secInNanosec / 2;
        else
        	Bullet.timeBetweenNewBullets = Framework.secInNanosec / 5;
        this.rocketHolderXcoordinate = this.xCoordinate + this.offsetXRocketHolder;
        this.rocketHolderYcoordinate = this.yCoordinate + this.offsetYRocketHolder;
        
        // Move the machine gun with helicopter.
        this.machineGunXcoordinate = this.xCoordinate + this.offsetXMachineGun;
        this.machineGunYcoordinate = this.yCoordinate + this.offsetYMachineGun;
    }
    
    
    /**
     * Draws helicopter to the screen.
     * 
     * @param g Graphics2D
     */
    public void Draw(Graphics g)
    {
        g.drawImage(playerImg, xCoordinate, yCoordinate, null);
    }
    
}
