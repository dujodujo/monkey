package si.enginestarter;

import java.util.ArrayList;
import java.util.List;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.primitive.Vector2;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.shape.IShape;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.color.Color;

import si.enginestarter.MagneticType.EntityType;

import android.net.nsd.NsdManager.RegistrationListener;
import android.opengl.GLES20;
import android.util.FloatMath;
import android.util.Log;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class MagneticHero extends MagneticEntity<AnimatedSprite> {
	private static String TAG = MagneticHero.class.getName();
	
	protected MagneticBarrel barrel;
	
	private static final float densityHero = 2.f;
	private static final float elasticityHero = 0f;
	private static final float frictionHero = 0.9f;
	
	protected State state;
	protected Movement currentMovement;
	
	protected boolean collision;
	
	private static final FixtureDef heroFixtureDef = PhysicsFactory.createFixtureDef(densityHero,
		elasticityHero, frictionHero);
	
	public MagneticHero(final float pX, final float pY, final Vector2 pSpeed, final GameLevel pGameLevel) {
		super(pGameLevel);
		
		state = null;
		gameLevel.positions.add(new float[] {pX, pY});
		
		loadSprites(pX, pY);
		loadRectangle(pX, pY);
		loadBody();
		loadPhysicsConnector();		
		
		setBarrel(null);
		setSpeed(pSpeed);
		setHeroDirection(0);
		setState(new MovementState(this, gameLevel));
		set(body, spriteBody, physicsConnector, rectangle);
				
		gameLevel.attachChild(spriteBody);
	}
	
	@Override 
	public EntityType getEntity() { return EntityType.MAGNETIC_HERO; }
	
	@Override
	public void loadSprites(float pX, float pY) {
		spriteBody = new AnimatedSprite(pX, pY, ResourceManager.heroTTex,
			ResourceManager.getInstance().getActivity().getVertexBufferObjectManager());
		spriteBody.setBlendFunction(GLES20.GL_ONE, GLES20.GL_ONE);
		spriteBody.setBlendingEnabled(true);
		
		spriteBody.animate(100, new IAnimationListener() {
			
			@Override public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite,int pRemainingLoopCount, int pInitialLoopCount) {}
			@Override public void onAnimationStarted(AnimatedSprite pAnimatedSprite,int pInitialLoopCount) {}
			@Override public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite, int pOldFrameIndex,int pNewFrameIndex) {}
			@Override public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {}
		});
	}

	@Override
	public void loadRectangle(float pX, float pY) {
		rectangle = new Rectangle(pX, pY, spriteBody.getWidth(), spriteBody.getHeight(), 
			ResourceManager.getInstance().getActivity().getVertexBufferObjectManager());
	}

	@Override
	public void loadBody() {
		body = PhysicsFactory.createBoxBody(gameLevel.getWorld(), rectangle, BodyType.KinematicBody, heroFixtureDef);
	}

	@Override
	public void loadPhysicsConnector() {
		physicsConnector = new PhysicsConnector(rectangle, body);
		gameLevel.getWorld().registerPhysicsConnector(physicsConnector);
	}
	
	public void setHeroVisible() { spriteBody.setVisible(true); }
	
	public void setHeroInvisible() { spriteBody.setVisible(false); }
	
	public void setBarrel(MagneticBarrel barrel) { this.barrel = barrel; }
	
	public void setState(State newState) {
		State oldState = state;
		if(oldState != null)
			oldState.stop(newState);
		state = newState;
		state.start(oldState);
	}
	
	public void setBarrelObstacle(boolean collision) {
		this.collision = collision;
		barrel.setHeroCollision(collision);
	}
	
	public void setHeroDirection(float direction) { currentDirection = direction; }
	
	public boolean isBarrelObstacle() {  return collision; }
	
	public boolean isBarrelClicked() {
		if(barrel != null)
			return barrel.isClicked();
		return false;
	}
	
	public MagneticBarrel getBarrel() { return barrel; }
	
	public AnimatedSprite getHeroSprite() { return spriteBody; }
			
	public void setBarrelCollision(MagneticBarrel barrel) {
		setBarrel(barrel);
		setPosition(barrel.getCenterX(), barrel.getCenterY());
		setHeroInvisible();
	}
	
	public boolean checkCollision() { return gameLevel.testCollisionsBarrels(); }
	
	private void updateState(float pSecondsElapsed) { state.onUpdate(pSecondsElapsed); }
	
	@Override
	public void updateMovement() {
		currentMovement.movement();
	}
	
	@Override
	public void onUpdate(float pSecondsElapsed) {
		updateMovement();
		updateState(pSecondsElapsed);
	}
}