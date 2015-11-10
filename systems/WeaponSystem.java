package systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.miv.ProjectileFactory;

import components.ProjectileFiringComponent;
import components.WeaponComponent;

public class WeaponSystem extends EntitySystem {
	private ImmutableArray<Entity> entities;
	
	private ComponentMapper<WeaponComponent> wm = ComponentMapper.getFor(WeaponComponent.class);
	
	private ProjectileFactory projectileFactory;
	
	public WeaponSystem(ProjectileFactory projectileFactory) {
		this.projectileFactory = projectileFactory;
	}

	@Override
	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.all(WeaponComponent.class).exclude(ProjectileFiringComponent.class).get());
	}

	@Override
	public void removedFromEngine(Engine engine) {

	}

	@Override
	public void update(float deltaTime) {
		WeaponComponent weapon;

		for(Entity e : entities) {
			weapon = wm.get(e);
			
			// Weapon must not be starting up
			if(weapon.weaponStartup <= 0) {
				// Weapon must not be on cooldown
				if(weapon.weaponCooldown <= 0) {
					if(weapon.isActivated) {
						weapon.fireWeapon(projectileFactory);
					}
				} else {
					weapon.weaponCooldown -= deltaTime;
				}
			} else {
				weapon.weaponStartup -= deltaTime;
			}
			
			// Reset weapon startup cooldown if weapon not activated
			if(!weapon.isActivated) {
				weapon.weaponStartup = weapon.weaponEquipped.startup;
			}
		}
	}
}