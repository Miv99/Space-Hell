package components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.miv.ProjectileFactory;
import com.miv.ProjectileFactory.Projectile;

import items.Weapons.Weapon;
import onDeath.Events.CustomData;
import onDeath.Events.OnDeathEvent;

/**
 * Component for enemies only
 * The boolean antiLag used in all the methods are set to true only if the weapon fired fires a lot (90+) of projectiles at once
 * @author Miv
 *
 */
public class ProjectileFiringComponent implements Component {	
	private ProjectileFactory projectileFactory;
	public transient Body firer, target;
	
	public ProjectileFiringComponent(ProjectileFactory projectileFactory, Body firer, Body target) {
		this.projectileFactory = projectileFactory;
		this.firer = firer;
		this.target = target;
	}
	
	/**
	 * Fires a single projectile at the target
	 */
	public void fireProjectile(Projectile projectile, boolean antiLag) {
		Vector2 firerPosition = firer.getWorldCenter();
		Vector2 targetPosition = target.getWorldCenter();
		
		float angle = MathUtils.atan2(firerPosition.y - targetPosition.y, firerPosition.x - targetPosition.x) - (float)Math.PI;
		
		projectileFactory.newProjectile(projectile, firerPosition.x, firerPosition.y, angle, false, antiLag);
	}
	
	/**
	 * Fires a single projectile with onDeathComponent at the target
	 */
	public void fireOnDeathProjectile(Projectile projectile, boolean antiLag, OnDeathEvent onDeathEvent, CustomData customData) {
		Vector2 firerPosition = firer.getWorldCenter();
		Vector2 targetPosition = target.getWorldCenter();
		
		float angle = MathUtils.atan2(firerPosition.y - targetPosition.y, firerPosition.x - targetPosition.x) - (float)Math.PI;
		
		Entity e = projectileFactory.newProjectile(projectile, firerPosition.x, firerPosition.y, angle, false, antiLag);
		e.add(new OnDeathComponent(projectileFactory.engine, projectileFactory.world, projectileFactory, e, onDeathEvent).setCustomData(customData));
	}
	
	/**
	 * Fires a single projectile with onExpireComponent at the target
	 */
	public void fireOnExpireProjectile(Projectile projectile, boolean antiLag, OnDeathEvent onExpireEvent, CustomData customData) {
		Vector2 firerPosition = firer.getWorldCenter();
		Vector2 targetPosition = target.getWorldCenter();
		
		float angle = MathUtils.atan2(firerPosition.y - targetPosition.y, firerPosition.x - targetPosition.x) - (float)Math.PI;
		
		Entity e = projectileFactory.newProjectile(projectile, firerPosition.x, firerPosition.y, angle, false, antiLag);
		e.add(new OnExpireComponent(projectileFactory.engine, projectileFactory.world, projectileFactory, e, onExpireEvent).setCustomData(customData));
	}
	
	/**
	 * Fires a single projectile with onDeathComponent at the target and a custom time
	 * @param time - how long the projectile will exist for
	 */
	public void fireOnDeathProjectile(Projectile projectile, boolean antiLag, OnDeathEvent onDeathEvent, CustomData customData, float time) {
		Vector2 firerPosition = firer.getWorldCenter();
		Vector2 targetPosition = target.getWorldCenter();
		
		float angle = MathUtils.atan2(firerPosition.y - targetPosition.y, firerPosition.x - targetPosition.x) - (float)Math.PI;
		
		Entity e = projectileFactory.newProjectile(projectile, firerPosition.x, firerPosition.y, time, angle, false, antiLag);
		e.add(new OnDeathComponent(projectileFactory.engine, projectileFactory.world, projectileFactory, e, onDeathEvent).setCustomData(customData));
	}
	
	/**
	 * Fires a single projectile with onExpireComponent at the target
	 * @param time - how long the projectile will exist for
	 */
	public void fireOnExpireProjectile(Projectile projectile, boolean antiLag, OnDeathEvent onExpireEvent, CustomData customData, float time) {
		Vector2 firerPosition = firer.getWorldCenter();
		Vector2 targetPosition = target.getWorldCenter();
		
		float angle = MathUtils.atan2(firerPosition.y - targetPosition.y, firerPosition.x - targetPosition.x) - (float)Math.PI;
		
		Entity e = projectileFactory.newProjectile(projectile, firerPosition.x, firerPosition.y, time, angle, false, antiLag);
		e.add(new OnExpireComponent(projectileFactory.engine, projectileFactory.world, projectileFactory, e, onExpireEvent).setCustomData(customData));
	}
	
