package si.enginestarter;

import org.andengine.entity.particle.SpriteParticleSystem;
import org.andengine.entity.particle.emitter.PointParticleEmitter;
import org.andengine.entity.particle.initializer.AlphaParticleInitializer;
import org.andengine.entity.particle.initializer.RotationParticleInitializer;
import org.andengine.entity.particle.initializer.ScaleParticleInitializer;
import org.andengine.entity.particle.initializer.VelocityParticleInitializer;
import org.andengine.entity.particle.modifier.AlphaParticleModifier;
import org.andengine.entity.particle.modifier.ExpireParticleInitializer;
import org.andengine.entity.particle.modifier.OffCameraExpireParticleModifier;
import org.andengine.entity.particle.modifier.ScaleParticleModifier;
import org.andengine.entity.primitive.Vector2;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;

public class SmokeParticleSystem {
	
	protected PointParticleEmitter smokeEmitter;
	protected SpriteParticleSystem spriteSmoke;
	
	protected Vector2 particleVelocity;
	
	public SmokeParticleSystem(int numGears, ITextureRegion pTextureRegion) {
		
		particleVelocity = new Vector2(20f, 20f);
		
		smokeEmitter = new PointParticleEmitter(0f, 0f);
		spriteSmoke = new SpriteParticleSystem(smokeEmitter, numGears, numGears, numGears, pTextureRegion, 
			ResourceManager.getInstance().getActivity().getVertexBufferObjectManager()) {

				@Override
				protected void onManagedUpdate(float pSecondsElapsed) {
					super.onManagedUpdate(pSecondsElapsed);
					super.onManagedUpdate(pSecondsElapsed);
					if(this.mParticlesAlive == 0) {
						ResourceManager.getInstance().getActivity().runOnUpdateThread(new Runnable() {

							@Override
							public void run() {
								if(spriteSmoke != null) {
									spriteSmoke.detachSelf();
									if(!spriteSmoke.isDisposed()) {
										spriteSmoke.dispose();
									}
								}
							}
						});
					}
				}
		};
		spriteSmoke.addParticleInitializer(new AlphaParticleInitializer<Sprite>(1f));
		spriteSmoke.addParticleInitializer(new VelocityParticleInitializer<Sprite>(particleVelocity.x - 10f, particleVelocity.x + 10f, 
			particleVelocity.y - 10f, particleVelocity.y + 10f));
		spriteSmoke.addParticleInitializer(new RotationParticleInitializer<Sprite>(0f, 360f));
		spriteSmoke.addParticleInitializer(new ScaleParticleInitializer<Sprite>(0f));
		spriteSmoke.addParticleInitializer(new ExpireParticleInitializer<Sprite>(2f));
		
		spriteSmoke.addParticleModifier(new ScaleParticleModifier<Sprite>(1.f, 1.1f, 0f, 2f));
		spriteSmoke.addParticleModifier(new ScaleParticleModifier<Sprite>(1.1f, 2f, 2f, 4f));
		spriteSmoke.addParticleModifier(new AlphaParticleModifier<Sprite>(1f, 2f, 0.5f, 0f));
		spriteSmoke.addParticleModifier(new OffCameraExpireParticleModifier<Sprite>(ResourceManager.getInstance().getEngine().getCamera()));
	}
	
	public void run(float pX, float pY) { smokeEmitter.setCenter(pX, pY); }
}