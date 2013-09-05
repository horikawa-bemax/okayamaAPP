package jp.horikawa.okayamahockey;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;

public class TitleActivity extends Activity implements SurfaceHolder.Callback, Runnable {
	private GameView gameView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_title);
		
		gameView = (GameView)findViewById(R.id.gameView);
		
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
		
		SurfaceHolder h = gameView.getHolder();
		Canvas c = h.lockCanvas();
		gameView.drawBack(c);
		h.unlockCanvasAndPost(c);
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
