package si.android.toy.model;

import android.graphics.drawable.Drawable;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

public class City extends OverlayItem {
	private double longitude;
	private double latitude;
	
	public City(GeoPoint point, String title, String snippet) {
		super(point, title, snippet);
		
		this.longitude = point.getLongitudeE6();
		this.latitude = point.getLatitudeE6();
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
}
