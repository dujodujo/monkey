package si.android.tapping;

import android.app.Application;
import android.content.Context;
import android.util.Log;

public class TapApplication extends Application {
	private static final String TAG = TapApplication.class.getSimpleName();
	private static Application instance;

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "TapApplication.onCreate called");
		instance=this;
	}
	
	public static Context getContext() {
		return instance.getApplicationContext();
	}
}
