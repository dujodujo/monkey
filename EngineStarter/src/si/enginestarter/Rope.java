package si.enginestarter;

import java.util.ArrayList;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.util.color.Color;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

public class Rope {
	private static String TAG = Rope.class.getName();
	
	private int numRopes;
	private int ropeLength;
	private int ropeWidth;
	private int ropeOverlap;
	private ArrayList<Rectangle> ropes;
	private ArrayList<Body> ropeBodies;
	
	private static float densityRope = 0f;
	private static float elasticityRope = .5f;
	private static float frictionRope = .5f;
	
	public static FixtureDef ropeFixtureDef = 
		PhysicsFactory.createFixtureDef(densityRope, elasticityRope, frictionRope);
	
	private GameLevel gameLevel;
	
	public Rope(Body head, int numRopeSegments, int ropeSegmentLength, int ropeSegmentWidth,
			int ropeSegmetOverlap, float minDensity, float maxDensity, GameLevel gameLevel) {
		
		this.gameLevel = gameLevel;
		this.numRopes = numRopeSegments;
		this.ropeLength = ropeSegmentLength;
		this.ropeWidth = ropeSegmentWidth;
		this.ropeOverlap = ropeOverlap;
		this.ropes = new ArrayList<Rectangle>(numRopeSegments);
		this.ropeBodies = new ArrayList<Body>(numRopeSegments);
	
		RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
		
		for(int i = 0; i < numRopeSegments; i++) {
			
			if(i == 0) {
				float x = head.getWorldCenter().x;
				float y = head.getWorldCenter().y - ropeLength/2 + ropeOverlap;
				ropes.add(new Rectangle(x, y, ropeWidth, ropeLength, 
					ResourceManager.getInstance().getActivity().getVertexBufferObjectManager())); 
			} else {
				float x = ropes.get(i-1).getX();
				float y = ropes.get(i-1).getY() - ropes.get(i-1).getHeight() + ropeOverlap;
				ropes.add(new Rectangle(x, y, ropeWidth, ropeLength, 
					ResourceManager.getInstance().getActivity().getVertexBufferObjectManager()));
			}
			
			ropes.get(i).setColor(Color.WHITE);

			ropeBodies.add(PhysicsFactory.createCircleBody(gameLevel.getWorld(),
				ropes.get(i), BodyType.DynamicBody, ropeFixtureDef));
			
			ropeBodies.get(i).setAngularDamping(5f);
			ropeBodies.get(i).setLinearDamping(0.5f);
			ropeBodies.get(i).setBullet(true);
			
			if(i == 0) {
				revoluteJointDef.initialize(head, ropeBodies.get(i), head.getWorldCenter());
			} else {
				Vector2 anchor = new Vector2(ropeBodies.get(i-1).getWorldCenter().x,
					ropeBodies.get(i).getWorldCenter().y - ropeSegmentLength/2);
				revoluteJointDef.initialize(ropeBodies.get(i-1), ropeBodies.get(i), anchor);
			}
			
			PhysicsConnector ropePhysicsConnector = new PhysicsConnector(ropes.get(i), ropeBodies.get(i));
			gameLevel.getWorld().registerPhysicsConnector(ropePhysicsConnector);
			revoluteJointDef.collideConnected = false;
			gameLevel.getWorld().createJoint(revoluteJointDef);
			gameLevel.attachChild(ropes.get(i));
		}
	}
}
