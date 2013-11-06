package si.enginestarter;

import org.andengine.engine.camera.SmoothCamera;

import android.util.Log;

public class GameCamera extends SmoothCamera {
	public static String TAG = GameCamera.class.getName();
	
	private float xStart = 0.f;
	private float yStart = 0.f;
	
	protected MagneticHero hero;
	
	public GameCamera(float x, float y, float w, float h, float velX, float velY, float zoomFactor) {
		super(x, y, w, h, velX, velY, zoomFactor);
	}
	
	public void setStartPosition(final float x, final float y) {
		xStart = x;
		yStart = y;
	}
	
	public void setHero(MagneticHero hero) {
		this.hero = hero;
		this.setChaseEntity(hero.getHeroSprite());
	}
	
	public void moveToMagneticHero() {
		this.setChaseEntity(hero.getHeroSprite());
	}
	
	public void moveToStart() {
		this.setChaseEntity(null);
		this.setCenter(xStart, yStart);
	}

	@Override
	public void setCenter(float pCenterX, float pCenterY) {
		this.mTargetCenterX = pCenterX;
		this.mTargetCenterY = pCenterY;
	}

	@Override
	public void setCenterDirect(float pCenterX, float pCenterY) {
		super.setCenterDirect(pCenterX, pCenterY);
		this.setCenter(pCenterX, pCenterY);
	}

	@Override
	public void onUpdate(float pSecondsElapsed) {
		super.onUpdate(pSecondsElapsed);
		float yCurrentCenter = this.getCenterY();
		float xCurrentCenter = this.getCenterX();
		if(xCurrentCenter != this.mTargetCenterX || yCurrentCenter != this.mTargetCenterY) {
			final float xDifference = (xCurrentCenter - this.mTargetCenterX)/2.f;
			final float yDifference = (yCurrentCenter - this.mTargetCenterY)/2.f;
			super.setCenter(xCurrentCenter + xDifference, yCurrentCenter + yDifference);
		}
		final float targetZoomFactor = this.getTargetZoomFactor();
		this.setZoomFactorDirect(targetZoomFactor);
	}

	public static void setupMenus() {
		Log.d(TAG, "setupMenus");
		
		GameCamera gameCamera = ((GameCamera)ResourceManager.getInstance().getEngine().getCamera());
		gameCamera.setBoundsEnabled(false);
		gameCamera.setChaseEntity(null);
		float x = ResourceManager.getInstance().getCameraWidth()/2;
		float y = ResourceManager.getInstance().getCameraHeight()/2;
		gameCamera.setCenterDirect(x, y);
		gameCamera.setCenter(x, y);
		gameCamera.clearUpdateHandlers();
	}
}