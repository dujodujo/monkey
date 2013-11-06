package si.android.tapping.controller;

public interface ControllerState {
	boolean handleMessage(int what);
	boolean handleMessage(int what, Object data);
	void dispose();
}
