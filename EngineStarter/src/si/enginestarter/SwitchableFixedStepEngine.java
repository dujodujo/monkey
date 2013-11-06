package si.enginestarter;

import org.andengine.engine.Engine;
import org.andengine.engine.options.EngineOptions;
import org.andengine.util.time.TimeConstants;

public class SwitchableFixedStepEngine extends Engine {
	private final long stepLength;
	private boolean fixedStepEnabled = false;
	private long totalElapsedTime;
	private int stepsPerSecond;
	
	public SwitchableFixedStepEngine(EngineOptions pEngineOptions, boolean fixedStepEnabled, int stepsPerSecond) {
		super(pEngineOptions);
		
		this.stepsPerSecond = stepsPerSecond;
		this.stepLength = TimeConstants.NANOSECONDS_PER_SECOND / stepsPerSecond;
		this.fixedStepEnabled = fixedStepEnabled;
		this.totalElapsedTime = 0;
	}
		
	public void enableFixedStep() {
		this.fixedStepEnabled = true;
	}
	
	public void disableFixedStep() {
		this.fixedStepEnabled = false;
	}
	
	public int getStepsPerSecond() {
		return stepsPerSecond;
	}

	@Override
	public void onUpdate(long pNanosecondsElapsed) throws InterruptedException {
		if(fixedStepEnabled) {
			totalElapsedTime += pNanosecondsElapsed;
			while(this.totalElapsedTime > this.stepLength) {
				super.onUpdate(stepLength);
				totalElapsedTime -= this.stepLength;
			}
		} else {
			super.onUpdate(pNanosecondsElapsed);
		}
	}
}
