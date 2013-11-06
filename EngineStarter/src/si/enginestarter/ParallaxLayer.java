package si.enginestarter;

import java.util.ArrayList;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.Entity;
import org.andengine.entity.shape.IAreaShape;
import org.andengine.opengl.util.GLState;

public class ParallaxLayer extends Entity {
	public static String TAG = ParallaxLayer.class.getName();
	
	private ArrayList<ParallaxEntity> parallaxEntities;
	private int parallaxEntityCount;
	
	protected float parallaxValue;
	protected float parallaxScrollValue;
	protected float parallaxStepPerSecond;
	protected float parallaxScrollFactor;
	
	private GameCamera camera;
	private float xCameraLast;
	private float xCameraOffset;
	
	private float levelWidth;
	protected boolean scrollable;
	
	public ParallaxLayer() {}
	
	public ParallaxLayer(GameCamera camera, boolean isScrollable) {
		this(camera, isScrollable, 0f);
	}
	
	public ParallaxLayer(GameCamera camera, boolean isScrollable, float levelWidth) {
		this.camera = camera;
		this.scrollable = isScrollable;
		this.levelWidth = levelWidth;
		parallaxEntities = new ArrayList<ParallaxEntity>();
		parallaxEntityCount = parallaxEntities.size();
		
		this.parallaxValue = 1f;
		this.parallaxScrollValue = 1f;
		this.parallaxStepPerSecond = 1f;
		this.parallaxScrollFactor = 1f;
		
		xCameraLast = camera.getCenterX();
	}
	
	public boolean isScrollable() {
		return scrollable;
	}
	
	public float getParallaxValue() {
		return parallaxValue;
	}

	public void setParallaxValue(float parallaxValue) {
		this.parallaxValue = parallaxValue;
	}

	public float getParallaxStepPerSecond() {
		return parallaxStepPerSecond;
	}

	public void setParallaxStepPerSecond(float parallaxStepPerSecond) {
		this.parallaxStepPerSecond = parallaxStepPerSecond;
	}

	public float getParallaxScrollFactor() {
		return parallaxScrollFactor;
	}

	public void setParallaxScrollFactor(float parallaxScrollFactor) {
		this.parallaxScrollFactor = parallaxScrollFactor;
	}

	@Override
	protected void onManagedDraw(GLState pGLState, Camera pCamera) {
		super.preDraw(pGLState, pCamera);
		
		for(int i = 0; i < this.parallaxEntityCount; i++) {
			if(this.parallaxEntities.get(i).isScrollable()) {
				this.parallaxEntities.get(i).onDraw(pGLState, pCamera, this.parallaxScrollValue, levelWidth);
			} else {
				this.parallaxEntities.get(i).onDraw(pGLState, pCamera, this.parallaxValue, levelWidth);
			}
		}
	}
	
	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		if(isScrollable() && xCameraLast != camera.getCenterX()) {
			xCameraOffset = xCameraLast - this.camera.getCenterX();
			xCameraLast = this.camera.getCenterX();
			
			this.parallaxScrollValue += xCameraOffset * this.parallaxScrollFactor;
			xCameraOffset = 0;
		}
		this.parallaxValue += this.parallaxStepPerSecond * pSecondsElapsed;
		super.onManagedUpdate(pSecondsElapsed);
	}

	public void attachParallaxEntity(ParallaxEntity parallaxEntity) {
		this.parallaxEntities.add(parallaxEntity);
		this.parallaxEntityCount++;
	}
	
	public boolean detachEntity(ParallaxEntity parallaxEntity) {
		this.parallaxEntityCount--;
		boolean success = this.parallaxEntities.remove(parallaxEntity);
		if(!success)
			this.parallaxEntityCount++;
		return success;
	}
	
	public static class ParallaxEntity {
		private float parallaxFactor;
		private IAreaShape areaShape;
		private boolean scrollable;
				
		public ParallaxEntity(final float pFactor, final IAreaShape aShape) {
			this(pFactor, aShape, false);
		}
		
		public ParallaxEntity(final float pFactor, final IAreaShape aShape, boolean scroll) {
			parallaxFactor = pFactor;
			areaShape = aShape;
			scrollable = scroll;
		}
	
		public boolean isScrollable() {
			return scrollable;
		}
	
		public void onDraw(final GLState pGLState, final Camera pCamera, float parallaxValue, float levelWidth) {
			pGLState.pushModelViewGLMatrix();
			
			float widthRange;
			if(levelWidth != 0) {
				widthRange = levelWidth;
			} else {
				widthRange = pCamera.getWidth();
			}
			float offset = (parallaxValue * parallaxFactor) % areaShape.getWidthScaled();
			
			while(offset > 0) {
				offset -= areaShape.getWidthScaled();
			}
			
			pGLState.translateModelViewGLMatrixf(offset, 0, 0);
			float currentX = offset;
			do {
				this.areaShape.onDraw(pGLState, pCamera);
				pGLState.translateModelViewGLMatrixf(areaShape.getWidthScaled() - 1, 0, 0);
				currentX += areaShape.getWidthScaled();	
			} while(currentX < widthRange);
			
			pGLState.popModelViewGLMatrix();
		}
	}
}