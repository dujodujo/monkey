package si.enginestarter;

import org.andengine.entity.scene.CameraScene;

public abstract class ManagedLayer extends CameraScene {
	public static final float PPS=3000.f;

	protected boolean loaded;
	protected boolean isUnloadOnHidden;

	public ManagedLayer() {
		this(false);
	}
	
	public ManagedLayer(boolean pUnloadOnHidden) {
		this.isUnloadOnHidden = pUnloadOnHidden;
		this.loaded = false;
		this.setBackgroundEnabled(false);
	}
	
	public void onShowManagedLayer() {
		if(!loaded) {
			loaded = true;
			onLoadLayer();
		}
		this.setIgnoreUpdate(false);
		onShowLayer();
	}
	
	public void onHideManagedLayer() {
		this.setIgnoreUpdate(true);
		onHideLayer();
		if(isUnloadOnHidden) {
			onUnloadLayer();
		}
	}
	
	public abstract void onLoadLayer();
	public abstract void onUnloadLayer();
	public abstract void onHideLayer();
	public abstract void onShowLayer();
}