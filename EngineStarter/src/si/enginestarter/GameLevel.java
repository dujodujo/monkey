package si.enginestarter;

import java.util.ArrayList;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.primitive.Vector2;
import org.andengine.entity.scene.CameraScene;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.shape.IShape;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.entity.text.Text;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.adt.pool.GenericPool;
import org.andengine.util.color.Color;

import si.enginestarter.GameManager.GameLevelObjective;
import si.enginestarter.Levels.LevelDef;
import si.enginestarter.Levels.MagneticBeamDef;
import si.enginestarter.Levels.MagneticBoxDef;
import si.enginestarter.Levels.MagneticEnemyDef;
import si.enginestarter.Levels.MagneticEnemyDef.EnemyType;
import si.enginestarter.Levels.MagneticOrbDef;
import si.enginestarter.ParallaxLayer.ParallaxEntity;
import android.hardware.SensorManager;
import android.util.Log;

public class GameLevel extends ManagedGameScene implements IOnSceneTouchListener {
	private static String TAG = GameLevel.class.getName();
	
	public static final float LEVEL_WIDTH = 720f;
	public static final float LEVEL_HEIGHT = 720f;
	public static final float TIME_LEVEL_START = 1f;
	public static final float TIME_MOVEMENT_FINISH = 1f;
	public static final int VELOCITY_ITERATIONS = 20;
	public static final int POSITION_ITERATIONS = 20;
	
	public static final String LOADING_STEP_1  = "loading Scene";
	public static final String LOADING_STEP_2  = "loading Textures";
	public static final String LOADING_STEP_3  = "loading Ground";
	public static final String LOADING_STEP_4  = "loading Buttons";
	public static final String LOADING_STEP_5  = "loading Hero";
	public static final String LOADING_STEP_6  = "loading Beams";
	public static final String LOADING_STEP_7  = "loading Rats";
	public static final String LOADING_STEP_8  = "loading Boxes";
	public static final String LOADING_STEP_9  = "loading Camera";
	public static final String LOADING_STEP_10 = "loading Barrels";
	public static final String LOADING_STEP_11 = "loading Balloons";
	public static final String LOADING_STEP_12 = "loading Lights";
	
	private static float LOADING_SCREEN_DURATION = 0.1f;
	private static float MIN_LOADING_SCREEN_DURATION = 12.f;
	
	protected FixedStepPhysicsWorld physicsWorld;

	protected LevelDef levelDef;
	protected Vector2 position;
	
	protected MagneticHero magneticHero;
	protected MagneticBarrel magneticBarrel;
	protected MagneticOrb magneticOrb;
	protected MagneticDoll magneticDoll;
	
	protected ArrayList<float[]> positions;
	protected ArrayList<MagneticBeam> magneticBeams;
	protected ArrayList<MagneticBox> magneticBoxes;
	
	protected ArrayList<MagneticEntity> entities;
	protected ArrayList<MagneticBarrel> magneticBarrels;
	protected ArrayList<MagneticBalloon> magneticBalloons;
	protected ArrayList<MagneticBee> magneticBees;
	protected ArrayList<MagneticOrb> magneticOrbs;
	protected ArrayList<MagneticLight> magneticLights;
	
	protected GenericPool<Text> scoreTextPool;
	protected Text scoreText;
	protected int currentScore;
	
	private final GameCamera gameCamera;
	
	protected boolean levelStarted;
	protected boolean isMovement;
	protected boolean levelComplete;
	protected float totalMovementTime;
	
	public GameLevel(LevelDef levelDef) {
		super(LOADING_SCREEN_DURATION, MIN_LOADING_SCREEN_DURATION);
		Log.d(TAG, "GameLevel");
		
		this.levelDef = levelDef;
		
		positions = new ArrayList<float[]>();
		
		entities = new ArrayList<MagneticEntity>();
		magneticBeams = new ArrayList<MagneticBeam>();
		magneticBoxes = new ArrayList<MagneticBox>();
		magneticBarrels = new ArrayList<MagneticBarrel>();
		magneticBalloons = new ArrayList<MagneticBalloon>();
		magneticBees = new ArrayList<MagneticBee>();
		magneticOrbs = new ArrayList<MagneticOrb>();
		magneticLights = new ArrayList<MagneticLight>();
		
		gameCamera = ResourceManager.getInstance().getCamera();
		
		levelStarted = true;
		isMovement = false;
		levelComplete = false;
		
		createScoreTextPool();
	}
		
