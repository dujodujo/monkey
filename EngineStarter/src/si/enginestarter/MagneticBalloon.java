package si.enginestarter;

import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;

import android.util.Log;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import si.enginestarter.MagneticType.EntityType;


public class MagneticBalloon extends MagneticEntity<AnimatedSprite> {
	private static String TAG = MagneticBalloon.class.getName();
	
	protected boolean picked;
	
	private static final float densityBarrel = 2.f;
	private static final float elasticityBarrel = 0f;
	private static final float frictionBarrel = 0.9f;
	
	private static final FixtureDef balloonFixtureDef = PhysicsFactory.createFixtureDef(densityBarrel,
		elasticityBarrel, frictionBarrel);
	
	public MagneticBalloon(final float pX, final float pY, final GameLevel pGameLevel) {
		super(pGameLevel);
		
		gameLevel.positions.add(new float[] {pX, pY});

		loadSprites(pX, pY);
		loadRectangle(pX, pY);
		loadBody();
		loadPhysicsConnector();
		
		setPosition(pX, pY);
		set(body, spriteBody, physicsConnector, rectangle);
		picked = false;
		
		gameLevel.attachChild(spriteBody);
	}
	
	@Override
	public EntityType getEntity() { return EntityType.MAGNETIC_BALOON; }
	
	@Override
	public void loadSprites(float pX, float pY) {
		spriteBody = new AnimatedSprite(pX, pY, ResourceManager.bananaTTex,
			ResourceManager.getInstance().getActivity().getVertexBufferObjectManager()) {

				@Override
				protected void onManagedUpdate(float pSecondsElapsed) {
					super.onManagedUpdate(pSecondsElapsed);
					
					if(picked) {
						this.registerEntityModifier(new MoveModifier(4f,
							this.getX(), gameLevel.scoreText.getX(), this.getY(), gameLevel.scoreText.getY()));
						destroy(true);
					}
				}
		};
			
		spriteBody.animate(100, new IAnimationListener() {
			
			@Override public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite,int pRemainingLoopCount, int pInitialLoopCount) {
			}
			@Override public void onAnimationStarted(AnimatedSprite pAnimatedSprite,int pInitialLoopCount) {}
			@Override public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite, int pOldFrameIndex,int pNewFrameIndex) {}
			@Override public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
			}
		});
	}

	@Override
	public void loadRectangle(float pX, float pY) {
		rectangle = new Rectangle(pX, pY, spriteBody.getWidth(), spriteBody.getHeight(), 
			ResourceManager.getInstance().getActivity().getVertexBufferObjectManager());
	}

	@Override
	public void loadBody() {
		body = PhysicsFactory.createBoxBody(gameLevel.getWorld(), rectangle, BodyType.KinematicBody, balloonFixtureDef);
	}

	@Override
	public void loadPhysicsConnector() {
		physicsConnector = new PhysicsConnector(rectangle, body);
		gameLevel.getWorld().registerPhysicsConnector(physicsConnector);
	}
	
	@Override
	public void onUpdate(float pSecondsElapsed) {
		MagneticHero magneticHero = gameLevel.getMagneticHero();
		
		if(!picked) {
			if(rectangle.collidesWith(magneticHero.rectangle)) {
				gameLevel.addPoints(5);
				picked = true;
			}
		}
	}
}