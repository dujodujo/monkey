package si.android.toy.activity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import si.android.toy.ApplicationState;
import si.android.toy.R;
import si.android.toy.model.Address;
import si.android.toy.model.Country;
import si.android.toy.model.Toy;
import si.android.toy.model.UserData;
import si.android.toy.util.WidgetUtils;
import si.android.toy.view.adapter.ToyViewAdapter;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

public class ToyListActivity extends ListActivity {
	private static final String TAG = "ToyListActivity";
	private Map<Integer, MenuItem> menuItemsMap;
	protected ApplicationState appState;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.toys_list_layout);
		
		appState = (ApplicationState) getApplication();
		buildToyData();
	}
	
	private void buildToyData() {
		List<Toy> toys = new ArrayList<Toy>();
		toys.add(new Toy("asd", 100));
		toys.add(new Toy("qwe", 200));
		toys.add(new Toy("fgh", 400));
		
		setListAdapter(new ToyViewAdapter(this, toys));
		prepareToyClick();
	}
	
	@Override
	public ToyViewAdapter getListAdapter() {
		return (ToyViewAdapter) super.getListAdapter();
	}

	protected void prepareToyClick() {
		getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Toy toy = (Toy) ToyListActivity.this.getListAdapter().getItem(position);
				appState.getOrder().addToy(toy);
				
				showToySelectedDialog(toy);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menuItemsMap = new HashMap<Integer, MenuItem>();
		
		menuItemsMap.put(R.string.toyCart_toyList,
			menu.add(R.string.toyCart_toyList).setIcon(R.drawable.script_edit));
		menuItemsMap.put(R.string.toyList_viewShoppingCart,
			menu.add(R.string.toyList_viewShoppingCart).setIcon(R.drawable.cart));
		menuItemsMap.put(R.string.toyCart_checkout, 
			menu.add(R.string.toyCart_checkout).setIcon(R.drawable.cart_go));
		menuItemsMap.put(R.string.toyList_viewUserData, 
			menu.add(R.string.toyList_viewUserData).setIcon(R.drawable.user_green));
		
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		setMenuItemState(R.string.toyCart_toyList, false, false);
		setMenuItemState(R.string.toyList_viewShoppingCart, true, !isShoppingCartEmpty());
		setMenuItemState(R.string.toyCart_checkout, true, isShoppingCartCheckoutAllowed());
		setMenuItemState(R.string.toyList_viewUserData, true, true);

		return true;
	}

	protected void setMenuItemState(int itemTitleResID, boolean visible, boolean enabled) {
		MenuItem item = menuItemsMap.get(itemTitleResID);
		item.setEnabled(enabled);
		item.setVisible(visible);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getTitle().equals(getString(R.string.toyList_viewUserData))) {
			showUserDataActivity();

		} else if (item.getTitle().equals(getString(R.string.toyCart_toyList))) {
			showToyListActivity();

		} else if (item.getTitle().equals(getString(R.string.toyList_viewShoppingCart))) {
			showToyShoppingCartActivity();

		} else if (item.getTitle().equals(getString(R.string.toyCart_checkout))) {
			checkoutShoppingCartPromptUser();
		}
		return true;
	}

	private void checkoutShoppingCartPromptUser() {
		String totalToys = String.valueOf(appState.getOrder().getToysCount());
		String totalPrice = new DecimalFormat("0.00").format(appState.getOrder().getTotalPrice());
		
		AlertDialog alertDialog = WidgetUtils.createOkCancelAlertDialog(
			this, R.drawable.cart, R.string.toyList_shoppingCart,
			getString(R.string.toyList_checkoutMessage, totalToys, totalPrice),
			new OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					checkoutShoppingCart();
				}
			});
		alertDialog.show();
	}
	
	private void checkoutShoppingCart() {
		
	}
	
	private void showToySelectedDialog(Toy toy) {
		final AlertDialog alertDialog = new AlertDialog.Builder(ToyListActivity.this).create();
		
		alertDialog.setTitle(getResources().getString(R.string.toyList_toySelectedTitle, toy.getName()));
		alertDialog.setMessage(getResources().getString(R.string.toyList_toySelectedMsg));
		alertDialog.setIcon(R.drawable.table);
		
		alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "YES", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				showToyShoppingCartActivity();
			}
		});
		
		alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "NO", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				alertDialog.cancel();
			}
		});
		
		alertDialog.show();
	}
	
	private void showToyShoppingCartActivity() {
		Intent intent = new Intent(this, ToyShoppingCartActivity.class);
		startActivity(intent);
	}

	protected void showToyListActivity() {
		Intent intent = new Intent(this, ToyListActivity.class);
		startActivity(intent);
	}

	private void showUserDataActivity() {
		Intent intent = new Intent(this, UserDataFormActivity.class);
		startActivity(intent);
	}
	
	protected boolean isShoppingCartEmpty() {
		return appState.getOrder().getToys().size() == 0;
	}

	protected boolean isUserInfoValid() {
		return appState.getUserData().isValid();
	}

	protected boolean isShoppingCartCheckoutAllowed() {
		return !isShoppingCartEmpty() && isUserInfoValid();
	}
}
