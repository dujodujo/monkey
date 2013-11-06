package si.enginestarter;

import org.andengine.engine.handler.IUpdateHandler;

public class State implements IUpdateHandler {
	private static String TAG = State.class.getName();
	
	protected String stateName;
	protected GameLevel gameLevel;
	protected MagneticHero magneticHero;
	
	public State(MagneticHero pMagneticHero, GameLevel pGameLevel) { 
		magneticHero = pMagneticHero;
		gameLevel = pGameLevel;
		stateName = "State";
	}
	
	public void start(State lastState) {}
	
	public void stop(State nextState) {}
	
	protected void notifyMovementStart() {}
	
	protected void notifyMovementStop() {}
	
	public boolean isCurrentState() { return magneticHero.state == this; }
	
	public String getStateName() { return stateName; }
	
	@Override public void onUpdate(float pSecondsElapsed) {}

	@Override public void reset() {}
}