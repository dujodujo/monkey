package si.enginestarter;

import org.andengine.engine.FixedStepEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.source.AssetBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.color.Color;

import android.content.Context;
import android.graphics.Typeface;
import android.opengl.GLES20;
import android.util.Log;

public class ResourceManager {
	private static String NAME = ResourceManager.class.getName();
	
	private static final TextureOptions normalTextureOption = TextureOptions.BILINEAR;
	private static final TextureOptions beamTextureOption = new TextureOptions(GLES20.GL_LINEAR, 
		GLES20.GL_LINEAR, GLES20.GL_CLAMP_TO_EDGE, GLES20.GL_REPEAT, false);
	private static final TextureOptions transparentTextureOption = TextureOptions.BILINEAR;
	
	private static int WIDTH = 480;
	private static int HEIGHT = 720;
	
	private float cameraWidth;
	private float cameraHeight;
	private float cameraScale;
	
	private String previousAssetBasePath = "";
	
	public static TextureRegion woodenDynamicTex;
	public static TextureRegion metalBeamDynamicTex;
	public static TextureRegion metalBeamStaticTex;
	
	public static TiledTextureRegion boxSmallTTex;
	public static TiledTextureRegion boxLargeTTex;
	
	public static TextureRegion parallaxBackdropTex;
	
	public static TiledTextureRegion heroTTex;
	public static TiledTextureRegion redBeeTTex;
	public static TiledTextureRegion yellowBeeTTex;
	public static TiledTextureRegion greenBeeTTex;
	public static TiledTextureRegion bananaTTex;
	public static TiledTextureRegion normalBarrelTex;
	public static TiledTextureRegion autoBarrelTex;
	public static TiledTextureRegion lightBarrelTex;
	
	public static TextureRegion silverSprocketTex;
	public static TextureRegion goldSprocketTex;
	public static TextureRegion bronzeSprocketTex;
	
	public static TextureRegion magneticOrbTex;
	public static TextureRegion magneticFlaringOrbTex;
	
	public static TextureRegion woodenParticleTex;
	public static TiledTextureRegion smokeTTex;
	public static TextureRegion whiteSmokeTex;
	public static TiledTextureRegion explosionTTex;
	public static TiledTextureRegion collisionTTex;
	
	public static TextureRegion levelLayerTex;
	public static TextureRegion optionsLayerTex;
	
	public static TextureRegion powerBarBackground;
	public static TextureRegion powerBarLine;
	public static TextureRegion powerBarLens;
	
	public static TextureRegion menuBackgroundTex;
	public static TextureRegion menuMainTitleTex;
	public static TiledTextureRegion menuMainButtonTTex;

	public static TextureRegion pauseButtonTex;
	public static TextureRegion levelButtonTex;
	public static TextureRegion lockedButtonTex	;
	public static TiledTextureRegion levelStarTTex;

	public static TextureRegion arrow1Tex;
	
	public static String splashScreenText1 = "Splash screen text1";
	public static String splashScreenText2 = "Splash screen text2";
	
	public Font font;
	private static ResourceManager instance;
	public static GameActivity activity;
	public static SwitchableFixedStepEngine engine;
	public static Context context;
	public static GameCamera gameCamera;

	ResourceManager() {}
	
	public static ResourceManager getInstance() {
		if(instance == null)
			instance = new ResourceManager();
		return instance;
	}
	
	public Font getFont() { return font; }
	
	public SwitchableFixedStepEngine getEngine() { return engine; }
	
	public Context getContext() { return context; }
	
	public GameCamera getCamera() { return gameCamera; }
	
	public void setCamera(GameCamera gc) { gameCamera = gc; }
	
	public float getCameraWidth() { return cameraWidth; }
	
	public float getCameraHeight() { return cameraHeight; }
	
	public float getCameraScale() {  return cameraScale; }
	
	public GameActivity getActivity() { return activity; }
	
	public static void setupResources(GameActivity activity, SwitchableFixedStepEngine engine, Context context) {
		getInstance().activity = activity;
		getInstance().engine = engine;
		getInstance().context = context;
		setupCameraProperties();
		loadGameResources();
	}
	
	public static void setupCameraProperties() {
		getInstance().cameraWidth = 480;
		getInstance().cameraHeight = 720;
		getInstance().cameraScale = 1.f;
	}
	
