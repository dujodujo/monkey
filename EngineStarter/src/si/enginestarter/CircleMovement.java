package si.enginestarter;

import org.andengine.entity.primitive.Vector2;
import org.andengine.entity.shape.IShape;
import org.andengine.util.math.MathUtils;

import android.util.Log;

public class CircleMovement<T extends MagneticEntity<IShape>> implements Movement {
	public static String TAG = CircleMovement.class.getName();
	
	protected T entity;
	protected Vector2 center;
	protected float radius;
	protected float angle;
	
	public CircleMovement(T entity, final float pRadius, final float startAngle) {
		this.entity = entity;
		
		setAngle(startAngle);
		setCenter(entity.getX(), entity.getY());
		setRadius(pRadius);
	}
	
	@Override
	public void movement() {
		entity.body.setLinearVelocity(entity.getSpeed().x, entity.getSpeed().y);
		recomputeMovement();
	}
	
	public void setCenter(float pX, float pY) { center = new Vector2(pX, pY); }
	
	public void setRadius(float pRadius) { radius = pRadius; }
	
	public void setAngle(float startAngle) { angle = startAngle; }
	
	private void recomputeMovement() {
		float pX = entity.spriteBody.getX();
		float pY = entity.spriteBody.getY();

		angle++;
		
		float vx = entity.body.getLinearVelocity().x;
		float vy = entity.body.getLinearVelocity().y;
		
		pX += radius * vx * com.badlogic.gdx.math.MathUtils.cos(MathUtils.degToRad(angle));
		pY += radius * vy * com.badlogic.gdx.math.MathUtils.sin(MathUtils.degToRad(angle));
		entity.setPosition(pX, pY);
	}

	@Override
	public void setSpeed(Vector2 pSpeed) { entity.body.setLinearVelocity(pSpeed.x, pSpeed.y); }

	@Override
	public void stop() { entity.body.setLinearVelocity(0.f, 0.f); }
}