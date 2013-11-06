package si.enginestarter;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.primitive.Vector2;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.IAnimationData;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.modifier.IModifier.DeepCopyNotSupportedException;

import si.enginestarter.MagneticType.EntityType;

import android.util.Log;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;


public class MagneticBarrel extends MagneticEntity<AnimatedSprite> {
	private static String TAG = MagneticBarrel.class.getName();
	
	private AnimatedSprite spriteSmoke;
	private AnimatedSprite spriteCollision;
	
	protected static final float densityBarrel = 2.f;
	protected static final float elasticityBarrel = 0f;
	protected static final float frictionBarrel = 0.9f;
	
	protected static final FixtureDef barrelFixtureDef = PhysicsFactory.createFixtureDef(densityBarrel,
		elasticityBarrel, frictionBarrel);
	
	protected boolean active;
	private boolean collision;
	protected boolean clicked;
	
	protected float barrelAngle;
	
	protected Movement currentMovement;
	
	RotationDef rotation;
	//RotationClockwiseDef rotation;
	//RotationCircularDef rotationCircular;
	
	public MagneticBarrel(final float pX, final float pY, final int pDirection, final Vector2 pSpeed, 
			StopMovementDef moveDef, final GameLevel pGameLevel) {
		this(pX, pY, pDirection, pSpeed, pGameLevel);
		currentMovement = new StopMovement(this);
	}
	
	public MagneticBarrel(final float pX, final float pY, final int pDirection, final Vector2 pSpeed, 
			CircularMovementDef moveDef, final GameLevel pGameLevel) {
		this(pX, pY, pDirection, pSpeed, pGameLevel);
		currentMovement = new CircleMovement(this, moveDef.radius, moveDef.radius);
	}
	
	public MagneticBarrel(final float pX, final float pY, final int pDirection, final Vector2 pSpeed, 
			LinearMovementDef moveDef, final GameLevel pGameLevel) {
		this(pX, pY, pDirection, pSpeed, pGameLevel);
		currentMovement = new LinearMovement(this);
	}
	
	public MagneticBarrel(final float pX, final float pY, final int pDirection, final Vector2 pSpeed, 
			HalfCircleMovementDef moveDef, final GameLevel pGameLevel) {
		this(pX, pY, pDirection, pSpeed, pGameLevel);
		currentMovement = new HalfCircleMovement(this, moveDef.minAngle, moveDef.maxAngle, moveDef.radius);
	}
	
	public MagneticBarrel(final float pX, final float pY, final int pDirection, final Vector2 pSpeed, 
			HarmonicMovementDef moveDef, final GameLevel pGameLevel) {
		this(pX, pY, pDirection, pSpeed, pGameLevel);
		currentMovement = new HarmonicMovement(this, moveDef.amplitude, moveDef.angle, moveDef.xmin, moveDef.xmax);
	}
	
	private MagneticBarrel(final float pX, final float pY, final int pDirection, final Vector2 pSpeed, 
			final GameLevel pGameLevel) {
		super(pGameLevel);
		
		gameLevel.positions.add(new float[] {pX, pY});

		loadSprites(pX, pY);
		loadRectangle(pX, pY);
		loadBody();
		loadPhysicsConnector();
		
		active = false;
		clicked = false;
		
		//rotation = new RotationClockwiseDef(0, 4, 0, 0.1f, 0);
		//rotationCircular = new RotationCircularDef(0, 15, 0, 0.1f, 0);
		rotation = new StopRotationDef(5, 5, 0, 0.1f);
		
		setSpeed(pSpeed);
		setHeroCollision(true);
		setDirection(pDirection);
		setPosition(pX, pY);
		set(body, spriteBody, physicsConnector, rectangle);
		
		gameLevel.attachChild(spriteBody);
		gameLevel.attachChild(spriteSmoke);
		gameLevel.attachChild(spriteCollision);
	}
	
	@Override
	public EntityType getEntity() { return EntityType.MAGNETIC_BARREL; }
	
