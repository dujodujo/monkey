package si.grrr;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.support.v4.app.NavUtils;

public class MainActivity extends Activity implements OnClickListener{
	private int visible;
	private String lastPicture;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button show1 = (Button)findViewById(R.id.show1);
        show1.setOnClickListener(this);
        
        Button show2 = (Button)findViewById(R.id.show2);
        show2.setOnClickListener(this);
        
        Button app = (Button)findViewById(R.id.app);
        app.setOnClickListener(this);
        
        Button menu = (Button)findViewById(R.id.menu);
        menu.setOnClickListener(this);
        
        Button clear = (Button)findViewById(R.id.clear);
        clear.setOnClickListener(this);
        
        ImageView picture = (ImageView)findViewById(R.id.picture);
        picture.setVisibility(View.INVISIBLE);
        
        if(savedInstanceState != null) {
        	this.visible = savedInstanceState.getInt("visible");
        	this.lastPicture = savedInstanceState.getString("lastPicture");
        	if(this.visible == 1) {
        		if(lastPicture.equals("lotus")) {
        			picture.setImageResource(R.drawable.lotus);
        			picture.setVisibility(View.VISIBLE);
        		} else if(lastPicture.equals("pasijonka")) {
        			picture.setImageResource(R.drawable.cvet_pasijonke);
        			picture.setVisibility(View.VISIBLE);
        		}
        	} else {
        		picture.setVisibility(View.INVISIBLE);
        	}
        } else {
        	picture.setVisibility(View.INVISIBLE);
        }
    }

	public void onClick(View view) {
		ImageView picture = (ImageView)findViewById(R.id.picture);
		switch(view.getId()) {
		case R.id.show1:
			picture.setImageResource(R.drawable.cvet_pasijonke);
			picture.setVisibility(View.VISIBLE);
			this.visible = 1;
			this.lastPicture = "pasijonka";
			break;
		case R.id.show2:
			picture.setImageResource(R.drawable.lotus);
			picture.setVisibility(View.VISIBLE);
			this.visible = 1;
			this.lastPicture = "lotus";
			break;
		case R.id.menu:
			Intent intent1 = new Intent(this, MenuActivity.class);
			startActivity(intent1);
			break;
		case R.id.app:
			Intent intent2 = new Intent(this, ApplicationActivity.class);
			startActivity(intent2);
			break;
		case R.id.clear:
			picture.setVisibility(View.INVISIBLE);
			this.visible = 0;
			this.lastPicture = "";
			break;
		case R.id.form:
			Intent intent3 = new Intent(this, FormActivity.class);
			startActivity(intent3);
			break;
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putInt("visible", this.visible);
		outState.putString("lastPicture", this.lastPicture);
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
