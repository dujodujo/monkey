package si.android.tapping.controller;

public class LockedState extends TapState {

	public LockedState(TapController controller) {
		super(controller);
	}
	
	@Override
	public boolean handleMessage(int what) {
		switch(what) {
		case TapController.MESAGE_COUNT_UP:
		case TapController.MESAGE_COUNT_DOWN:
		case TapController.MESAGE_RESET_COUNT:
			return true;
		default:
			return super.handleMessage(what);
		}
	}

	@Override
	public boolean handleMessage(int what, Object data) {
		switch(what) {
			case TapController.MESAGE_UPDATE_LABEL:
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
		controller.setMessageState(new UnlockedState(controller));
	}
}
