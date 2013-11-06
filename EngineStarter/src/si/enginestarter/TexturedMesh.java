package si.enginestarter;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.primitive.TexturedMesh.DrawMode;
import org.andengine.entity.primitive.TexturedMesh.ITexturedMeshVertexBufferObject;
import org.andengine.entity.shape.IShape;
import org.andengine.entity.shape.Shape;
import org.andengine.opengl.shader.PositionColorTextureCoordinatesShaderProgram;
import org.andengine.opengl.shader.ShaderProgram;
import org.andengine.opengl.shader.constants.ShaderProgramConstants;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.opengl.vbo.DrawType;
import org.andengine.opengl.vbo.HighPerformanceVertexBufferObject;
import org.andengine.opengl.vbo.IVertexBufferObject;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.opengl.vbo.attribute.VertexBufferObjectAttribute;
import org.andengine.opengl.vbo.attribute.VertexBufferObjectAttributes;
import org.andengine.opengl.vbo.attribute.VertexBufferObjectAttributesBuilder;

import android.opengl.GLES20;

public class TexturedMesh extends Shape {
	protected static final int xVertex = 0;
	protected static final int yVertex = xVertex + 1;
	protected static final int color = yVertex + 1;
	protected static final int uTextureCoordinate = color + 1;
	protected static final int vTextureCoordinate = uTextureCoordinate + 1;
	protected static final int vertexSize = 5;
	
	private ITexturedMeshVertexBufferObject meshvbo;
	private int vertexCount;
	private int drawMode;
	private ITextureRegion textureRegion;
	
	public TexturedMesh(float pX, float pY, float[] pBufferData, int pVertexCount, 
		DrawMode pDrawMode, VertexBufferObjectManager pVertexBufferObjectManager) {
		this(pX, pY, pBufferData, pVertexCount, pDrawMode, null, pVertexBufferObjectManager);
	}
	
	public TexturedMesh(float pX, float pY, float[] pBufferData, int pVertexCount, DrawMode pDrawMode,
		ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
		this(pX, pY, pBufferData, pVertexCount, pDrawMode, pTextureRegion, pVertexBufferObjectManager, DrawType.STATIC);
	}

	public TexturedMesh(float pX, float pY, float[] pBufferData, int pVertexCount, DrawMode pDrawMode,
		ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType) {
		this(pX, pY, pVertexCount, pDrawMode, pTextureRegion,
			new TexturedMeshVertexBufferObject(pVertexBufferObjectManager, pBufferData, pVertexCount, pDrawType, true, TexturedMesh.vbo));
	}
	
	public TexturedMesh(float pX, float pY, int pVertexCount, DrawMode pDrawMode, 
		ITextureRegion pTextureRegion, ITexturedMeshVertexBufferObject pMeshVertexBufferObject) {
		super(pX, pY, PositionColorTextureCoordinatesShaderProgram.getInstance());

		this.drawMode = pDrawMode.getDrawMode();
		this.textureRegion = pTextureRegion;
		this.meshvbo = pMeshVertexBufferObject;
		this.vertexCount = pVertexCount;

		if(textureRegion != null) {
			this.setBlendingEnabled(true);
			this.initBlendFunction(pTextureRegion);
			this.onUpdateTextureCoordinates();
		}

		this.onUpdateVertices();
		this.onUpdateColor();
		this.meshvbo.setDirtyOnHardware();
		this.setBlendingEnabled(true);
	}
	
	private void onUpdateTextureCoordinates() {
		this.meshvbo.onUpdateTextureCoordinates(this);
	}
	
	@Override
	protected void onUpdateVertices() {
		this.meshvbo.onUpdateVertices(this);
	}
	
	@Override
	protected void onUpdateColor() {
		this.meshvbo.onUpdateColor(this);
	}

	public static final VertexBufferObjectAttributes vbo = new VertexBufferObjectAttributesBuilder(3)
		.add(ShaderProgramConstants.ATTRIBUTE_POSITION_0_LOCATION, 
			 ShaderProgramConstants.ATTRIBUTE_POSITION, 2, GLES20.GL_FLOAT, false)
		.add(ShaderProgramConstants.ATTRIBUTE_COLOR_LOCATION, 
			 ShaderProgramConstants.ATTRIBUTE_COLOR, 4, GLES20.GL_UNSIGNED_BYTE, true)
		.add(ShaderProgramConstants.ATTRIBUTE_TEXTURECOORDINATES_LOCATION, 
			 ShaderProgramConstants.ATTRIBUTE_TEXTURECOORDINATES, 2, GLES20.GL_FLOAT, false)
		.build();
	
	public float[] getBufferData() {
		return this.meshvbo.getBufferData();
	}
	
	public ITextureRegion getTextureRegion() {
		return this.textureRegion;
	}
	
	@Override
	public boolean collidesWith(IShape pOtherShape) {
		return false;
	}
	
