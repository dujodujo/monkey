package si.enginestarter;

public abstract class ManagedMenuScene extends ManagedScene {

	public ManagedMenuScene(float loadingScreenDuration, float minLoadingScreenDuration) {
		super(loadingScreenDuration, minLoadingScreenDuration);
	}

	@Override
	public void onUnloadManagedScene() {
		if(this.loaded) {
			this.onUnloadScene();
		}
	}
}
