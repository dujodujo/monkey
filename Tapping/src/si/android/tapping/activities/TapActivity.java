package si.android.tapping.activities;

import si.android.tapping.R;
import si.android.tapping.TapApplication;
import si.android.tapping.controller.TapController;
import si.android.tapping.observer.Counter;
import si.android.tapping.observer.OnChangeListener;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ToggleButton;

public class TapActivity extends Activity implements OnChangeListener<Counter> {
	private static final String TAG = TapActivity.class.getSimpleName();
	private Counter counter;
	private TapController controller;
	
	private EditText label;
	private TextView current_count;
	private ImageView plus_button;
	private Button minus_button;
	private CompoundButton locked_button;
	private Dialog saveDialog;
	
	public static final String EXTRA_TAP_ID = "tapID";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.main);

		counter = new Counter();
		counter.addListener(this);
		controller = new TapController(counter);

		label = (EditText)findViewById(R.id.label);
		current_count = (TextView)findViewById(R.id.count_view);
		plus_button = (ImageView)findViewById(R.id.plus_button);
		minus_button = (Button)findViewById(R.id.minus_button);
		locked_button = (CompoundButton)findViewById(R.id.locked_button);
		
		int tapId = getIntent().getIntExtra(EXTRA_TAP_ID, -1);
		Log.i(TAG, "onCreate tapId:" + tapId);
		
		controller.handleMessage(TapController.MESAGE_POPULATE_MODEL_BY_ID, tapId);

		label.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {}

			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

			public void onTextChanged(CharSequence s, int start, int before, int count) {
				controller.handleMessage(TapController.MESAGE_UPDATE_LABEL, s.toString());
			}
		});
		
		plus_button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				controller.handleMessage(TapController.MESAGE_COUNT_UP);
			}
		});
		minus_button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				controller.handleMessage(TapController.MESAGE_COUNT_DOWN);
			}
		});
		
		locked_button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				controller.handleMessage(TapController.MESAGE_UPDATE_LOCK, isChecked);
			}
		});
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		if(counter.getId() > 0) {
			controller.handleMessage(TapController.MESAGE_POPULATE_MODEL_BY_ID, counter.getId());
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		controller.dispose();
		if(saveDialog != null && saveDialog.isShowing()) {
			saveDialog.dismiss();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case R.id.reset:
				return controller.handleMessage(TapController.MESAGE_RESET_COUNT);
			case R.id.new_tap:
				createSaveDialog();
				return true;
			case R.id.taps:
				startActivity(new Intent(this, TapListActivity.class));
				return true;
			case R.id.save:
				return controller.handleMessage(TapController.MESAGE_SAVE_MODEL);
			default:
				return false;
		}
	}

	public void onChange(Counter counter) {
		runOnUiThread(new Runnable() {
			public void run() {
				updateView();
			}
		});
	}
	
	private void updateView() {
		if(!label.getText().toString().equals(counter.getLabel())) {
			label.setText(counter.getLabel());
		}
		label.setEnabled(!counter.isLocked());
		current_count.setText(Integer.toString(counter.getCounter()));
		this.locked_button.setChecked(counter.isLocked());
	}
	
	private void createSaveDialog() {
		if(saveDialog != null && saveDialog.isShowing()) {
			saveDialog.dismiss();
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Save Counter?");
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				controller.handleMessage(TapController.MESAGE_SAVE_MODEL);
				controller.handleMessage(TapController.MESAGE_CREATE_NEW_MODEL);
			}
		});
		
		builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				controller.handleMessage(TapController.MESAGE_CREATE_NEW_MODEL);
			}
		});
		saveDialog = builder.show();
	}
}
