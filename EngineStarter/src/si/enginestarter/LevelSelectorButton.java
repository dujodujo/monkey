package si.enginestarter;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

import android.util.Log;

public class LevelSelectorButton extends Sprite {
	private static String TAG = LevelSelectorButton.class.getName();
	
	private int iLevel;
	private int iWorld;
	private Text buttonText;
	protected Sprite lockedLevel;
	protected TiledSprite star;
	
	private boolean isTouched = false;
	private boolean isClicked = false;
	private boolean isLocked = false;
	
	public LevelSelectorButton(int pLevelIndex, int pWorldIndex, float pX, float pY,
		float pWidth, float pHeight, ITextureRegion pTextureRegion,
		VertexBufferObjectManager pVertexBufferObjectManager, Scene pScene) {
		super(pX, pY, pWidth, pHeight, pTextureRegion, pVertexBufferObjectManager);
		
		iLevel = pLevelIndex;
		iWorld = pWorldIndex;
		
		lockedLevel = new Sprite(pX, pY, ResourceManager.lockedButtonTex, 
			ResourceManager.getInstance().getActivity().getVertexBufferObjectManager());
		
		star = new TiledSprite(pX, pY, ResourceManager.levelStarTTex,
			ResourceManager.getInstance().getActivity().getVertexBufferObjectManager());
		
		String maxLevel = new String(GameActivity.SHARED_PREFS_LEVELS_COMPLETE) + 1;
		isLocked = iLevel > 1;
	}

	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
		if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
			this.isTouched = true;
			Log.d(TAG, "touch");
		} else if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_MOVE) {
			if((pTouchAreaLocalX > this.getWidth()) ||
			   (pTouchAreaLocalX < 0.f) ||
			   (pTouchAreaLocalY > this.getHeight()) ||
			   (pTouchAreaLocalY < 0.f)) {
				if(this.isTouched) {
					this.isTouched = false;
				}
			} else {
				if(!this.isTouched) {
					this.isTouched = true;
				}
			}
			Log.d(TAG, String.valueOf(isTouched));
		} else if((pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) && this.isTouched) {
			this.isTouched = false;
			if(!isLocked) {
				this.isClicked = true;
			}
			Log.d(TAG, "clikced");
		}
		return false;
	}

	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);
		
		if(!isLocked) {
			if(lockedLevel.hasParent()) {
				Log.d(TAG, "detach");
				lockedLevel.detachSelf();
				star.setCurrentTileIndex(1);
				attachChild(star);
			}
			if(!isTouched) {
				if(LevelSelectorButton.this.isClicked) {
					
					Log.d(TAG, "update");
					
					int level = LevelSelectorButton.this.iLevel; 
					int world = LevelSelectorButton.this.iWorld;
					SceneManager.getInstance().showScene(new GameLevel(Levels.getLevelDef(level, world)));
					LevelSelectorButton.this.isClicked = false;
				}
			}
		} else {
			if(!lockedLevel.hasParent()) {
				Log.d(TAG, "attach");
				attachChild(lockedLevel);
				star.detachSelf();
			}
		}
	}
}