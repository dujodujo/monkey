package si.enginestarter;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.shape.IShape;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;

import si.enginestarter.MagneticType.EntityType;

import android.util.Log;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class WoodenBeamDynamic<T> extends MagneticBeam<IShape> {
	private static final String TAG = WoodenBeamDynamic.class.getName();
	
	protected float platformAngle;
	
	private static final float platformDensity = 1f;
	private static final float platformElasticity = 1f;
	private static final float platformFriction = 1f;
	private static FixtureDef platformFixtureDef;
	
	protected ITextureRegion texture;
	
	private boolean remove;
	
	public WoodenBeamDynamic(float pX, float pY, float pLength, float pHeight, float pAngle, GameLevel gameLevel) {
		super(gameLevel);
		
		gameLevel.positions.add(new float[] {pX, pY});
		
		loadTexture(pLength, pHeight);
		loadSprites(pX, pY);
		loadRectangle(pX, pY);
		loadBody();
				
		setWoodenBeamAngle(pAngle);
		
		remove = false;

		gameLevel.attachChild(spriteBody);
	}
	
	@Override
	public EntityType getEntity() { return EntityType.MAGNETIC_BEAM; }
	
	@Override
	public void loadSprites(float pX, float pY) {
		spriteBody = new Sprite(pX, pY, texture, ResourceManager.getInstance().getActivity().getVertexBufferObjectManager());
	}

	@Override
	public void loadRectangle(float pX, float pY) {
		rectangle = new Rectangle(pX, pY, texture.getWidth(), texture.getHeight(), 
			ResourceManager.getInstance().getActivity().getVertexBufferObjectManager());
	}

	@Override
	public void loadBody() {
		platformFixtureDef = PhysicsFactory.createFixtureDef(platformDensity, platformElasticity, platformFriction);
		body = PhysicsFactory.createBoxBody(gameLevel.getWorld(), rectangle, BodyType.StaticBody,
			platformFixtureDef);

	}

	@Override
	public void loadPhysicsConnector() {
		physicsConnector = new PhysicsConnector(rectangle, body);
		gameLevel.getWorld().registerPhysicsConnector(physicsConnector);
	}
	
	@Override
	public void loadTexture(float pWidth, float pHeight) {
		texture = ResourceManager.woodenDynamicTex.deepCopy();
		texture.setTextureWidth(pWidth);
		texture.setTextureHeight(pHeight);
	}
	
	private final TouchInput woodTouch = new TouchInput() {
		
		@Override
		protected boolean onActionDown(float pX, float pY) {
			if(rectangle.getX() < pX && pX < (rectangle.getX() + rectangle.getWidth()))
				if(rectangle.getY() < pY && pY < (rectangle.getY() + rectangle.getHeight()))
					return true;
			return false;
		}
		
		@Override
		protected void onActionMove(float pX, float pY, float xEnd, float yEnd, float xStart, float yStart) {}

		@Override
		protected void onActionUp(float pX, float pY, float xEnd, float yEnd, float xStart, float yStart) {}
	};
	
	
	private void setWoodenBeamAngle(float pAngle) {
		platformAngle = pAngle;
	}
	
	@Override public void onUpdate(float pSecondsElapsed) {}

	@Override public void reset() {}

	public void onTouchInput(TouchEvent pSceneTouchEvent) {
		if(woodTouch.onTouch(pSceneTouchEvent))
			Log.d(TAG, "true");
		else
			Log.d(TAG, "false");
	}
}