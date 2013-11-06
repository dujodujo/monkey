package si.enginestarter;

import android.util.Log;

public class RotationCircularDef extends RotationDef {
	
	public RotationCircularDef(final int pStartFrameAnimation, final int pEndFrameAnimation, 
			final int pCurrentFrameAnimation, final float pDuration) {
		super(pStartFrameAnimation, pEndFrameAnimation, pCurrentFrameAnimation, pDuration);
	}
	
	public int update(float pSecondsElapsed, int pCurrentTileIndex) {
		animationDuration += pSecondsElapsed;
		
		if(animationDuration > duration) {
			if(pNextTileIndex >= endFrameAnimation) {
				pNextTileIndex = currentFrameAnimation = startFrameAnimation;
			} else {
				if(pNextTileIndex == 0)
					pNextTileIndex = currentFrameAnimation++;
				else if(pNextTileIndex % 4 == 0)
					pNextTileIndex = ++currentFrameAnimation;
				else
					pNextTileIndex = currentFrameAnimation++;
			}
			animationDuration = 0;
		}
		return pNextTileIndex;
	}
}