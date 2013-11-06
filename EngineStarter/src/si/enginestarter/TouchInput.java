package si.enginestarter;

import org.andengine.input.touch.TouchEvent;

public abstract class TouchInput {
	private boolean isActive = false;
	private int boundingPointerIndex = -1;
	private float xStart = 0.f;
	private float yStart = 0.f;
	private float yPrevious = 0.f;
	private float xPrevious = 0.f;
	
	public boolean onTouch(TouchEvent touchEvent) {
		if(isActive) {
			if(touchEvent.getPointerID() == this.boundingPointerIndex) {	
				if(touchEvent.isActionMove()) {
					onActionMove(touchEvent.getX(), touchEvent.getY(), xPrevious, yPrevious, xStart, yStart);
					xPrevious = touchEvent.getX();
					yPrevious = touchEvent.getY();
				} else {
					isActive = false;
					this.boundingPointerIndex = -1;
					onActionUp(touchEvent.getX(), touchEvent.getY(), xPrevious, yPrevious, xStart, yStart);
					return true;
				}
			} else {
				return false;
			}
		} else {
			if(touchEvent.isActionDown()) {
				isActive = onActionDown(touchEvent.getX(), touchEvent.getY());
				if(isActive) {
					this.boundingPointerIndex = touchEvent.getPointerID();
					this.xStart = touchEvent.getX();
					this.yStart = touchEvent.getY();
					this.xPrevious = touchEvent.getX();
					this.yPrevious = touchEvent.getY();
				}
			}
		}
		return isActive;
	}
	
	public boolean canHandleEvent(TouchEvent touchEvent) {
		if(!isActive || this.boundingPointerIndex == touchEvent.getPointerID())
			return true;
		return false;
	}
	
	protected abstract boolean onActionDown(float x, float y);
	protected abstract void onActionMove(float x, float y, float xPrevious, float yPrevious, float xStart, float yStart);
	protected abstract void onActionUp(float x, float y, float xPrevious, float yPrevious, float xStart, float yStart);
}
