package si.enginestarter;

import org.andengine.entity.Entity;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import android.util.Log;

public class MainMenu extends ManagedMenuScene {
	private static String NAME = MainMenu.class.getName();
	
	protected Entity menuScreen;
	protected Entity levelSelectScreen;
	protected Sprite menuBackground;
	
	private LevelSelector levelSelector;
	
	private float cameraWidth;
	private float cameraHeight;
	
	private static MainMenu instance;
	private static float LOADING_SCREEN_DURATION = 0.001f;
	private static float MIN_LOADING_SCREEN_DURATION = 1.f;
	
 	public static MainMenu getInstance() {
		if(instance == null)
			instance = new MainMenu();
		return instance;
	}
	
	public MainMenu() {
		super(LOADING_SCREEN_DURATION, MIN_LOADING_SCREEN_DURATION);
		this.setTouchAreaBindingOnActionDownEnabled(true);
		this.setTouchAreaBindingOnActionMoveEnabled(true);
		
		cameraWidth = ResourceManager.getInstance().getCameraWidth();
		cameraHeight = ResourceManager.getInstance().getCameraHeight();
	}
	
	@Override 
	public Scene onLoadingScreenLoadAndShow() {
		Log.d(NAME, "onLoadingScreenLoadAndShow");
		
		ResourceManager.loadMenuResources();
		GameCamera.setupMenus();
		
		Scene menuLoadingScene = new Scene();
		this.menuBackground = new Sprite(0.f, 0.f, ResourceManager.menuBackgroundTex,
			ResourceManager.getInstance().getActivity().getVertexBufferObjectManager());

		this.menuBackground.setWidth(cameraWidth);
		this.menuBackground.setHeight(cameraHeight);
		this.menuBackground.setPosition(0.f, 0.f);
		menuLoadingScene.attachChild(menuBackground);
		return menuLoadingScene;
	}

	@Override 
	public void onLoadingScreenUnloadAndHide() {
		//menuBackground.detachSelf();
		Log.d(NAME, "onLoadingScreenUnloadAndHide");
	}

	@Override public void onUnloadScene() {
		Log.d(NAME, "onUnloadScene");
	}

	@Override public void onHideScene() {
		Log.d(NAME, "onHideScene");
	}

	@Override 
	public void onLoadScene() {
		Log.d(NAME, "onLoadScene");
		
		ResourceManager.loadGameResources();
		
		menuScreen = new Entity(0f, cameraHeight/6f) {
			boolean hasloaded = false;
			
			@Override
			protected void onManagedUpdate(final float pSecondsElapsed) {
				super.onManagedUpdate(pSecondsElapsed);
				if(!this.hasloaded) {
					this.hasloaded = true;
				}
			}
		};
		
		TiledTextureRegion mainMenuButtons = ResourceManager.menuMainButtonTTex;
		
		AButton play = new AButton(100, 100, mainMenuButtons.getTextureRegion(1)) {
			@Override
			public void onClick() {}
		};
		menuScreen.attachChild(play);
		registerTouchArea(play);
		
		AButton options = new AButton(play.getX() + play.getWidth() + 2f, play.getY(), 
				mainMenuButtons.getTextureRegion(2)) {
			@Override
			public void onClick() {
				SceneManager.getInstance().showLayer(OptionsLayer.getInstance(), false, false, true);
			}
		};
		menuScreen.attachChild(options);
		registerTouchArea(options);		
		
		levelSelectScreen = new Entity(0f, cameraHeight/4f);
		
		String world = new String(GameActivity.SHARED_PREFS_LEVELS_COMPLETE);
		levelSelector = new LevelSelector(0, cameraHeight/4f,
			GameActivity.getIntFromSharedPreferences(world) + 1, ResourceManager.levelButtonTex,
			ResourceManager.getInstance().getFont(), this);		

		levelSelectScreen.attachChild(this.levelSelector);
		attachChild(levelSelectScreen);
		attachChild(menuScreen);
	}

	@Override 
	public void onShowScene() {
		if(!this.menuBackground.hasParent()) {
			this.attachChild(this.menuBackground);
			this.sortChildren();
		}
		Log.d(NAME, "onShowScene");
	}
}