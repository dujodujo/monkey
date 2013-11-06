package si.android.toy.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.maps.MapView;

public class PopUpPanel {
	View popUp = null;
	boolean isVisible = false;
	private MapView mapView = null;
	private LayoutInflater inflater = null;
	
	private ViewGroup parent = null;

	public PopUpPanel(MapView mapView, int layout) {
		this.mapView = mapView;
		parent = (ViewGroup) this.mapView.getParent();
		
		inflater = (LayoutInflater) mapView.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.popUp = inflater.inflate(layout, parent, false);
		
		popUp.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				hide();
			}
		});
	}
	
	public View getView() {
		return popUp;
	}
	
	public void show(boolean alignTop) {
		//RelativeLayout.LayoutParams layoutParams = 
		//	new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
		//									RelativeLayout.LayoutParams.WRAP_CONTENT);
		/*
		if(alignTop) {
			layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
			layoutParams.setMargins(0, 20, 0, 0);
		} else {
			layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
			layoutParams.setMargins(0, 0, 0, 40);
		}
		*/
		hide();
		
		//((ViewGroup)this.mapView.getParent()).addView(popUp, layoutParams);
		((ViewGroup)this.mapView.getParent()).addView(popUp);
		isVisible = true;
	}
	
	private void hide() {
		if(isVisible) {
			((ViewGroup)this.mapView.getParent()).removeView(popUp);
			isVisible = false;
		}
	}
}
