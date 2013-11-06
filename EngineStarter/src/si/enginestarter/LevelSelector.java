package si.enginestarter;

import java.util.ArrayList;

import org.andengine.entity.Entity;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.util.Log;

public class LevelSelector extends Entity {
	private static String TAG = LevelSelector.class.getName();
	
	private final int columns = 3;
	private final int rows = 1;
	private final int tileDim = 64;
	private final int tilePad = 2;
	
	private Scene scene;
	
	private float x;
	private float y;
	private float xstart;
	private float ystart;
	private int iWorld;
	private int numLevels;
	
	public ArrayList<LevelSelectorButton> buttons;

	public LevelSelector(final float cameraWidth, final float cameraHeight, final int pWorldIndex,
			final ITextureRegion textureRegion, final Font font, final Scene pScene) {
		Log.d(TAG, "LevelSelector");
		this.scene = pScene;
		this.iWorld = pWorldIndex;
		
		numLevels = Levels.getNumLevels(iWorld);
		buttons = new ArrayList<LevelSelectorButton>(numLevels);
		
		xstart = x = cameraWidth + tileDim * 2;
		ystart = y = cameraHeight + tileDim * 3;

		createTiles(textureRegion, font);
		setPosition(cameraWidth, cameraHeight);
	}
	
	private void createTiles(final ITextureRegion textureRegion, final Font font) {
		int currentTileLevel = 1;
		
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				LevelSelectorButton levelButton = new LevelSelectorButton(currentTileLevel, iWorld,
					x, y, tileDim, tileDim, textureRegion, 
					ResourceManager.getInstance().getEngine().getVertexBufferObjectManager(), this.scene);
								
				scene.registerTouchArea(levelButton);
				scene.attachChild(levelButton);
				buttons.add(levelButton);
				
				x += tilePad + tileDim + tilePad;
				currentTileLevel++;
			}
			x = xstart;
			y = ystart + tileDim + tilePad;
		}
	}
}