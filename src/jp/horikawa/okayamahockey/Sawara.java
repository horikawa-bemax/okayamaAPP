package jp.horikawa.okayamahockey;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;

public class Sawara {
	private Bitmap image;
	private Matrix matrix;
	private Rect rect;
	private RectF fieldRect;
	
	public Sawara(Context context, RectF rc) {
		fieldRect = rc;
		image = BitmapFactory.decodeResource(context.getResources(), R.drawable.sawara);
		double scale = rc.width()*0.3/image.getWidth();
		rect = new Rect(0, 0, (int)(rc.width()*0.3), (int)(image.getHeight()*scale));
		image = Bitmap.createScaledBitmap(image, rect.width(), rect.height(), false);
		matrix = new Matrix();
		
		initPosition();
	}
	
	public void draw(Canvas canvas){
		canvas.drawBitmap(image, matrix, null);
	}
	
	public void move(){
		
	}
	
	public void initPosition(){
		matrix.setTranslate(fieldRect.centerX()-rect.centerX(), fieldRect.centerY()-rect.centerY());
	}
}
