package si.enginestarter;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.view.RenderSurfaceView;
import org.andengine.ui.activity.BaseGameActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View.MeasureSpec;

public class GameActivity extends BaseGameActivity {
	private static String NAME = GameActivity.class.getName();
	public Context context;
	public static int WIDTH = 800;
	public static int HEIGHT = 480;
	
	public static float DESIGN_WINDOW_WIDTH = 1280.f;
	public static float DESIGN_WINDOW_HEIGHT = 800.f;
	public static float MAX_WIDTH = 1600.f;
	public static float MIN_WIDTH = 800.f;
	public static float MAX_HEIGHT = 480.f;
	public static float MIN_HEIGHT = 960.f;
	
	public static final String SHARED_PREFS_MAIN = "Settings";
	public static final String SHARED_PREFS_LEVEL_STARS = "level.stars";
	public static final String SHARED_PREFS_LEVEL_HIGHSCORE = "level.highscore";
	public static final String SHARED_PREFS_LEVELS_COMPLETE = "levels.complete";
	public static final String SHARED_PREFS_ACTIVITY_START_COUNT = "count";
	public static final String SHARED_PREFS_RATING_SUCCESS = "rating";
	public static int applicationStarted = 0;
		
	private GameCamera camera;
		
	public Context getContext() {
		return context;
	}
	
	@Override
	public Engine onCreateEngine(EngineOptions pEngineOptions) {
		return new SwitchableFixedStepEngine(pEngineOptions, false, 60);
	}

	@Override
	public EngineOptions onCreateEngineOptions() {
		FillResolutionPolicy EngineFillResolutionPolicy = new FillResolutionPolicy() {

			@Override
			public void onMeasure(RenderSurfaceView pRenderSurfaceView, 
					int pWidthMeasureSpec, int pHeightMeasureSpec) {
				super.onMeasure(pRenderSurfaceView, pWidthMeasureSpec, pHeightMeasureSpec);
				final int measuredWidth = MeasureSpec.getSize(pWidthMeasureSpec);
				final int measuredHeight = MeasureSpec.getSize(pHeightMeasureSpec);
				
				float windowWidth = measuredWidth / getResources().getDisplayMetrics().xdpi;
				float windowHeight = measuredHeight / getResources().getDisplayMetrics().ydpi;
				
				float scaledWidth = DESIGN_WINDOW_WIDTH * (windowWidth / DESIGN_WINDOW_WIDTH);
				float boundScaledWidth = Math.round(Math.max(Math.min(scaledWidth, MAX_WIDTH), MIN_WIDTH));
				
				float boundScaledHeight = boundScaledWidth * windowHeight / windowWidth;

				if(boundScaledHeight > MAX_HEIGHT) {
					float boundAdjustmentRatio = MAX_HEIGHT / boundScaledHeight;
					boundScaledWidth *= boundAdjustmentRatio;
					boundScaledHeight *= boundAdjustmentRatio;
				} else if(boundScaledHeight < MIN_HEIGHT) {
					float boundAdjustmentRatio = MIN_HEIGHT / boundScaledHeight;
					boundScaledWidth *= boundAdjustmentRatio;
					boundScaledHeight *= boundAdjustmentRatio;
				}
				GameActivity.this.camera.set(0f, 0f, boundScaledWidth, boundScaledHeight);
			}
		};
				
		camera = new GameCamera(0.f, 0.f, 320, 240, 4000, 3000, 0.5f);
		ResourceManager.getInstance().setCamera(camera);
		
		EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, 
			EngineFillResolutionPolicy, camera);
		
		engineOptions.getRenderOptions().setDithering(false);
		engineOptions.getRenderOptions().setMultiSampling(false);
		engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
		return engineOptions;
	}

	@Override
	public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws Exception {
		if(this.getApplicationContext() != null) {
			Log.d(NAME, "context not null");
		}
		ResourceManager.setupResources(this, (SwitchableFixedStepEngine)this.getEngine(), this.getApplicationContext());
		Log.d(NAME, "setupResources End");
		
		int appStarted = getIntFromSharedPreferences(SHARED_PREFS_ACTIVITY_START_COUNT) + 1;
		setIntToSharedPreferences(SHARED_PREFS_ACTIVITY_START_COUNT, appStarted);
		
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws Exception {
		Log.d(NAME, "onCreateScene");
		ResourceManager.loadMenuResources();
		
		SceneManager.getInstance().showScene(new SplashScreen());
		pOnCreateSceneCallback.onCreateSceneFinished(mEngine.getScene());
	}
	
	public static int setIntToSharedPreferences(String stringValue, int intValue) {
		SharedPreferences sharedPreferences = 
			ResourceManager.getInstance().getActivity().getSharedPreferences(SHARED_PREFS_MAIN, 0);
		sharedPreferences.edit().putInt(stringValue, intValue).commit();
		return intValue;
	}
	
	public static boolean setBooleanToSharedPreferences(String stringValue, boolean booleanValue) {
		SharedPreferences sharedPreferences = 
			ResourceManager.getInstance().getActivity().getSharedPreferences(SHARED_PREFS_MAIN, 0);
		sharedPreferences.edit().putBoolean(stringValue, booleanValue).commit();
		return booleanValue;
	}
	
	public static int getIntFromSharedPreferences(String str) {
		SharedPreferences sharedPreferences = 
			ResourceManager.getInstance().getActivity().getSharedPreferences(SHARED_PREFS_MAIN, 0);
		return sharedPreferences.getInt(str, 0);
	}
	
	public static boolean getBooleanFromSharedPreferences(String string) {
		SharedPreferences sharedPreferences = 
			ResourceManager.getInstance().getActivity().getSharedPreferences(SHARED_PREFS_MAIN, 0);
		return sharedPreferences.getBoolean(string, false);
	}
	
	public static int getLevelStars(final int levelStars) {
		return getIntFromSharedPreferences(SHARED_PREFS_LEVEL_STARS + String.valueOf(levelStars));
	}

	@Override
	public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
		pOnPopulateSceneCallback.onPopulateSceneFinished();	
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	
	@Override
	protected synchronized void onResume() {
		super.onResume();
		System.gc();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		System.exit(0);
	}
}