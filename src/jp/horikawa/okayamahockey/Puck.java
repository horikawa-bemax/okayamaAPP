package jp.horikawa.okayamahockey;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;

public class Puck {
	private float speed, vector;
	//double radian; //ラジアン（角度)
	float cr, vx, vy;
	private RectF rect, fieldRect;
	private Paint paint;
	private Point point;

	public Puck(float cr, RectF field) {
		this.cr = cr;
		this.fieldRect = field;
		speed = cr / 5;
		setVector();
		rect = new RectF();
		paint = new Paint();
		paint.setColor(Color.WHITE);
		point = new Point();
	}
	
	public void setVector(){
		double radian = Math.random() * Math.PI / 2 + Math.PI / 4;
		vx = (float)(speed * Math.cos(radian));
		vy = (float)(speed * Math.sin(radian));
	}
	
	public void initPosition(float cx, float cy){
		rect.set(cx-cr, cy-cr, cx+cr, cy+cr);
	}
	
	public void move(){
		boolean bound = false;
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
	}

	public void draw(Canvas c){
		c.drawCircle(rect.centerX(), rect.centerY(), cr, paint);
	}
	
	Point getPoint(){
		point.set((int)rect.centerX(), (int)rect.centerY());
		return point;
	}
}
