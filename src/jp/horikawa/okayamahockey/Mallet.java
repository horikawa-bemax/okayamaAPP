package jp.horikawa.okayamahockey;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * マレット
 * @author MasaakiHorikawa
 *
 */
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
		cr = r;
		speed = (int)cr;
		rect = new RectF(0, 0, r*2, r*2);
		imageRect = new Rect();
		paint = new Paint();
	}
	
	Resources getRes(){
		return res;
	}
	
	void setPaint(Paint p){
		paint = p;
	}
	
	void setImage(Bitmap b){
		image = b;
		imageRect = new Rect(0, 0, b.getWidth(), b.getHeight());
	}
		
	void draw(Canvas canvas){
		canvas.drawCircle(rect.centerX(), rect.centerY(), cr, paint);
		canvas.drawBitmap(image, imageRect, rect, null);
	}
	
	void initPosition(float cx, float cy){
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
		Paint p = new Paint();
		p.setColor(getRes().getColor(R.color.peach));
		setPaint(p);
		
		Bitmap b = BitmapFactory.decodeResource(getRes(), R.drawable.peach);
		setImage(b);
	}

}

class MuscatMallet extends Mallet{

	public MuscatMallet(Context context, float r) {
		super(context, r);
		Paint p = new Paint();
		p.setColor(getRes().getColor(R.color.muscat));
		setPaint(p);
		
		Bitmap b = BitmapFactory.decodeResource(getRes(), R.drawable.muscat);
		Matrix m = new Matrix();
		m.setRotate(180);
		setImage(Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), m, false));
	}

}