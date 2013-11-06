package si.android.tapping.controller;

import android.util.Log;
import si.android.tapping.observer.Counter;

public class TapController extends Controller {
	private static final String TAG = TapController.class.getSimpleName();
	private ControllerState messageState;
	private Counter model;

	public static final int MESAGE_SAVE_MODEL		= 1;
	public static final int MESAGE_COUNT_UP			= 2;
	public static final int MESAGE_COUNT_DOWN		= 3;
	public static final int MESAGE_UPDATE_LOCK		= 4;
	public static final int MESAGE_UPDATE_LABEL 	= 5;
	public static final int MESAGE_SAVE_FINISHED	= 6;
	public static final int MESAGE_RESET_COUNT 		= 7;
	public static final int MESAGE_POPULATE_MODEL_BY_ID = 8;
	public static final int MESAGE_CREATE_NEW_MODEL = 9;
	
	public TapController(Counter model) {
		this.model = model;
		messageState = new UnlockedState(this);
	}
	
	public Counter getModel() {
		return this.model;
	}
	
	@Override
	public void dispose() {
		super.dispose();
		this.messageState.dispose();
	}
	
	protected void setMessageState(ControllerState messageState) {
		if(this.messageState != null) {
			this.messageState.dispose();
		}
		this.messageState = messageState;
	}
	
	@Override
	public boolean handleMessage(int what, Object data) {
		Log.i(TAG, "handling message " + what);
		return this.messageState.handleMessage(what, data);
	}

	@Override
	public boolean handleMessage(int what) {
		Log.i(TAG, "handling message " + what);
		return this.messageState.handleMessage(what);
	}
}
