package si.android.tapping.controller;

import si.android.tapping.db.CounterDb;
import si.android.tapping.observer.Counter;
import android.os.Handler;
import android.os.HandlerThread;

public class TapState implements ControllerState {
	private HandlerThread workerThread;
	protected TapController controller;
	protected Counter model;
	private Handler workerHandler;
	
	public TapState(TapController controller) {
		this.controller = controller;
		this.model = this.controller.getModel();
		this.workerThread = new HandlerThread("Unlock save thread");
		this.workerThread.start();
		this.workerHandler = new Handler(workerThread.getLooper());
	}
	
	public void dispose() {
		workerThread.getLooper().quit();
	}
	
	public boolean handleMessage(int what) {
		return handleMessage(what, null);
	}
	
	public boolean handleMessage(int what, Object data) {
		switch(what) {
		case TapController.MESAGE_SAVE_MODEL:
			saveModel();
			return true;
		case TapController.MESAGE_POPULATE_MODEL_BY_ID:
			populateModel((Integer)data);
			return true;
		case TapController.MESAGE_CREATE_NEW_MODEL:
			createModel();
			return true;
		default:
			return false;
		}
	}
	
	private void saveModel() {
		workerHandler.post(new Runnable(){
			public void run() {
				synchronized(model) {
					CounterDb counter = new CounterDb();
					if(model.getId() > 0) {
						int effected = (int)counter.update(model);
						if(effected < 1) {
							long id = counter.insert(model);
							model.setId((int)id);
						}
					} else {
						long id = counter.insert(model);
						model.setId((int)id);
					}
					controller.notifyHandlers(TapController.MESAGE_SAVE_FINISHED, 0, 0, null);
				}
			}
		});
	}
	
	private void populateModel(final int id) {
		if(id < 0) {
			return;
		}
		workerHandler.post(new Runnable() {
			public void run() {
				synchronized(model) {
					CounterDb db = new CounterDb();
					Counter counter = db.get(id);
					if(counter == null)
						counter = new Counter();
					counter.consume(model);
				}
			}
		});
	}
	
	private void createModel() {
		Counter counter = new Counter();
		model.consume(model);
	}
}
