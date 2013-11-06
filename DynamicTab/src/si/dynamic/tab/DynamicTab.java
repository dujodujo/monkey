package si.dynamic.tab;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DigitalClock;
import android.widget.TabHost;
import android.support.v4.app.NavUtils;

public class DynamicTab extends Activity {
	private TabHost tabHost = null;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		tabHost = (TabHost)findViewById(R.id.thTabHost);
		tabHost.setup();
		
		TabHost.TabSpec spec = tabHost.newTabSpec("button tab");
		spec.setContent(R.id.button1);
		spec.setIndicator("Button1");
		tabHost.addTab(spec);		
	}
	
	public void addTab(View view) {
		TabHost.TabSpec spec = tabHost.newTabSpec("tag1");
		spec.setContent(new TabHost.TabContentFactory() {
			
			public View createTabContent(String tag) {
				return (new DigitalClock(DynamicTab.this));
			}
		});
		spec.setIndicator("Digital Clock");
		tabHost.addTab(spec);
	}
}
