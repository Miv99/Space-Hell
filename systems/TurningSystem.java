package systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.MathUtils;

import components.HitboxComponent;
import components.TurningComponent;

public class TurningSystem extends EntitySystem {
	private ImmutableArray<Entity> entities;
	
	private ComponentMapper<HitboxComponent> hm = ComponentMapper.getFor(HitboxComponent.class);
	private ComponentMapper<TurningComponent> tm = ComponentMapper.getFor(TurningComponent.class);

	@Override
	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.all(HitboxComponent.class, TurningComponent.class).get());
	}

	@Override
	public void removedFromEngine(Engine engine) {

	}

	@Override
	public void update(float deltaTime) {
		HitboxComponent hitbox;
		TurningComponent turning;

		for(Entity e : entities) {
			hitbox = hm.get(e);
			turning = tm.get(e);
			
			// Rotate to face focus
			float angle = MathUtils.atan2(hitbox.body.getWorldCenter().y - hm.get(turning.focus).body.getWorldCenter().y, hitbox.body.getWorldCenter().x - hm.get(turning.focus).body.getWorldCenter().x) + (float)Math.PI;
			hitbox.body.setTransform(hitbox.body.getPosition(), angle); 
		}
	}
}