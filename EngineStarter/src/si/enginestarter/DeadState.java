package si.enginestarter;

public class DeadState extends State {

	public DeadState(MagneticHero pMagneticHero, GameLevel pGameLevel) {
		super(pMagneticHero, pGameLevel);
		stateName = "MovementState";
	}
	
	@Override
	public void start(State lastState) {
	}
	
	@Override
	public void stop(State nextState) { 
	}

	@Override
	public void onUpdate(float pSecondsElapsed) {
	}
}
