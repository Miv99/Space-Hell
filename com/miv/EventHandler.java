package com.miv;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import ai.RedBoss;
import ai.StarbaseBoss;
import components.HealthComponent;
import components.HitboxComponent;
import components.PlayerComponent;
import components.ShieldComponent;
import components.ShipComponent;
import components.WeaponComponent;
import items.Ships.Ship;
import loadSave.LoadSave;
import loadSave.LoadSave.Data;
import utils.EntityUtils;

public class EventHandler implements InputProcessor {
	
	private ComponentMapper<HitboxComponent> hm = ComponentMapper.getFor(HitboxComponent.class);
	private ComponentMapper<HealthComponent> hpm = ComponentMapper.getFor(HealthComponent.class);
	private ComponentMapper<ShipComponent> pm = ComponentMapper.getFor(ShipComponent.class);
	private ComponentMapper<ShieldComponent> sm = ComponentMapper.getFor(ShieldComponent.class);
	private ComponentMapper<WeaponComponent> wm = ComponentMapper.getFor(WeaponComponent.class);
	
	public boolean leftClickHeld, rightClickHeld;
	
	private Main game;
	
	public EventHandler(Main game) {
		this.game = game;
	}
	
	public boolean keyDown (int keycode) {
		if(keycode == Input.Keys.ESCAPE) {
			if(game.paused) {
				game.resume();
			} else {
				game.pause();
			}
		}
		else if(keycode == Input.Keys.NUM_1) {
			game.engine.addEntity(EntityUtils.newEnemyShip(game.engine, game.world, game.projectileFactory, Ship.RED_BOSS, game.player, RedBoss.class, null, "Red Boss", 100, 100, 3000f, 500f, 40f, 600f));
		}
		else if(keycode == Input.Keys.NUM_2) {
			game.engine.addEntity(EntityUtils.newEnemyShip(game.engine, game.world, game.projectileFactory, Ship.STARBASE_BOSS, game.player, StarbaseBoss.class, null, "Starbase Boss", 100, 100, 4000f, 500f, 40f, 600f, false));
		}
		else if(keycode == Input.Keys.O) {
			LoadSave.saveGame(new Data(pm.get(game.player), hpm.get(game.player), wm.get(game.player), sm.get(game.player), game.map, hm.get(game.player).body.getPosition(), 1));
		}
		else if(keycode == Input.Keys.P) {
			LoadSave.loadGame(game, LoadSave.getSaveData(1));
		}
		return false;
	}

	public boolean keyUp (int keycode) {
		
		
	   return false;
	}

	public boolean keyTyped (char character) {
	   return false;
	}

	public boolean touchDown (int x, int y, int pointer, int button) {
		if(button == Input.Buttons.LEFT) {
			leftClickHeld = true;
			
			// Get all players with weapons and activate weapons
			ImmutableArray<Entity> entities = game.engine.getEntitiesFor(Family.all(WeaponComponent.class, PlayerComponent.class).get());
			for(Entity e : entities) {
				wm.get(e).isActivated = true;
			}
		} else if(button == Input.Buttons.RIGHT) {
			rightClickHeld = true;
			
			// Get all players with shields and activate shields
			ImmutableArray<Entity> entities = game.engine.getEntitiesFor(Family.all(ShieldComponent.class, PlayerComponent.class).get());
			for(Entity e : entities) {
				sm.get(e).shieldActivated = true;
			}
		}
		return false;
	}

	public boolean touchUp (int x, int y, int pointer, int button) {
		if(button == Input.Buttons.LEFT) {
			leftClickHeld = false;
			
			// Get all players with weapons and deactivate weapons
			ImmutableArray<Entity> entities = game.engine.getEntitiesFor(Family.all(WeaponComponent.class, PlayerComponent.class).get());
			for(Entity e : entities) {
				wm.get(e).isActivated = false;
			}
		} else if(button == Input.Buttons.RIGHT) {
			rightClickHeld = false;
			
			// Get all players with shields and deactivate shields
			ImmutableArray<Entity> entities = game.engine.getEntitiesFor(Family.all(ShieldComponent.class, PlayerComponent.class).get());
			for(Entity e : entities) {
				sm.get(e).shieldActivated = false;
			}
		}
	    return false;
	}

	public boolean touchDragged (int x, int y, int pointer) {
		return false;
	}

	public boolean mouseMoved (int x, int y) {
	    return false;
	}

	public boolean scrolled (int amount) {
	    return false;
	}
	
	// Called after every frame
	public void frameUpdate(float deltaTime, Camera cam) {
		// Get all players with hitboxes
		ImmutableArray<Entity> entities = game.engine.getEntitiesFor(Family.all(ShipComponent.class, HitboxComponent.class, PlayerComponent.class).get());
		
		HitboxComponent hitbox;
		ShipComponent player;
		WeaponComponent weapon;
		
		// Get mouse position in the world
		Vector3 worldCoordinates = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		cam.unproject(worldCoordinates);
		
		for(Entity e : entities) {
			hitbox = hm.get(e);
			weapon = wm.get(e);
			player = pm.get(e);
			
		    Vector2 playerPosition = hitbox.body.getPosition();
			
			// Get angle of mouse to player
		    float angle = MathUtils.atan2(worldCoordinates.y - playerPosition.y, worldCoordinates.x - playerPosition.x);
		    
		    float force = player.force;
		    
			// Apply brakes if shift pressed
		    if(Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
		    	force /= 1.25f;
		    	hitbox.applyBrakes(deltaTime, player.brakePower);
		    }
			// Movement if WASD is pressed

			if(Gdx.input.isKeyPressed(Keys.D)) {
				hitbox.moveRight(player.speed, force, deltaTime);
			} else if(Gdx.input.isKeyPressed(Keys.A)) {
				hitbox.moveLeft(player.speed, force, deltaTime);
			} 
			if(Gdx.input.isKeyPressed(Keys.W)) {
				hitbox.moveUp(player.speed, force, deltaTime);
			} else if(Gdx.input.isKeyPressed(Keys.S)) {
				hitbox.moveDown(player.speed, force, deltaTime);
			}
			
			// Set weapon fire location
			if(weapon != null && (weapon.isActivated || weapon.isFiring)) {
				weapon.setAim(playerPosition.x, playerPosition.y, angle);
			}
		    
		    // Rotate the player's body accordingly
		    hitbox.body.setTransform(playerPosition, angle);
		}
	}
}