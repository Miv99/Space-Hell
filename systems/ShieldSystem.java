package systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;

import components.ShieldComponent;

public class ShieldSystem extends EntitySystem {
	private ImmutableArray<Entity> entities;
	
	private ComponentMapper<ShieldComponent> sm = ComponentMapper.getFor(ShieldComponent.class);
	
	public ShieldSystem() {
		
	}

	@Override
	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.all(ShieldComponent.class).get());
	}

	@Override
	public void removedFromEngine(Engine engine) {

	}

	@Override
	public void update(float deltaTime) {
		ShieldComponent shield;

		for(Entity e : entities) {			
			shield = sm.get(e);
			
			if(shield.shieldActivated && !shield.shieldBroken) {
				if(shield.shieldStrength >= 0) {
					shield.shieldStrength -= deltaTime;
					if(shield.shieldStrength <= 0) {
						shield.shieldBroken = true;
					}
				}
			}
			
			// Increase shield time if right click not held
			if(!shield.shieldActivated || shield.shieldBroken) {
				shield.shieldStrength += deltaTime * shield.shieldEquipped.rechargeMultiplier;
				if(shield.shieldStrength >= shield.shieldEquipped.strength) {
					shield.shieldStrength = shield.shieldEquipped.strength;
					if(shield.shieldBroken) {
						shield.shieldBroken = false;
					}
				}
			}
		}
	}
}