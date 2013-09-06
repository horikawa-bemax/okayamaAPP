package jp.horikawa.okayamahockey;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.SurfaceHolder;

public class GameActivity extends Activity implements SurfaceHolder.Callback, Runnable{
	private GameView gameView;
	private SurfaceHolder holder;
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
		
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		loop = false;
	}

	@Override
	public void run() {
		Canvas canvas;
		
		loop = true;
		while(loop){
			long st = System.currentTimeMillis();
			puck.move();
			canvas = holder.lockCanvas();
			gameView.drawBack(canvas);
			puck.draw(canvas);
			holder.unlockCanvasAndPost(canvas);
			long et = System.currentTimeMillis();
			long dis = et - st;
			if(dis < 17){
				try {
					Thread.sleep(17-dis);
					Log.d("dis",""+dis);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
