package si.enginestarter;


import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import si.enginestarter.Levels.MagneticEnemyDef.EnemyColor;
import si.enginestarter.MagneticType.EntityType;

import android.util.Log;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;

public class MagneticBee extends MagneticEntity<AnimatedSprite> {
	private static final String TAG = MagneticBee.class.getName();
	
	private boolean collision;
	
	private static final float density = 2.f;
	private static final float elasticity = 0.f;
	private static final float friction = 0.9f;
	
	protected TiledTextureRegion texture;
	
	private static final FixtureDef beeFixtureDef = 
		PhysicsFactory.createFixtureDef(density, elasticity, friction);
	
	public MagneticBee(float pX, float pY, EnemyColor enemyColor, GameLevel pGameLevel) {
		super(pGameLevel);
		
		gameLevel.positions.add(new float[] {pX, pY});
		
		this.collision = false;
		
		texture = loadTexture(enemyColor);
		loadSprites(pX, pY);
		loadRectangle(pX, pY);
		loadBody();
		loadPhysicsConnector();
		
		set(body, spriteBody, physicsConnector, rectangle);
		
		gameLevel.attachChild(spriteBody);
	}
	
	@Override
	public EntityType getEntity() { return EntityType.MAGNETIC_ENEMY; }
	
	@Override
	public void loadSprites(float pX, float pY) {
		spriteBody = new AnimatedSprite(pX, pY, texture, 
			ResourceManager.getInstance().getActivity().getVertexBufferObjectManager());
	}

	@Override
	public void loadRectangle(float pX, float pY) {
		rectangle = new Rectangle(pX, pY, spriteBody.getWidth(), spriteBody.getHeight(), 
			ResourceManager.getInstance().getActivity().getVertexBufferObjectManager());
	}

	@Override
	public void loadBody() {
		body = PhysicsFactory.createBoxBody(gameLevel.getWorld(), rectangle, 
			BodyType.StaticBody, beeFixtureDef);
	}

	@Override
	public void loadPhysicsConnector() {
		physicsConnector = new PhysicsConnector(rectangle, body);
		gameLevel.getWorld().registerPhysicsConnector(physicsConnector);
	}
	
	private TiledTextureRegion loadTexture(EnemyColor enemyColor) {
		TiledTextureRegion texture = null;
		switch(enemyColor) {
			case Red:
				texture = ResourceManager.redBeeTTex; break;
			case Green:
				texture = ResourceManager.greenBeeTTex; break;
			case Yellow:
				texture = ResourceManager.yellowBeeTTex; break;
		}
		return texture;
	}
	
	@Override
	public void onUpdate(float pSecondsElapsed) {
		Log.d(TAG, "Bee");
	}
}