	/**
	 * Fires a single projectile with onDeathComponent at the target and a custom time
	 * @param time - how long the projectile will exist for
	 */
	public void fireAngledOnDeathProjectile(Projectile projectile, boolean antiLag, OnDeathEvent onDeathEvent, CustomData customData, float angle) {
		Vector2 firerPosition = firer.getWorldCenter();
				
		Entity e = projectileFactory.newProjectile(projectile, firerPosition.x, firerPosition.y, angle, false, antiLag);
		e.add(new OnDeathComponent(projectileFactory.engine, projectileFactory.world, projectileFactory, e, onDeathEvent).setCustomData(customData));
	}
	
	/**
	 * Fires a single projectile with onExpireComponent at the target
	 * @param time - how long the projectile will exist for
	 */
	public void fireAngledOnExpireProjectile(Projectile projectile, boolean antiLag, OnDeathEvent onExpireEvent, CustomData customData, float angle) {
		Vector2 firerPosition = firer.getWorldCenter();
				
		Entity e = projectileFactory.newProjectile(projectile, firerPosition.x, firerPosition.y, angle, false, antiLag);
		e.add(new OnExpireComponent(projectileFactory.engine, projectileFactory.world, projectileFactory, e, onExpireEvent).setCustomData(customData));
	}
	
	/**
	 * Fires a single projectile at a specific angle
	 * @param projectile
	 */
	public void fireProjectile(Projectile projectile, float angle, boolean antiLag) {
		Vector2 firerPosition = firer.getWorldCenter();
				
		projectileFactory.newProjectile(projectile, firerPosition.x, firerPosition.y, angle, false, antiLag);
	}
	
	/**
	 * 
	 */
	public void leadProjectile(Projectile projectile, boolean antiLag) {
		Vector2 firerPosition = firer.getWorldCenter();
		Vector2 targetPosition = firer.getWorldCenter();
		Vector2 targetVelocity = firer.getLinearVelocity();
		
		// Angle the target is moving at in radians
		float targetAngle = targetVelocity.angleRad();
		
		// Loop through every 2 degrees to find most efficient path
		for(float deg = 0; deg < 2 * Math.PI; deg += (Math.PI / 45)) {
			if(projectileWillHit(projectile, deg, targetAngle, firerPosition, targetPosition, targetVelocity)) {
				fireProjectile(projectile, deg, antiLag);
				break;
			}
		}
	}
	
	/**
	 * Fires a shot at a specific angle with a specific projectile life time
	 * @param firer
	 */
	public void fire(Weapon weapon, float time, float angle, boolean antiLag) {
		Vector2 firerPosition = firer.getWorldCenter();
		
		fireWeapon(weapon, firerPosition, time, angle, antiLag);
	}
	
	/**
	 * Fires a shot at a specific angle
	 * @param firer
	 */
	public void fire(Weapon weapon, float angle, boolean antiLag) {
		Vector2 firerPosition = firer.getWorldCenter();
		
		fireWeapon(weapon, firerPosition, angle, antiLag);
	}
	
	/**
	 * Fires a shot directly at the target
	 * @param firer
	 */
	public void fire(Weapon weapon, boolean antiLag) {
		Vector2 firerPosition = firer.getWorldCenter();
		Vector2 targetPosition = target.getWorldCenter();
		
		float angle = MathUtils.atan2(firerPosition.y - targetPosition.y, firerPosition.x - targetPosition.x) - (float)Math.PI;
		
		fireWeapon(weapon, firerPosition, angle, antiLag);
	}
	
	/**
	 * Fires a shot so that it will hit the target (assuming it will remain at constant velocity)
	 * @param firer
	 */
	public void leadShot(Weapon weapon, boolean antiLag) {
		Vector2 firerPosition = firer.getWorldCenter();
		Vector2 targetPosition = target.getWorldCenter();
		Vector2 targetVelocity = target.getLinearVelocity();
		
		// Angle the target is moving at in radians
		float targetAngle = targetVelocity.angleRad();
		
		// Loop through every 2 degrees to find most efficient path
		for(float deg = 0; deg < 2 * Math.PI; deg += (Math.PI / 45)) {
			if(projectileWillHit(weapon.projectile, deg, targetAngle, firerPosition, targetPosition, targetVelocity)) {
				fireWeapon(weapon, firerPosition, deg, antiLag);
				break;
			}
		}
	}
	
