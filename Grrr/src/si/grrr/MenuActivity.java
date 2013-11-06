package si.grrr;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MenuActivity extends Activity {
	private int visible;
	private String lastPicture;
	private ImageView picture;
	private Button exit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.menu_main);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		AlertDialog.Builder adb1 = new AlertDialog.Builder(this);
		AlertDialog.Builder adb2 = new AlertDialog.Builder(this);
		
		adb1.setTitle("Application")
		.setCancelable(true)
		.setPositiveButton("OK", null)
		.setMessage(R.string.alert_dialog);
		
		adb2.setTitle("App exit")
		.setCancelable(true)
		.setNegativeButton("NO", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				return;
			}
		})
		.setPositiveButton("YES", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		})
		.setMessage(R.string.alert_dialog_question);
		
		switch(item.getItemId()) {
		case R.id.app:
			adb1.show();
			return true;
		case R.id.exit:
			adb2.show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
