package jp.horikawa.okayamahockey;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable{
	private Resources res;
	private RectF viewRect, fieldRect;
	private Paint fieldPaint;
	
	public GameView(Context context) {
		super(context);
		res = context.getResources();
		init();
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		res = context.getResources();
		init();
	}

	public GameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		res = context.getResources();
		init();
	}

	public void drawBack(Canvas c){
		c.drawColor(res.getColor(R.color.frame));
		c.drawRoundRect(fieldRect, 10, 10, fieldPaint);
	}
	
	private void init(){
		fieldPaint = new Paint();
		fieldPaint.setColor(res.getColor(R.color.field));
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		viewRect = new RectF(0,0,width,height);
		fieldRect = new RectF(10, 10, width-10, height-10);
		
		Canvas c = getHolder().lockCanvas();
		drawBack(c);
		getHolder().unlockCanvasAndPost(c);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		
	}

	@Override
	public void run() {
		
		
	}
	
	
}
