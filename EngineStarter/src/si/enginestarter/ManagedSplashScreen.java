package si.enginestarter;

import org.andengine.entity.scene.Scene;

import android.util.Log;

public abstract class ManagedSplashScreen extends ManagedScene {
	private static String NAME = ManagedSplashScreen.class.getName();
	
	protected ManagedSplashScreen managedSplashScreen = this;
	
	public ManagedSplashScreen() {
		this(0f, 0f);
		Log.d(NAME, NAME);
	}
	
	public ManagedSplashScreen(float loadingScreenDuration, float minLoadingScreenDuration) {
		super(loadingScreenDuration, minLoadingScreenDuration);
		this.setOnSceneTouchListenerBindingOnActionDownEnabled(true);
		this.setTouchAreaBindingOnActionDownEnabled(true);
		this.setTouchAreaBindingOnActionMoveEnabled(true);
		GameCamera.setupMenus();
		this.setPosition(0, 0);
		this.dispose();
		
		Log.d(NAME, NAME);
	}
	
	@Override
	public void onUnloadScene() {
		Log.d(NAME, "onUnloadScene");
		ResourceManager.getInstance().getEngine().runOnUpdateThread(new Runnable() {

			@Override
			public void run() {
				managedSplashScreen.detachChildren();
				for(int i = 0; i < managedSplashScreen.getChildCount(); i++) {
					managedSplashScreen.getChildByIndex(i).dispose();
				}
				managedSplashScreen.clearEntityModifiers();
				managedSplashScreen.clearTouchAreas();
				managedSplashScreen.clearUpdateHandlers();
				managedSplashScreen.unloadSplashTextures();
			}
		});
	}
	
	@Override
	public void onHideScene() {
		Log.d(NAME, "onHideScene");
	}
	
	public abstract void unloadSplashTextures();
	
	@Override
	public Scene onLoadingScreenLoadAndShow() { return null; }
	
	@Override
	public void onLoadingScreenUnloadAndHide() {}
	
	@Override
	protected void onShowScene() {}
}