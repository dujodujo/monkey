package si.enginestarter;

import org.andengine.entity.primitive.Vector2;
import org.andengine.entity.shape.IShape;
import org.andengine.util.math.MathUtils;

import android.util.Log;

public class LinearMovement<T extends MagneticEntity<IShape>> implements Movement {
	public static String TAG = LinearMovement.class.getName();
	
	T entity;
	float dir;
		
	public LinearMovement(T entity) {
		this.entity = entity;
	}
	
	@Override
	public void movement() {
		float dir = entity.getDirection();

		float aX = com.badlogic.gdx.math.MathUtils.cos(MathUtils.degToRad(dir));
		float aY = com.badlogic.gdx.math.MathUtils.sin(MathUtils.degToRad(dir));
		
		Log.d(TAG, "angle");
		Log.d(TAG, String.valueOf(aX) + " " + String.valueOf(aY));
		
		entity.body.setLinearVelocity(entity.getSpeed().x * aX, entity.getSpeed().y * aY);
		recomputeMovement();
	}
	
	private void recomputeMovement() {
		float pX = entity.spriteBody.getX();
		float pY = entity.spriteBody.getY();
		
		float vX = entity.body.getLinearVelocity().x;
		float vY = entity.body.getLinearVelocity().y;
		
		pX += vX;
		pY += vY;
		entity.setPosition(pX, pY);
		
		Log.d(TAG, String.valueOf(vX) + " " + String.valueOf(vY));
	}
	
	public void stop() { entity.body.setLinearVelocity(0.f, 0.f); }

	@Override
	public void setSpeed(Vector2 pSpeed) { entity.body.setLinearVelocity(pSpeed.x, pSpeed.y); }
}