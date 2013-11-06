package si.enginestarter;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.Entity;
import org.andengine.entity.sprite.Sprite;

import com.badlogic.gdx.math.Vector2;

public class BouncingPowerBar extends Entity {
	private static BouncingPowerBar instance;
	
	/*
	private static final float LINE_SPEED = 1.5f;
	private static final float LINE_MIN_Y = 25.f;
	private static final float LINE_MAX_Y = 250.f;
	private static final float LINE_RANGE = LINE_MAX_Y - LINE_MIN_Y;
	
	private static final float LINE_POSITION_X = 32f;
	private static final float LINE_POSITION_Y = 16f;
	*/
	private static final float LENS_POSITION_X = 32f;
	private static final float LENS_POSITION_Y = 32f;
	
	private MagneticHero magneticHero;
	//private boolean isLineMoving;
	
	private static Sprite BACKGROUND;
	//private static Sprite LINE;
	private static Sprite LENS;
	
	private BouncingPowerBar() {
		
		BACKGROUND = new Sprite(LENS_POSITION_X, LENS_POSITION_Y, ResourceManager.powerBarBackground, 
			ResourceManager.getInstance().getActivity().getVertexBufferObjectManager());
		//LINE = new Sprite(LINE_POSITION_X, LINE_POSITION_Y, ResourceManager.powerBarLine,
		//	ResourceManager.getInstance().getActivity().getVertexBufferObjectManager());
		LENS = new Sprite(LENS_POSITION_X, LENS_POSITION_Y, ResourceManager.powerBarLens, 
			ResourceManager.getInstance().getActivity().getVertexBufferObjectManager());
		
		//isLineMoving = false;
		setScale(0.5f, 0.5f);
		
		attachChild(BACKGROUND);
		attachChild(LENS);
		//BACKGROUND.attachChild(LINE);
		
		GameManager.getGameLevel().getWorld().setGravity(new Vector2(0, 0));
		magneticHero = GameManager.getGameLevel().magneticHero;
	}
	
	public static BouncingPowerBar getInstance() {
		if(instance == null)
			instance = new BouncingPowerBar();
		return instance;
	}
	
	public void attachInstanceToHud(final HUD pHud) {
		if(instance.hasParent()) {
			instance.detachSelf();
		}
		pHud.attachChild(instance);
	}

	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		/*
		if(isLineMoving) {
			LINE.setY(LINE.getY() + (pSecondsElapsed * LINE_SPEED * LINE_RANGE));
			if(LINE.getY() > LINE_MAX_Y) {
				isLineMoving = false;
				LINE.setY(LINE_MAX_Y - (LINE.getY() - LINE_MAX_Y));
			}
		} else {
			LINE.setY(LINE.getY() - (pSecondsElapsed * LINE_SPEED * LINE_RANGE));
			if(LINE.getY() < LINE_MIN_Y) {
				isLineMoving = true;
				LINE.setY(LINE_MIN_Y + (LINE_MIN_Y - LINE.getY()));
			}
		}
		*/
		super.onManagedUpdate(pSecondsElapsed);
	}
}