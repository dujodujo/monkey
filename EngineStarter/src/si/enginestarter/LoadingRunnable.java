package si.enginestarter;

public abstract class LoadingRunnable implements Runnable {
	private String loadingText;
	private ManagedGameScene gameScene;
	
	public LoadingRunnable(String loadingText, ManagedGameScene gameScene) {
		this.loadingText = loadingText;
		this.gameScene = gameScene;
	}
	
	@Override
	public void run() {
		if(loadingText.trim().length() != 0) {
			if(gameScene != null) {
				if(gameScene.getLoadingText() != null){
					if(loadingText != null) {
						gameScene.getLoadingText().setText(gameScene.getLoadingText() + 
							"\n" + loadingText);
					}
				}
			}
		}
		onLoad();
	}
	
	public abstract void onLoad();
}