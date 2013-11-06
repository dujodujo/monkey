package si.enginestarter;

public class StopRotationDef extends RotationDef {

	public StopRotationDef(int pStartFrameAnimation, int pEndFrameAnimation, int pCurrentFrameAnimation, float pDuration) {
		super(pStartFrameAnimation, pEndFrameAnimation, pCurrentFrameAnimation, pDuration);
	}

	@Override
	public int update(float pSecondsElapsed, int pCurrentTileIndex) {
		return startFrameAnimation;
	}
}
