package si.android.toy.map;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class DrawLineOverlay extends Overlay {
	private MapView mapView;
	
	public DrawLineOverlay(MapView mapView) {
		this.mapView = mapView;
	}

	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		super.draw(canvas, mapView, shadow);
		
		Paint paint = new Paint();
		
		paint.setDither(true);
		paint.setColor(Color.RED);
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		paint.setStrokeJoin(Paint.Join.ROUND);
		paint.setStrokeCap(Paint.Cap.ROUND);
		paint.setStrokeWidth(4);
		
		GeoPoint gp1 = new GeoPoint(34159000, 73220000);
		GeoPoint gp2 = new GeoPoint(33695043, 73050000);
		
		GeoPoint gp4 = new GeoPoint(33695043, 73050000);
		GeoPoint gp3 = new GeoPoint(33615043, 73050000);
		
		Point p1 = new Point();
		Point p2 = new Point();
		Path path1 = new Path();
		
		Point p3 = new Point();
		Point p4 = new Point();
		Path path2 = new Path();
		
		mapView.getProjection().toPixels(gp2, p3);
		mapView.getProjection().toPixels(gp1, p4);
		
		path1.moveTo(p4.x, p4.y);
		path1.lineTo(p3.x, p3.y);
		
		mapView.getProjection().toPixels(gp3, p1);
		mapView.getProjection().toPixels(gp4, p2);
		
		path2.moveTo(p2.x, p2.y);
		path2.lineTo(p1.x, p1.y);

		canvas.drawPath(path1, paint);
		canvas.drawPath(path2, paint);
	}
}