	private void fireWeapon(Weapon weapon, Vector2 firerPosition, float angle, boolean antiLag) {
		// Fire all bullets
		if(weapon.controlledSpread != 0) {
			Timer.schedule(new Task() {
				int projectile = 0;
				float angleStart = angle - (((weapon.bullets - 1)/2) * weapon.controlledSpread * MathUtils.degreesToRadians);
				
	            @Override
	            public void run() {
	            	// Different angle for each projectile
	        		float angle2 = angleStart + (projectile * weapon.controlledSpread * MathUtils.degreesToRadians);
	        		projectileFactory.newProjectile(weapon.projectile, firerPosition.x, firerPosition.y, angle2, false, antiLag);
	        		projectile++;
	            }
		    }
		        , 0        // delay
		        , weapon.time     // interval
		        , weapon.bullets - 1 // repeats
			);
		}
		else {
			Timer.schedule(new Task() {
	            @Override
	            public void run() {
	        		// Change spread of projectiles depending on weapon
	        		float angle2 = angle + (MathUtils.degreesToRadians * MathUtils.random(-weapon.spread, weapon.spread));
	        		
	        		projectileFactory.newProjectile(weapon.projectile, firerPosition.x, firerPosition.y, angle2, false, antiLag);
	            }
		    }
		        , 0        // delay
		        , weapon.time     // interval
		        , weapon.bullets - 1 // repeats
			);
		}
	}
	
	// Fires a weapon with a specific projectile life time
	private void fireWeapon(Weapon weapon, Vector2 firerPosition, float time, float angle, boolean antiLag) {
		// Fire all bullets
		if(weapon.controlledSpread != 0) {
			Timer.schedule(new Task() {
				int projectile = 0;
				float angleStart = angle - (((weapon.bullets - 1)/2) * weapon.controlledSpread * MathUtils.degreesToRadians);
				
	            @Override
	            public void run() {
	            	// Different angle for each projectile
	        		float angle2 = angleStart + (projectile * weapon.controlledSpread * MathUtils.degreesToRadians);
	        		projectileFactory.newProjectile(weapon.projectile, firerPosition.x, firerPosition.y, angle2, false, antiLag);
	        		projectile++;
	            }
		    }
		        , 0        // delay
		        , weapon.time     // interval
		        , weapon.bullets - 1 // repeats
			);
		}
		else {
			Timer.schedule(new Task() {
	            @Override
	            public void run() {
	        		// Change spread of projectiles depending on weapon
	        		float angle2 = angle + (MathUtils.degreesToRadians * MathUtils.random(-weapon.spread, weapon.spread));
	        		
	        		projectileFactory.newProjectile(weapon.projectile, firerPosition.x, firerPosition.y, time, angle2, false, antiLag);
	            }
		    }
		        , 0        // delay
		        , weapon.time     // interval
		        , weapon.bullets - 1 // repeats
			);
		}
	}
	
	public float getAngleToTarget() {
		Vector2 firerPosition = firer.getWorldCenter();
		Vector2 targetPosition = target.getWorldCenter();
		
		return MathUtils.atan2(firerPosition.y - targetPosition.y, firerPosition.x - targetPosition.x) - (float)Math.PI;
	}
	
	/**
	 * Checks if the projectile will hit the target before the projectile expires
	 * @param projectile - projectile being fired
	 * @param projectileAngle - angle that the projectile is being shot at in radians
	 * @param targetAngle - angle that the target is moving in, in radians
	 * @param projectileStartingPos - staring position of the projectile
	 * @param targetStartingPos - starting position of the target
	 * @param targetVelocity - velocity of the target
	 * @return
	 */
	private boolean projectileWillHit(Projectile projectile, float projectileAngle, float targetAngle, Vector2 projectileStartingPos, Vector2 targetStartingPos, Vector2 targetVelocity) {
		Vector2 projectilePos, targetPos;
		for(float i = 0; i < projectile.time; i += projectile.time/25) {
			projectilePos = new Vector2((Math.abs(projectile.speed) * i * MathUtils.cos(projectileAngle)) + projectileStartingPos.x, (Math.abs(projectile.speed) * i * MathUtils.sin(projectileAngle)) + projectileStartingPos.y);
			targetPos = new Vector2((Math.abs(targetVelocity.x) * i * MathUtils.cos(targetAngle)) + targetStartingPos.x, (Math.abs(targetVelocity.y) * i * MathUtils.sin(targetAngle)) + targetStartingPos.y);
			
			if(Math.abs(Math.hypot(projectilePos.x - targetPos.x, projectilePos.y - targetPos.y)) <= 5) {
				return true;
			}
		}
		return false;
	}
}
