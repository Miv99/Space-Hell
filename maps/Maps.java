package maps;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.miv.Images.Image;
import com.miv.Main;
import com.miv.Constants;

import systems.RenderSystem;
import utils.CustomTask;
import components.AIComponent;
import components.ActorComponent;
import components.HealthComponent;
import components.HitboxComponent;
import components.PlayerComponent;
import components.ShieldComponent;
import components.ShipComponent;
import components.VisualComponent;
import components.WeaponComponent;

public class Maps {
	public static final float ASTEROID_BASE_DENSITY = 0.2f;
	public static final float ASTEROID_BASE_HP = 100f;
		
	public static enum Map {
		OUTPOST(Outpost.class),
		
		MISSION_1(Mission1.class);
		
		public Class<?> mainClass;
		
		Map(Class<?> mainClass) {
			this.mainClass = mainClass;
		}
	}
	
	public static enum Terrain {
		STARBASE_1("Terrain", "Starbase 1", Image.STARBASE_1),
		
		STARBASE_1_CORRIDOR_1("Terrain", "Starbase 1 Corridor 1", Image.STARBASE_1_CORRIDOR_1),
		STARBASE_1_CORRIDOR_2("Terrain", "Starbase 1 Corridor 2", Image.STARBASE_1_CORRIDOR_2),
		STARBASE_1_CORRIDOR_3("Terrain", "Starbase 1 Corridor 3", Image.STARBASE_1_CORRIDOR_3),
		STARBASE_1_CORRIDOR_4("Terrain", "Starbase 1 Corridor 4", Image.STARBASE_1_CORRIDOR_4),
		
