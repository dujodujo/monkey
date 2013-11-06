package si.enginestarter;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.util.color.Color;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;

public class PauseLayer extends ManagedLayer {
	public static String TAG = PauseLayer.class.getName();
	
	private static PauseLayer instance;
	private static GameLevel currentLevel;
	protected Sprite layerBackground;
	private boolean restartLevel;
	private boolean nextLevel;
	private boolean resumeLevel;
	private AButton resume;
	private Text text;
	
	PauseLayer() { 
		super();
		
		restartLevel = false;
		nextLevel = false;
		resumeLevel = false;
	}
	
	public static PauseLayer getInstance() {
		if(instance == null)
			instance = new PauseLayer();
		return instance;
	}
	
	public static PauseLayer getInstance(GameLevel pCurrentLevel) {
		instance.setCurrentLevel(pCurrentLevel);
		return getInstance();
	}
	
	public static void setCurrentLevel(GameLevel pCurrentLevel) {
		currentLevel = pCurrentLevel;
	}
	
	IUpdateHandler slideInUpdateHandler = new IUpdateHandler() {

		@Override
		public void onUpdate(float pSecondsElapsed) {
			if(PauseLayer.getInstance().layerBackground.getY() > 0.f) {
				float y = PauseLayer.getInstance().getY() - pSecondsElapsed * PauseLayer.PPS;
				PauseLayer.getInstance().setY(Math.max(y, 0));
			} else {
				ResourceManager.getInstance().getEngine().unregisterUpdateHandler(this);
			}
		}

		@Override public void reset() {}
	};
	
	IUpdateHandler slideOutUpdateHandler=new IUpdateHandler() {

		@Override
		public void onUpdate(float pSecondsElapsed) {
			float y1 = ResourceManager.getInstance().getCameraHeight();
			float y2 = PauseLayer.getInstance().layerBackground.getHeight();
			if(PauseLayer.getInstance().layerBackground.getY() < (y1 + y2)/2) {
				float y = PauseLayer.getInstance().getY() + pSecondsElapsed * PauseLayer.PPS;
				PauseLayer.getInstance().setY(Math.min(y, y1 + y2)/2);
			} else {
				ResourceManager.getInstance().getEngine().unregisterUpdateHandler(this);
				SceneManager.getInstance().hideLayer();
			}
			PauseLayer.getInstance().currentLevel.disposeLevel();
			SceneManager.getInstance().showMainMenu();
		}

		@Override public void reset() {}
	};
	
	@Override
	public void onLoadLayer() {
		/*
		if(this.loaded)
			return;
		loaded = true;
		
		this.setTouchAreaBindingOnActionDownEnabled(true);
		this.setTouchAreaBindingOnActionMoveEnabled(true);
		
		Rectangle fadingRec=new Rectangle(0.f, 0.f, ResourceManager.getInstance().getCameraWidth(),
			ResourceManager.getInstance().getCameraHeight(), 
			ResourceManager.getInstance().getActivity().getVertexBufferObjectManager());
			fadingRec.setColor(Color.BLACK);
			this.attachChild(fadingRec);
			
			float xCamera = ResourceManager.getInstance().getCameraWidth()/2;
			float yCamera = ResourceManager.getInstance().getCameraHeight()/2;
			this.layerBackground = new Sprite(0.f, 0.f, xCamera, yCamera, ResourceManager.levelLayerTex,
				ResourceManager.getInstance().getActivity().getVertexBufferObjectManager());
			attachChild(this.layerBackground);
			this.layerBackground.setScale(1.f);
			
			final AButton resume = new AButton(200f, 200f, ResourceManager.pauseButtonTex) {

				@Override
				public void onClick() {
					PauseLayer.this.restartLevel = false;
					PauseLayer.this.nextLevel = false;
					PauseLayer.this.resumeLevel = true;
				}
			};
			this.layerBackground.attachChild(resume);
			this.registerTouchArea(resume);
			
			final AButton levelSelect = new AButton(200f, 150f, ResourceManager.getInstance().levelLayerLevelSelectTex) {

				@Override
				public void onClick() {
					PauseLayer.this.restartLevel = false;
					PauseLayer.this.nextLevel = false;
					PauseLayer.this.resumeLevel = false;
					onHideLayer();
				}
			};
			this.layerBackground.attachChild(levelSelect);
			this.registerTouchArea(levelSelect);
			
			final AButton nextLevel = new AButton(200f, 100f, ResourceManager.levelLayerNextLevelTex) {

				@Override
				public void onClick() {
					PauseLayer.this.restartLevel = false;
					PauseLayer.this.nextLevel = true;
					PauseLayer.this.resumeLevel = false;
					PauseLayer.this.onHideLayer();
				}
			};
			this.layerBackground.attachChild(nextLevel);
			this.registerTouchArea(nextLevel);
		*/
	}

	@Override
	public void onShowLayer() {
		ResourceManager.getInstance().getEngine().registerUpdateHandler(slideInUpdateHandler);
		restartLevel = false;
		nextLevel = false;
		this.resumeLevel = true;
		
		this.text.setText("score" + 10);
		this.text.setScale(1.f);
		
		String maxLevel = GameActivity.SHARED_PREFS_LEVELS_COMPLETE;
		int currentMaxLevel = GameActivity.getIntFromSharedPreferences(maxLevel);
		if(currentMaxLevel >= this.currentLevel.levelDef.iLevel)
			this.resume.setEnabled(true);
		else
			this.resume.setEnabled(false);
	}
	
	@Override
	public void onHideLayer() {
		ResourceManager.getInstance().getEngine().registerUpdateHandler(this.slideOutUpdateHandler);
	}
	
	@Override
	public void onUnloadLayer() {}
}
