package si.enginestarter;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.primitive.Vector2;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.color.Color;

import si.enginestarter.MagneticType.EntityType;

import android.util.Log;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class MetalBeamDynamic<T> extends MagneticBeam<Sprite> {
	private static final String TAG = MetalBeamDynamic.class.getName();
	
	private static final float platformDensity = 1f;
	private static final float platformElasticity = 1f;
	private static final float platformFriction = 1f;
	private static final float massplatform = 1.f;
	
	private static FixtureDef platformFixtureDef;
	
	protected ITextureRegion texture;
	
	protected float platformDistance;
	protected float platformSpeed;
	protected float platformAngle;
		
	public MetalBeamDynamic(float pX, float pY, float pLength, float pHeight, float pAngle, GameLevel pGameLevel) {
		super(pGameLevel);
		
		gameLevel.positions.add(new float[] {pX, pY});
		
		loadTexture(pLength, pHeight);
		loadSprites(pX, pY);
		loadRectangle(pX, pY);
		loadBody();
		loadPhysicsConnector();
		
		setPosition(pX, pY);
		set(body, spriteBody, physicsConnector, rectangle);
		
		gameLevel.attachChild(spriteBody);
	}
	
	@Override
	public EntityType getEntity() { return EntityType.MAGNETIC_BEAM; }
	
	@Override
	public void loadTexture(float pWidth, float pHeight) {
		texture = ResourceManager.metalBeamDynamicTex.deepCopy();
		texture.setTextureWidth(pWidth);
		texture.setTextureHeight(pHeight);
	}
	
	@Override
	public void loadSprites(float pX, float pY) {
		spriteBody = new Sprite(pX, pY, texture, 
			ResourceManager.getInstance().getActivity().getVertexBufferObjectManager());
	}

	@Override
	public void loadRectangle(float pX, float pY) {
		rectangle = new Rectangle(pX, pY, texture.getWidth(), texture.getHeight(),
			ResourceManager.getInstance().getActivity().getVertexBufferObjectManager());
	}

	@Override
	public void loadBody() {
		body = PhysicsFactory.createBoxBody(gameLevel.getWorld(), rectangle, BodyType.KinematicBody, platformFixtureDef);
	}

	@Override
	public void loadPhysicsConnector() {
		physicsConnector = new PhysicsConnector(rectangle, body);
		gameLevel.getWorld().registerPhysicsConnector(physicsConnector);
	}
}