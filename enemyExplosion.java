package skyline;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Timer;

import org.newdawn.slick.Music;
import org.newdawn.slick.Sound;

public class enemyExplosion {
	public boolean active;
	public static Image enemyExplosionImg;
	private int xCoordinate, yCoordinate;
	private long gametime;
	private long timeToPlay;
	public static Sound explosion;
	public static Sound bossDead;
	public enemyExplosion(int x, int y, long gametime) {
		explosion.play((float)1, (float) 0.08);
		xCoordinate = x;
		yCoordinate = y;
		active = true;
		this.gametime = (int) (gametime / Framework.milisecInNanosec / 1000);
		timeToPlay = (int) (this.gametime + 100);
		
	}
	
	public boolean enemyExplosionUpdate()
    {
		if(gametime - timeToPlay == 0)
		{
			active = false;
		}
		else
			gametime++;
		return active;
        
    }
	
	public void Draw(Graphics2D g2d)
	{
		g2d.drawImage(enemyExplosionImg, xCoordinate, yCoordinate, null);
	}

}
