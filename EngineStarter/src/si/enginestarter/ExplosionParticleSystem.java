package si.enginestarter;

import java.util.Iterator;

import org.andengine.entity.particle.Particle;
import org.andengine.entity.particle.SpriteParticleSystem;
import org.andengine.entity.particle.emitter.PointParticleEmitter;
import org.andengine.entity.particle.initializer.RotationParticleInitializer;
import org.andengine.entity.particle.initializer.ScaleParticleInitializer;
import org.andengine.entity.particle.modifier.AlphaParticleModifier;
import org.andengine.entity.particle.modifier.ExpireParticleInitializer;
import org.andengine.entity.particle.modifier.OffCameraExpireParticleModifier;
import org.andengine.entity.particle.modifier.ScaleParticleModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.opengl.texture.region.ITextureRegion;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import android.hardware.SensorManager;

public class ExplosionParticleSystem {
	private static String TAG = ExplosionParticleSystem.class.getName();
	
	protected PointParticleEmitter explosionEmitter;
	protected SpriteParticleSystem spriteExplosion;
	
	public ExplosionParticleSystem(int numGears, ITextureRegion pTextureRegion) {
		
		explosionEmitter = new PointParticleEmitter(0f, 0f);
		
		spriteExplosion = new SpriteParticleSystem(explosionEmitter, numGears, numGears, numGears, pTextureRegion, 
			ResourceManager.getInstance().getActivity().getVertexBufferObjectManager()) {
			boolean loaded = false;

			@Override
			protected void onManagedUpdate(float pSecondsElapsed) {
				super.onManagedUpdate(pSecondsElapsed);
				if(loaded) {
					if(mParticlesAlive == 0) {
						ResourceManager.getInstance().getActivity().runOnUpdateThread(new Runnable() {

							@Override
							public void run() {
								if(spriteExplosion != null) {
									spriteExplosion.detachSelf();
									if(!spriteExplosion.isDisposed()) {
										spriteExplosion.dispose();
									}
								}
							}
						});
					}
				} else {
					loaded = true;
					spriteExplosion.addParticleInitializer(new RotationParticleInitializer<Sprite>(0f, 360f));
					spriteExplosion.addParticleInitializer(new ScaleParticleInitializer<Sprite>(0f));
					spriteExplosion.addParticleInitializer(new ExpireParticleInitializer<Sprite>(3f));
					spriteExplosion.addParticleModifier(new ScaleParticleModifier<Sprite>(1f, 3f, 1f, 1.3f));
					spriteExplosion.addParticleModifier(new AlphaParticleModifier<Sprite>(2.5f, 3f, 1f, 0f));
					spriteExplosion.addParticleModifier(new OffCameraExpireParticleModifier<Sprite>(ResourceManager.getInstance().getEngine().getCamera()));
					spriteExplosion.onUpdate(1f);
					for(Particle<Sprite> currentParticle : this.mParticles) {
						float x = MathUtils.random(-200, 200);
						float y = MathUtils.random(200, 200);
						currentParticle.getPhysicsHandler().setVelocity(x, y);
						currentParticle.getPhysicsHandler().setAcceleration(-SensorManager.GRAVITY_EARTH * 120);
					}
					spriteExplosion.setParticlesSpawnEnabled(false);
				}
			}
		};
	}
	
	public void run(float pX, float pY) { explosionEmitter.setCenter(pX, pY); }
	
	public void createExplosion(final Vector2 pExplosionPosition, float pExplosion, GameLevel pGameLevel) {
		Iterator<Body> bodies = pGameLevel.getWorld().getBodies();
		
		while(bodies.hasNext()) {
			final Body body = bodies.next();
			if(body.getType() == BodyType.DynamicBody) {
				final Vector2 bodyPosition = Vector2Pool.obtain(body.getWorldCenter());
				final Vector2 normalizedDirection = Vector2Pool.obtain(bodyPosition).sub(pExplosionPosition).nor();
				final float distance = bodyPosition.dst(pExplosionPosition);
				final Vector2 force = Vector2Pool.obtain(normalizedDirection).mul(pExplosion * (1f / distance));
				body.applyForce(force, body.getWorldCenter());
				
				Vector2Pool.recycle(force);
				Vector2Pool.recycle(normalizedDirection);
				Vector2Pool.recycle(bodyPosition);
			}
		}
	}
}