	public static void loadGameResources() {
		ResourceManager.getInstance().loadGameTextures();
		ResourceManager.getInstance().loadSharedResources();
	}
	
	public void loadGameTextures() {
		previousAssetBasePath = BitmapTextureAtlasTextureRegionFactory.getAssetBasePath();
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("Game/Elements/");
		final TextureManager textureManager = ResourceManager.getInstance().getActivity().getTextureManager();
		
		if(metalBeamDynamicTex == null) {
			BitmapTextureAtlas metalBeam = new BitmapTextureAtlas(textureManager, 128, 64, beamTextureOption);
			metalBeamDynamicTex = BitmapTextureAtlasTextureRegionFactory.createFromAsset(metalBeam, 
				ResourceManager.getInstance().getActivity(), "metalBeamDynamic.png", 0, 0);
			metalBeam.load();
		}
		
		if(metalBeamStaticTex == null) {
			BitmapTextureAtlas metalBeamStatic = new BitmapTextureAtlas(textureManager, 128, 64, beamTextureOption);
			metalBeamStaticTex = BitmapTextureAtlasTextureRegionFactory.createFromAsset(metalBeamStatic, 
				ResourceManager.getInstance().getActivity(), "metalBeamStatic.png", 0, 0);
			metalBeamStatic.load();
		}
		
		if(woodenDynamicTex == null) {
			BitmapTextureAtlas woodenBeam = new BitmapTextureAtlas(textureManager, 128, 64, beamTextureOption);
			woodenDynamicTex = BitmapTextureAtlasTextureRegionFactory.createFromAsset(woodenBeam, 
				ResourceManager.getInstance().getActivity(), "woodenBeamDynamic.png", 0, 0);
			woodenBeam.load();
		}
		
		if(boxSmallTTex == null)
			boxSmallTTex = this.getTiledTextureRegion("boxSmall.png", 2, 2, normalTextureOption);
		if(boxLargeTTex == null)
			boxLargeTTex = this.getTiledTextureRegion("boxLarge.png", 2, 2, normalTextureOption);
		if(parallaxBackdropTex == null)
			parallaxBackdropTex = this.getTextureRegion("VerticalBackground.png", transparentTextureOption);
		
		if(heroTTex == null)
			heroTTex = this.getTiledTextureRegion("heroBody.png", 14, 1, normalTextureOption);
		if(redBeeTTex == null)
			redBeeTTex = this.getTiledTextureRegion("bees.png", 3, 1, normalTextureOption);
		if(yellowBeeTTex == null)
			yellowBeeTTex = this.getTiledTextureRegion("bees.png", 3, 1, normalTextureOption);
		if(greenBeeTTex == null)
			greenBeeTTex = this.getTiledTextureRegion("bees.png", 3, 1, normalTextureOption);
		if(bananaTTex == null)
			bananaTTex = this.getTiledTextureRegion("banana.png", 8, 1, normalTextureOption);
		if(normalBarrelTex == null)
			normalBarrelTex = this.getTiledTextureRegion("BarrelsRotation.png", 4, 4, normalTextureOption);
		if(autoBarrelTex == null)
			autoBarrelTex = this.getTiledTextureRegion("AutoBarrelsRotation.png", 4, 4, normalTextureOption);
		if(lightBarrelTex == null)
			lightBarrelTex = this.getTiledTextureRegion("LightBarrel.png", 2, 1, normalTextureOption);
		
		if(magneticOrbTex == null)
			magneticOrbTex = this.getTextureRegion("orb.png", transparentTextureOption);
		if(magneticFlaringOrbTex == null)
			magneticFlaringOrbTex = this.getTextureRegion("flaringOrb.png", transparentTextureOption);
				
		if(powerBarBackground == null) 
			powerBarBackground = getTextureRegion("barBackground.png", transparentTextureOption);
		if(powerBarLine == null) 
			powerBarLine = getTextureRegion("barLine.png", transparentTextureOption);
		if(powerBarLens == null) 
			powerBarLens = getTextureRegion("barLens.png", transparentTextureOption);
		if(pauseButtonTex == null)
			pauseButtonTex = getTextureRegion("lockButton.png", transparentTextureOption);
		
		if(levelLayerTex == null) 
			levelLayerTex = getTextureRegion("levelLayer.png", transparentTextureOption);
		
		if(woodenParticleTex == null)
			woodenParticleTex = getTextureRegion("woodenParticle.png", transparentTextureOption);
		if(smokeTTex == null)
			smokeTTex = getTiledTextureRegion("smoke.png", 12, 1, transparentTextureOption);
		if(whiteSmokeTex == null)
			whiteSmokeTex = getTextureRegion("whiteSmoke.png", transparentTextureOption);
		if(explosionTTex == null)
			explosionTTex = getTiledTextureRegion("explosion.png", 4, 3, normalTextureOption);
		if(collisionTTex == null)
			collisionTTex = getTiledTextureRegion("collision.png", 5, 1, normalTextureOption);
		
		if(silverSprocketTex == null)
			silverSprocketTex = getTextureRegion("silverSprocket.png", transparentTextureOption);
		if(goldSprocketTex == null)
			goldSprocketTex = getTextureRegion("goldSprocket.png", transparentTextureOption);
		if(bronzeSprocketTex == null)
			bronzeSprocketTex = getTextureRegion("bronzeSprocket.png", transparentTextureOption);
			
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(previousAssetBasePath);
	}
	
