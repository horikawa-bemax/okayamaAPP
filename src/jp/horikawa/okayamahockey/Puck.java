package jp.horikawa.okayamahockey;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;

public class Puck {
	//double radian; //ラジアン（角度)
	float cr, vec[];
	private RectF rect, fieldRect;
	private Paint paint;
	private Point point;

	public Puck(float cr, RectF field) {
		this.cr = cr;
		this.fieldRect = field;
		rect = new RectF();
		paint = new Paint();
		paint.setColor(Color.WHITE);
		vec = new float[2];
		setRect(field.centerX(), field.centerY());
		initVector();
	}
	
	public void initVector(){
		double radian = Math.random() * Math.PI / 2 + Math.PI / 4;
		float speed = cr / 5;
		vec[0] = (float)(speed * Math.cos(radian));
		vec[1] = (float)(speed * Math.sin(radian));
	}
	
	public void move(){
		boolean bound = false;
		float dx = vec[0], dy = vec[1];
		
		if(rect.left < fieldRect.left){
			vec[0] = -vec[0];
			dx = fieldRect.left - rect.left;
			bound = true;
		}
		if(rect.right > fieldRect.right){
			vec[0] = -vec[0];
			dx = fieldRect.right - rect.right;
			bound = true;
		}
		if(rect.top < fieldRect.top){
			vec[1] = -vec[1];
			dy = fieldRect.top - rect.top;
			bound = true;
		}
		if(rect.bottom > fieldRect.bottom){
			vec[1] = -vec[1];
			dy = fieldRect.bottom - rect.bottom;
			bound = true;
		}
		rect.offset(dx, dy);
	}

	public void draw(Canvas c){
		c.drawCircle(rect.centerX(), rect.centerY(), cr, paint);
	}
	
	Point getPoint(){
		return new Point((int)rect.centerX(), (int)rect.centerY());
	}
	
	float getSpeed(){
		return (float)Math.sqrt(vec[0]*vec[0]+vec[1]*vec[1]);
	}
	
	void setRect(float cx, float cy){
		rect.set(cx-cr, cy-cr, cx+cr, cy+cr);
	}
	
	void offset(float dx, float dy){
		rect.offset(dx,  dy);
	}
}
