package systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.miv.Constants;

import components.AIMovementComponent;
import components.AIMovementComponent.MovementType;
import components.HitboxComponent;
import components.ShipComponent;
import utils.Utils;

public class AIMovementSystem extends EntitySystem {	
	private ImmutableArray<Entity> entities;
	
	private ComponentMapper<AIMovementComponent> am = ComponentMapper.getFor(AIMovementComponent.class);
	private ComponentMapper<HitboxComponent> hm = ComponentMapper.getFor(HitboxComponent.class);
	private ComponentMapper<ShipComponent> sm = ComponentMapper.getFor(ShipComponent.class);
		
	public AIMovementSystem() {
	}

	@Override
	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.all(AIMovementComponent.class, HitboxComponent.class, ShipComponent.class).get());
	}

	@Override
	public void removedFromEngine(Engine engine) {

	}

	@Override
	public void update(float deltaTime) {
		AIMovementComponent ai;
		HitboxComponent hitbox;
		Vector2 pos;
		Vector2 velocity;
		Vector2 targetPos = null;
		ShipComponent ship;
		
		for(Entity e : entities) {
			ai = am.get(e);
			hitbox = hm.get(e);
			pos = hitbox.body.getPosition();
			velocity = hitbox.body.getLinearVelocity();
			ship = sm.get(e);
			if(ai.target != null) {
				targetPos = hm.get(ai.target).body.getPosition();
			}
			
			boolean applyHorizontalBrakes = false, applyVerticalBrakes = false;
			
			if(ai.activated) {
				boolean moveRight = false, moveLeft = false, moveUp = false, moveDown = false;
				
				if(ai.type == MovementType.FOLLOW_TARGET) {
					if(pos.x > targetPos.x) {
						if(velocity.x > 0) {
							applyHorizontalBrakes = true;
						}
						moveLeft = true;
					} else if(pos.x < targetPos.x) {
						if(velocity.x < 0) {
							applyHorizontalBrakes = true;
						}
						moveRight = true;
					}
					
					if(pos.y > targetPos.y) {
						if(velocity.y > 0) {
							applyVerticalBrakes = true;
						}
						moveDown = true;
					} else if(pos.y < targetPos.y) {
						if(velocity.y > 0) {
							applyVerticalBrakes = true;
						}
						moveUp = true;
					}
				}
				else if(ai.type == MovementType.STALK_TARGET) {
					// If not within range, move to within range
					if(!Utils.inRange(pos, targetPos, ai.stalkRange)) {
						if(pos.x > targetPos.x) {
							if(velocity.x > 0) {
								applyHorizontalBrakes = true;
							}
							moveLeft = true;
						} else if(pos.x < targetPos.x) {
							if(velocity.x < 0) {
								applyHorizontalBrakes = true;
							}
							moveRight = true;
						}
						
						if(pos.y > targetPos.y) {
							if(velocity.y > 0) {
								applyVerticalBrakes = true;
							}
							moveDown = true;
						} else if(pos.y < targetPos.y) {
							if(velocity.y > 0) {
								applyVerticalBrakes = true;
							}
							moveUp = true;
						}
					}
					// If within range, slow down
					else {
						applyHorizontalBrakes = true;
						applyVerticalBrakes = true;
					}
				}
				if(applyHorizontalBrakes) {
					hitbox.applyHorizontalBrakes(deltaTime, Constants.ENEMY_BRAKE_POWER);
				}
				if(applyVerticalBrakes) {
					hitbox.applyVerticalBrakes(deltaTime, Constants.ENEMY_BRAKE_POWER);
				}
				
				// Movement
				if(moveRight) {
					hitbox.moveRight(ship.speed, ship.force, deltaTime);
				} else if(moveLeft) {
					hitbox.moveLeft(ship.speed, ship.force, deltaTime);
				} 
				if(moveUp) {
					hitbox.moveUp(ship.speed, ship.force, deltaTime);
				} else if(moveDown) {
					hitbox.moveDown(ship.speed, ship.force, deltaTime);
				}
			} else {
				if(Utils.inRange(pos, targetPos, ai.beginRange)) {
					ai.activated = true;
				}
			}
		}
	}
}