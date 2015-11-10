package components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.miv.BodyEditorLoader;
import com.miv.Constants;
import com.miv.Constants.ENTITY;
import items.Ships.Ship;

import maps.Maps.Terrain;

public class HitboxComponent implements Component {
	// Pre-loaded for faster projectile creation
	public static BodyEditorLoader projectiles = new BodyEditorLoader(Gdx.files.internal("assets\\Projectiles.json"));
		
	public Body body;
	public Vector2 bodyOrigin = new Vector2(0, 0);
		
	/**
	 * Projectiles
	 */
	public HitboxComponent(World world, Entity e, String source, String name, float width, float x, float y, float density, short categoryBits, short maskBits, boolean antiLag) {
		// Create a loader for the file saved from the editor if it doesn't exist in the ArrayMap loaders
		BodyEditorLoader loader = null;
		if(antiLag) {
			loader = projectiles;
		} else {
			loader = new BodyEditorLoader(Gdx.files.internal("assets\\" + source + ".json"));
		}
	    
	    // Create a BodyDef, as usual.
	    BodyDef bd = new BodyDef();
	    bd.position.set(x, y);
	    bd.type = BodyType.DynamicBody;
	    bd.fixedRotation = true;
	 
	    // Create a FixtureDef, as usual.
	    FixtureDef fd = new FixtureDef();
	    fd.density = density;
	    fd.friction = 0f;
	    fd.restitution = 0.25f;
	    fd.filter.categoryBits = categoryBits;
	    fd.filter.maskBits = maskBits;
	 
	    // Create a Body, as usual.
	    body = world.createBody(bd);
	    
	    // Create user data
	    body.setUserData(new UserData(e));
	    
	    bodyOrigin = loader.getOrigin(name, Constants.SCALE);
	 
	    // Create the body fixture automatically by using the loader.
	    loader.attachFixture(body, name, fd, width * Constants.SCALE);
	}
	
	/**
	 * Ships
	 */
	public HitboxComponent(World world, Entity e, Ship ship, float x, float y) {
		// Create a loader for the file saved from the editor if it doesn't exist in the ArrayMap loaders
		BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("assets\\" + ship.source + ".json"));
	    
	    // Create a BodyDef, as usual.
	    BodyDef bd = new BodyDef();
	    bd.position.set(x, y);
	    if(ship.isStatic) {
		    bd.type = BodyType.StaticBody;
	    } else {
		    bd.type = BodyType.DynamicBody;
	    }
	    bd.fixedRotation = true;
	 
	    // Create a FixtureDef, as usual.
	    FixtureDef fd = new FixtureDef();
	    fd.density = ship.density;
	    fd.friction = 0f;
	    fd.restitution = 0.25f;
	    fd.filter.categoryBits = ship.group.categoryBits;
	    fd.filter.maskBits = ship.group.maskBits;
	 
	    // Create a Body, as usual.
	    body = world.createBody(bd);
	    
	    // Create user data
	    body.setUserData(new UserData(e));
	    
	    bodyOrigin = loader.getOrigin(ship.name, Constants.SCALE);
	 