	@Override
	public void loadSprites(float pX, float pY) {
		spriteBody = new AnimatedSprite(pX, pY, ResourceManager.normalBarrelTex,
			ResourceManager.getInstance().getActivity().getVertexBufferObjectManager()) {
			
				@Override
				protected void onManagedUpdate(float pSecondsElapsed) {
					super.onManagedUpdate(pSecondsElapsed);
					
					int currentTileIndex = rotation.update(pSecondsElapsed, spriteBody.getCurrentTileIndex());
					setBarrelDirection(currentTileIndex);
					spriteBody.setCurrentTileIndex(currentTileIndex);
				}
		};
		
		spriteSmoke = new AnimatedSprite(pX, pY, ResourceManager.smokeTTex,
			ResourceManager.getInstance().getActivity().getVertexBufferObjectManager());
		spriteSmoke.setVisible(false);
		
		spriteCollision = new AnimatedSprite(pX, pY, ResourceManager.collisionTTex,
			ResourceManager.getInstance().getActivity().getVertexBufferObjectManager());
		spriteCollision.setVisible(false);
	}
	
	protected void setBarrelDirection(int direction) { setDirection(direction); }
	
	@Override
	public void loadRectangle(float pX, float pY) {
		rectangle = new Rectangle(pX, pY, spriteBody.getWidth(), spriteBody.getHeight(), 
			ResourceManager.getInstance().getActivity().getVertexBufferObjectManager());
	}

	@Override
	public void loadBody() {
		body = PhysicsFactory.createBoxBody(gameLevel.getWorld(), rectangle, BodyType.KinematicBody, barrelFixtureDef);
	}

	@Override
	public void loadPhysicsConnector() {
		physicsConnector = new PhysicsConnector(rectangle, body);
		gameLevel.getWorld().registerPhysicsConnector(physicsConnector);
	}

	public void startCollisionAnimation(float pX, float pY) {
		spriteCollision.animate(30, new IAnimationListener() {
			
			@Override public void onAnimationStarted(AnimatedSprite pAnimatedSprite, int pInitialLoopCount) {
				spriteCollision.setVisible(true);
			}
			
			@Override public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite, int pRemainingLoopCount, int pInitialLoopCount) {
				spriteCollision.setVisible(false);
			}
			
			@Override public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite, int pOldFrameIndex, int pNewFrameIndex) {}
			
			@Override public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
				spriteCollision.setVisible(false);
			}
		});
	}
	
	public void startSmokeAnimation() {
		spriteSmoke.setVisible(true);
		spriteSmoke.animate(40, new IAnimationListener() {
			
			@Override public void onAnimationStarted(AnimatedSprite pAnimatedSprite, int pInitialLoopCount) {}
			
			@Override public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite, int pRemainingLoopCount, int pInitialLoopCount) {
				spriteSmoke.setVisible(false);
			}
			
			@Override public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite, int pOldFrameIndex, int pNewFrameIndex) {}
			
			@Override public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
				spriteSmoke.setVisible(false);
			}
		});
	}
		
	public void setHeroCollision(boolean collision) { this.collision = collision; }
	
	public boolean isClicked() { return clicked; }
	
	public boolean isHeroCollision() { return collision; }
	
	public void notifyCollision() {
		startCollisionAnimation(getCenterX(), getCenterY());
		
		MagneticHero hero = gameLevel.getMagneticHero();
		hero.setBarrelCollision(this);
	}
	
	private final TouchInput barrelTouch = new TouchInput() {
		
		@Override
		protected boolean onActionDown(float pX, float pY) {
			if(rectangle.contains(pX, pY))
				return true;
			return false;
		}
		
		@Override
		protected void onActionMove(float pX, float pY, float xEnd, float yEnd, float xStart, float yStart) {}

		@Override
		protected void onActionUp(float pX, float pY, float xEnd, float yEnd, float xStart, float yStart) {}
	};
	
	public void onTouchInput(TouchEvent pSceneTouchEvent) {
		if(barrelTouch.onTouch(pSceneTouchEvent)) {
			if(active) {
				startSmokeAnimation();
				clicked = true;
				gameLevel.magneticHero.setHeroDirection(getDirection());
			}
		}
	}
		
	@Override
	public void updateMovement() { currentMovement.movement(); }

	@Override
	public void onUpdate(float pSecondsElapsed) {
		updateMovement();
		
		Rectangle hero = gameLevel.magneticHero.rectangle;
		if(rectangle.collidesWith(hero) && isHeroCollision()) {
			active = true;
			notifyCollision();
			gameLevel.magneticHero.setHeroDirection(getDirection());
		}
	}
}