	public void addPoints(final IEntity pEntity, final int pPoints) { this.currentScore += pPoints; }
	
	public void addPosition(float position[]) { this.positions.add(position); }
	
	public FixedStepPhysicsWorld getWorld() { return physicsWorld; }
	
	public MagneticHero getMagneticHero() { return magneticHero; }
	
	public GameCamera getCamera() { return gameCamera; }
	
	@Override
	public void onLoadLevel() {
		Log.d(TAG, "onLoadLevel");
		GameManager.getInstance().setGameLevel(this);
		
		com.badlogic.gdx.math.Vector2 gravity = new com.badlogic.gdx.math.Vector2(0, -SensorManager.GRAVITY_EARTH * 2);
		this.physicsWorld = new FixedStepPhysicsWorld(ResourceManager.getInstance().getEngine().getStepsPerSecond(),
			gravity, false, VELOCITY_ITERATIONS, POSITION_ITERATIONS);

		this.registerUpdateHandler(physicsWorld);
		
		this.addLoadingStep(new LoadingRunnable(LOADING_STEP_1, this) {
			
			@Override
			public void onLoad() {
				Log.d(TAG, LOADING_STEP_1);
				CameraScene scene = new CameraScene(ResourceManager.getInstance().getEngine().getCamera());
				GameLevel.this.attachChild(scene);
				
				//ParallaxLayer backgroundLayer = new ParallaxLayer(GameLevel.this.gameCamera, true);
				//Sprite background = new Sprite(0f, 0f, ResourceManager.parallaxBackdropTex,
				//	ResourceManager.getInstance().getActivity().getVertexBufferObjectManager());
				//backgroundLayer.attachParallaxEntity(new ParallaxEntity(1f, background, true));
				//scene.attachChild(backgroundLayer);
			}
		});
	
		this.addLoadingStep(new LoadingRunnable(LOADING_STEP_2, this) {
			
			@Override
			public void onLoad() {
				Log.d(TAG, LOADING_STEP_2);
			}
		});
		
		this.addLoadingStep(new LoadingRunnable(LOADING_STEP_3, this) {

			@Override
			public void onLoad() {
				Log.d(TAG, LOADING_STEP_3);
				for(MagneticOrbDef orb : GameLevel.this.levelDef.magneticOrbs) {
					magneticOrbs.add(new MagneticOrb(orb.x, orb.y, orb.duration, orb.fade,
						orb.rotationSpeed, GameLevel.this));
				}
			}
		});
		
		this.addLoadingStep(new LoadingRunnable(LOADING_STEP_4, this) {

			@Override
			public void onLoad() {
				Log.d(TAG, LOADING_STEP_4);
				GameLevel.this.gameCamera.setHUD(new HUD());
				GameLevel.this.gameCamera.getHUD().setVisible(true);
				
				GameLevel.this.scoreText = new Text(300, 0, ResourceManager.getInstance().font, "0:  ", 
					ResourceManager.getInstance().getActivity().getVertexBufferObjectManager());
				GameLevel.this.scoreText.setPosition(300, 0);
				GameLevel.this.setColor(Color.YELLOW);
				GameLevel.this.scoreText.setScale(.5f);
				GameLevel.this.scoreText.setAlpha(1f);
				
				GameLevel.this.gameCamera.getHUD().attachChild(GameLevel.this.scoreText);
				
				if(GameLevel.this.gameCamera.getHUD() != null)
					BouncingPowerBar.getInstance().attachInstanceToHud(GameLevel.this.gameCamera.getHUD());
			}
		});
		
		this.addLoadingStep(new LoadingRunnable(LOADING_STEP_5, this) {

			@Override
			public void onLoad() {
				Log.d(TAG, LOADING_STEP_5);
				magneticHero = new MagneticHero(525f, 525f, new Vector2(1f, 1f),  GameLevel.this);
				entities.add(magneticHero);
				gameCamera.setHero(magneticHero);
			}
		});
		
		this.addLoadingStep(new LoadingRunnable(LOADING_STEP_6, this) {
			@Override
			public void onLoad() {
				Log.d(TAG, LOADING_STEP_6);
				for(MagneticBeamDef currentBeam : GameLevel.this.levelDef.beams) {
					MagneticBeam<Sprite> mb;
					switch(currentBeam.getBeamType()) {
						case MetalDynamic:
							MetalBeamDynamic mbd = new MetalBeamDynamic(currentBeam.x, currentBeam.y, 
								currentBeam.length, currentBeam.height, currentBeam.rotation, GameLevel.this);
							entities.add(mbd);
							magneticBeams.add(mbd);
							break;
						case MetalStatic:
							MetalBeamStatic mbs = new MetalBeamStatic(currentBeam.x, currentBeam.y, 
								currentBeam.length, currentBeam.height, currentBeam.rotation, GameLevel.this);
							entities.add(mbs);
							magneticBeams.add(mbs);
							break;
						case WoodenDynamic:
							WoodenBeamDynamic wbd = new WoodenBeamDynamic(currentBeam.x, currentBeam.y, 
								currentBeam.length, currentBeam.height, currentBeam.rotation, GameLevel.this);
							entities.add(wbd);
							magneticBeams.add(wbd);
							break;
					}
				}
			}
		});
		
		this.addLoadingStep(new LoadingRunnable(LOADING_STEP_7, this) {
			@Override
			public void onLoad() {
				Log.d(TAG, LOADING_STEP_7);
				for(MagneticEnemyDef currentEnemy : GameLevel.this.levelDef.enemies) {
					switch(currentEnemy.getEnemyType()) {
						case Bee:
							MagneticBee mb;
							switch(currentEnemy.getEnemyColor()) {
								case Green:
									mb = MagneticEnemy.createMagneticBee(currentEnemy.x, currentEnemy.y, 
										currentEnemy.enemyColor, GameLevel.this);
									entities.add(mb);
									magneticBees.add(mb);
									break;
								case Yellow:
									mb = MagneticEnemy.createMagneticBee(currentEnemy.x, 
										currentEnemy.y, currentEnemy.enemyColor, GameLevel.this);
									entities.add(mb);
									magneticBees.add(mb);
									break;
							}
						break;
						case Duck:
						break;
					}
				}
			}
		});
		
		this.addLoadingStep(new LoadingRunnable(LOADING_STEP_8, this) {
			@Override
			public void onLoad() {
				Log.d(TAG, LOADING_STEP_8);
				for(MagneticBoxDef currentBox : GameLevel.this.levelDef.boxesDef) {
					MagneticBox mb;
					mb = new MagneticBox(currentBox.x, currentBox.y, currentBox.boxSize, currentBox.boxType, GameLevel.this);
					magneticBoxes.add(mb);
				}
			}
		});
		
		this.addLoadingStep(new LoadingRunnable(LOADING_STEP_9, this) {

			@Override
			public void onLoad() {
				Log.d(TAG, LOADING_STEP_9);
				GameLevel.this.gameCamera.setBounds(0f, 0f, 2000f, 2000f);
				GameLevel.this.gameCamera.setBoundsEnabled(true);
				
				((SwitchableFixedStepEngine) ResourceManager.getInstance().getEngine()).enableFixedStep();
			}
		});
		
		this.addLoadingStep(new LoadingRunnable(LOADING_STEP_10, this) {
			@Override
			public void onLoad() {
				Log.d(TAG, LOADING_STEP_10);
					
				MagneticBarrel m1;
				m1 = new MagneticBarrel(650, 525f, 4, new Vector2(0.05f, 0f), 
					new StopMovementDef(),  GameLevel.this);
				entities.add(m1);
				magneticBarrels.add(m1);
				
				MagneticBarrel m2;
				m2 = new MagneticBarrel(1050, 525f, 4, new Vector2(0.05f, 0f), 
					new StopMovementDef(), GameLevel.this);
				entities.add(m2);
				magneticBarrels.add(m2);
				
				MagneticAutoBarrel m3;
				m3 = new MagneticAutoBarrel(1450f, 525f, 4, new Vector2(0.05f, 0f), 
					new StopMovementDef(), GameLevel.this);
				entities.add(m3);
				magneticBarrels.add(m3);
			}
		});
		
		this.addLoadingStep(new LoadingRunnable(LOADING_STEP_11, this) {
			@Override
			public void onLoad() {
				Log.d(TAG, LOADING_STEP_11);
				
				float x = 725;
				float y = 525;
				for(int i = 0; i < 8; i++) {
					MagneticBalloon mb1;
					mb1 = new MagneticBalloon(x + i*25, y, GameLevel.this);
					entities.add(mb1);
					magneticBalloons.add(mb1);
				}
				
				x = 1100;
				y = 525;
				for(int i = 0; i < 8; i++) {
					MagneticBalloon mb;
					mb = new MagneticBalloon(x + i*25, y, GameLevel.this);
					entities.add(mb);
					magneticBalloons.add(mb);
				}

			}
		});
		
		this.addLoadingStep(new LoadingRunnable(LOADING_STEP_12, this) {
			@Override
			public void onLoad() {
				Log.d(TAG, LOADING_STEP_12);
				
				MagneticLight ml;
				ml = new MagneticLight(1650f, 525f, GameLevel.this);
				entities.add(ml);
				magneticLights.add(ml);
			}
		});
		
		this.setBackground(new Background(1f, 1f, 1f));
		this.setBackgroundEnabled(true);
		this.registerUpdateHandler(updateGameLevel);
		this.setOnSceneTouchListener(this);
	}
	
