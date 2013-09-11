package jp.horikawa.okayamahockey;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.ImageView;

public class TitleActivity extends Activity implements SurfaceHolder.Callback, Runnable {
	private GameView gameView;
	private Puck puck;
	private Thread thread;
	private boolean loop;
	private Handler handler;
	private long beginTime;
	private ImageView pushStartImage;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_title);
		
		gameView = (GameView)findViewById(R.id.gameView1);
		pushStartImage = (ImageView)findViewById(R.id.push_start_image);
		
		gameView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Intent intent = new Intent(gameView.getContext(), GameActivity.class);
				startActivity(intent);
				return false;
			}
		});
		
		gameView.getHolder().addCallback(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.title, menu);
		return true;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		gameView.setViewRect(new RectF(0, 0, width, height));
		gameView.setFieldRect(new RectF(10, 10, width-10, height-10));
		
		puck = new Puck(width*0.05f, gameView.getFieldRect());
		puck.initPosition(gameView.getFieldRect().centerX(), gameView.getFieldRect().centerY());
		puck.setRadian();

		Bitmap img = BitmapFactory.decodeResource(getResources(), R.drawable.push_start);
		img = Bitmap.createScaledBitmap(img, (int)(width*0.6),(int)(img.getHeight() * img.getWidth()/(double)width), false);
		pushStartImage.setImageBitmap(img);
		
		handler = new Handler();
		beginTime = System.currentTimeMillis();
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
		Canvas canvas = gameView.getHolder().lockCanvas();
		gameView.drawBack(canvas);
		puck.draw(canvas);
		gameView.getHolder().unlockCanvasAndPost(canvas);
		if(st - beginTime > 10000){
			puck.initPosition(gameView.getFieldRect().centerX(), gameView.getFieldRect().centerY());
			puck.setRadian();
			beginTime = st;
		}
		long et = System.currentTimeMillis();
		long dt = et - st;
		if(17>dt){
			handler.postDelayed(this, 17-dt);
		}else{
			handler.post(this);
		}
	}
}
