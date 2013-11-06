package si.android.tapping.controller;

import java.util.ArrayList;

import si.android.tapping.db.CounterDb;
import si.android.tapping.observer.Counter;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

public class TapListController extends Controller {
	private static final String TAG = TapListController.class.getSimpleName();
	private HandlerThread workerThread;
	private Handler workerHandler;
	private ArrayList<Counter> models;
	
	public static final int MESSAGE_GET_COUNTERS = 1;
	public static final int MESSAGE_MODEL_UPDATED = 2;
	public static final int MESSAGE_DELETE_COUNTER = 3;
	public static final int MESSAGE_COUNTER_UP = 4;
	public static final int MESSAGE_COUNTER_DOWN = 5;
	
	public ArrayList<Counter> getModel() {
		return this.models;
	}
	
	public TapListController(ArrayList<Counter> models) {
		this.models = models;
		this.workerThread = new HandlerThread("Worker Thread");
		this.workerThread.start();
		this.workerHandler = new Handler(workerThread.getLooper());
	}
	
	@Override
	public void dispose() {
		super.dispose();
		this.workerThread.getLooper().quit();
	}

	@Override
	public boolean handleMessage(int what, Object data) {
		switch(what) {
		case MESSAGE_GET_COUNTERS:
			getCounters();
			return true;
		case MESSAGE_DELETE_COUNTER:
			deleteCounter((Integer) data);
			getCounters();
			return true;
		case MESSAGE_COUNTER_UP:
			changeCount(1, (Counter) data);
			getCounters();
		case MESSAGE_COUNTER_DOWN:
			changeCount(-1, (Counter) data);
			getCounters();
			return true;
		}
		return false;
	}
	
	private void getCounters() {
		workerHandler.post(new Runnable() {
			public void run() {
				CounterDb db = new CounterDb();
				ArrayList<Counter> counters = db.getAllCounters();
				synchronized(models) {
					while(models.size() > 0) {
						models.remove(0);
					}
					for(Counter counter : counters) {
						models.add(counter);
					}
					notifyHandlers(MESSAGE_MODEL_UPDATED, 0, 0, null);
				}
			}
		});
	}
	
	private void deleteCounter(final int itemId) {
		workerHandler.post(new Runnable() {
			public void run() {
				CounterDb counter = new CounterDb();
				counter.delete(itemId);
			}
		});
	}
	
	private void changeCount(final int value, final Counter counter) {
		workerHandler.post(new Runnable() {
			public void run() {
				synchronized(counter) {
					int current_count = counter.getCounter();
					counter.setCounter(current_count + value);
					CounterDb db = new CounterDb();
					db.update(counter);
				}
			}
		});
	}
}
