package si.enginestarter;

import org.andengine.entity.scene.Scene;

import android.util.Log;

public abstract class ManagedScene extends Scene {
	private static String NAME = ManagedScene.class.getName();
	
	protected float loadingScreenDuration;
	protected float minLoadingScreenDuration;
	protected boolean hasLoadingScreen;
	protected boolean loaded = false;
	
	protected abstract void onLoadScene();
	protected abstract void onUnloadScene();
	protected abstract Scene onLoadingScreenLoadAndShow();
	protected abstract void onLoadingScreenUnloadAndHide();
	protected abstract void onShowScene();
	protected abstract void onHideScene();
	
	public ManagedScene() {
		this(0f, 0f);
		
		Log.d(NAME, NAME);
	}
	
	public ManagedScene(float loadingScreenDuration, float minLoadingScreenDuration) {
		this.loadingScreenDuration = loadingScreenDuration;
		this.minLoadingScreenDuration = minLoadingScreenDuration;
		hasLoadingScreen = this.loadingScreenDuration > 0.f;
		
		Log.d(NAME, NAME);
	}
	
	public void onLoadManagedScene() {
		Log.d(NAME, "onLoadManagedScene");
		
		if(!this.loaded) {
			onLoadScene();
			loaded = true;
			this.setIgnoreUpdate(true);
		}
	}
	
	public void onUnloadManagedScene() {
		if(loaded) {
			loaded = false;
			ResourceManager.getInstance().getEngine().runOnUpdateThread(new Runnable() {

				@Override
				public void run() {
					onUnloadScene();
				}
			});
		}
	}
	
	public void onShowManagedScene() {
		this.setIgnoreUpdate(false);
		onShowScene();
	}

	public void onHideManagedScene() {
		this.setIgnoreUpdate(true);
		onHideScene();
	}
}