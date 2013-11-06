package si.enginestarter;

import org.andengine.entity.primitive.Vector2;
import org.andengine.entity.shape.IShape;
import org.andengine.util.math.MathUtils;

import android.util.Log;

public class HarmonicMovement<T extends MagneticEntity<IShape>> implements Movement {
	public static String TAG = LinearMovement.class.getName();
	
	T entity;
	protected float amplitude;
	protected float angle;
	protected float xmin;
	protected float xmax;
	
	public HarmonicMovement(T entity, final float pAmplitude, final float pAngle, 
			final float pMin, final float pMax) {
		this.entity = entity;
		
		this.amplitude = pAmplitude;
		this.angle = pAngle;
		this.xmin = pMin;
		this.xmax = pMax;
	}
	
	@Override
	public void movement() {
		entity.body.setLinearVelocity(entity.getSpeed().x, entity.getSpeed().y);
		recomputeMovement();
	}
	
	private void recomputeMovement() {
		float xposition = entity.spriteBody.getX();
		float yposition = entity.spriteBody.getY();
		float vx = entity.body.getLinearVelocity().x;
		
		angle += 1f;
		float dx = amplitude * vx * com.badlogic.gdx.math.MathUtils.cos(MathUtils.degToRad(angle));
		
		if(dx > xmax)
			xposition = xmax;
		else if(dx < xmin)
			xposition = xmin;
		else
			xposition += dx;
		entity.spriteBody.setPosition(xposition, yposition);
	}

	@Override
	public void setSpeed(Vector2 pSpeed) { entity.body.setLinearVelocity(pSpeed.x, pSpeed.y); }

	@Override
	public void stop() {
		
	}
}