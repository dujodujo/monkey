package si.enginestarter;

import org.andengine.entity.primitive.Vector2;
import org.andengine.entity.shape.IShape;
import org.andengine.util.math.MathUtils;

public class StopMovement<T extends MagneticEntity<IShape>> implements Movement {
	private static final String TAG = StopMovement.class.getName();
	
	T entity;

	public StopMovement(T entity) {
		this.entity = entity;
	}
	
	@Override
	public void movement() {
		recomputeMovement();
	}
	
	private void recomputeMovement() { stop(); }

	@Override
	public void setSpeed(Vector2 pSpeed) { entity.body.setLinearVelocity(pSpeed.x, pSpeed.y); }

	@Override
	public void stop() { entity.body.setLinearVelocity(0f, 0f); }
}