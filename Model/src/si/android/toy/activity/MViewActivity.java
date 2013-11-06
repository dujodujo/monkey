package si.android.toy.activity;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import si.android.toy.ApplicationState;
import si.android.toy.R;
import si.android.toy.adapter.AddressAdapter;
import si.android.toy.map.CityItemizedOverlay;
import si.android.toy.map.DrawLineOverlay;
import si.android.toy.map.LandmarksOverlay;
import si.android.toy.map.LocationIndicatorOverlay;
import si.android.toy.map.LocationSelectorOverlay;
import si.android.toy.map.LocationSelectorOverlay.OnLocationSelectedListener;
import si.android.toy.util.StringUtil;
import si.android.toy.view.SlidingPanel;
import si.android.toy.view.adapter.AddressViewAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class MViewActivity extends MapActivity implements OnClickListener, LocationListener {
	
	private static final String TAG = "MViewActivity";
	
	private static final int ZOOM_LEVEL = 7;
	public static enum MapViewDisplayMode {SHOW, SELECT}
	public static final String INTENT_EXTRA_KEY_ADDRESS = Address.class.getName();
	protected MapViewDisplayMode mapViewDisplayMode = null;
	
	private List<Overlay> overlays = null;
	
	private LocationManager locationManager = null;
	private MapView mapView = null;
	private MapController controller = null;
	private Location currentLocation = null;
	private String locationProvider = null;
	
	private SlidingPanel panel = null;
	private Button satelite_view = null;
	private Button normal_view = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.map_layout);
		initializeButtons();
		mapView = initializeMapView();
		
		//mapViewDisplayMode = (MapViewDisplayMode) getIntent().getExtras().get(AddressFromActivity.MAPVEIW_DISPLAY_MODE);
		//panel = (SlidingPanel)findViewById(R.id.sliding_panel);
		/*
		switch(mapViewDisplayMode) {
			case SHOW:
				ApplicationState appState = (ApplicationState) getApplication();
				navigateToAddress(appState.getAddress());
				break;
			//case SELECT:
			//	currentLocation = initializeLocationManager();
			//	if(currentLocation != null) {
			//		navigateTo(currentLocation);
			//	}
			default:
				break;
		}
		*/
		
		currentLocation = initializeLocationManager();
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

		/*
		List<Overlay> overlays = mapView.getOverlays();
		DrawLineOverlay drawOverlay = new DrawLineOverlay(mapView);
		overlays.add(drawOverlay);
		*/
		
		this.satelite_view.setOnClickListener(this);
		this.normal_view.setOnClickListener(this);
	}
	
	private void initializeButtons() {
		normal_view = (Button)findViewById(R.id.normal_view);
		satelite_view = (Button)findViewById(R.id.satelite_view);
	}
	
	private MapView initializeMapView() {
		MapView mapView = (MapView)findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		mapView.displayZoomControls(true);
		mapView.setSatellite(true);
		return mapView;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.map_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case R.id.settings:
				panel.toggle();
				return true;
			case R.id.multiple_markers_view:
				Intent intent = new Intent(this, LandmarksActivity.class);
				startActivity(intent);
				//this.onLocationChanged(currentLocation);
				//LandmarksOverlay landmarks = new LandmarksOverlay(this.mapView);
				//mapView.getOverlays().add(landmarks);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
	public void onProviderDisabled(String provider) {
		Toast.makeText(getApplicationContext(), "GPS is disabled", Toast.LENGTH_LONG).show();
	}

	public void onProviderEnabled(String provider) {
		Toast.makeText(getApplicationContext(), "GPS is enabled", Toast.LENGTH_LONG).show();
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {}

	protected Location initializeLocationManager() {
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setPowerRequirement(Criteria.NO_REQUIREMENT);
		
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationProvider = locationManager.getBestProvider(criteria, true);
		return locationManager.getLastKnownLocation(locationProvider);
	}
	
	private void navigateToAddress(si.android.toy.model.Address address) {
		String locationName = AddressViewAdapter.getLocationName(address);
		Log.d(TAG, locationName + " ");
		if(!StringUtil.isNullOrEmpty(locationName)) {
			JSONObject json = getLocationInfo(locationName);
			GeoPoint geoPoint = getGeoPoint(json);
			if(geoPoint != null) {
				navigateTo(geoPoint);
			}
		}
	}
	
	public JSONObject getLocationInfo(String address) {

		HttpGet httpGet = new HttpGet("http://maps.google.com/maps/api/geocode/json?address=" + address
			+ "&sensor=true");
		HttpClient client = new DefaultHttpClient();
		HttpResponse response;
		StringBuilder stringBuilder = new StringBuilder();

		try {
			response = client.execute(httpGet);
			HttpEntity entity = response.getEntity();
			InputStream stream = entity.getContent();
			int b;
			while ((b = stream.read()) != -1) {
				stringBuilder.append((char) b);
			}
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = new JSONObject(stringBuilder.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return jsonObject;
	}
	
	public GeoPoint getGeoPoint(JSONObject jsonObject) {
		Double lon = new Double(0);
		Double lat = new Double(0);
		try {

			lon = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
				.getJSONObject("geometry").getJSONObject("location")
				.getDouble("lng");

			lat = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
				.getJSONObject("geometry").getJSONObject("location")
				.getDouble("lat");

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return new GeoPoint((int) (lat * 1E6), (int) (lon * 1E6));
	}
	
	protected void navigateTo(GeoPoint geoPoint) {
		if(geoPoint == null)
			return;
		
		final int STARTING_ZOOM_LEVEL = mapView.getMaxZoomLevel() - ZOOM_LEVEL;
		
		mapView.getController().animateTo(geoPoint);
		mapView.getController().setZoom(STARTING_ZOOM_LEVEL);
		
		switch(mapViewDisplayMode) {
			case SHOW:
				mapView.getOverlays().add(new LocationIndicatorOverlay(geoPoint));
				break;
			//case SELECT:
			//	mapView.getOverlays().add(new LocationSelectorOverlay(geoPoint, this));
			//	break;
		}
	}

	public void onClick(View v) {
		if(v.getId() == R.id.satelite_view) {
			if(!mapView.isSatellite()) {
				mapView.setSatellite(true);
			}
		} else if(v.getId() == R.id.normal_view){
			if(mapView.isSatellite()) {
				mapView.setSatellite(false);
			}
		}
	}
	
	public void onLocationChanged(Location location) {
		double latitude = location.getLatitude();
		double longitude = location.getLongitude();
		
		Toast.makeText(getApplicationContext(), latitude + " " + longitude, Toast.LENGTH_SHORT).show();
		
		GeoPoint point = new GeoPoint((int) (latitude * 1E6), (int) (longitude * 1E6));
		
		controller = mapView.getController();
		controller.animateTo(point);
		controller.setZoom(7);
		
		Log.d(TAG, latitude + ", " + longitude);
		
		LocationIndicatorOverlay marker = new LocationIndicatorOverlay(point);
		List<Overlay> markers = mapView.getOverlays();
		markers.clear();
		markers.add(marker);
		
		mapView.invalidate();
	}

	private GeoPoint getPoint(double latitude, double longitude) {
		return (new GeoPoint((int)(latitude * LandmarksActivity.MEGA), 
			(int)(longitude * LandmarksActivity.MEGA)));
	}

	
	/*
	protected void navigateTo(Location location) {
		GeoPoint geoPoint = getGeoPointFromLocation(location);
		navigateTo(geoPoint);
	}

	protected GeoPoint getGeoPointFromLocation(Location location) {
		return new GeoPoint((int) (location.getLatitude() * 1E6), (int) (location.getLongitude() * 1E6));
	}

	public void onLocationSelected(GeoPoint newLocation) {
		final int RESULTS = 1;
		
		double latitude = newLocation.getLatitudeE6() / 1E6;
		double longitude = newLocation.getLongitudeE6() / 1E6;
		
		Geocoder geocoder = new Geocoder(this, Locale.GERMANY);
		try {
			List<Address> addresses = geocoder.getFromLocation(latitude, longitude, RESULTS);
			Toast.makeText(this, addresses.get(0).getLongitude() + "" , Toast.LENGTH_LONG).show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	*/
}
