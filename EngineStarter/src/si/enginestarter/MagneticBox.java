package si.enginestarter;

import java.util.Iterator;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.shape.IShape;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.entity.text.Text;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.adt.pool.GenericPool;
import org.andengine.util.color.Color;

import si.enginestarter.Levels.MagneticBoxDef;
import si.enginestarter.MagneticType.EntityType;

import android.util.Log;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;

public class MagneticBox extends MagneticEntity<Sprite> {
	private static String TAG = MagneticBox.class.getName();
	
	protected MagneticBoxDef.BoxSize boxSize;
	protected MagneticBoxDef.BoxType boxType;
	private TiledTextureRegion boxTiledSpriteTTex;
	
	private static final float densityBox = 30.f;
	private static final float elasticityBox = 0.1f;
	private static final float frictionBox = 0.5f;
	
	private static final FixtureDef boxFixtureDef = 
		PhysicsFactory.createFixtureDef(densityBox, elasticityBox, frictionBox);
	
	protected GameLevel gameLevel;
	
	protected GenericPool<MagneticBox> boxesPool;
	
	public MagneticBox(float pX, float pY, MagneticBoxDef.BoxSize boxSize, MagneticBoxDef.BoxType boxType, GameLevel gameLevel) {
		super(gameLevel);
		this.boxSize = boxSize;
		this.boxType = boxType;
		
		this.gameLevel.addPosition(new float[] {pX, pY});
		
		loadBoxSize();
		loadSprites(pX, pY);
		loadRectangle(pX, pY);
		loadBody();
		loadPhysicsConnector();
		
		setPosition(pX, pY);
		set(body, spriteBody, physicsConnector, rectangle);
		
		gameLevel.attachChild(spriteBody);
	}
	
	private void loadBoxSize() {
		switch(this.boxSize) {
			case SMALL:
				boxTiledSpriteTTex = ResourceManager.boxSmallTTex; break;
			case LARGE:
				boxTiledSpriteTTex = ResourceManager.boxLargeTTex; break;
			default:
				boxTiledSpriteTTex = ResourceManager.boxSmallTTex; break;
		}
	}
	
	@Override
	public EntityType getEntity() { return EntityType.MAGNETIC_BOX; }

	@Override
	public void loadSprites(float pX, float pY) {
		
		spriteBody = new TiledSprite(pX, pY, boxTiledSpriteTTex,
			ResourceManager.getInstance().getActivity().getVertexBufferObjectManager()) {
			
			@Override
			protected void onManagedUpdate(float pSecondsElapsed) {
				super.onManagedUpdate(pSecondsElapsed);
			}
		};
		spriteBody.setScale(.25f, .25f);
		boxTiledSpriteTTex.setCurrentTileIndex(boxType.type);
	}

	@Override
	public void loadRectangle(float pX, float pY) {
		rectangle = new Rectangle(pX, pY, boxTiledSpriteTTex.getWidth(), boxTiledSpriteTTex.getHeight(), 
			ResourceManager.getInstance().getActivity().getVertexBufferObjectManager());
	}

	@Override
	public void loadBody() {
		body = PhysicsFactory.createBoxBody(gameLevel.getWorld(), rectangle, BodyType.StaticBody, boxFixtureDef);
	}

	@Override
	public void loadPhysicsConnector() {
		physicsConnector = new PhysicsConnector(rectangle, body);
		gameLevel.getWorld().registerPhysicsConnector(physicsConnector);
	}
	
	public void createBoxExplosion() {
		/*
		Iterator<Body> bodies = gameLevel.getWorld().getBodies();
		int i = 0;
		while(bodies.hasNext()) {
			Body b = bodies.next();
			i++;
			Log.d(TAG, String.valueOf(i));
			if(b.getType() == BodyType.DynamicBody) {
				Vector2 bodyPosition = Vector2Pool.obtain(b.getWorldCenter());
				Vector2 directionBodyBomb = Vector2Pool.obtain(bombPosition).sub(bodyPosition).nor();
				float dist = bodyPosition.dst(bombPosition);
				Vector2 force = Vector2Pool.obtain(directionBodyBomb).mul(pExplosionConstant*1/dist);
				b.applyForce(force, b.getWorldCenter());
				
				Vector2Pool.recycle(force);
				Vector2Pool.recycle(directionBodyBomb);
				Vector2Pool.recycle(bodyPosition);
			}
		}
		*/
	}

	@Override
	public void onUpdate(float pSecondsElapsed) {
		//createBoxExplosion();
	}
}
