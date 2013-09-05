package jp.horikawa.okayamahockey;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class Puck {
	private float cr;
	private float speed;
	private double radian; //ラジアン（角度）
	private RectF rect;
	private Paint paint;

	public Puck(float cr) {
		this.cr = cr;
		speed = cr / 10;
		setRadian();
		rect = new RectF();
		paint = new Paint();
		paint.setColor(Color.WHITE);
	}
	
	public void setRadian(){
		radian = Math.random() * Math.PI * 2;
	}
	
	public void initPosition(float cx, float cy){
		rect.set(cx-cr, cy-cr, cx+cr, cy+cr);
	}
	
	public void move(){
		float vx = (float)(speed * Math.cos(radian));
		float vy = (float)(speed * Math.sin(radian));
		rect.offset(vx, vy);
	}
	
	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public void draw(Canvas c){
		c.drawCircle(rect.centerX(), rect.centerY(), cr, paint);
	}
}
