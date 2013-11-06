package si.enginestarter;

import org.andengine.entity.Entity;
import org.andengine.entity.sprite.Sprite;

import android.opengl.GLES20;
import android.util.Log;

public class MagneticOrb extends Entity {
	private static String TAG = MagneticOrb.class.getName();
	
	private static final float MIN_ALPHA = 0.2f;
	private static final float MAX_ALPHA = 1.f;
	
	private static float TRANSITION_DURATION;
	private static float FADE;
	private static float ROTATION_SPEED;
	
	private GameLevel gameLevel;
	
	private Sprite magneticOrb;
	private Sprite magneticFlaringOrb;
	
	private float fade;
	private boolean active;
	
	public MagneticOrb(final float pX, final float pY, final float duration, 
			final float f, final float rotationSpeed, GameLevel pGameLevel) {
		Log.d(TAG, "MagneticOrb");
		this.gameLevel = pGameLevel;
		
		loadSprites(pX, pY);
		setOrbProperties(magneticOrb);
		setOrbProperties(magneticFlaringOrb);
		
		active = true;
		
		TRANSITION_DURATION = duration;
		FADE = f;
		ROTATION_SPEED = rotationSpeed;
		
		gameLevel.attachChild(this);
	}
	
	private void loadSprites(float pX, float pY) {
		this.magneticOrb = new Sprite(pX, pY, ResourceManager.magneticOrbTex,
			ResourceManager.getInstance().getActivity().getVertexBufferObjectManager());
		this.magneticFlaringOrb = new Sprite(pX, pY, ResourceManager.magneticFlaringOrbTex,
			ResourceManager.getInstance().getActivity().getVertexBufferObjectManager());
	}
	
	private void setOrbProperties(Sprite sprite) {
		sprite.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE);
		sprite.setBlendingEnabled(true);
		sprite.setAlpha(MIN_ALPHA);
		attachChild(sprite);
	}
	
	private void fadeIn(final float pSecondsElapsed) {
		if(magneticOrb.getAlpha() < MAX_ALPHA) {
			magneticOrb.setAlpha(magneticOrb.getAlpha() + 0.008f); 
			magneticFlaringOrb.setAlpha(magneticOrb.getAlpha());
		}
	}
	
	private void fadeOut(final float pSecondsElapsed) {
		if(magneticOrb.getAlpha() > MIN_ALPHA) {
			magneticOrb.setAlpha(magneticOrb.getAlpha() - 0.008f);
			magneticFlaringOrb.setAlpha(magneticOrb.getAlpha());
		}
	}
	
	private void setOrbRotation(float pSecondsElapsed) {
		float rotation = magneticOrb.getRotation();
		magneticOrb.setRotation(rotation + pSecondsElapsed * ROTATION_SPEED);
		magneticFlaringOrb.setRotation(rotation - pSecondsElapsed * ROTATION_SPEED);
	}
	
	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		if(active) {
			if(fade < FADE) {
				fadeIn(pSecondsElapsed);
				setOrbRotation(pSecondsElapsed);
				fade += pSecondsElapsed;
			} else if(fade < FADE + TRANSITION_DURATION) {
				setOrbRotation(pSecondsElapsed);
				fade += pSecondsElapsed;
			} else {
				active = false;
			}
		} else {
			if(FADE < fade) {
				setOrbRotation(pSecondsElapsed);
				fade -= pSecondsElapsed;
			} else if(FADE - TRANSITION_DURATION < fade && fade < FADE) {
				fadeOut(pSecondsElapsed);
				setOrbRotation(pSecondsElapsed);
				fade -= pSecondsElapsed;
			} else {
				active = true;
			}
		}
	}
}