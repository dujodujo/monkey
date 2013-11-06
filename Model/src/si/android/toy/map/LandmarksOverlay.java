package si.android.toy.map;

import java.util.ArrayList;
import java.util.List;

import si.android.toy.R;
import si.android.toy.activity.LandmarksActivity;
import si.android.toy.view.PopUpPanel;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class LandmarksOverlay extends ItemizedOverlay<LandmarkItem> {
	private String TAG = "LandmarksOverlay";
	private MapView mapView = null;
	private Drawable star = null;

	private List<LandmarkItem> landmarks = new ArrayList<LandmarkItem>();
	
	private PopUpPanel panel = null;
	
	public LandmarksOverlay(MapView mapView) {
		super(null);
		
		this.mapView = mapView;
		this.star = getMarker(R.drawable.star_big_on);
		
		panel = new PopUpPanel(this.mapView, R.layout.popup);
		
		populateLandmarks();
		populate();
	}
	
	private void populateLandmarks() {
		landmarks.add(new LandmarkItem(getPoint(52.509868, 13.376271), 
			"Potsdamer Platz", "Landmark", getMarker(android.R.drawable.star_big_on), star));
		
		landmarks.add(new LandmarkItem(getPoint(52.516225, 13.376888),
			"Brandenburg Gate", "Landmark", getMarker(android.R.drawable.star_big_on), star));
		
		landmarks.add(new LandmarkItem(getPoint(52.518596, 13.375096), 
			"Reichstag", "Landmark", getMarker(android.R.drawable.star_big_on), star));
		
		landmarks.add(new LandmarkItem(getPoint(52.514841, 13.350375), 
			"Victory Column", "Landmark", getMarker(android.R.drawable.star_big_on), star));
	}
	
	@Override
	public boolean onTap(int i) {
		OverlayItem item = getItem(i);
		GeoPoint geoPoint = item.getPoint();
		Point point = mapView.getProjection().toPixels(geoPoint, null);
		
		View view = panel.getView();
	      
	    ((TextView) view.findViewById(R.id.latitude)).setText(String.valueOf(geoPoint.getLatitudeE6() / 1E6));
	    ((TextView) view.findViewById(R.id.longitude)).setText(String.valueOf(geoPoint.getLongitudeE6() / 1E6));
	    ((TextView) view.findViewById(R.id.x)).setText(String.valueOf(point.x));
	    ((TextView) view.findViewById(R.id.y)).setText(String.valueOf(point.y));
	      
	    panel.show(true);
	    return true;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event, MapView mapView) {
		if(event.getAction() == 1) {
			GeoPoint point = mapView.getProjection().fromPixels( (int) event.getX(), (int) event.getY());
		}
		return false; 
	}

	public void toggleStar() {
		LandmarkItem focus = this.getFocus();
		if(focus != null) {
			focus.toggleStar();
		}
		mapView.invalidate();
	}
	
	private Drawable getMarker(int resource) {
		Drawable marker = mapView.getContext().getResources().getDrawable(resource);
		marker.setBounds(0, 0, marker.getIntrinsicWidth(), marker.getIntrinsicHeight());
		boundCenter(marker);
		return marker;
	}
	
	private GeoPoint getPoint(double latitude, double longitude) {
		return (new GeoPoint((int)(latitude * 1E6), (int)(longitude * 1E6)));
	}
	
	public GeoPoint getCenterPoint() {
		int xMax = (int) (landmarks.get(0).getPoint().getLatitudeE6());
		int yMax = (int) (landmarks.get(0).getPoint().getLongitudeE6());
		int xMin = (int) (landmarks.get(0).getPoint().getLatitudeE6());
		int yMin = (int) (landmarks.get(0).getPoint().getLongitudeE6());
		
		for(int i = 1; i < landmarks.size(); i++) {
			xMax = (int) Math.max(xMax, landmarks.get(i).getPoint().getLatitudeE6());
			yMax = (int) Math.max(yMax, landmarks.get(i).getPoint().getLongitudeE6());
			
			xMin = (int) Math.min(xMin, landmarks.get(i).getPoint().getLatitudeE6());
			yMin = (int) Math.min(yMin, landmarks.get(i).getPoint().getLongitudeE6());
		}
		
		int xCenter = (int) (xMax - ((xMax - xMin) * 0.5));
		int yCenter = (int) (yMax - ((yMax - yMin) * 0.5));
		return (new GeoPoint((int) (xCenter), (int) (yCenter)));
	}
	
	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		super.draw(canvas, mapView, shadow);	
		boundCenterBottom(star);
	}
	
	@Override
	protected LandmarkItem createItem(int i) {
		return landmarks.get(i);
	}
	
	@Override
	public int size() {
		return landmarks.size();
	}

	public List<LandmarkItem> getLandmarks() {
		return landmarks;
	}

}