	public void unloadGameTextures() {}
	
	public static void loadMenuResources() {
		getInstance().loadMenuTextures();
		getInstance().loadSharedResources();
	}
	
	public void loadSharedResources() {
		loadFonts();
	}
	
	public void loadFonts() {
		if(font == null) {
			font = FontFactory.create(engine.getFontManager(), engine.getTextureManager(),
				256, 256, Typeface.create(Typeface.DEFAULT, Typeface.NORMAL), 32.f, true, 
				Color.WHITE_ABGR_PACKED_INT);
			font.load();
		}
	}
	
	public void loadMenuTextures() {
		previousAssetBasePath = BitmapTextureAtlasTextureRegionFactory.getAssetBasePath();
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("Game/Menu/");
		
		if(levelButtonTex == null) {
			BitmapTextureAtlas levelButton = new BitmapTextureAtlas(ResourceManager.getInstance().getActivity().getTextureManager(), 
				52, 52, transparentTextureOption);
			levelButtonTex = BitmapTextureAtlasTextureRegionFactory.createFromAsset(levelButton, 
				ResourceManager.getInstance().getActivity(), "levelButton.png", 0, 0);
			levelButton.load();
		}
		
		if(lockedButtonTex == null) {
			BitmapTextureAtlas lockedButton = new BitmapTextureAtlas(ResourceManager.getInstance().getActivity().getTextureManager(),
				64, 64, transparentTextureOption);
			lockedButtonTex = BitmapTextureAtlasTextureRegionFactory.createFromAsset(lockedButton, 
				ResourceManager.getInstance().getActivity(), "lockButton.png", 0, 0);
			lockedButton.load();
		}
		
		if(levelStarTTex == null) {
			BitmapTextureAtlas levelStar = new BitmapTextureAtlas(ResourceManager.getInstance().getActivity().getTextureManager(),
				64, 64, transparentTextureOption);
			levelStarTTex = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(levelStar, 
				ResourceManager.getInstance().getActivity(), "levelStars.png", 0, 0, 4, 1);
			levelStar.load();
		}
		
		if(arrow1Tex == null) {
			BitmapTextureAtlas arrow1 = new BitmapTextureAtlas(ResourceManager.getInstance().getActivity().getTextureManager(),
				64, 64, transparentTextureOption);
			arrow1Tex = BitmapTextureAtlasTextureRegionFactory.createFromAsset(arrow1, 
					ResourceManager.getInstance().getActivity(), "arrow1.png", 0, 0);
			arrow1.load();
		}
		
		if(menuBackgroundTex == null) 
			menuBackgroundTex = getTextureRegion("background.png", normalTextureOption);
		if(menuMainTitleTex == null) 
			menuMainTitleTex = getTextureRegion("title.png", transparentTextureOption);
		if(menuMainButtonTTex == null) 
			menuMainButtonTTex = getTiledTextureRegion("buttons.png", 2, 2, transparentTextureOption);
		
		if(optionsLayerTex == null) 
			optionsLayerTex = getTextureRegion("optionsLayer.png", transparentTextureOption);
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(previousAssetBasePath);
	}
	
	public void unloadMenuTextures() {}

