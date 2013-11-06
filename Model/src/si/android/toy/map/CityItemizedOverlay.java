package si.android.toy.map;

import java.util.ArrayList;
import java.util.List;

import si.android.toy.model.City;

import android.graphics.drawable.Drawable;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class CityItemizedOverlay extends ItemizedOverlay {
	private List<City> overlays = new ArrayList<City>();
	
	public CityItemizedOverlay(Drawable marker) {
		super(boundCenterBottom(marker));
	}
	
	public void initializeItemCities() {
		overlays.add(new City(new GeoPoint(523100, 132400), "Berlin", ""));
		overlays.add(new City(new GeoPoint(510300, 134400), "Dresden", ""));
		overlays.add(new City(new GeoPoint(500600, 84400), "Frankfurt", ""));
		overlays.add(new City(new GeoPoint(480800, 113400), "Munich", ""));
		overlays.add(new City(new GeoPoint(541900, 100800), "Kiel", ""));
	}
	
	@Override
	protected OverlayItem createItem(int i) {
		return overlays.get(i);
	}

	@Override
	public int size() {
		return overlays.size();
	}
	
	public void addCityOverlayItem(City city) {
		overlays.add(city);
		populate();
	}
}
