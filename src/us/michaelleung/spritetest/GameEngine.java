
package us.michaelleung.spritetest;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class GameEngine {
	
	private Sprite fatty;
	private Sprite shooter;
	private Resources resources;


	Bitmap back;
	
	public void init(Resources resources) {
		this.resources = resources;
		BitmapFactory.Options ops = new BitmapFactory.Options();
		ops.inPurgeable = true;
		back = BitmapFactory.decodeResource(resources, R.drawable.bg, ops);
		shooter = new Sprite(80, 200);
		shooter.initialize(BitmapFactory.decodeResource(resources, R.drawable.fire, ops), 58, 89, 2, 7);
	}
	
	public void update(long timer) {
		shooter.update(timer);
	}
	
	public void draw(Canvas canvas) {
		drawBackground(canvas);
		shooter.draw(canvas);
	}

	private void drawBackground(Canvas canvas) {
		canvas.drawBitmap(back, 0, 0, null);
	}
}
