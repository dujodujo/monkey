package si.android.toy.activity;

import java.util.ArrayList;
import java.util.List;

import si.android.toy.ApplicationState;
import si.android.toy.R;
import si.android.toy.activity.MViewActivity.MapViewDisplayMode;
import si.android.toy.model.Address;
import si.android.toy.view.adapter.AddressViewAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TableLayout;

public class AddressFromActivity extends Activity {
	public static final String MAPVEIW_DISPLAY_MODE = MapViewDisplayMode.class.getName();
	protected static final int SELECT_ADDRESS_IN_MAP = 1;
	protected ViewGroup rootView;
	protected List<MenuItem> menuItems = new ArrayList<MenuItem>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		rootView = initializeRootView();
		setContentView(rootView);
	}
	
	private TableLayout initializeRootView() {
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		TableLayout view = (TableLayout) inflater.inflate(getLayoutResourceID(), rootView, true);
		return view;
	}
	
	protected int getLayoutResourceID() {
		return R.layout.address_form_merge;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menuItems.add(menu.add(R.string.address_buttonShowMap).setIcon(R.drawable.house));
		menuItems.add(menu.add(R.string.address_buttonFromMap).setIcon(R.drawable.house_link));
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getTitle().equals(getResources().getString(R.string.address_buttonShowMap))) {
			showMapView(MapViewDisplayMode.SHOW);
			return true;
		} else if(item.getTitle().equals(getResources().getString(R.string.address_buttonFromMap))) {
			showMapView(MapViewDisplayMode.SELECT);
			return true;
		}
		return false;
	}

	protected void showMapView(MapViewDisplayMode mode) {
		Intent intent = new Intent(this, MViewActivity.class);
		intent.putExtra(AddressFromActivity.MAPVEIW_DISPLAY_MODE, mode);
		
		switch(mode) {
			case SELECT:
				startActivity(intent);				
				break;
			case SHOW:
				startActivity(intent);
				break;
		}
	}
}
