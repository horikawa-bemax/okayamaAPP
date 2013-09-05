package jp.horikawa.okayamahockey;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.SurfaceView;

/**
 * GameViewクラス
 * @author Masaaki Horikawa
 *
 */
public class GameView extends SurfaceView{
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
		viewRect = new RectF();
		fieldRect = new RectF();
		fieldPaint = new Paint();
		fieldPaint.setColor(res.getColor(R.color.field));
	}

	Resources getRes() {
		return res;
	}

	void setRes(Resources res) {
		this.res = res;
	}

	RectF getFieldRect() {
		return fieldRect;
	}

	void setFieldRect(RectF fieldRect) {
		this.fieldRect = fieldRect;
	}

	void setViewRect(RectF viewRect) {
		this.viewRect = viewRect;
	}
}
