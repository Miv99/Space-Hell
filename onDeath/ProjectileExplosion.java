package onDeath;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.miv.ProjectileFactory;

import components.HitboxComponent;
import components.OnDeathComponent;
import components.OnExpireComponent;
import onDeath.Events.CustomData;

public class ProjectileExplosion {
	static ComponentMapper<HitboxComponent> hm = ComponentMapper.getFor(HitboxComponent.class);
	static ComponentMapper<OnDeathComponent> dm = ComponentMapper.getFor(OnDeathComponent.class);
	static ComponentMapper<OnExpireComponent> em = ComponentMapper.getFor(OnExpireComponent.class);
	
	// Explosion that sends four projectiles flying, one every 0- deg
	public static class FourProjectileExplosion {
		public static void onDeath(Engine engine, World world, ProjectileFactory projectileFactory, Entity parent, CustomData customData) {
			if(!customData.hasRecursion || customData.recursionLimit > 0) {
				CustomData clone = customData.clone();
				int recursionNumber = clone.recursionLimit;
				Vector2 pos = hm.get(parent).body.getPosition();
				
				for(float angle = 0f; angle < MathUtils.PI2; angle += MathUtils.degreesToRadians * 90) {
					Entity e = projectileFactory.newProjectile(clone.explosionProjectile, pos.x, pos.y, angle, clone.isPlayer, true);
					if(clone.onDeathEvent != null) {
						e.add(new OnDeathComponent(engine, world, projectileFactory, e, clone.onDeathEvent).setCustomData(clone.setRecursionLimit(recursionNumber - 1)));
					}
					if(clone.onExpireEvent != null) {
						e.add(new OnExpireComponent(engine, world, projectileFactory, e, clone.onExpireEvent).setCustomData(clone.setRecursionLimit(recursionNumber - 1)));
					}
				}
			}
		}
	}
	// Explosion that sends eight projectiles flying, one every 45 deg
	public static class EightProjectileExplosion {
		public static void onDeath(Engine engine, World world, ProjectileFactory projectileFactory, Entity parent, CustomData customData) {
			if(!customData.hasRecursion || customData.recursionLimit > 0) {
				CustomData clone = customData.clone();
				int recursionNumber = clone.recursionLimit;
				Vector2 pos = hm.get(parent).body.getPosition();
				
				for(float angle = 0f; angle < MathUtils.PI2; angle += MathUtils.degreesToRadians * 45) {
					Entity e = projectileFactory.newProjectile(clone.explosionProjectile, pos.x, pos.y, angle, clone.isPlayer, true);
					if(clone.onDeathEvent != null) {
						e.add(new OnDeathComponent(engine, world, projectileFactory, e, clone.onDeathEvent).setCustomData(clone.setRecursionLimit(recursionNumber - 1)));
					}
					if(clone.onExpireEvent != null) {
						e.add(new OnExpireComponent(engine, world, projectileFactory, e, clone.onExpireEvent).setCustomData(clone.setRecursionLimit(recursionNumber - 1)));
					}
				}
			}
		}
	}
}
