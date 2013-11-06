package si.android.tapping.controller;

import si.android.tapping.TapApplication;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;
import android.util.Log;

public class UnlockedState extends TapState implements OnSharedPreferenceChangeListener {
	private static final String TAG = UnlockedState.class.getSimpleName();
	private boolean allowClick = false;
	
	public UnlockedState(TapController controller) {
		super(controller);
		setPreferences();
	}
	
	@Override
	public void dispose() {
		super.dispose();
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(TapApplication.getContext());
		preferences.unregisterOnSharedPreferenceChangeListener(this);
	}
	
	@Override
	public boolean handleMessage(int what) {
		switch(what) {
		case TapController.MESAGE_COUNT_UP:
			this.moveCount(1);
			return true;
		case TapController.MESAGE_COUNT_DOWN:
			this.moveCount(-1);
			return true;
		case TapController.MESAGE_RESET_COUNT:
			model.setCounter(0);
			return true;
		default:
			return super.handleMessage(what);
		}
	}
	
	@Override
	public boolean handleMessage(int what, Object data) {
		switch(what) {
		case TapController.MESAGE_UPDATE_LABEL:
			updateLabel((String) data);
			return true;
		case TapController.MESAGE_UPDATE_LOCK:
			updateLock((Boolean) data);
			return true;
		default:
			return super.handleMessage(what, data);
		}
	}

	private void updateLock(boolean lock) {
		model.setLocked(lock);
		controller.setMessageState(new LockedState(controller));
	}
	
	private void updateLabel(String label) {
		model.setLabel(label);
	}
	
	
	private void moveCount(int value) {
		model.setCounter(model.getCounter() + value);
	}

	private void setPreferences() {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(TapApplication.getContext());
		preferences.registerOnSharedPreferenceChangeListener(this);
		allowClick = preferences.getBoolean("click", true);
		
	}

	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		setPreferences();
	}
}
