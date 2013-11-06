package si.enginestarter;

public class HarmonicMovementDef implements MovementDef {
	private static final String TAG = HarmonicMovementDef.class.getName();

	protected float amplitude;
	protected float angle;
	protected float xmin;
	protected float xmax;
	
	final public MovementType TYPE = MovementType.HARMONIC;

	public HarmonicMovementDef(final float pAmplitude, final float pAngle, 
			final float pMin, final float pMax) {
		this.amplitude = pAmplitude;
		this.angle = pAngle;
		this.xmax = pMax;
		this.xmin = pMin;
	}
}
