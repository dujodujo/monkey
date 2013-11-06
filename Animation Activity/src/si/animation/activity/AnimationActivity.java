package si.animation.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.ViewFlipper;

public class AnimationActivity extends Activity implements 
AdapterView.OnItemSelectedListener {

	private String strings[] = {"Push Up", "Push Left", "Cross Fade", "Hyperspace"};
	private ViewFlipper flipper;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.animation_2);
	
	    flipper = (ViewFlipper)findViewById(R.id.flipper);
	    flipper.startFlipping();
	    
	    Spinner spinner = (Spinner)findViewById(R.id.spinner);
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, 
	    		android.R.layout.simple_spinner_item, strings);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spinner.setAdapter(adapter);
	    spinner.setOnItemSelectedListener(this);
    }

	public void onItemSelected(AdapterView<?> parent, View v, int position,
			long id) {
		switch(position) {
		case 0:
			flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.animator.push_up_in));
			flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.animator.push_up_out));
			break;
		case 1:
			flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.animator.push_left_in));
			flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.animator.push_left_out));
			break;
		case 2:
			flipper.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
			flipper.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
		default:
			flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.animator.hyperspace_in));
			flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.animator.hyperspace_out));
			break;
		}
	}

	public void onNothingSelected(AdapterView<?> arg0) {
	}
}