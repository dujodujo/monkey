package si.enginestarter;

import org.andengine.entity.primitive.Vector2;

public class ReadyState extends MovementState {

	public ReadyState(MagneticHero pMagneticHero, GameLevel pGameLevel) {
		super(pMagneticHero, pGameLevel);
		stateName = "ReadyState";
	}

	@Override
	public void start(State lastState) { 
		super.start(lastState); 
	}

	@Override
	public void stop(State nextState) {
		super.stop(nextState);
	}

	@Override
	public void onUpdate(float pSecondsElapsed) {
		Vector2 center = magneticHero.getBarrel().getCenterXY();
		if(!magneticHero.rectangle.contains(center.x, center.y)) {
			magneticHero.setBarrelObstacle(true);
			magneticHero.setState(new MovementState(magneticHero, gameLevel));
			magneticHero.setHeroVisible();
		}
		magneticHero.setBarrelObstacle(false);
	}
}