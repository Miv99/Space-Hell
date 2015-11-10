package systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;

import components.EnemyShipSpawnerComponent;

public class EnemyShipSpawnerSystem extends EntitySystem {
	private ImmutableArray<Entity> entities;
	
	private ComponentMapper<EnemyShipSpawnerComponent> em = ComponentMapper.getFor(EnemyShipSpawnerComponent.class);
		
	public EnemyShipSpawnerSystem() {
	}

	@Override
	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.all(EnemyShipSpawnerComponent.class).get());
	}

	@Override
	public void removedFromEngine(Engine engine) {

	}

	@Override
	public void update(float deltaTime) {
		EnemyShipSpawnerComponent spawner;
		
		for(Entity e : entities) {
			spawner = em.get(e);
			
			for(int i = 0; i < spawner.interval.length; i++) {
				spawner.time[i] -= deltaTime;
				if(spawner.time[i] <= 0) {
					spawner.spawn(i);
					spawner.time[i] = spawner.interval[i];
				}
			}
		}
	}
}
