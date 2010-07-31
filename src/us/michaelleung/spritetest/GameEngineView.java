package us.michaelleung.spritetest;

//w41xh54 

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameEngineView extends SurfaceView implements SurfaceHolder.Callback {
    // used to keep track of time between updates and amount of time to sleep for
    long lastUpdate = 0;
    long sleepTime = 0;

    GameEngine gameEngine;

    // objects which house info about the screen
    SurfaceHolder surfaceHolder;
    Context context;
 
    // our Thread class which houses the game loop
    private PaintThread thread;
 
    // initialization code
    void initView() {
      // initialize our screen holder
      SurfaceHolder holder = getHolder();
      holder.addCallback(this);

      //initialize our game engine
      gameEngine = new GameEngine();
      gameEngine.init(context.getResources());
      
      // initialize our Thread class. A call will be made to start it later
      thread = new PaintThread(holder, context, new Handler(), gameEngine);
      setFocusable(true);
   }
 
   // class constructors
   public GameEngineView(Context context, AttributeSet attrs, int defStyle) {
       super(context, attrs, defStyle);
       this.context = context;
       initView();
   }
   
   public GameEngineView(Context context, AttributeSet attrs){
       super(context, attrs);
       this.context = context;
       initView();
   }

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		if(thread.state == PaintThread.PAUSED){
            // When game is opened again in the Android OS
            thread = new PaintThread(getHolder(), context, new Handler(), gameEngine);
            thread.start();
        } else {
            //creating the game Thread for the first time
            thread.start();
        }

	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		boolean retry = true;
	     // code to end gameloop
	     thread.state = PaintThread.PAUSED;
	        while (retry) {
	            try {
	                // code to kill Thread
	                thread.join();
	                retry = false;
	            } catch (InterruptedException e) {
	            	Log.e("fail", e.getMessage());
	            }
	            
	        }

		
	}
	
}