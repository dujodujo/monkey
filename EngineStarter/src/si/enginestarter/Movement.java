package si.enginestarter;

import org.andengine.entity.primitive.Vector2;

public interface Movement {
	
	public void movement();
	public void setSpeed(Vector2 pSpeed);
	public void stop();
}
