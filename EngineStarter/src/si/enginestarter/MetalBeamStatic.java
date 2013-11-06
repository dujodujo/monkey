package si.enginestarter;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.shape.IShape;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

import si.enginestarter.MagneticType.EntityType;

import android.util.Log;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class MetalBeamStatic<T> extends MagneticBeam<IShape> {
	private static final String TAG = MetalBeamStatic.class.getName();
	
	private static final float platformDensity = 1f;
	private static final float platformElasticity = 1f;
	private static final float platformFriction = 1f;
	private static FixtureDef platformFixtureDef;
	
	protected ITextureRegion texture;
	
	protected float platformAngle;
	
	public MetalBeamStatic(float pX, float pY, float pLength, float pHeight, float pAngle, GameLevel gameLevel) {
		super(gameLevel);

		gameLevel.positions.add(new float[] {pX, pY});

		loadTexture(pLength, pHeight);
		loadSprites(pX, pY);
		loadRectangle(pX, pY);
		loadBody();
		
		setMetalBeamAngle(pAngle);
		
		gameLevel.attachChild(spriteBody);
	}
	
	@Override
	public EntityType getEntity() { return EntityType.MAGNETIC_BEAM; }

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
		platformFixtureDef = PhysicsFactory.createFixtureDef(platformDensity, platformElasticity, platformFriction);
		body = PhysicsFactory.createBoxBody(gameLevel.getWorld(), rectangle, 
			BodyType.KinematicBody, platformFixtureDef);
	}

	@Override
	public void loadPhysicsConnector() {
		physicsConnector = new PhysicsConnector(rectangle, body);
		gameLevel.getWorld().registerPhysicsConnector(physicsConnector);
	}

	@Override
	public void loadTexture(float pLength, float pHeight) {
		texture = ResourceManager.metalBeamStaticTex.deepCopy();
		texture.setTextureWidth(pLength);
		texture.setTextureWidth(pHeight);
	}
	
	private void setMetalBeamAngle(float angle) {
		this.platformAngle = angle;
	}
	
	@Override public void onUpdate(float pSecondsElapsed) {}
}
