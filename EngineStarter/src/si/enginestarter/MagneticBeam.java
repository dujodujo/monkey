package si.enginestarter;

import org.andengine.entity.shape.IShape;

public abstract class MagneticBeam<T> extends MagneticEntity<IShape> {
	private static String NAME = MagneticBeam.class.getName();
	
	public MagneticBeam(GameLevel pGameLevel) { 
		super(pGameLevel);
	}

	abstract public void loadTexture(float pWidth, float pHeight);
}
