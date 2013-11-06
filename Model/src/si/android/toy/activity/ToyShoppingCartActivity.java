package si.android.toy.activity;

import java.util.List;

import si.android.toy.R;
import si.android.toy.model.Toy;
import si.android.toy.model.ToyLineItem;
import si.android.toy.view.adapter.ToyLineItemViewAdapter;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

public class ToyShoppingCartActivity extends ToyListActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		TextView titleView = (TextView)findViewById(R.id.toys_list_title);
		titleView.setText(R.string.toyCart_title);
	}

	@Override
	protected void onResume() {
		super.onResume();
		refreshUI();
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		
		setMenuItemState(R.string.toyCart_toyList, true, true);
		setMenuItemState(R.string.toyList_viewShoppingCart, false, false);
		
		return true;
	}
	
	protected void addToy(Toy toy) {
		appState.getOrder().addToy(toy);
		refreshUI();
		Toast.makeText(ToyShoppingCartActivity.this, R.string.toyCart_ToyAdded, Toast.LENGTH_LONG).show();
	}
	
	protected void removeToy(Toy toy) {
		appState.getOrder().removeToy(toy);
		refreshUI();
		Toast.makeText(ToyShoppingCartActivity.this, R.string.toyCart_ToyRemoved, Toast.LENGTH_LONG).show();
		if(isShoppingCartEmpty()) {
			Toast.makeText(ToyShoppingCartActivity.this, R.string.toyCart_cartIsEmpty, Toast.LENGTH_LONG).show();
			showToyListActivity();
		}
	}
	
	protected void prepareToyClick() {
		getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Toy toy = getListAdapter().getItem(position);
				//showToySelecetedDialog(toy);
			}
		});
	}

	protected void refreshUI() {
		setListAdapter();
		onContentChanged();
		prepareToyClick();
	}
	
	protected void setListAdapter() {
		List<ToyLineItem> toyLineItems = appState.getOrder().getToys();
		setListAdapter(new ToyLineItemViewAdapter(this, toyLineItems));
	}
}
