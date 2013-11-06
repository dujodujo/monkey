package si.enginestarter;

public class RotationClockwiseDef extends RotationDef {
	
	public RotationClockwiseDef(final int pStartFrameAnimation, final int pEndFrameAnimation, 
			final int pCurrentFrameAnimation, final float pDuration) {
		super(pStartFrameAnimation, pEndFrameAnimation, pCurrentFrameAnimation, pDuration);
		clockwise = false;
	}
	
	public int update(float pSecondsElapsed, int pCurrentTileIndex) {
		animationDuration += pSecondsElapsed;
		
		if(animationDuration > duration) {
			if(clockwise) {
				if(pCurrentTileIndex == endFrameAnimation) {
					clockwise = false;
				} else {
					pNextTileIndex = ++currentFrameAnimation;
				}
			} else {
				if(pCurrentTileIndex == startFrameAnimation) {
					clockwise = true;
				} else {
					pNextTileIndex = --currentFrameAnimation;
				}
			}
			animationDuration = 0;
		}
		return pNextTileIndex;
	}
}
