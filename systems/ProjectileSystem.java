package systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.physics.box2d.World;

import components.HitboxComponent;
import components.OnExpireComponent;
import components.ProjectileComponent;

public class ProjectileSystem extends EntitySystem {
	private ImmutableArray<Entity> entities;
	
	private ComponentMapper<ProjectileComponent> pm = ComponentMapper.getFor(ProjectileComponent.class);
	private ComponentMapper<OnExpireComponent> em = ComponentMapper.getFor(OnExpireComponent.class);
	
	private Engine engine;
	private World world;
	
	public ProjectileSystem(Engine engine, World world) {
		this.engine = engine;
		this.world = world;
	}

	@Override
	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.all(ProjectileComponent.class).get());
	}

	@Override
	public void removedFromEngine(Engine engine) {

	}

	@Override
	public void update(float deltaTime) {
		ProjectileComponent projectile;

		for(Entity e : entities) {
			projectile = pm.get(e);
			
			projectile.time -= deltaTime;
			if(projectile.time <= 0) {
				if(em.has(e)) {
					em.get(e).onExpire();
				}
				
				world.destroyBody(e.getComponent(HitboxComponent.class).body);
				engine.removeEntity(e);
			}
		}
	}
}