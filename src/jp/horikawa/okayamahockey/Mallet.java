package jp.horikawa.okayamahockey;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
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
	private float tx, ty;
	float cr, vec[];
	private int speed;
	private Bitmap image;
	private int pointerId;
	private Point point;
	
	public Mallet(Context context, RectF rc) {
		res = context.getResources();
		cr = rc.width() * 0.1F;
		speed = (int)(cr/2);
		rect = new RectF(0, 0, cr*2, cr*2);
		imageRect = new Rect();
		paint = new Paint();
		pointerId = -1;
		point = new Point();
		vec = new float[2];
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
		tx = cx;
		ty = cy;
	}
	
	public void setTarget(float x, float y){
		tx = x;
		ty = y;
	}
	
	public void move(){
		vec[0] = tx-rect.centerX();
		vec[1] = ty-rect.centerY();
		
		float len = (float)Math.sqrt(vec[0]*vec[0]+vec[1]*vec[1]);
		
		if(len > speed){
			vec[0] = speed * vec[0] / len; 
			vec[1] = speed * vec[1] / len;
		}
		
		rect.offset(vec[0], vec[1]);
	}

	public void setPointerId(int pointerId) {
			if(this.pointerId == -1){
				this.pointerId = pointerId;
			}
	}
	
	public void removePointerId(){
		this.pointerId = -1;
	}
	
	public int getPointerId(){
		return pointerId;
	}
	
	Point getPoint(){
		point.set((int)rect.centerX(), (int)rect.centerY());
		return point;
	}
}

class PeachMallet extends Mallet{
	
	public PeachMallet(Context context, RectF rc) {
		super(context, rc);
		Paint p = new Paint();
		p.setColor(getRes().getColor(R.color.peach));
		setPaint(p);
		
		Bitmap b = BitmapFactory.decodeResource(getRes(), R.drawable.peach);
		setImage(b);
		initPosition(rc.centerX(), rc.height()*0.8F);
	}

}

class MuscatMallet extends Mallet{

	public MuscatMallet(Context context, RectF rc) {
		super(context, rc);
		Paint p = new Paint();
		p.setColor(getRes().getColor(R.color.muscat));
		setPaint(p);
		
		Bitmap b = BitmapFactory.decodeResource(getRes(), R.drawable.muscat);
		Matrix m = new Matrix();
		m.setRotate(180);
		setImage(Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), m, false));
		initPosition(rc.centerX(), rc.height()*0.2F);
	}

}