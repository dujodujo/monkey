package si.enginestarter;

import android.util.Log;

public class MovementState extends State {
	public static String TAG = MovementState.class.getName();
	
	public MovementState(MagneticHero pMagneticHero, GameLevel pGameLevel) {
		super(pMagneticHero, pGameLevel);
		stateName = "MovementState";
	}
	
	@Override
	public void start(State lastState) {
		magneticHero.currentMovement = new LinearMovement(magneticHero);
		magneticHero.currentMovement.movement();
		magneticHero.movingEntity(true);
	}
	
	@Override
	public void stop(State nextState) { 
		magneticHero.currentMovement = null;
	}

	@Override
	public void onUpdate(float pSecondsElapsed) {
		if(magneticHero.movement && magneticHero.checkCollision()) {
			magneticHero.setState(new StopState(magneticHero, magneticHero.gameLevel));
		}
	}
}
