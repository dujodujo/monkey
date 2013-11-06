package si.enginestarter;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.scene.Scene;

import android.util.Log;

public class SceneManager extends Object {
	private static String TAG = SceneManager.class.getName();
	
	protected static SceneManager instance;
	protected ManagedScene currentScene;
	protected ManagedScene nextScene;
	protected Engine engine;
	protected boolean loadingScreenRegistered;
	protected boolean cameraHud;
	boolean layerShown;

	protected int numFramesPassed;
	
	protected Scene scene;
	protected Scene placeholderScene;
	
	ManagedLayer currentLayer;
	
	public static SceneManager getInstance() {
		if(instance == null)
			instance = new SceneManager();
		return instance;
	}
	
	public SceneManager() {
		engine = ResourceManager.getInstance().getEngine();
		numFramesPassed = -1;
		loadingScreenRegistered = false;
	}
	
	public Scene getScene() { return scene; }
	
	public void showScene(ManagedScene managedScene) {
		Log.d(TAG, "showSceneBegin");
		
		if(managedScene.hasLoadingScreen) {
			managedScene.setChildScene(managedScene.onLoadingScreenLoadAndShow(), true, true, true);
			if(loadingScreenRegistered) {
				numFramesPassed--;
				nextScene.clearChildScene();
				nextScene.onLoadingScreenUnloadAndHide();
			} else {
				engine.registerUpdateHandler(loadingSceneHandler);
				loadingScreenRegistered = true;
			}
			nextScene = managedScene;
			engine.setScene(managedScene);
			return;
		}
		nextScene = managedScene;
		engine.setScene(nextScene);

		if(currentScene != null) {
			currentScene.onUnloadManagedScene();
			currentScene.onHideManagedScene();
		}
		nextScene.onLoadManagedScene();
		nextScene.onShowManagedScene();
		currentScene = nextScene;
	}

	private IUpdateHandler loadingSceneHandler = new IUpdateHandler() {

		@Override
		public void onUpdate(float pSecondsElapsed) {

			nextScene.loadingScreenDuration += pSecondsElapsed;
			numFramesPassed++;
			if(numFramesPassed == 1) {
				if(currentScene != null) {
					currentScene.onHideManagedScene();
					currentScene.onUnloadManagedScene();
				}
				nextScene.onLoadManagedScene();
			}
			if(numFramesPassed > 1 && 
			   nextScene.loadingScreenDuration >= nextScene.minLoadingScreenDuration &&
			   nextScene.loaded) {

				nextScene.clearChildScene();
				nextScene.onLoadingScreenUnloadAndHide();
				nextScene.onShowManagedScene();
				
				currentScene = nextScene;
				nextScene.loadingScreenDuration = 0.f;
				numFramesPassed = -1;
				ResourceManager.getInstance().getEngine().unregisterUpdateHandler(this);
				loadingScreenRegistered = false;
			}
		}

		@Override
		public void reset() {}
	};
	
	public void showMainMenu() { showScene(MainMenu.getInstance()); }
	
	
	public void showLayer(final ManagedLayer pLayer, final boolean pSuspendSceneDrawing, 
			final boolean pSuspendSceneUpdates, final boolean pSuspendSceneTouchEvents) {
		if(ResourceManager.engine.getCamera().hasHUD()){
			cameraHud = true;
		} else {
			cameraHud = false;
			HUD placeholderHud = new HUD();
			ResourceManager.engine.getCamera().setHUD(placeholderHud);
		}
		if(pSuspendSceneDrawing || pSuspendSceneUpdates || pSuspendSceneTouchEvents) {
			ResourceManager.engine.getCamera().getHUD().setChildScene(pLayer, pSuspendSceneDrawing, pSuspendSceneUpdates, pSuspendSceneTouchEvents);
			ResourceManager.engine.getCamera().getHUD().setOnSceneTouchListenerBindingOnActionDownEnabled(true);
			if(placeholderScene == null) {
				placeholderScene = new Scene();
				placeholderScene.setBackgroundEnabled(false);
			}
			currentScene.setChildScene(placeholderScene, pSuspendSceneDrawing,
				pSuspendSceneUpdates, pSuspendSceneTouchEvents);
		} else {
			ResourceManager.engine.getCamera().getHUD().setChildScene(pLayer);
		}
		pLayer.setCamera(ResourceManager.engine.getCamera());
		pLayer.onShowManagedLayer();
		
		layerShown = true;
		currentLayer = pLayer;
	}
	
	public void hideLayer() {
		if(layerShown) {
			ResourceManager.engine.getCamera().getHUD().clearChildScene();
			if(currentScene.hasChildScene())
				if(currentScene.getChildScene() == placeholderScene)
					currentScene.clearChildScene();
			if(!cameraHud)
				ResourceManager.engine.getCamera().setHUD(null);
			layerShown = false;
			currentLayer = null;
		}
	}
}