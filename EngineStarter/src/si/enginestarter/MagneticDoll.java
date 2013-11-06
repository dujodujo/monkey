package si.enginestarter;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.color.Color;
import org.andengine.util.math.MathUtils;

import android.util.Log;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

public class MagneticDoll extends Entity implements IOnSceneTouchListener {
	private static String TAG = MagneticDoll.class.getName();
	
	//private Body ground;
	//private Body roof;
	//private Body leftWall;
	//private Body rightWall;
	
	private Body head;
	private Body torso;
	private Body leftArm;
	private Body rightArm;
	private Body leftLeg;
	private Body rightLeg;

	private MouseJointDef mouseJointDef;
	private MouseJoint mouseJoint;
	private Body mouseJointGround;
	
	private static float densityHead = 30f;
	private static float elasticityHead = .1f;
	private static float frictionHead = .9f;
	
	private static float densityArm = 100f;
	private static float elasticityArm = .7f;
	private static float frictionArm = .9f;
	
	private static float densityLeg = 20f;
	private static float elasticityLeg = .5f;
	private static float frictionLeg = .9f;
	
	private static float densityTorso = 30f;
	private static float elasticityTorso = .5f;
	private static float frictionTorso = .9f;
	
	private static float densityElement = 0f;
	private static float elasticityElement = .5f;
	private static float frictionElement = .5f;
	
	public static FixtureDef headFixtureDef = PhysicsFactory.createFixtureDef(densityHead, elasticityHead, frictionHead);
	public static FixtureDef armFixtureDef = PhysicsFactory.createFixtureDef(densityArm, elasticityArm, frictionArm);
	public static FixtureDef legFixtureDef = PhysicsFactory.createFixtureDef(densityLeg, elasticityLeg, frictionLeg);
	public static FixtureDef torsoFixtureDef = PhysicsFactory.createFixtureDef(densityTorso, elasticityTorso, frictionTorso);
	//public static FixtureDef elementFixtureDef = PhysicsFactory.createFixtureDef(densityElement, elasticityElement, frictionElement);
	
	private GameLevel gameLevel;
	
	public MagneticDoll(final float pX, final float pY, final GameLevel gameLevel) {
		Log.d(TAG, TAG);
		
		this.gameLevel = gameLevel;
		this.gameLevel.getWorld().setGravity(new Vector2(0, 0));
		
		loadBodyParts();
		loadJointParts();
		loadMouseJoint();
		
		this.gameLevel.registerUpdateHandler(this);
		this.gameLevel.setOnSceneTouchListener(this);
		this.gameLevel.attachChild(this);
	}
	
	private void loadMouseJoint() {
		mouseJointGround = gameLevel.getWorld().createBody(new BodyDef());
		mouseJointDef = new MouseJointDef();
		mouseJointDef.bodyA = mouseJointGround;
		mouseJointDef.bodyB = head;
		mouseJointDef.dampingRatio = 1f;
		mouseJointDef.frequencyHz = 10f;
		mouseJointDef.maxForce = 100.f * torso.getMass();
		mouseJointDef.collideConnected = false;
	}
	
	private void loadJointParts() {
		Log.d(TAG, "loadJointParts");

		loadJoint(head, torso, -30f, 30f, false, true, 400f, 340f);
		loadJoint(leftArm, torso, -180f, 0f, false, true, 360f, 320f);
		loadJoint(rightArm, torso, 0f, 180f, false, true, 440, 320f);
		loadJoint(leftLeg, torso, -120f, 0f, false, true, 380f, 180f);
		loadJoint(rightLeg, torso, 0f, 120f, false, true, 420f, 180f);
	}
	
	private void loadJoint(Body bodyA, Body bodyB, float lowerAngle, float upperAngle,
			boolean collideConnected, boolean enableLimit, float pX, float pY) {
		
		final RevoluteJointDef jointDef = new RevoluteJointDef();
		jointDef.initialize(bodyA, bodyB, new Vector2(pX, pY));
		jointDef.collideConnected = true;
		jointDef.enableLimit = true;
		jointDef.lowerAngle = MathUtils.degToRad(-30f);
		jointDef.upperAngle = MathUtils.degToRad(30f);
		gameLevel.getWorld().createJoint(jointDef);
	}
	
	private void loadBodyParts() {
		Log.d(TAG, "loadBodyParts");
		Rectangle rec;
		
		rec = loadBody(210f, 140f, 40f, 40f);
		head = PhysicsFactory.createBoxBody(gameLevel.getWorld(), rec, BodyType.DynamicBody, headFixtureDef);
		gameLevel.getWorld().registerPhysicsConnector(new PhysicsConnector(rec, head));
		
		rec = loadBody(205f, 180f, 60f, 90f);
		torso = PhysicsFactory.createBoxBody(gameLevel.getWorld(), rec, BodyType.DynamicBody, torsoFixtureDef);
		gameLevel.getWorld().registerPhysicsConnector(new PhysicsConnector(rec, torso));
		
		rec = loadBody(180f, 180f, 20f, 60f);
		leftArm = PhysicsFactory.createBoxBody(gameLevel.getWorld(), rec, BodyType.DynamicBody, armFixtureDef);
		gameLevel.getWorld().registerPhysicsConnector(new PhysicsConnector(rec, leftArm));
		
		rec = loadBody(270f, 180f, 20f, 60f);
		rightArm = PhysicsFactory.createBoxBody(gameLevel.getWorld(), rec, BodyType.DynamicBody, armFixtureDef);
		gameLevel.getWorld().registerPhysicsConnector(new PhysicsConnector(rec, rightArm));
		
		rec = loadBody(180f, 270f, 20f, 60f);
		leftLeg = PhysicsFactory.createBoxBody(gameLevel.getWorld(), rec, BodyType.DynamicBody, legFixtureDef);
		gameLevel.getWorld().registerPhysicsConnector(new PhysicsConnector(rec, leftLeg));
		
		rec = loadBody(270f, 270f, 20f, 60f);
		rightLeg = PhysicsFactory.createBoxBody(gameLevel.getWorld(), rec, BodyType.DynamicBody, legFixtureDef);
		gameLevel.getWorld().registerPhysicsConnector(new PhysicsConnector(rec, rightLeg));
	}
	
	private Rectangle loadBody(float pX, float pY, float pWidth, float pHeight) {
		Log.d(TAG, "loadBody");
		Rectangle rect = new Rectangle(pX, pY, pWidth, pHeight,
			ResourceManager.getInstance().getActivity().getVertexBufferObjectManager());
		rect.setColor(Color.RED);
		gameLevel.attachChild(rect);
		return rect;
	}
	
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		if(pSceneTouchEvent.isActionDown()) {
			mouseJointDef.target.set(head.getWorldCenter());
			mouseJoint = (MouseJoint)gameLevel.getWorld().createJoint(mouseJointDef);
			final Vector2 vec = Vector2Pool.obtain(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
			mouseJoint.setTarget(vec);
			Vector2Pool.recycle(vec);
			return true;
		} else if(pSceneTouchEvent.isActionMove()) {
			final Vector2 vec = Vector2Pool.obtain(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
			mouseJoint.setTarget(vec);
			Vector2Pool.recycle(vec);
			return true;
		} else if(pSceneTouchEvent.isActionCancel() || pSceneTouchEvent.isActionOutside() || pSceneTouchEvent.isActionUp()) {
			gameLevel.getWorld().destroyJoint(mouseJoint);
		}
		return true;
	}

	@Override
	public void reset() {}

	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		setPosition(200, 200);
		super.onManagedUpdate(pSecondsElapsed);
	}
}
