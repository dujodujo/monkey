package si.enginestarter;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;

public abstract class AButton extends Sprite {
	private static final float NORMAL_SCALE = 1.f;
	private static final float LARGE_SCALE = 1.5f;
	private static final float DURATION = 1.f;
	private static final float ALPHA = 1.f;
	private static final float NOALPHA = 0.5f;
	
	protected float normalScale = NORMAL_SCALE;
	protected float largeScale = LARGE_SCALE;
	protected float alpha = ALPHA;
	protected float noAlpha = NOALPHA;
	
	private boolean touched = false;
	private boolean enabled = true;
	private boolean clicked = false;
	private boolean touchStarted = true;
	
	public AButton(float pX, float pY, ITextureRegion pTextureRegion) {
		super(pX, pY, pTextureRegion, ResourceManager.getInstance().getEngine().getVertexBufferObjectManager());
		setWidth(55f);
		setHeight(52f);
	}
	
	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);
		if(!this.touched) {
			if(clicked) {
				onClick();
				clicked = false;
			}
		}
		if(enabled) {
			if(this.getAlpha() != alpha) {
				this.setAlpha(alpha);
			}
		} else {
			if(this.getAlpha() != noAlpha) {
				this.setAlpha(alpha);
			}
		}
	}
	
	public abstract void onClick();
	
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
			if(enabled)
				touched = true;
		} else if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP && touched && touchStarted) {
			touched = false;
			clicked = true;
			touchStarted = false;
		} else if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_MOVE) {
			if(pTouchAreaLocalX > this.getWidth() ||
			   pTouchAreaLocalX < 0.f ||
			   pTouchAreaLocalY > this.getHeight() ||
			   pTouchAreaLocalY < 0.f) {
				if(touched) 
					touched = false;
			} else {
				if(touchStarted && !touched)
					if(enabled)
						touched = true;
			}
		}
		return true;
	}

	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enable) {
		this.enabled = enable;
	}
}