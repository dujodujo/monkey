package si.android.toy.map;

import android.graphics.drawable.Drawable;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

public class LandmarkItem extends OverlayItem {
	
	private Drawable marker = null;
	private Drawable star = null;
	private boolean isStar = false;
	
	public LandmarkItem(GeoPoint point, String title, String snippet) {
		this(point, title, snippet, null, null);
	}
	
	public LandmarkItem(GeoPoint point, String title, String snippet, Drawable marker, Drawable star) {
		super(point, title, snippet);
		
		this.marker = marker;
		this.star = star;
	}
	
	public void toggleStar() {
		this.isStar = !isStar;
	}
	
	@Override
	public Drawable getMarker(int stateBitset) {
		Drawable result = isStar ? star : marker;
		setState(result, stateBitset);
		
		return result;
	}

	@Override
	public GeoPoint getPoint() {
		return super.getPoint();
	}
}
