package si.enginestarter;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.color.Color;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;

public class CompleteLevelLayer extends ManagedLayer {
	private static String NAME = "CompleteLevelLayer";
	
	private GameLevel currentLevel;
	private Sprite layerBackground;
	private TiledSprite starsTiledSprite;
	
	private boolean restartLevel;
	private boolean nextLevel;
	
	private static CompleteLevelLayer instance;
	
	public CompleteLevelLayer() {}
	
	public static CompleteLevelLayer getInstance() {
		if(instance == null) {
			instance = new CompleteLevelLayer();
		}
		return instance;
	}
	
	public static CompleteLevelLayer getInstance(final GameLevel pCurrentLevel) {
		if(instance == null) {
			instance = new CompleteLevelLayer();
			instance.setCurrentLevel(pCurrentLevel);
		}
		return instance;
	}
	
	IUpdateHandler slideInUpdateHandler=new IUpdateHandler() {

		@Override
		public void onUpdate(float pSecondsElapsed) {
			if(CompleteLevelLayer.this.layerBackground.getY() > 0.f) {
				float y=CompleteLevelLayer.this.getY()-(pSecondsElapsed*CompleteLevelLayer.PPS);
				CompleteLevelLayer.this.setY(Math.max(y, 0));
			} else {
				ResourceManager.getInstance().getEngine().unregisterUpdateHandler(this);
			}
		}

		@Override public void reset() {}
	};
	
	
	IUpdateHandler slideOutUpdateHandler=new IUpdateHandler() {

		@Override
		public void onUpdate(float pSecondsElapsed) {
			float y1=ResourceManager.getInstance().getCameraHeight();
			float y2=CompleteLevelLayer.this.layerBackground.getHeight();
			if(CompleteLevelLayer.this.layerBackground.getY() < (y1+y2)/2) {
				float y=OptionsLayer.getInstance().getY()+(pSecondsElapsed*OptionsLayer.PPS);
				OptionsLayer.getInstance().setY(Math.min(y, (y1+y2)/2));
			} else {
				ResourceManager.getInstance().getEngine().unregisterUpdateHandler(this);
				SceneManager.getInstance().hideLayer();
				CompleteLevelLayer.this.currentLevel.disposeLevel();
				SceneManager.getInstance().showMainMenu();
			}
		}
		@Override public void reset() {}
	};

	
	@Override
	public void onUnloadLayer() {}

	@Override
	public void onHideLayer() {
		ResourceManager.getInstance().getEngine().registerUpdateHandler(slideOutUpdateHandler);
	}
	
	@Override
	public void onLoadLayer() {
		/*
		if(this.loaded)
			return;
		this.loaded = true;
		
		this.setTouchAreaBindingOnActionDownEnabled(true);
		this.setTouchAreaBindingOnActionMoveEnabled(true);
		
		Rectangle rec = new Rectangle(0.f, 0.f, ResourceManager.getInstance().getCameraWidth(),
			ResourceManager.getInstance().getCameraHeight(), 
			ResourceManager.getInstance().getActivity().getVertexBufferObjectManager());
		rec.setColor(Color.BLACK);
		this.attachChild(rec);
		
		float xcamera = ResourceManager.getInstance().getCameraWidth()/2;
		float ycamera = ResourceManager.getInstance().getCameraHeight()/2;
		this.layerBackground = new Sprite(0.f, 0.f, xcamera, ycamera, ResourceManager.levelLayerTex,
			ResourceManager.getInstance().getActivity().getVertexBufferObjectManager());
		attachChild(this.layerBackground);
		this.layerBackground.setScale(1.f);
		
		final AButton backToLevelSelection = new AButton(200.f, 200.f, ResourceManager.levelLayerLevelSelectTex) {
			
			@Override
			public void onClick() {
				CompleteLevelLayer.this.nextLevel = false;
				CompleteLevelLayer.this.restartLevel = false;
				CompleteLevelLayer.this.onHideLayer();
			}
		};
		this.layerBackground.attachChild(backToLevelSelection);
		this.registerTouchArea(backToLevelSelection);
		
		final AButton restartLevel = new AButton(200.f, 150.f, ResourceManager.levelLayerRestartLevelTex) {

			@Override
			public void onClick() {
				CompleteLevelLayer.this.nextLevel = false;
				CompleteLevelLayer.this.restartLevel = true;
				CompleteLevelLayer.this.onHideLayer();
			}
		};
		this.layerBackground.attachChild(restartLevel);
		this.registerTouchArea(restartLevel);
		
		final AButton nextLevel = new AButton(200.f, 100.f, ResourceManager.levelLayerRestartLevelTex) {

			@Override
			public void onClick() {
				CompleteLevelLayer.this.nextLevel = true;
				CompleteLevelLayer.this.restartLevel = false;
				CompleteLevelLayer.this.onHideLayer();
			}
		};
		this.layerBackground.attachChild(nextLevel);
		this.registerTouchArea(nextLevel);
		
		Sprite backgroundStars = new Sprite(200.f, 50, ResourceManager.levelLayerLayersStarsTTex,
			ResourceManager.getInstance().getActivity().getVertexBufferObjectManager());
		this.layerBackground.attachChild(backgroundStars);
		
		TiledSprite tiledStars = new TiledSprite(200.f, 0.f, ResourceManager.levelLayerLayersStarsTTex,
			ResourceManager.getInstance().getActivity().getVertexBufferObjectManager());
		this.layerBackground.attachChild(tiledStars);
		
		*/
	}
	
	@Override
	public void onShowLayer() {
		ResourceManager.getInstance().getEngine().registerUpdateHandler(this.slideInUpdateHandler);
		this.restartLevel = false;
		this.nextLevel = false;
		
		//for(TiledSprite currentBox : this.currentLevel.boxes)
		//	this.currentLevel.addPoints(currentBox, 1);
		
		this.starsTiledSprite.registerEntityModifier(new ScaleModifier(1f, 1f, 1f));
		this.starsTiledSprite.registerEntityModifier(new AlphaModifier(1f, 1f, 1f));
		
		String maxLevel = GameActivity.SHARED_PREFS_LEVELS_COMPLETE;
		int currentMaxLevel = GameActivity.getIntFromSharedPreferences(maxLevel);
		if(currentMaxLevel < this.currentLevel.levelDef.iLevel) {
			GameActivity.setIntToSharedPreferences(maxLevel, currentLevel.levelDef.iLevel);
		}
	}
	
	public void setCurrentLevel(GameLevel pCurrentLevel) {
		this.currentLevel = pCurrentLevel;
	}
}
