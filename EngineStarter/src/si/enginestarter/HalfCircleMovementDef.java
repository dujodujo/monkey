package si.enginestarter;

public class HalfCircleMovementDef implements MovementDef {
	private static final String TAG = HalfCircleMovementDef.class.getName();
	
	public float currentAngle;
	public float nextAngle;
	public float minAngle;
	public float maxAngle;
	public float radius;
	
	final public MovementType TYPE = MovementType.CIRCULAR;

	public HalfCircleMovementDef(final float pMinAngle, final float pMaxAngle,
		final float pNextAngle, final float pCurrentAngle, final float pRadius) {
		
		this.currentAngle = pCurrentAngle;
		this.nextAngle = pNextAngle;
		this.minAngle = pMinAngle;
		this.maxAngle = pMaxAngle;
		this.radius = pRadius;
	}
}
