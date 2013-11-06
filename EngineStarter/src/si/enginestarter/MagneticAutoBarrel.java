package si.enginestarter;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.primitive.Vector2;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.input.touch.TouchEvent;

import si.enginestarter.MagneticType.EntityType;

public class MagneticAutoBarrel extends MagneticBarrel {

	public MagneticAutoBarrel(float pX, float pY, int pDirection,
			Vector2 pSpeed, CircularMovementDef moveDef, GameLevel pGameLevel) {
		super(pX, pY, pDirection, pSpeed, moveDef, pGameLevel);

		active = true;
		clicked = true;
	}
	
	public MagneticAutoBarrel(float pX, float pY, int pDirection,
			Vector2 pSpeed, HalfCircleMovementDef moveDef, GameLevel pGameLevel) {
		super(pX, pY, pDirection, pSpeed, moveDef, pGameLevel);
		
		active = true;
		clicked = true;
	}
	
	public MagneticAutoBarrel(float pX, float pY, int pDirection,
			Vector2 pSpeed, LinearMovementDef moveDef, GameLevel pGameLevel) {
		super(pX, pY, pDirection, pSpeed, moveDef, pGameLevel);
		
		active = true;
		clicked = true;
	}
	
	public MagneticAutoBarrel(float pX, float pY, int pDirection,
			Vector2 pSpeed, HarmonicMovementDef moveDef, GameLevel pGameLevel) {
		super(pX, pY, pDirection, pSpeed, moveDef, pGameLevel);
		
		active = true;
		clicked = true;
	}
	
	public MagneticAutoBarrel(float pX, float pY, int pDirection,
			Vector2 pSpeed, StopMovementDef moveDef, GameLevel pGameLevel) {
		super(pX, pY, pDirection, pSpeed, moveDef, pGameLevel);
		
		active = true;
		clicked = true;
	}

	@Override
	public EntityType getEntity() { return EntityType.MAGNETIC_AUTO_BARREL; }
	
	@Override
	public void loadSprites(float pX, float pY) {
		super.loadSprites(pX, pY);
		
		spriteBody = new AnimatedSprite(pX, pY, ResourceManager.autoBarrelTex,
			ResourceManager.getInstance().getActivity().getVertexBufferObjectManager()) {
				
				@Override
				protected void onManagedUpdate(float pSecondsElapsed) {
					super.onManagedUpdate(pSecondsElapsed);
					
					int currentTileIndex = rotation.update(pSecondsElapsed, spriteBody.getCurrentTileIndex());
					setBarrelDirection(currentTileIndex);
					spriteBody.setCurrentTileIndex(currentTileIndex);
				}
			};
	}
	
	public void onTouchInput(TouchEvent pSceneTouchEvent) {}
	
	@Override
	public void onUpdate(float pSecondsElapsed) {
		updateMovement();
		
		Rectangle hero = gameLevel.magneticHero.rectangle;
		if(rectangle.collidesWith(hero) && isHeroCollision()) {
			notifyCollision();
			gameLevel.magneticHero.setHeroDirection(getDirection());
			startSmokeAnimation();
		}
	}
}
