package jp.horikawa.okayamahockey;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public abstract class Mallet {
	private Resources res;
	private Paint paint;
	private RectF rect;
	private Rect imageRect;
	private float cr, tx, ty;
	private int speed;
	private Bitmap image;
	
	public Mallet(Context context, float r) {
		res = context.getResources();
		paint = new Paint();
		cr = r;
		speed = (int)cr;
	}
	
	public Resources getRes(){
		return res;
	}
	
	public void setPaintColor(int color){
		paint.setColor(color);
	}
	
	public void setImage(int drawable){
		image = BitmapFactory.decodeResource(res, drawable);
		imageRect = new Rect(0,0,image.getWidth(), image.getHeight());
	}
	
	public void draw(Canvas canvas){
		canvas.drawCircle(rect.centerX(), rect.centerY(), cr, paint);
		canvas.drawBitmap(image, imageRect, rect, null);
	}
	
	public void initPosition(float cx, float cy){
		rect.set(cx-cr, cy-cr, cx+cr, cy+cr);
	}
	
	public void move(float x, float y){
		tx = x;
		ty = y;
		
		float dx = x-rect.centerX();
		float dy = y-rect.centerY();
		
		float len = (float)Math.sqrt(dx*dx+dy*dy);
		
		if(len > speed){
			dx = speed * dx / len; 
			dy = speed * dy / len;
		}
		
		rect.offset(dx, dy);
	}
	
}

class PeachMallet extends Mallet{
	
	public PeachMallet(Context context, float r) {
		super(context, r);
		setPaintColor(getRes().getColor(R.color.peach));
		setImage(getRes().getInteger(R.drawable.ic_launcher));
	}

	@Override
	public void draw(Canvas canvas) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
}

class MuscatMallet extends Mallet{

	public MuscatMallet(Context context, float r) {
		super(context, r);
	}

	@Override
	public void draw(Canvas canvas) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
	
}