package us.michaelleung.spritetest;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

// 33x42
public class Sprite {
	
	private Bitmap animation;
	private int xPos;
	private int yPos;
	private Rect sourceRectangle;
	private int framesPerSecond;
    private int noOfFrames;
    private int currentFrame;
    private long frameTimer;
    private int spriteHeight;
    private int spriteWidth;
    
    public Sprite(int x, int y) {
    	sourceRectangle = new Rect(0,0,0,0);
    	frameTimer = 0;
    	currentFrame = 0;
    	xPos = x;
    	yPos = y;
    }
    
    public void initialize(Bitmap theBitmap, int height, int width, int theFPS, int theFrameCount) {
        animation = theBitmap;
        spriteHeight = height;
        spriteWidth = width;
        sourceRectangle.top = 0;
        sourceRectangle.bottom = spriteHeight;
        sourceRectangle.left = 0;
        sourceRectangle.right = spriteWidth;
        framesPerSecond = 1000 / theFPS;
        noOfFrames = theFrameCount;
        
    }
    
    public void update(long gameTime) {
        if(gameTime > frameTimer + framesPerSecond ) {
            frameTimer = gameTime;
            currentFrame +=1;
     
            if(currentFrame >= noOfFrames) {
                currentFrame = 0;
            }
        }
     
        sourceRectangle.left = currentFrame * spriteWidth;
        sourceRectangle.right = sourceRectangle.left + spriteWidth;
    }
    
    public void draw(Canvas canvas) {
    	Rect destinationRectangle = new Rect(xPos, yPos, xPos + spriteWidth, yPos + spriteHeight);
    	canvas.drawBitmap(animation, sourceRectangle, destinationRectangle, null);
    }

}
