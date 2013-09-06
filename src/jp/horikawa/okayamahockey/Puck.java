package jp.horikawa.okayamahockey;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class Puck {
	private float cr;
	private float speed;
	private double radian; //ラジアン（角度）
	private RectF rect, fieldRect;
	private Paint paint;

	public Puck(float cr, RectF field) {
		this.cr = cr;
		this.fieldRect = field;
		speed = cr / 5;
		setRadian();
		rect = new RectF();
		paint = new Paint();
		paint.setColor(Color.WHITE);
	}
	
	public void setRadian(){
		radian = Math.random() * Math.PI / 2 + Math.PI / 4;
	}
	
	public void initPosition(float cx, float cy){
		rect.set(cx-cr, cy-cr, cx+cr, cy+cr);
	}
	
	public void move(){
		boolean bound = false;
		float vx = (float)(speed * Math.cos(radian));
		float vy = (float)(speed * Math.sin(radian));
		float dx = vx, dy = vy;
		
		if(rect.left < fieldRect.left){
			vx = -vx;
			dx = fieldRect.left - rect.left;
			bound = true;
		}
		if(rect.right > fieldRect.right){
			vx = -vx;
			dx = fieldRect.right - rect.right;
			bound = true;
		}
		if(rect.top < fieldRect.top){
			vy = -vy;
			dy = fieldRect.top - rect.top;
			bound = true;
		}
		if(rect.bottom > fieldRect.bottom){
			vy = -vy;
			dy = fieldRect.bottom - rect.bottom;
			bound = true;
		}
		rect.offset(dx, dy);
		if(bound){
			if(Math.asin(vy/speed)>0){
				radian = Math.acos(vx/speed);
			}else{
				radian = Math.PI * 2 - Math.acos(vx/speed);
			}
		}
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
