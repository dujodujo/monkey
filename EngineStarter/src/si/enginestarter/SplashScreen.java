package si.enginestarter;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.FadeInModifier;
import org.andengine.entity.modifier.FadeOutModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.ScaleAtModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontUtils;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.IModifier.IModifierListener;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class SplashScreen extends ManagedSplashScreen {
	private static String TAG = SplashScreen.class.getName();
	
	private static float animationDuration = 5f;
	
	private BitmapTextureAtlas logoTexture;
	private ITextureRegion logoTextureRegion;
	private Sprite logoSprite;
	
	private String previousAssetBasePath = "";
	private SequenceEntityModifier logoSequenceEntMod;
	
	private static float xx = ResourceManager.getInstance().getCameraWidth()/2.f;
	private static float yy = ResourceManager.getInstance().getCameraHeight()/2.f;
	
	public SplashScreen() {
		super();
		
		this.previousAssetBasePath = BitmapTextureAtlasTextureRegionFactory.getAssetBasePath();
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("Game/Splash/");
		
		logoTexture = new	BitmapTextureAtlas(ResourceManager.getInstance().getEngine().getTextureManager(), 
			48, 48, TextureOptions.BILINEAR);
		
		logoTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(logoTexture,
			ResourceManager.getInstance().getContext(), "logo.png", 0, 0);
		
		logoSprite = new Sprite(xx, yy, logoTextureRegion, 
			ResourceManager.getInstance().getEngine().getVertexBufferObjectManager());
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(previousAssetBasePath);
		
		logoSequenceEntMod = new SequenceEntityModifier(new FadeInModifier(animationDuration));
	}
	
	@Override
	public void unloadSplashTextures() {
		Log.d(TAG, "unloadTextures");
		logoTexture.unload();
	}
	
	@Override
	protected void onLoadScene() {
		Log.d(TAG, "onLoadScene");
		
		logoTexture.load();
		
		float x = ResourceManager.getInstance().getCameraWidth()/2.f;
		float y = ResourceManager.getInstance().getCameraHeight()/2.f;
		ResourceManager.getInstance().getCamera().setCenter(x, y);
		
		setBackgroundEnabled(true);
		setBackground(new Background(0f, 0f, 0f));
		
		attachChild(logoSprite);
		registerTouchArea(logoSprite);
		
		this.registerUpdateHandler(new IUpdateHandler() {

			@Override
			public void onUpdate(float pSecondsElapsed) {
				logoSprite.registerEntityModifier(logoSequenceEntMod);
				SplashScreen.this.managedSplashScreen.unregisterUpdateHandler(this);
			}

			@Override
			public void reset() {}
		});
		
		logoSequenceEntMod.addModifierListener(new IModifierListener<IEntity>() {
			
			@Override
			public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
				SceneManager.getInstance().showMainMenu();
			}
		});
	}
}