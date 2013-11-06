package si.android.tapping.controller;

import java.util.ArrayList;

import android.os.Handler;
import android.os.Message;

abstract class Controller {
	private static final String Tag = Controller.class.getSimpleName();
	private ArrayList<Handler> handlers = new ArrayList<Handler>();
	
	public Controller() {}
	
	public void dispose() {}
	
	abstract public boolean handleMessage(int what, Object data);
	
	public boolean handleMessage(int what) {
		return handleMessage(what, null);
	}
	
	public void addHandler(Handler handler) {
		handlers.add(handler);
	}
	
	public void removeHandler(Handler handler) {
		handlers.remove(handler);
	}
	
	protected final void notifyHandlers(int what, int arg1, int arg2, Object obj) {
		if(!handlers.isEmpty()) {
			for(Handler handler : handlers) {
				Message msg = Message.obtain(handler, what, arg1, arg2, obj);
				msg.sendToTarget();
			}
		}
	}
}
