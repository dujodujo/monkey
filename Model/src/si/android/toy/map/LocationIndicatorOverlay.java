package si.android.toy.map;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class LocationIndicatorOverlay extends Overlay {
	
	private static final int innerRadius = 4;
	private static final int outerRadius = 8;
	
	protected GeoPoint geoPoint;
	protected Canvas canvas;
	protected MapView mapView;
	protected boolean shadow;
	protected Point point;
	protected double markerArea;
	
	public LocationIndicatorOverlay(GeoPoint geoPoint) {
		this.geoPoint = geoPoint;
	}
	
	public GeoPoint getGeoPoint() {
		return geoPoint;
	}

	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		super.draw(canvas, mapView, shadow);
		
		this.canvas = canvas;
		this.mapView = mapView;
		this.shadow = shadow;
		
		Projection projection = mapView.getProjection();
		point = new Point();
		projection.toPixels(this.geoPoint, point);
		
		Paint paint = new Paint();
		// alpha, red, green, blue
		paint.setARGB(155, 255, 255, 255);
		canvas.drawCircle(point.x, point.y, outerRadius, paint);
		
		paint.setARGB(155, 255, 0, 0);
		canvas.drawCircle(point.x, point.y, innerRadius, paint);
		
	}

	@Override
	public boolean onTap(GeoPoint p, MapView mapView) {
		Log.d("TAG", p.getLatitudeE6() + ", " + p.getLongitudeE6());
		return super.onTap(p, mapView);
	}
}
