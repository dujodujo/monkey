package si.enginestarter;

import java.util.ArrayList;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.util.HorizontalAlign;

import android.util.Log;

public abstract class ManagedGameScene extends ManagedScene {
	protected static String NAME = ManagedGameScene.class.getName();
	
	private static final float LOADING_TEXT_RED = 1.f;
	private static final float LOADING_TEXT_GREEN = 1.f;
	private static final float LOADING_TEXT_BLUE = 1.f;
	private static final Font LOADING_TEXT_FONT = ResourceManager.getInstance().getFont();
	private static final int MAX_LOADING_TEXT = 300;
	
	private static final float LOADING_PROGRESS_BAR_RED = 1.f;
	private static final float LOADING_PROGRESS_BAR_GREEN = 1.f;
	private static final float LOADING_PROGRESS_BAR_BLUE = 1.f;
	private static final float LOADING_PROGRESS_BAR_WIDTH = 10.f;
	private static final float LOADING_PROGRESS_BAR_HEIGHT = 10.f;
	
	private static final float LOADING_SCREEN_BACKGROUND_RED = 0.1f;
	private static final float LOADING_SCREEN_BACKGROUND_GREEN = 0.1f;
	private static final float LOADING_SCREEN_BACKGROUND_BLUE = 0.1f;
	
	private Text loadingText;
	private Rectangle loadingRectangle;
	private Scene loadingScene;
	private int loadingStepsTotal = 0;
	
	private static float endDurationDate = 10.f;	
	
	public Text getLoadingText() { return loadingText; }
	
	public Rectangle getLoadingRectangle() { return loadingRectangle; }
	
	public Scene getLoadingScene() { return loadingScene; }
	
	ArrayList<LoadingRunnable> loadingSteps = new ArrayList<LoadingRunnable>();
	
	public IUpdateHandler loadingStepsHandler = new IUpdateHandler() {
		
		@Override
		public void onUpdate(float pSecondsElapsed) {
			if(!ManagedGameScene.this.loadingSteps.isEmpty()) {
				ManagedGameScene.this.loadingSteps.get(0).run();
				ManagedGameScene.this.loadingSteps.remove(0);
				float len = ManagedGameScene.this.loadingSteps.size() / ManagedGameScene.this.loadingStepsTotal;
				ManagedGameScene.this.loadingRectangle.setWidth(ResourceManager.getInstance().getCameraWidth() * (1f - len));
				if(ManagedGameScene.this.loadingSteps.isEmpty()) {
					ManagedGameScene.this.loaded = true;
					ResourceManager.getInstance().getEngine().unregisterUpdateHandler(this);
					return;
				}
				ManagedGameScene.this.loaded = false;
			}
		}
		
		@Override
		public void reset() {}	
	};
	
	public ManagedGameScene() {
		this(0f, 0f);
	}
	
	public ManagedGameScene(float loadingScreenTimeShown, float minLoadingScreenTimeShown) {
		super(loadingScreenTimeShown, minLoadingScreenTimeShown);
		Log.d(NAME, "ManagedGameScene");
		this.setOnSceneTouchListenerBindingOnActionDownEnabled(true);
		this.setTouchAreaBindingOnActionDownEnabled(true);
		this.setTouchAreaBindingOnActionMoveEnabled(true);
	}

	public void addLoadingStep(final LoadingRunnable pLoadingStep) {
		this.loadingSteps.add(pLoadingStep);
		this.loadingStepsTotal++;
	}
	
	public abstract void onLoadLevel();
	
	@Override
	public void onLoadScene() {
		Log.d(NAME, "onLoadScene");
		ResourceManager.loadGameResources();
		ResourceManager.getInstance().getEngine().registerUpdateHandler(loadingStepsHandler);
		onLoadLevel();
	}

	@Override
	public Scene onLoadingScreenLoadAndShow() {
		Log.d(NAME, "ManagedGameScene:onLoadingScreenLoadAndShowBegin");
		
		ResourceManager.getInstance().getEngine().disableFixedStep();
		this.loadingScene = new Scene();
		this.loadingScene.setBackgroundEnabled(true);
		this.loadingScene.setBackground(new Background(LOADING_SCREEN_BACKGROUND_RED,
			LOADING_SCREEN_BACKGROUND_RED, LOADING_SCREEN_BACKGROUND_RED));		
		
		this.loadingRectangle = new Rectangle(0.f, 0.f, LOADING_PROGRESS_BAR_HEIGHT, LOADING_PROGRESS_BAR_WIDTH,
			ResourceManager.getInstance().getEngine().getVertexBufferObjectManager());
		this.loadingRectangle.setColor(LOADING_PROGRESS_BAR_RED, LOADING_PROGRESS_BAR_GREEN, LOADING_PROGRESS_BAR_BLUE);
		this.loadingScene.attachChild(loadingRectangle);
		return this.loadingScene;
	}

	@Override
	public void onLoadingScreenUnloadAndHide() {
		ResourceManager.getInstance().getActivity().runOnUpdateThread(new Runnable() {

			@Override
			public void run() {
				ManagedGameScene.this.loadingRectangle.detachSelf();
				ManagedGameScene.this.loadingRectangle.dispose();
				ManagedGameScene.this.loadingScene.dispose();
				
				ManagedGameScene.this.loadingRectangle = null;
				ManagedGameScene.this.loadingScene = null;
			}
		});
	}

	@Override
	public void onUnloadScene() {
		ResourceManager.getInstance().getEngine().runOnUpdateThread(new Runnable() {

			@Override
			public void run() {
				ManagedGameScene.this.detachChildren();
				ManagedGameScene.this.clearEntityModifiers();
				ManagedGameScene.this.clearTouchAreas();
				ManagedGameScene.this.clearUpdateHandlers();
			}
		});
	}

	@Override
	public void onShowScene() {}

	@Override
	public void onHideScene() {}
}