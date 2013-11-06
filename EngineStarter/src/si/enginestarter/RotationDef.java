package si.enginestarter;

public abstract class RotationDef {
	
	public static String TAG = RotationDef.class.getName();
	
	protected int startFrameAnimation;
	protected int endFrameAnimation;
	protected int currentFrameAnimation;
	protected float duration;
	protected float animationDuration;
	protected boolean clockwise;
	protected int pNextTileIndex;
	
	public RotationDef(final int pStartFrameAnimation, final int pEndFrameAnimation, 
		final int pCurrentFrameAnimation, final float pDuration) {
		
		startFrameAnimation = pStartFrameAnimation;
		endFrameAnimation = pEndFrameAnimation;
		currentFrameAnimation = pCurrentFrameAnimation;
		duration = pDuration;
		clockwise = true;
		pNextTileIndex = startFrameAnimation;
		animationDuration = 0f;
	}
	
	public abstract int update(float pSecondsElapsed, int pCurrentTileIndex);
}