	    // Create the body fixture automatically by using the loader.
	    loader.attachFixture(body, ship.name, fd, ship.image.imageWidth * Constants.SCALE);
	}
	
	/**
	 * Terrain
	 */
	public HitboxComponent(World world, Entity e, Terrain terrain, float width, float x, float y) {
		// Create a loader
		BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("assets\\" + terrain.source + ".json"));
	    
	    // Create a FixtureDef, as usual.
	    FixtureDef fd = new FixtureDef();
	    fd.density = terrain.density;
	    fd.friction = 0f;
	    fd.restitution = 0.25f;
	    fd.filter.categoryBits = ENTITY.CATEGORY_WALL;
	    fd.filter.maskBits = ENTITY.MASK_WALL;
	    
	    // Create a BodyDef, as usual.
	    BodyDef bd = new BodyDef();
	    bd.position.set(x, y);
	    if(terrain.isStatic) {
		    bd.type = BodyType.StaticBody;
	    } else {
		    bd.type = BodyType.DynamicBody;
		    fd.density = terrain.density;
	    }
	    bd.fixedRotation = true;
	 
	    // Create a Body, as usual.
	    body = world.createBody(bd);
	    
	    // Create user data
	    body.setUserData(new UserData(e));
	    
	    bodyOrigin = loader.getOrigin(terrain.name, Constants.SCALE);
	 
	    // Create the body fixture automatically by using the loader.
	    loader.attachFixture(body, terrain.name, fd, width * Constants.SCALE);
	}
	
	/**
	 * Terrain
	 */
	public HitboxComponent(World world, Entity e, Terrain terrain, float width, float density, float x, float y) {
		// Create a loader
		BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("assets\\" + terrain.source + ".json"));
	    
	    // Create a FixtureDef, as usual.
	    FixtureDef fd = new FixtureDef();
	    fd.density = density;
	    fd.friction = 0f;
	    fd.restitution = 0.25f;
	    fd.filter.categoryBits = ENTITY.CATEGORY_WALL;
	    fd.filter.maskBits = ENTITY.MASK_WALL;
	    
	    // Create a BodyDef, as usual.
	    BodyDef bd = new BodyDef();
	    bd.position.set(x, y);
	    if(terrain.isStatic) {
		    bd.type = BodyType.StaticBody;
	    } else {
		    bd.type = BodyType.DynamicBody;
		    fd.density = density;
	    }
	    bd.fixedRotation = false;
	 
	    // Create a Body, as usual.
	    body = world.createBody(bd);
	    
	    // Create user data
	    body.setUserData(new UserData(e));
	    
	    bodyOrigin = loader.getOrigin(terrain.name, Constants.SCALE);
	 
	    // Create the body fixture automatically by using the loader.
	    loader.attachFixture(body, terrain.name, fd, width * Constants.SCALE);
	}
	
	/**
	 * Enemy ship spawners
	 */
	public HitboxComponent(World world, Entity e, Terrain terrain, float width, float x, float y, boolean isEnemyShipSpawner) {
		// Create a loader
		BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("assets\\" + terrain.source + ".json"));
	    
	    // Create a FixtureDef, as usual.
	    FixtureDef fd = new FixtureDef();
	    fd.density = terrain.density;
	    fd.friction = 0f;
	    fd.restitution = 0.25f;
	    fd.filter.categoryBits = ENTITY.CATEGORY_ENEMY;
	    fd.filter.maskBits = ENTITY.MASK_ENEMY;
	    
	    // Create a BodyDef, as usual.
	    BodyDef bd = new BodyDef();
	    bd.position.set(x, y);
	    if(terrain.isStatic) {
		    bd.type = BodyType.StaticBody;
	    } else {
		    bd.type = BodyType.DynamicBody;
		    fd.density = terrain.density;
	    }
	    bd.fixedRotation = false;
	 
	    // Create a Body, as usual.
	    body = world.createBody(bd);
	    
	    // Create user data
	    body.setUserData(new UserData(e));
	    
	    bodyOrigin = loader.getOrigin(terrain.name, Constants.SCALE);
	 
	    // Create the body fixture automatically by using the loader.
	    loader.attachFixture(body, terrain.name, fd, width * Constants.SCALE);
	}
	
	/**
	 * Walls/Doors
	 */
	public HitboxComponent(World world, Entity e, float x, float y, float width, float height, boolean isDoor) {
		// Create our body definition
		BodyDef groundBodyDef =new BodyDef();  
		// Set its world position
		groundBodyDef.position.set(x, y);  

		// Create a body from the defintion and add it to the world
		body = world.createBody(groundBodyDef); 
		
		// Create user data
	    body.setUserData(new UserData(e));
		
		// Create a polygon shape
		PolygonShape groundBox = new PolygonShape();  
				
		groundBox.setAsBox((width/2), (height/2));
		
		// Create a fixture from our polygon shape and add it to our ground body  
		FixtureDef fd = new FixtureDef();
		fd.shape = groundBox;
	    fd.density = 0f;
	    fd.friction = 0f;
	    fd.restitution = 0.25f;
	    if(isDoor) {
		    fd.filter.categoryBits = ENTITY.CATEGORY_DOOR;
		    fd.filter.maskBits = ENTITY.MASK_DOOR;
	    } else {
		    fd.filter.categoryBits = ENTITY.CATEGORY_WALL;
		    fd.filter.maskBits = ENTITY.MASK_WALL;
	    }
		body.createFixture(fd); 
		
		bodyOrigin = new Vector2((width/2), (height/2));
		
		// Clean up after ourselves
		groundBox.dispose();
	}
	
	/**
	 * Have the body move in a direction; called every frame if body is in state of motion
	 * @param speed - max speed of body
	 * @param force - force applied to body
	 * @param deltaTime - time passed since last frame
	 */
	public void moveRight(float speed, float force, float deltaTime) {
		if(body.getLinearVelocity().x < speed) {
			body.applyForceToCenter(new Vector2((force * deltaTime) / Constants.SCALE, 0), true);
		} else {
			body.setLinearVelocity(speed, body.getLinearVelocity().y);
		}
	}
	public void moveLeft(float speed, float force, float deltaTime) {
		if(body.getLinearVelocity().x > -speed) {
			body.applyForceToCenter(new Vector2((-force * deltaTime) / Constants.SCALE, 0), true);
		} else {
			body.setLinearVelocity(-speed, body.getLinearVelocity().y);
		}
	}
	public void moveUp(float speed, float force, float deltaTime) {
		if(body.getLinearVelocity().y < speed) {
			body.applyForceToCenter(new Vector2(0, (force * deltaTime) / Constants.SCALE), true);
		} else {
			body.setLinearVelocity(body.getLinearVelocity().x, speed);
		}
	}
	public void moveDown(float speed, float force, float deltaTime) {
		if(body.getLinearVelocity().y > -speed) {
			body.applyForceToCenter(new Vector2(0, (-force * deltaTime) / Constants.SCALE), true);
		} else {
			body.setLinearVelocity(body.getLinearVelocity().x, -speed);
		}
	}
	
	/**
	 * 
	 * @param deltaTime - time passed since last frame
	 * @param brakePower - how much the velocity is decreased by (%); eg a value of 0.9f means that velocity is set to 90% of current each time the function is called
	 */
	public void applyBrakes(float deltaTime, float brakePower) {
		Vector2 velocity = body.getLinearVelocity();
		
		if(velocity.x != 0) {
			velocity.set(velocity.x * brakePower, velocity.y);
		}
		
		if(velocity.y != 0) {
			velocity.set(velocity.x, velocity.y * brakePower);
		}
		body.setLinearVelocity(velocity);
	}
	
	/**
	 * 
	 * @param deltaTime - time passed since last frame
	 * @param brakePower - how much the velocity is decreased by (%); eg a value of 0.9f means that velocity is set to 90% of current each time the function is called
	 */
	public void applyHorizontalBrakes(float deltaTime, float brakePower) {
		Vector2 velocity = body.getLinearVelocity();
		
		if(velocity.x != 0) {
			velocity.set(velocity.x * brakePower, velocity.y);
		}
		
		body.setLinearVelocity(velocity);
	}
	
	/**
	 * 
	 * @param deltaTime - time passed since last frame
	 * @param brakePower - how much the velocity is decreased by (%); eg a value of 0.9f means that velocity is set to 90% of current each time the function is called
	 */
	public void applyVerticalBrakes(float deltaTime, float brakePower) {
		Vector2 velocity = body.getLinearVelocity();
		
		if(velocity.y != 0) {
			velocity.set(velocity.x, velocity.y * brakePower);
		}
		body.setLinearVelocity(velocity);
	}
	
	
	// For bodies
	public static class UserData {
		private ComponentMapper<HealthComponent> hm = ComponentMapper.getFor(HealthComponent.class);
		
		// Entity is queued for destruction (from both engine and world)
		public boolean queuedForDestruction;
		// Entity that the body belongs to
		public Entity e;
				
		public UserData(Entity e) {
			this.e = e;
		}
		
		// Have the entity who this body belongs to take damage
		public void takeDamage(float damage) {
			hm.get(e).takeDamage(damage, this);
		}
	}
}
