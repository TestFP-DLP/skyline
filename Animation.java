package skyline;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


/**
 * Class for animations, e.g. explosion.
 * 
 * @author www.gametutorial.net
 */

public class Animation {

    private BufferedImage animImage;

    private int frameWidth;

    private int frameHeight;

    private int numberOfFrames;

    private long frameTime;

    private long startingFrameTime;

    private long timeForNextFrame;

    private int currentFrameNumber;

    private boolean loop;

    public int x;
    public int y;

    private int startingXOfFrameInImage;

    private int endingXOfFrameInImage;

    public boolean active;

    private long showDelay;

    private long timeOfAnimationCration;

    public Animation(BufferedImage animImage, int frameWidth, int frameHeight, int numberOfFrames, long frameTime, boolean loop, int x, int y, long showDelay)
    {
    	this.animImage = animImage;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.numberOfFrames = numberOfFrames;
        this.frameTime = frameTime;
        this.loop = loop;

        this.x = x;
        this.y = y;
        
        this.showDelay = showDelay;
        
        timeOfAnimationCration = System.currentTimeMillis();

        startingXOfFrameInImage = 0;
        endingXOfFrameInImage = frameWidth;

        startingFrameTime = System.currentTimeMillis() + showDelay;
        timeForNextFrame = startingFrameTime + this.frameTime;
        currentFrameNumber = 0;
        active = true;
    }

    public void changeCoordinates(int x, int y)
    {
        this.x = x;
        this.y = y;
    }


    private void Update()
    {
        if(timeForNextFrame <= System.currentTimeMillis())
        {
            // Next frame.
            currentFrameNumber++;

            // If the animation is reached the end, we restart it by seting current frame to zero. If the animation isn't loop then we set that animation isn't active.
            if(currentFrameNumber >= numberOfFrames)
            {
                currentFrameNumber = 0;

                // If the animation isn't loop then we set that animation isn't active.
                if(!loop)
                    active = false;
            }

            // Starting and ending coordinates for cuting the current frame image out of the animation image.
            startingXOfFrameInImage = currentFrameNumber * frameWidth;
            endingXOfFrameInImage = startingXOfFrameInImage + frameWidth;

            // Set time for the next frame.
            startingFrameTime = System.currentTimeMillis();
            timeForNextFrame = startingFrameTime + frameTime;
        }
    }

    /**
     * Draws current frame of the animation.
     * 
     * @param g2d Graphics2D
     */
    public void Draw(Graphics2D g2d)
    {
        this.Update();
        
        // Checks if show delay is over.
        if(this.timeOfAnimationCration + this.showDelay <= System.currentTimeMillis())
            g2d.drawImage(animImage, x, y, x + frameWidth, y + frameHeight, startingXOfFrameInImage, 0, endingXOfFrameInImage, frameHeight, null);
    }
}