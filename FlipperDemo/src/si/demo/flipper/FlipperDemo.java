package si.demo.flipper;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ViewFlipper;
import android.support.v4.app.NavUtils;

public class FlipperDemo extends Activity {
	private ViewFlipper flipper;
	private static final String oil[] = {
		                                 "ExxonMobil", "Shell", "Chevron",
		                                 "ConnocoPhillips", "Imperial Oil",
		                                 "Petrobas", "PetroChina", "Sinopec",
		                                 "Total", "Statoil","Lukoil"};
		                                 

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		flipper = (ViewFlipper)findViewById(R.id.details);
		for(String item: oil) {
			Button button = new Button(this);
			button.setText(item);
			flipper.addView(button, 
					new ViewGroup.LayoutParams(
							ViewGroup.LayoutParams.MATCH_PARENT,
							ViewGroup.LayoutParams.MATCH_PARENT));
			flipper.setFlipInterval(3000);
			flipper.startFlipping();
		}
	}
}
