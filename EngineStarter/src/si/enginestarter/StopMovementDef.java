package si.enginestarter;

public class StopMovementDef implements MovementDef {
	private static final String TAG = StopMovementDef.class.getName();
	
	final public MovementType TYPE = MovementType.STOP_MOVEMENT;

	public StopMovementDef() {}
}