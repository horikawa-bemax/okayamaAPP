package jp.horikawa.okayamahockey;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.View.OnTouchListener;

public class GameActivity extends Activity implements SurfaceHolder.Callback, Runnable, OnTouchListener{
	private GameView gameView;
	private SurfaceHolder holder;
	private Handler handler;
	private Thread thread;
	//
	private Mallet malletP, malletM;
	private Puck puck;
	private boolean loop;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		
		gameView = (GameView)findViewById(R.id.gameView);
		holder = gameView.getHolder();
		
		thread = new Thread(this);
		
		holder.addCallback(this);
		gameView.setOnTouchListener(this);
		
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		loop = false;
		thread.stop();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}
	
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		gameView.setViewRect(new RectF(0,0,width,height));
		gameView.setFieldRect(new RectF(10, 10, width-10, height-10));
		
		puck = new Puck(width*0.05f, gameView.getFieldRect());
		puck.initPosition(width/2, height/2);
		
		malletP = new PeachMallet(this, width*0.1f);
		malletP.initPosition(gameView.getFieldRect().centerX(), gameView.getFieldRect().height()*0.8f);
		malletM = new MuscatMallet(this, width*0.1f);
		malletM.initPosition(gameView.getFieldRect().centerX(), gameView.getFieldRect().height()*0.2f);
		
		handler = new Handler();
		handler.post(this);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		handler.removeCallbacks(this);
	}

	@Override
	public void run() {

		long st = System.currentTimeMillis();
			
		puck.move();
		malletP.move();
		malletM.move();
		
		Canvas	canvas = holder.lockCanvas();
		gameView.drawBack(canvas);
		puck.draw(canvas);
		malletP.draw(canvas);
		malletM.draw(canvas);
		holder.unlockCanvasAndPost(canvas);
			
		long et = System.currentTimeMillis();
		long dt = et - st;
		if(dt < 17){
			handler.postDelayed(this, 17-dt);
		}else{
			handler.post(this);
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int pointerCount = event.getPointerCount();
		int actionIndex;
		int pointerId;
		
		switch(event.getActionMasked()){
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_POINTER_DOWN:
			actionIndex = event.getActionIndex();
			pointerId = event.getPointerId(actionIndex);
			if(event.getY(actionIndex) > gameView.getFieldRect().centerY()){
				if(malletP.getPointerId() == -1){
					malletP.setPointerId(pointerId);
					malletP.setTarget(event.getX(actionIndex), event.getY(actionIndex)-80);
				}
			}else{
				if(malletM.getPointerId() == -1){
					malletM.setPointerId(pointerId);
					malletM.setTarget(event.getX(actionIndex), event.getY(actionIndex)+80);
				}
			}
			break;
		case MotionEvent.ACTION_MOVE:
			for(int i=0; i<pointerCount; i++){
				if(event.getPointerId(i) == malletP.getPointerId()){
					malletP.setTarget(event.getX(i), event.getY(i)-60);

				}else if(event.getPointerId(i) == malletM.getPointerId()){
					malletM.setTarget(event.getX(i), event.getY(i)+60);

				}
			}
			Log.d("Touch_Move","");
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_UP:
			actionIndex = event.getActionIndex();
			pointerId = event.getPointerId(actionIndex);
			if(pointerId == malletP.getPointerId()){
					malletP.removePointerId();
			}else if(pointerId == malletM.getPointerId()){
					malletM.removePointerId();
			}
		}
		return true;
	}
}
