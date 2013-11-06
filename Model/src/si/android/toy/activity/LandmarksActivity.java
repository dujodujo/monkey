package si.android.toy.activity;

import java.util.List;

import si.android.toy.R;
import si.android.toy.map.CityItemizedOverlay;
import si.android.toy.map.LandmarkItem;
import si.android.toy.map.LandmarksOverlay;
import si.android.toy.map.LocationIndicatorOverlay;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;

public class LandmarksActivity extends MapActivity {
	private String TAG = "LandmarksActivity";
	public static final double MEGA = 1E6;
	
	private LocationManager locationManager = null;
	private MapView mapView = null;
	private String locationProvider = null;
	
	private LandmarksOverlay landmarksOverlays = null;
	private MyLocationOverlay myLocationOverlay = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.map_layout);
		
		mapView = initializeMapView();
		
		myLocationOverlay = new MyLocationOverlay(this, mapView);
		mapView.getOverlays().add(myLocationOverlay);
		
		new OverlayTask().execute();
	}
	
	protected Location initializeLocationManager() {
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setPowerRequirement(Criteria.NO_REQUIREMENT);
		
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationProvider = locationManager.getBestProvider(criteria, true);
		return locationManager.getLastKnownLocation(locationProvider);
	}
	
	private MapView initializeMapView() {
		MapView mapView = (MapView)findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		mapView.displayZoomControls(true);
		mapView.setSatellite(true);
		return mapView;
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		
		myLocationOverlay.disableCompass();
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		myLocationOverlay.enableCompass();
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}
	
	class OverlayTask extends AsyncTask<Void, Void, Void> {
		
		@Override
		protected void onPreExecute() {
			if(landmarksOverlays != null) {
				mapView.getOverlays().remove(landmarksOverlays);
				mapView.invalidate();
				landmarksOverlays = null;
			}
		}
		
		@Override
		protected Void doInBackground(Void... unused) {
			SystemClock.sleep(3000);	// simulating work
			
			landmarksOverlays = new LandmarksOverlay(mapView);
			GeoPoint point = landmarksOverlays.getCenterPoint();
			mapView.getController().animateTo(point);
			mapView.getController().setZoom(14);
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void unused) {
			mapView.getOverlays().add(landmarksOverlays);
			mapView.invalidate();
		}
	}
	
	public void onProviderDisabled(String provider) {}

	public void onProviderEnabled(String provider) {}

	public void onStatusChanged(String provider, int status, Bundle extras) {}
}
