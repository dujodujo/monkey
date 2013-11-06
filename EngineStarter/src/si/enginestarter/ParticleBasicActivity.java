package si.enginestarter;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.particle.SpriteParticleSystem;
import org.andengine.entity.particle.emitter.CircleOutlineParticleEmitter;
import org.andengine.entity.particle.emitter.PointParticleEmitter;
import org.andengine.entity.particle.initializer.VelocityParticleInitializer;
import org.andengine.entity.particle.modifier.ExpireParticleInitializer;
import org.andengine.entity.particle.modifier.ScaleParticleModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.IGameInterface.OnCreateResourcesCallback;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;
import org.andengine.ui.IGameInterface.OnPopulateSceneCallback;
import org.andengine.ui.activity.BaseGameActivity;


public class ParticleBasicActivity extends BaseGameActivity {
	public static final int WIDTH = 800;
	public static final int HEIGHT = 480;
	
	private Scene mScene;
	private Camera mCamera;
	
	private ITextureRegion mParticleTextureRegion;
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		mCamera = new Camera(0, 0, WIDTH, HEIGHT);
		EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED,
			new FillResolutionPolicy(), mCamera);
		return engineOptions;
	}

	@Override
	public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/game/");		
		BuildableBitmapTextureAtlas texture = new BuildableBitmapTextureAtlas(mEngine.getTextureManager(), 32, 32,
			TextureOptions.BILINEAR);		
		mParticleTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texture, 
			getAssets(), "Smoke.png");		
		try {
			texture.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
		} catch (TextureAtlasBuilderException e) {
			e.printStackTrace();
		}
		texture.load();
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) {
		mScene = new Scene();		
		pOnCreateSceneCallback.onCreateSceneFinished(mScene);
	}

	@Override
	public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) {
		CircleOutlineParticleEmitter particleEmitter = new CircleOutlineParticleEmitter(WIDTH/2, HEIGHT/2, 50.f);
		SpriteParticleSystem particleSystem = new SpriteParticleSystem(particleEmitter, 10, 20, 200, 
			mParticleTextureRegion, mEngine.getVertexBufferObjectManager());
		particleSystem.addParticleInitializer(new VelocityParticleInitializer<Sprite>(-50, 50, -10, -400));
		particleSystem.addParticleInitializer(new ExpireParticleInitializer<Sprite>(25, 30));
		particleSystem.addParticleModifier(new ScaleParticleModifier<Sprite>(3, 6, 0.2f, 2f, 0.2f, 2f));
		particleEmitter.setCenter(WIDTH / 2, HEIGHT);
		mScene.attachChild(particleSystem);
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}
}
