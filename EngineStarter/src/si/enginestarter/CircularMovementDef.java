package si.enginestarter;


public class CircularMovementDef implements MovementDef {
	private static final String TAG = CircularMovementDef.class.getName();
	
	protected float radius;
	protected float angle;
	final public MovementType TYPE = MovementType.CIRCULAR;

	public CircularMovementDef(final float pRadius, final float pAngle) {
		this.radius = pRadius;
		this.angle = pAngle;
	}
}
