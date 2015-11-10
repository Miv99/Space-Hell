package components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.miv.ProjectileFactory;

import items.Weapons.Weapon;

/**
 * Used by player only
 * @author Miv
 */
public class WeaponComponent implements Component {
	// Time until weapon can start firing (beings counting down when fire is held down)
	public float weaponStartup;
	// Time until weapon can be fired again in seconds
	public float weaponCooldown;
	
	// Current weapon equipped
	public Weapon weaponEquipped;
	
	// Weapon fire button is being held down
	public boolean isActivated;
	// Weapon is currently firing
	public boolean isFiring;
	
	// Origin of projectiles and angle of travel
	private float xOrigin, yOrigin, angle;
	
	// Whether this ship is a player (true) or an enemy (false)
	private boolean isPlayer;
		
	public WeaponComponent(Weapon weapon, boolean isPlayer) {
		weaponEquipped = weapon;
		this.isPlayer = isPlayer;
	}
	
	public WeaponComponent() {}
	
	// Equip weapon
	public void equipWeapon(Weapon weapon) {
		weaponEquipped = weapon;
		weaponCooldown = weapon.cooldown;
		weaponStartup = weapon.startup;
	}
	
	/**
	 * Set location of firing for automatic firing
	 * @param x - x location of where weapon was fired from in pixels
	 * @param y - y location of where weapon was fired from in pixels
	 * @param angle - angle of bullet in radians
	 */
	public void setAim(float x, float y, float angle) {
		xOrigin = x;
		yOrigin = y;
		this.angle = angle;
	}
	
	/**
	 * Fire the weapon
	 * @param x - x location of where weapon was fired from in pixels
	 * @param y - y location of where weapon was fired from in pixels
	 * @param angle - angle of bullet in radians
	 */
	public void fireWeapon(ProjectileFactory projectileFactory, float x, float y, float angle) {
		// Reset weapon cooldown
		weaponCooldown = weaponEquipped.cooldown;
		
		isFiring = true;
		
		// Fire all bullets
		if(weaponEquipped.controlledSpread != 0) {
			Timer.schedule(new Task() {
				int projectile = 0;
				float angleStart = angle - (((weaponEquipped.bullets - 1)/2) * weaponEquipped.controlledSpread * MathUtils.degreesToRadians);
				
	            @Override
	            public void run() {
	            	// Different angle for each projectile
	        		float angle2 = angleStart + (projectile * weaponEquipped.controlledSpread * MathUtils.degreesToRadians);
	        		projectileFactory.newProjectile(weaponEquipped.projectile, x, y, angle2, isPlayer, false);
	        		projectile++;
	            }
		    }
		        , 0        // delay
		        , weaponEquipped.time     // interval
		        , weaponEquipped.bullets - 1 // repeats
			);
		}
		else {
			Timer.schedule(new Task() {
	            @Override
	            public void run() {
	        		// Change spread of projectiles depending on weapon
	        		float angle2 = angle + (MathUtils.degreesToRadians * MathUtils.random(-weaponEquipped.spread, weaponEquipped.spread));
	        		
	        		projectileFactory.newProjectile(weaponEquipped.projectile, x, y, angle2, isPlayer, false);
	            }
		    }
		        , 0        // delay
		        , weaponEquipped.time     // interval
		        , weaponEquipped.bullets - 1 // repeats
			);
		}
		
		// Set isFiring to false when all bullets have been shot
		Timer.schedule(new Task() {
            @Override
            public void run() {
        		isFiring = false;
            }
	    }
	        , (weaponEquipped.bullets - 1) * weaponEquipped.time        // delay
		);
	}
	
	/**
	 * Fire the weapon according to data set by setAim
	 */
	public void fireWeapon(ProjectileFactory projectileFactory) {
		// Reset weapon cooldown
		weaponCooldown = weaponEquipped.cooldown;
		
		isFiring = true;
		
		// Fire all bullets
		if(weaponEquipped.controlledSpread != 0) {
			Timer.schedule(new Task() {
				int projectile = 0;
				float angleStart = angle - (((weaponEquipped.bullets - 1)/2) * weaponEquipped.controlledSpread * MathUtils.degreesToRadians);
				
	            @Override
	            public void run() {
	            	// Different angle for each projectile
	        		float angle2 = angleStart + (projectile * weaponEquipped.controlledSpread * MathUtils.degreesToRadians);
	        		projectileFactory.newProjectile(weaponEquipped.projectile, xOrigin, yOrigin, angle2, isPlayer, false);
	        		projectile++;
	            }
		    }
		        , 0        // delay
		        , weaponEquipped.time     // interval
		        , weaponEquipped.bullets - 1 // repeats
			);
		}
		else {
			Timer.schedule(new Task() {
	            @Override
	            public void run() {
	        		// Change spread of projectiles depending on weapon
	        		float angle2 = angle + (MathUtils.degreesToRadians * MathUtils.random(-weaponEquipped.spread, weaponEquipped.spread));
	        		
	        		projectileFactory.newProjectile(weaponEquipped.projectile, xOrigin, yOrigin, angle2, isPlayer, false);
	            }
		    }
		        , 0        // delay
		        , weaponEquipped.time     // interval
		        , weaponEquipped.bullets - 1 // repeats
			);
		}
		
		// Set isFiring to false when all bullets have been shot
		Timer.schedule(new Task() {
            @Override
            public void run() {
        		isFiring = false;
            }
	    }
	        , (weaponEquipped.bullets - 1) * weaponEquipped.time        // delay
		);
	}
}
