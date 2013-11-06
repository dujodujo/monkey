package si.android.toy.activity;

import java.util.HashMap;
import java.util.List;

import si.android.toy.ApplicationState;
import si.android.toy.R;
import si.android.toy.data.ToyData;
import si.android.toy.model.Address;
import si.android.toy.model.Category;
import si.android.toy.model.Country;
import si.android.toy.model.UserData;
import si.android.toy.view.adapter.CategoriesViewAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

public class StartingActivity extends Activity {
	private static final String TAG = "StartingActivity";
	public static final String category = "category";
	
	private ApplicationState appState;
	private GridView gridView;
	private HashMap<Integer, MenuItem> menuItemsMap;
	
	private List<Category> categories;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		appState = (ApplicationState) getApplication();
		appState.setUserData(new UserData("John Doe", "+386 012334564", "john.doe@gmail.com", 
			new Address("Platz der Republik 1", "10557", "Berlin", new Country("Germany", "de"))));
		
		setContentView(R.layout.grid);
		gridView = (GridView)findViewById(R.id.grid);
		
		buildCategoryData();
	}
	
	private void buildCategoryData() {
		ToyData.initilaizeCategories();
		categories = ToyData.getCategories();
		gridView.setAdapter(new CategoriesViewAdapter(appState, categories));
		
		prepareCategoryClick();
	}

	//TODO switch on dialog
	private void prepareCategoryClick() {
		gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//showCategorySelectedDialog(position);
				Intent intent = new Intent(appState, ToyListActivity.class);
				startActivity(intent);
			}
		});
	}
	
	private void showCategorySelectedDialog(final int position) {
		final AlertDialog alertDialog = new AlertDialog.Builder(StartingActivity.this).create();
		
		
		alertDialog.setTitle(getResources().getString(R.string.categoriesList_categoryTitle));
		alertDialog.setMessage(getResources().getString(R.string.categoriesList_checkoutMessage));
		alertDialog.setIcon(R.drawable.table);
		
		alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "YES", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(appState, ToyListActivity.class);
				startActivity(intent);
			}
		});
		
		alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "NO", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				alertDialog.cancel();
			}
		});
		
		alertDialog.show();
	}

	//---------------------------------------
	// MENU
	//---------------------------------------
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menuItemsMap = new HashMap<Integer, MenuItem>();
		menuItemsMap.put(R.string.landmarks,
			menu.add(R.string.landmarks).setIcon(R.drawable.star_big_on));
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		setMenuItemState(R.string.landmarks, true, true);
		return true;
	}
	
	private void setMenuItemState(int itemID, boolean visible, boolean enabled) {
		MenuItem item = menuItemsMap.get(itemID);
		item.setEnabled(enabled);
		item.setVisible(visible);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getTitle().equals(getResources().getString(R.string.landmarks))) {
			showLandmarksActivity();
		}
		return true;
	}
	
	private void showLandmarksActivity() {
		Intent intent = new Intent(this, LandmarksActivity.class);
		startActivity(intent);
	}
}
