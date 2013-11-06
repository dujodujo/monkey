package si.enginestarter;

import org.andengine.entity.primitive.Vector2;
import org.andengine.entity.shape.IShape;
import org.andengine.util.math.MathUtils;

import android.util.Log;

public class HalfCircleMovement<T extends MagneticEntity<IShape>> implements Movement {
	private static final String TAG = HalfCircleMovement.class.getName();
	
	T entity;
	private float currentAngle;
	private float nextAngle;
	private float minAngle;
	private float maxAngle;
	private float radius;

	public HalfCircleMovement(T entity, final float minAngle, final float maxAngle, final float pRadius) {
		this.entity = entity;
		
		this.currentAngle = minAngle;
		this.nextAngle = currentAngle + 1;
		this.maxAngle = maxAngle;
		this.radius = pRadius;
	}
	
	@Override
	public void movement() {
		recomputeMovement();
	}
	
	private void recomputeMovement() {
		float pX = entity.spriteBody.getX();
		float pY = entity.spriteBody.getY();
		
		float vx = entity.body.getLinearVelocity().x;
		float vy = entity.body.getLinearVelocity().y;
		
		if(nextAngle >= currentAngle) {
			if(nextAngle == minAngle ) {
				currentAngle = minAngle;
				nextAngle++;
			} else if(currentAngle < maxAngle){
				currentAngle++;
				nextAngle++;
			} else {
				--nextAngle;
				--nextAngle;
			}
			pX += radius * vx * com.badlogic.gdx.math.MathUtils.cos(MathUtils.degToRad(currentAngle));
			pY += radius * vy * com.badlogic.gdx.math.MathUtils.sin(MathUtils.degToRad(currentAngle));
		} else {
			if(currentAngle > nextAngle && currentAngle > minAngle) {
				currentAngle--;
				nextAngle--;
			} else if(currentAngle == minAngle){
				currentAngle = minAngle;
				nextAngle = currentAngle;
				nextAngle++;
			}
			pX -= radius * vx * com.badlogic.gdx.math.MathUtils.cos(MathUtils.degToRad(currentAngle));
			pY -= radius * vy * com.badlogic.gdx.math.MathUtils.sin(MathUtils.degToRad(currentAngle));
		}
		entity.setPosition(pX, pY);
		
		Log.d(TAG, "start");
		Log.d(TAG, String.valueOf(pX) + " " + String.valueOf(pY));
		Log.d(TAG, String.valueOf(entity.rectangle.getX()) + " " + String.valueOf(entity.rectangle.getY()));
		Log.d(TAG, String.valueOf(entity.spriteBody.getX()) + " " + String.valueOf(entity.spriteBody.getY()));
	}

	@Override
	public void setSpeed(Vector2 pSpeed) { entity.body.setLinearVelocity(pSpeed.x, pSpeed.y); }

	@Override
	public void stop() {
		
	}
}