	public void disposeLevel() {
		this.gameCamera.setChaseEntity(null);
		GameCamera.setupMenus();
	}
	
	private void createScoreTextPool() {
		scoreTextPool = new GenericPool<Text>() {

			@Override
			protected Text onAllocatePoolItem() {
				return new Text(0f, 0f, ResourceManager.getInstance().getFont(), "", 12,
					ResourceManager.getInstance().getActivity().getVertexBufferObjectManager()) {
						Text t = this;
					
						@Override
						public void onAttached() {
							setVisible(true);
							setAlpha(1f);
							setScale(1f);
							setColor(Color.YELLOW);
						}
						
						@Override
						protected void onManagedUpdate(float pSecondsElapsed) {
							super.onManagedUpdate(pSecondsElapsed);
							setAlpha(this.getAlpha() - pSecondsElapsed/2f);
							setScale(this.getScaleX() - pSecondsElapsed);
							if(this.getAlpha() <= 0.1f) {
								this.setVisible(false);
								ResourceManager.getInstance().getActivity().runOnUpdateThread(new Runnable() {
									@Override
									public void run() {
										t.detachSelf();
										GameLevel.this.scoreTextPool.recyclePoolItem(t);
									}
								});
							}
						}
				};
			}
		};
	}
	
	public void addPoints(final int points) {
		currentScore += points;
		scoreText.setText(String.valueOf(currentScore));
		
		Text scorePop = scoreTextPool.obtainPoolItem();
		scorePop.setColor(Color.YELLOW);
		scorePop.setText(String.valueOf(points));
		scorePop.setPosition(magneticHero.getX(), magneticHero.getY());
		
		if(magneticHero.spriteBody.hasParent())
			magneticHero.spriteBody.getParent().attachChild(scorePop);
		else
			attachChild(scorePop);
	}
	
	public IUpdateHandler updateGameLevel = new IUpdateHandler() {

		@Override
		public void onUpdate(float pSecondsElapsed) {
			for (MagneticEntity<Sprite> me : entities)
				me.onUpdate(pSecondsElapsed);
		}

		@Override
		public void reset() {}
	};
	
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		if(magneticBarrels.size() > 0)
			for(MagneticBarrel mb : magneticBarrels)
				mb.onTouchInput(pSceneTouchEvent);
		return true;
	}
	
	public boolean testCollisionsBarrels() {
		for(MagneticBarrel mb : magneticBarrels)
			if(mb.rectangle.collidesWith(magneticHero.rectangle))
				return true;
		return false;
	}
}