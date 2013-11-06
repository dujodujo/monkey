package si.enginestarter;

import java.util.Arrays;
import java.util.List;

import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.shape.IShape;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.input.touch.TouchEvent;

import si.enginestarter.MagneticType.EntityType;

import android.net.nsd.NsdManager.RegistrationListener;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class MagneticLight extends MagneticEntity<AnimatedSprite> {
	private static String TAG = MagneticLight.class.getName();
	
	private static final float densityLight = 2.f;
	private static final float elasticityLight = 0f;
	private static final float frictionLight = 0.9f;
	
	private static final FixtureDef lightFixtureDef = PhysicsFactory.createFixtureDef(densityLight,
		elasticityLight, frictionLight);
	
	private static float DURATION;
	private float light;
	private boolean lightOn;
	
	public MagneticLight(final float pX, final float pY, final GameLevel pGameLevel) {
		super(pGameLevel);
		
		gameLevel.positions.add(new float[] {pX, pY});

		loadSprites(pX, pY);
		loadRectangle(pX, pY);
		loadBody();
		loadPhysicsConnector();
		
		DURATION = 2f;
		lightOn = true;
		light = 0;
		
		setPosition(pX, pY);
		set(body, spriteBody, physicsConnector, rectangle);
		
		gameLevel.attachChild(spriteBody);
	}
	
	@Override
	public EntityType getEntity() { return EntityType.MAGNETIC_LIGHT; }
	
	@Override
	public void loadSprites(float pX, float pY) {
		spriteBody = new AnimatedSprite(pX, pY, ResourceManager.lightBarrelTex,
			ResourceManager.getInstance().getActivity().getVertexBufferObjectManager());
		spriteBody.setCurrentTileIndex(0);
	}

	@Override
	public void loadRectangle(float pX, float pY) {
		rectangle = new Rectangle(pX, pY, spriteBody.getWidth(), spriteBody.getHeight(), 
			ResourceManager.getInstance().getActivity().getVertexBufferObjectManager());
	}

	@Override
	public void loadBody() {
		body = PhysicsFactory.createBoxBody(gameLevel.getWorld(), rectangle, BodyType.KinematicBody, lightFixtureDef);
	}

	@Override
	public void loadPhysicsConnector() {
		physicsConnector = new PhysicsConnector(rectangle, body);
		gameLevel.getWorld().registerPhysicsConnector(physicsConnector);
	}
	
	@Override
	public void onUpdate(float pSecondsElapsed) {
		Rectangle hero = gameLevel.magneticHero.rectangle;
		if(rectangle.collidesWith(hero)) {
			lightOn = false;
			if(light < DURATION) {
				light += pSecondsElapsed;
				spriteBody.setCurrentTileIndex(1);
			} else {
				light = 0;
			}
		} else {
			lightOn = true;
		}
		
		if(lightOn)
			spriteBody.setCurrentTileIndex(0);
	}
}