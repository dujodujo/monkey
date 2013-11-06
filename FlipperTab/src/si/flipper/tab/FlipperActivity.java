package si.flipper.tab;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ViewFlipper;
import android.support.v4.app.NavUtils;

public class FlipperActivity extends Activity {
	ViewFlipper flipper = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		flipper = (ViewFlipper)findViewById(R.id.details);
	}
	
	public void next(View v) {
		flipper.showNext();
	}
	
	public void previous(View v) {
		flipper.showPrevious();
	}
}
