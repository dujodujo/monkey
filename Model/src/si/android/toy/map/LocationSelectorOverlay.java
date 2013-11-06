package si.android.toy.map;

import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;

public class LocationSelectorOverlay extends LocationIndicatorOverlay {
	private OnLocationSelectedListener listener;
	
	public LocationSelectorOverlay(GeoPoint startingPoint, OnLocationSelectedListener listener) {
		super(startingPoint);
		
		this.listener = listener;
	}
	
	@Override
	public boolean onTap(GeoPoint geoPoint, MapView mapView) {
		Log.d("LocationSelectorOverlay", "Tapped");
		//listener.onLocationSelected(geoPoint);
		return true;
	}

	public interface OnLocationSelectedListener {
		void onLocationSelected(GeoPoint newLocation);
	}
}