		SPAWNER_1("Terrain", "Spawner 1", Image.SPAWNER_1),
		
		
		ASTEROID_1("Asteroids", "Asteroid 1", Image.ASTEROID_1, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		ASTEROID_2("Asteroids", "Asteroid 2", Image.ASTEROID_2, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		ASTEROID_3("Asteroids", "Asteroid 3", Image.ASTEROID_3, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		ASTEROID_4("Asteroids", "Asteroid 4", Image.ASTEROID_4, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		ASTEROID_5("Asteroids", "Asteroid 5", Image.ASTEROID_5, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		ASTEROID_6("Asteroids", "Asteroid 6", Image.ASTEROID_6, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		ASTEROID_7("Asteroids", "Asteroid 7", Image.ASTEROID_7, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		ASTEROID_8("Asteroids", "Asteroid 8", Image.ASTEROID_8, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		ASTEROID_9("Asteroids", "Asteroid 9", Image.ASTEROID_9, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		ASTEROID_10("Asteroids", "Asteroid 10", Image.ASTEROID_10, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		
		ASTEROID_11("Asteroids", "Asteroid 11", Image.ASTEROID_11, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		ASTEROID_12("Asteroids", "Asteroid 12", Image.ASTEROID_12, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		ASTEROID_13("Asteroids", "Asteroid 13", Image.ASTEROID_13, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		ASTEROID_14("Asteroids", "Asteroid 14", Image.ASTEROID_14, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		ASTEROID_15("Asteroids", "Asteroid 15", Image.ASTEROID_15, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		ASTEROID_16("Asteroids", "Asteroid 16", Image.ASTEROID_16, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		ASTEROID_17("Asteroids", "Asteroid 17", Image.ASTEROID_17, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		ASTEROID_18("Asteroids", "Asteroid 18", Image.ASTEROID_18, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		ASTEROID_19("Asteroids", "Asteroid 19", Image.ASTEROID_19, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		ASTEROID_20("Asteroids", "Asteroid 20", Image.ASTEROID_20, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		
		ASTEROID_21("Asteroids", "Asteroid 21", Image.ASTEROID_21, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		ASTEROID_22("Asteroids", "Asteroid 22", Image.ASTEROID_22, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		ASTEROID_23("Asteroids", "Asteroid 23", Image.ASTEROID_23, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		ASTEROID_24("Asteroids", "Asteroid 24", Image.ASTEROID_24, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		ASTEROID_25("Asteroids", "Asteroid 25", Image.ASTEROID_25, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		ASTEROID_26("Asteroids", "Asteroid 26", Image.ASTEROID_26, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		ASTEROID_27("Asteroids", "Asteroid 27", Image.ASTEROID_27, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		ASTEROID_28("Asteroids", "Asteroid 28", Image.ASTEROID_28, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		ASTEROID_29("Asteroids", "Asteroid 29", Image.ASTEROID_29, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		ASTEROID_30("Asteroids", "Asteroid 30", Image.ASTEROID_30, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		
		ASTEROID_31("Asteroids", "Asteroid 31", Image.ASTEROID_31, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		ASTEROID_32("Asteroids", "Asteroid 32", Image.ASTEROID_32, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		ASTEROID_33("Asteroids", "Asteroid 33", Image.ASTEROID_33, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		ASTEROID_34("Asteroids", "Asteroid 34", Image.ASTEROID_34, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		ASTEROID_35("Asteroids", "Asteroid 35", Image.ASTEROID_35, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		ASTEROID_36("Asteroids", "Asteroid 36", Image.ASTEROID_36, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		ASTEROID_37("Asteroids", "Asteroid 37", Image.ASTEROID_37, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		ASTEROID_38("Asteroids", "Asteroid 38", Image.ASTEROID_38, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		ASTEROID_39("Asteroids", "Asteroid 39", Image.ASTEROID_39, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		ASTEROID_40("Asteroids", "Asteroid 40", Image.ASTEROID_40, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		
		ASTEROID_41("Asteroids", "Asteroid 41", Image.ASTEROID_41, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		ASTEROID_42("Asteroids", "Asteroid 42", Image.ASTEROID_42, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		ASTEROID_43("Asteroids", "Asteroid 43", Image.ASTEROID_43, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		ASTEROID_44("Asteroids", "Asteroid 44", Image.ASTEROID_44, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		ASTEROID_45("Asteroids", "Asteroid 45", Image.ASTEROID_45, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		ASTEROID_46("Asteroids", "Asteroid 46", Image.ASTEROID_46, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		ASTEROID_47("Asteroids", "Asteroid 47", Image.ASTEROID_47, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		ASTEROID_48("Asteroids", "Asteroid 48", Image.ASTEROID_48, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		ASTEROID_49("Asteroids", "Asteroid 49", Image.ASTEROID_49, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		ASTEROID_50("Asteroids", "Asteroid 50", Image.ASTEROID_50, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		
		ASTEROID_51("Asteroids", "Asteroid 51", Image.ASTEROID_51, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		ASTEROID_52("Asteroids", "Asteroid 52", Image.ASTEROID_52, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		ASTEROID_53("Asteroids", "Asteroid 53", Image.ASTEROID_53, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		ASTEROID_54("Asteroids", "Asteroid 54", Image.ASTEROID_54, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		ASTEROID_55("Asteroids", "Asteroid 55", Image.ASTEROID_55, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		ASTEROID_56("Asteroids", "Asteroid 56", Image.ASTEROID_56, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		ASTEROID_57("Asteroids", "Asteroid 57", Image.ASTEROID_57, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		ASTEROID_58("Asteroids", "Asteroid 58", Image.ASTEROID_58, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		ASTEROID_59("Asteroids", "Asteroid 59", Image.ASTEROID_59, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		ASTEROID_60("Asteroids", "Asteroid 60", Image.ASTEROID_60, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		
		ASTEROID_61("Asteroids", "Asteroid 61", Image.ASTEROID_61, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		ASTEROID_62("Asteroids", "Asteroid 62", Image.ASTEROID_62, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		ASTEROID_63("Asteroids", "Asteroid 63", Image.ASTEROID_63, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY),
		ASTEROID_64("Asteroids", "Asteroid 64", Image.ASTEROID_64, ASTEROID_BASE_HP, ASTEROID_BASE_DENSITY);
		
		public String source, name;
		public Image image;
		
		public boolean isStatic;
		public float hp, density;
		
		Terrain(String source, String name, Image image) {
			this.source = source;
			this.name = name;
			this.image = image;
			isStatic = true;
		}
		
		Terrain(String source, String name, Image image, float density) {
			this.source = source;
			this.name = name;
			this.image = image;
			isStatic = false;
			this.density = density;
		}
		
		Terrain(String source, String name, Image image, float hp, float density) {
			this.source = source;
			this.name = name;
			this.image = image;
			isStatic = false;
			this.hp = hp;
			this.density = density;
		}
	}
	
	public static Texture getRandomBackground() {
		return new Texture(Gdx.files.internal("assets\\Backgrounds\\" + MathUtils.random(0, Constants.NUMBER_OF_BACKGROUND_IMAGES - 1) + ".png"));
	}
	
	public static void loadMap(Main main, Entity player, Map map) {
		// Destroy all bodies
		Array<Body> bodies = new Array<Body>();
		main.world.getBodies(bodies);
		for(Body b : bodies) {
			main.world.destroyBody(b);
		}
		
		// Create a copy of the player
		Entity playerCopy = new Entity();
		playerCopy.add(new ActorComponent("Player"));
		
		HealthComponent health = player.getComponent(HealthComponent.class);
		HealthComponent healthCopy = new HealthComponent(health.maxHP);
		playerCopy.add(healthCopy);
		
		ShipComponent ship = player.getComponent(ShipComponent.class);
		
		HitboxComponent hitbox = player.getComponent(HitboxComponent.class);
		HitboxComponent hitboxCopy = new HitboxComponent(main.world, playerCopy, ship.ship, hitbox.body.getPosition().x, hitbox.body.getPosition().y);
		playerCopy.add(hitboxCopy);
		
		playerCopy.add(new PlayerComponent());
		playerCopy.add(new VisualComponent(ship.ship.image.sprite));
		playerCopy.add(new ShieldComponent(player.getComponent(ShieldComponent.class).shieldEquipped, ship.ship.image.shieldWidth, ship.ship.image.shieldHeight));
		playerCopy.add(new WeaponComponent(player.getComponent(WeaponComponent.class).weaponEquipped, true));
		
		ShipComponent shipCopy = new ShipComponent(ship.ship);
		shipCopy.speed = ship.speed;
		shipCopy.force = ship.force;
		shipCopy.brakePower = ship.brakePower;
		playerCopy.add(shipCopy);
		
		// Stop all AI timers
		for(Entity e : main.engine.getEntities()) {
			if(e.getComponent(AIComponent.class) != null) {
				for(CustomTask t : e.getComponent(AIComponent.class).timers.tasks) {
					t.task.cancel();
				}
			}
		}
		
		// Remove all entities from engine
		main.engine.removeAllEntities();
		
		// Add player copy to engine
		main.engine.addEntity(playerCopy);
		
		// Set camera to focus on player
		main.engine.getSystem(RenderSystem.class).focus = playerCopy;
				
		// Load map
		try {
			Method m = map.mainClass.getMethod("create", Main.class, Entity.class);
			m.invoke(null, main, playerCopy);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
				
		// Update reference
		main.player = playerCopy;
		main.parallaxSystem.focus = hitboxCopy.body;
		
		// Center camera on player
		Vector3 position = main.camera.position;
		Vector2 bodyPosition = hitboxCopy.body.getPosition();
		position.x = bodyPosition.x;
		position.y = bodyPosition.y;
	}
}
