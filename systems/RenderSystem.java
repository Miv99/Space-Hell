package systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import components.HitboxComponent;
import components.ShieldComponent;
import components.VisualComponent;

public class RenderSystem extends EntitySystem {
	private ImmutableArray<Entity> entities;

	private SpriteBatch batch;
	private Camera camera;
	public Entity focus;

	private ComponentMapper<HitboxComponent> hm = ComponentMapper.getFor(HitboxComponent.class);
	private ComponentMapper<VisualComponent> vm = ComponentMapper.getFor(VisualComponent.class);
	private ComponentMapper<ShieldComponent> sm = ComponentMapper.getFor(ShieldComponent.class);
	
	public RenderSystem (Camera camera, Entity focus) {
		batch = new SpriteBatch();

		this.camera = camera;
		this.focus = focus;
	}

	@Override
	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.all(HitboxComponent.class, VisualComponent.class).get());
	}

	@Override
	public void removedFromEngine(Engine engine) {

	}
	
	/**
	 * Smoothly center the camera on the entity
	 */
	public void center() {
		float lerp = 0.1f;
		Vector3 position = camera.position;
		Vector2 bodyPosition = hm.get(focus).body.getPosition();
		position.x += (bodyPosition.x - position.x) * lerp;
		position.y += (bodyPosition.y - position.y) * lerp;
	}

	@Override
	public void update(float deltaTime) {
		HitboxComponent hitbox;
		VisualComponent visual;
		
		camera.update();

		batch.begin();
		batch.setProjectionMatrix(camera.combined);
		
		for(Entity e : entities) {
			// Get hitbox and sprite
			hitbox = hm.get(e);
			visual = vm.get(e);
			
			// Draw sprite
			Vector2 position = hitbox.body.getPosition();
			visual.sprite.setRotation(MathUtils.radiansToDegrees * hitbox.body.getAngle());
			visual.sprite.setPosition(position.x - hitbox.bodyOrigin.x, position.y - hitbox.bodyOrigin.y);
			visual.sprite.draw(batch);
			
			// If shield activated and entity's shield is not broken, draw the shield
			if(sm.has(e)) {
				ShieldComponent shield = sm.get(e);
				
				if(shield.shieldActivated && !shield.shieldBroken) {
					shield.sprite.setRotation(MathUtils.radiansToDegrees * hitbox.body.getAngle());
					shield.sprite.setPosition(
							position.x - hitbox.bodyOrigin.x - (shield.sprite.getWidth() - visual.sprite.getWidth())/2, 
							position.y - hitbox.bodyOrigin.y - (shield.sprite.getHeight() - visual.sprite.getHeight())/2
					);
					sm.get(e).sprite.draw(batch);
				}
			}
		}

		batch.end();
		
		center();
	}
}