	private TextureRegion getTextureRegion(String textureRegionPath, TextureOptions texOptions) {
		String path = BitmapTextureAtlasTextureRegionFactory.getAssetBasePath() + textureRegionPath;
		
		final IBitmapTextureAtlasSource bitmapTextureAtlasSource = 
			AssetBitmapTextureAtlasSource.create(activity.getAssets(), path);
		
		int widthTexture = bitmapTextureAtlasSource.getTextureWidth();
		int heightTexture = bitmapTextureAtlasSource.getTextureHeight();
		final BitmapTextureAtlas bitmapTextureAtlas = 
			new BitmapTextureAtlas(activity.getTextureManager(), widthTexture, heightTexture, texOptions);
		
		final TextureRegion textureRegion = 
			new TextureRegion(bitmapTextureAtlas, 0, 0, widthTexture, heightTexture, false) {

			@Override
			public void updateUV() {
				super.updateUV();
			}
		};
		
		bitmapTextureAtlas.addTextureAtlasSource(bitmapTextureAtlasSource, 0, 0);
		bitmapTextureAtlas.load();
		return textureRegion;
	}
	
	private TiledTextureRegion getTiledTextureRegion(String textureRegionPath, int pColumns, int pRows,
			TextureOptions texOptions) {
		String path = BitmapTextureAtlasTextureRegionFactory.getAssetBasePath() + textureRegionPath;
		
		final IBitmapTextureAtlasSource bitmapTextureAtlasSource = 
			AssetBitmapTextureAtlasSource.create(activity.getAssets(), path);
		
		int widthTexture = bitmapTextureAtlasSource.getTextureWidth();
		int heightTexture = bitmapTextureAtlasSource.getTextureHeight();
		final BitmapTextureAtlas bitmapTextureAtlas = 
			new BitmapTextureAtlas(activity.getTextureManager(), widthTexture, heightTexture, texOptions);
		
		final ITextureRegion textureRegions[] = new ITextureRegion[pColumns * pRows];
		final int widthTile = widthTexture / pColumns;
		final int heightTile = heightTexture / pRows;
		for(int i = 0; i < pColumns; i++) {
			for(int j = 0; j < pRows; j++) {
				int index = j * pColumns + i;
				int x = widthTile * i;
				int y = heightTile * j;
				textureRegions[index] = 
					new TextureRegion(bitmapTextureAtlas, x, y, widthTile, heightTile, false) {

					@Override
					public void updateUV() {
						super.updateUV();
					}
				};
			}
		}
		TiledTextureRegion tiledTextureRegion = new TiledTextureRegion(bitmapTextureAtlas, false, textureRegions);
		bitmapTextureAtlas.addTextureAtlasSource(bitmapTextureAtlasSource, 0, 0);
		bitmapTextureAtlas.load();
		return tiledTextureRegion;
	}
	
	private TiledTextureRegion getCustomTiledTextureRegion(String textureRegionPath, int[] sizes, TextureOptions texOptions) {
		String path = BitmapTextureAtlasTextureRegionFactory.getAssetBasePath() + textureRegionPath;
		
		final IBitmapTextureAtlasSource bitmapTextureAtlasSource = 
			AssetBitmapTextureAtlasSource.create(activity.getAssets(), path);
		
		int widthTexture = bitmapTextureAtlasSource.getTextureWidth();
		int heightTexture = bitmapTextureAtlasSource.getTextureHeight();
		final BitmapTextureAtlas bitmapTextureAtlas = 
			new BitmapTextureAtlas(activity.getTextureManager(), widthTexture, heightTexture, texOptions);
		
		final ITextureRegion textureRegions[] = new ITextureRegion[sizes.length];
		int x = 0;
		int y = 0;
		for(int i = 0; i < sizes.length/2; i+=2) {
			float width = sizes[i];
			float height = sizes[i+1];
			textureRegions[i] = new TextureRegion(bitmapTextureAtlas, x * width, y * height, width, height, false) {

				@Override
				public void updateUV() {
					super.updateUV();
				}
			};
		}
		TiledTextureRegion tiledTextureRegion = new TiledTextureRegion(bitmapTextureAtlas, false, textureRegions);
		bitmapTextureAtlas.addTextureAtlasSource(bitmapTextureAtlasSource, 0, 0);
		bitmapTextureAtlas.load();
		return tiledTextureRegion;
	}
}