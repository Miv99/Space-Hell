package systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;

import components.AIComponent;
import components.HitboxComponent;

public class AIPhaseSystem extends EntitySystem {
	private ImmutableArray<Entity> entities;
	
	private ComponentMapper<AIComponent> am = ComponentMapper.getFor(AIComponent.class);
	private ComponentMapper<HitboxComponent> hm = ComponentMapper.getFor(HitboxComponent.class);
		
	public AIPhaseSystem() {
	}

	@Override
	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.all(AIComponent.class).get());
	}

	@Override
	public void removedFromEngine(Engine engine) {

	}

	@Override
	public void update(float deltaTime) {
		AIComponent ai;
		
		for(Entity e : entities) {
			ai = am.get(e);
			
			ai.update(deltaTime, hm.get(e).body.getWorldCenter());
		}
	}
}
