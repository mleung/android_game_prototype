package us.michaelleung.spritetest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;

public class PaintThread extends Thread {
    private SurfaceHolder surfaceHolder;
    private Handler handler;
    private Context context;
    private Paint blackPaint;
    GameEngine gameEngine;

    // for consistent rendering
    private long sleepTime;
    // amount of time to sleep for (in milliseconds)
    private static final long DELAY = 70;
    
    // state of game (Running or Paused).
    int state = 1;
    public final static int RUNNING = 1;
    public final static int PAUSED = 2;

    public PaintThread(SurfaceHolder surfaceHolder, Context context, Handler handler, 
                               GameEngine gameEngine) {
            
        // data about the screen
        this.surfaceHolder = surfaceHolder;
        this.handler = handler;
        this.context = context;

        // black painter below to clear the screen before the game is rendered
        blackPaint = new Paint();
        blackPaint.setARGB(255, 0, 0, 0);
 
        this.gameEngine = gameEngine;
    }


    // This is the most important part of the code. It is invoked when the call to start() is
    // made from the SurfaceView class. It loops continuously until the game is finished or
    // the application is suspended.
    @Override
    public void run() {

     // UPDATE
     while (state == RUNNING) {
    	 // time before update
    	 long beforeTime = System.nanoTime();
    	 // This is where we update the game engine
    	 gameEngine.update(beforeTime);

	     // DRAW
	     Canvas c = null;
	     try {
	           // lock canvas so nothing else can use it
	           c = surfaceHolder.lockCanvas(null);
	           synchronized (surfaceHolder) {
	                //clear the screen with the black painter.
	                c.drawRect(0, 0, c.getWidth(), c.getHeight(), blackPaint);
	                gameEngine.draw(c);
	         }
	     } finally {
	         // do this in a finally so that if an exception is thrown
	         // during the above, we don't leave the Surface in an
	         // inconsistent state
	         if (c != null) {
	             surfaceHolder.unlockCanvasAndPost(c);
	         }
	     }

	     // SLEEP
	     // Sleep time. Time required to sleep to keep game consistent
	     // This starts with the specified DELAY time (in milliseconds) then subtracts from that the
	     // actual time it took to update and render the game. This allows our game to render smoothly.
	     this.sleepTime = DELAY - ((System.nanoTime() - beforeTime) / 1000000L);

	     try {
            // actual sleep code
            if(sleepTime > 0) {
            	sleep(sleepTime);
            }
	     } catch (InterruptedException ex) {
            Log.e("fail", ex.getMessage());
	     }
     }

    }
}