	@Override
	public IVertexBufferObject getVertexBufferObject() {
		return null;
	}

	@Override
	public boolean contains(float pX, float pY) {
		return false;
	}

	@Override
	public void reset() {
		super.reset();
		this.initBlendFunction(this.getTextureRegion().getTexture());
	}

	@Override
	protected void preDraw(GLState pGLState, Camera pCamera) {
		super.preDraw(pGLState, pCamera);
		this.textureRegion.getTexture().bind(pGLState);
		this.meshvbo.bind(pGLState, this.mShaderProgram);
	}
	
	@Override
	protected void draw(GLState pGLState, Camera pCamera) {
		this.meshvbo.bind(pGLState, this.mShaderProgram);
	}
	
	@Override
	protected void postDraw(GLState pGLState, Camera pCamera) {
		super.postDraw(pGLState, pCamera);
		this.meshvbo.unbind(pGLState, this.mShaderProgram);
	}
	
	protected static float[] buildVertexList(float[] xVertices, float[] yVertices) {
		assert(xVertices.length == yVertices.length);
		
		float[] bufferData = new float[TexturedMesh.vertexSize * xVertices.length];
		updateVertexList(xVertices, yVertices, bufferData);
		return bufferData;
	}
	
	protected static void updateVertexList(float[] xVertices, float[] yVertices, float[] bufferData) {
		for(int i = 0; i < xVertices.length; i++) {
			bufferData[(TexturedMesh.vertexSize * i) + TexturedMesh.xVertex] = xVertices[i];
			bufferData[(TexturedMesh.vertexSize * i) + TexturedMesh.yVertex] = yVertices[i];
		}
	}
	
	public static interface ITexturedMeshVertexBufferObject extends IVertexBufferObject {
		public float[] getBufferData();
		public void onUpdateColor(final TexturedMesh mesh);
		public void onUpdateVertices(final TexturedMesh mesh);
		public void onUpdateTextureCoordinates(final TexturedMesh mesh);
	}
	
	public static class TexturedMeshVertexBufferObject extends HighPerformanceVertexBufferObject implements ITexturedMeshVertexBufferObject {
		private final int vertexCount;
		
		public TexturedMeshVertexBufferObject(VertexBufferObjectManager pVertexBufferObjectManager, float[] pBufferData, 
			final int pVertexCount, final DrawType pDrawType, final boolean pAutoDispose, final VertexBufferObjectAttributes pVertexBufferObjectAttributes) {
			super(pVertexBufferObjectManager, pBufferData, pDrawType, pAutoDispose, pVertexBufferObjectAttributes);
			
			vertexCount = pVertexCount;
		}

		@Override
		public void onUpdateColor(TexturedMesh mesh) {
			final float[] bufferData = this.mBufferData;
			final float packedColor = mesh.getColor().getABGRPackedFloat();
			for(int i = 0; i < this.vertexCount; i++) {
				bufferData[(i + TexturedMesh.vertexSize) + TexturedMesh.color] = packedColor;
			}
			this.setDirtyOnHardware();
		}

		@Override
		public void onUpdateVertices(TexturedMesh mesh) {
			this.setDirtyOnHardware();
		}

		@Override
		public void onUpdateTextureCoordinates(TexturedMesh mesh) {
			final float[] bufferData = this.mBufferData;
			final ITextureRegion textureRegion = mesh.getTextureRegion();
			
			float textureWidth = textureRegion.getWidth();
			float textureHeight = textureRegion.getHeight();
			
			float xx = 0;
			float yy = 0;
			
			for(int i = 0; i < this.vertexCount; i++) {
				float x = bufferData[(i + TexturedMesh.vertexSize) + TexturedMesh.xVertex];
				float y = bufferData[(i + TexturedMesh.vertexSize) + TexturedMesh.yVertex];
				
				float u =  (x - xx) / textureWidth + 0.5f;
				float v = -(y - yy) / textureHeight + 0.5f;
				
				bufferData[(i + TexturedMesh.vertexSize) + TexturedMesh.uTextureCoordinate] = u;
				bufferData[(i + TexturedMesh.vertexSize) + TexturedMesh.vTextureCoordinate] = v;
			}
			this.setDirtyOnHardware();
		}
		
		public static enum DrawMode {
			POINTS(GLES20.GL_POINTS), 
			LINE_STRIP(GLES20.GL_LINE_STRIP), 
			LINE_LOOP(GLES20.GL_LINE_LOOP), 
			LINES(GLES20.GL_LINES), 
			TRIANGLE_STRIP(GLES20.GL_TRIANGLE_STRIP), 
			TRIANGLE_FAN(GLES20.GL_TRIANGLE_FAN),
			TRIANGLES(GLES20.GL_TRIANGLES);
			
			public final int drawMode;
			
			private DrawMode(final int drawMode) {
				this.drawMode = drawMode;
			}
			
			public int getDrawMode() {
				return this.drawMode;
			}
		}
	}
}