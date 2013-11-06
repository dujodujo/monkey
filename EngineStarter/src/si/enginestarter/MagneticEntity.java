package si.enginestarter;

import java.util.Currency;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.primitive.Vector2;
import org.andengine.entity.shape.IShape;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;

import si.enginestarter.MagneticType.EntityType;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public abstract class MagneticEntity<T extends IShape> implements IUpdateHandler, MEntity {
	public static String TAG = MagneticEntity.class.getName();

	protected boolean remove;
	protected boolean movement = false;
	
	protected Body body;
	public T spriteBody;
	protected PhysicsConnector physicsConnector;
	protected Rectangle rectangle;
	
	protected Vector2 speed;
	
	protected GameLevel gameLevel;
	
	protected float currentDirection;
	protected static float directions[] = {90f, 90f, 45f, 45f, 0f, 0f, 315f, 315f, 270f, 270f, 225f, 225f, 
		180f, 180f, 135, 135f};
	
	public MagneticEntity(GameLevel pGameLevel) {
		gameLevel = pGameLevel;
		setSpeed(0f, 0f);
	}
	
	public void set(Body pBody, T pEntity, PhysicsConnector pPhysicsConnector, Rectangle pRectangle) {
		body = pBody;
		spriteBody = pEntity;
		physicsConnector = pPhysicsConnector;
		rectangle = pRectangle;
		remove = false;
	}
	
	public abstract void loadSprites(float pX, float pY);
	public abstract void loadRectangle(float pX, float pY);
	public abstract void loadBody();
	public abstract void loadPhysicsConnector();
	
	public float getX() { return rectangle.getX(); }
	
	public float getY() { return rectangle.getY(); }
	
	public Vector2 getXY() { return new Vector2(getX(), getY()); }
	
	public float getCenterX() { return rectangle.getWidth()/2 + getX(); }
	
	public float getCenterY() { return rectangle.getHeight()/2 + getY(); }
	
	public Vector2 getCenterXY() { return new Vector2(getCenterX(), getCenterY()); }
	
	public Vector2 getPosition() { return new Vector2(getX(), getY()); }
	
	public GameLevel getGameLevel() { return gameLevel; }
	
	public Vector2 getSpeed() { return speed; }
		
	public void setSpeed(Vector2 pSpeed) { setSpeed(pSpeed.x, pSpeed.y); }
	
	public void setSpeed(float pX, float pY) { speed = new Vector2(pX, pY); }
	
	public void setPosition(Vector2 position) { setPosition(position.x, position.y); }
	
	public void setPosition(float pX, float pY) { 
		spriteBody.setPosition(pX, pY);
		rectangle.setPosition(pX, pY);
	}
	
	public void setDirection(int direction) {
		currentDirection = directions[direction];
	}
	
	public float getDirection() { return currentDirection; }
	
	public void movingEntity(boolean m) { movement = m; }
		
	public void destroy(boolean r) {
		remove = r;
		if(!remove) {
			ResourceManager.getInstance().getActivity().runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					gameLevel.getWorld().unregisterPhysicsConnector(physicsConnector);
					gameLevel.getWorld().destroyBody(body);
					spriteBody.detachSelf();
					spriteBody.dispose();
					spriteBody = null;
					body = null;
				}
			});
		}
	}

	public void updateMovement() {}
	
	@Override public void onUpdate(float pSecondsElapsed) {}

	@Override public void reset() {}
}