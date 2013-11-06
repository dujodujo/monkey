package si.enginestarter;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

public abstract class ToggleButton extends TiledSprite {
	private static final float NORMAL_SCALE = 1.f;
	private static final float GROW_SCALE = 1.5f;
	private static final float DURATION = 1.f;
	
	private float normalScale = NORMAL_SCALE;
	private float growScale = GROW_SCALE;
	
	private boolean isTouched = false;
	private boolean isClicked = false;
	private boolean touchStarted = false;
	
	private boolean isState;
	
	public abstract void onClick();
	public abstract boolean checkState();
	
	public ToggleButton(float pX, float pY, ITiledTextureRegion pTiledTextureRegion, final boolean pCurrentState) {
		super(pX, pY, pTiledTextureRegion, 
			ResourceManager.getInstance().getActivity().getVertexBufferObjectManager());
		
		if(isState)
			this.setCurrentTileIndex(0);
		else
			this.setCurrentTileIndex(1);
	}
	
	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);
		if(this.isTouched) {
			this.registerEntityModifier(new ScaleModifier(DURATION, normalScale, growScale) {

				@Override
				protected void onModifierFinished(IEntity pItem) {
					super.onModifierFinished(pItem);
				}
			});
		} else if(!isTouched) {
			this.registerEntityModifier(new ScaleModifier(DURATION, normalScale, growScale) {

				@Override
				protected void onModifierFinished(IEntity pItem) {
					super.onModifierFinished(pItem);
					if(isClicked) {
						onClick();
						isClicked = false;
					}
				}
			});
		}
		
		if(isState != this.checkState()) {
			isState = checkState();
			if(isState)
				this.setCurrentTileIndex(0);
			else
				this.setCurrentTileIndex(1);
		}
	}
	
	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
		
		if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
			if(pTouchAreaLocalX > this.getWidth() ||
			   pTouchAreaLocalX < 0.f ||
			   pTouchAreaLocalY > this.getHeight() ||
			   pTouchAreaLocalY < 0.f) {
				touchStarted = false;
			} else {
				touchStarted = true;
			}
		} else if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP && isTouched && touchStarted) {
			isTouched = false;
			isClicked = true;
			touchStarted = false;
		} else if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_MOVE) {
			if(touchStarted) {
				if(pTouchAreaLocalX > this.getWidth() ||
				   pTouchAreaLocalX < 0.f ||
				   pTouchAreaLocalY > this.getHeight() ||
				   pTouchAreaLocalY < 0.f) {
					if(isTouched) 
						isTouched = false;
				} else {
					if(!isTouched)
						isTouched = true;
				}
			}
		}
		return true;
	}
}
