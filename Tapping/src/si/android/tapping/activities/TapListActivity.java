package si.android.tapping.activities;

import java.util.ArrayList;

import si.android.tapping.R;
import si.android.tapping.adapters.CounterAdapter;
import si.android.tapping.controller.TapListController;
import si.android.tapping.observer.Counter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class TapListActivity extends Activity implements Handler.Callback {
	private ArrayList<Counter> counters;
	private CounterAdapter adapter;
	private TapListController controller;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tap_list);
		
		counters = new ArrayList<Counter>();
		controller = new TapListController(counters);
		controller.addHandler(new Handler(this));
		
		adapter = new CounterAdapter(this, counters);
		ListView list = (ListView)findViewById(R.id.listView);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> a, View view, int i, long l) {
				Intent intent = new Intent(TapListActivity.this, TapActivity.class);
				intent.putExtra(TapActivity.EXTRA_TAP_ID, adapter.getItem(i).getId());
				startActivity(intent);
			}
		});
		
		get_data();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		controller.dispose();
	}
	
	private void get_data() {
		controller.handleMessage(TapListController.MESSAGE_GET_COUNTERS);
	}


	public boolean handleMessage(Message msg) {
		switch(msg.what) {
			case TapListController.MESSAGE_MODEL_UPDATED:
				runOnUiThread(new Runnable() {
					public void run() {
						adapter.notifyDataSetChanged();
					}
				});
				return true;
		}
		return false;
	}
}
