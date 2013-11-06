package si.enginestarter;

import android.util.Log;

public class StopState extends State {
	private static String TAG = StopState.class.getName();

	public StopState(MagneticHero pMagneticHero, GameLevel pGameLevel) {
		super(pMagneticHero, pGameLevel);
		stateName = "StopState";
	}

	@Override
	public void start(State lastState) { 
		magneticHero.currentMovement = new StopMovement(magneticHero);
		magneticHero.currentMovement.movement();
		magneticHero.movingEntity(false);
	}

	@Override
	public void stop(State nextState) {
		magneticHero.currentMovement = null;
	}
	
	@Override
	public void onUpdate(float pSecondsElapsed) {
		if(magneticHero.isBarrelClicked()) {
			magneticHero.setState(new ReadyState(magneticHero, magneticHero.gameLevel));
			magneticHero.setBarrelObstacle(false); 
		}
	}
}