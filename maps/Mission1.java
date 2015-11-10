package maps;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.miv.Constants;
import com.miv.Main;
import com.miv.ProjectileFactory;

import ai.BasicTurret;
import ai.Swarmer;
import components.EnemyShipSpawnerComponent;
import components.HealthComponent;
import components.HitboxComponent;
import components.VisualComponent;
import components.AIMovementComponent.MovementType;
import items.Ships.Ship;
import maps.Maps.Terrain;
import utils.EntityUtils;
import utils.MapUtils;

public class Mission1 {
	public static void create(Main main, Entity player) {
		float startY = 200;
		float range = 100f;
		
		// Add enemies
		for(int i = 0; i < 15; i++) {
			//EntityUtils.newEnemyShip(main.engine, main.world, main.projectileFactory, Ship.SWARMER_4, player, Swarmer.class, MovementType.FOLLOW_TARGET, "Swarmer 4", 400 + i*25, 400, 100f, 500f, 30f, 600f);
		}
		
		// Spawners and corridors
		// Bottom left spawner and corridors
		addBottomSwarmerSpawner(main.engine, main.world, main.projectileFactory, Ship.SWARMER_4, player, Swarmer.class, MovementType.FOLLOW_TARGET, "Swarmer 4", 100f, range, 30f, 600f, 10f, Terrain.SPAWNER_1, 700f, 
				250, startY);
		// Left vertical corridors
		main.engine.addEntity(
			MapUtils.createTerrainObject(main.engine, main.world, Terrain.STARBASE_1_CORRIDOR_1, 
					250, startY + (Terrain.STARBASE_1.image.imageHeight/2 * Constants.SCALE) + (Terrain.STARBASE_1_CORRIDOR_1.image.imageHeight/2 * Constants.SCALE))
		);
		main.engine.addEntity(
			MapUtils.createTerrainObject(main.engine, main.world, Terrain.STARBASE_1_CORRIDOR_1, 
					250, startY + (Terrain.STARBASE_1.image.imageHeight/2 * Constants.SCALE) + (Terrain.STARBASE_1_CORRIDOR_1.image.imageHeight/2 * Constants.SCALE) * 3)
		);
		main.engine.addEntity(
			MapUtils.createTerrainObject(main.engine, main.world, Terrain.STARBASE_1_CORRIDOR_1, 
					250, startY + (Terrain.STARBASE_1.image.imageHeight/2 * Constants.SCALE) + (Terrain.STARBASE_1_CORRIDOR_1.image.imageHeight/2 * Constants.SCALE) * 5)
		);
		// Left corner corridor
		main.engine.addEntity(
			MapUtils.createTerrainObject(main.engine, main.world, Terrain.STARBASE_1_CORRIDOR_2, 
					250, startY + (Terrain.STARBASE_1.image.imageHeight/2 * Constants.SCALE) + (Terrain.STARBASE_1_CORRIDOR_1.image.imageHeight/2 * Constants.SCALE) * 7)
		);
		// Left horizontal corridors
		main.engine.addEntity(
			MapUtils.createTerrainObject(main.engine, main.world, Terrain.STARBASE_1_CORRIDOR_3, 
					250 + (Terrain.STARBASE_1_CORRIDOR_1.image.imageWidth/2 * Constants.SCALE) * 2, startY + (Terrain.STARBASE_1.image.imageHeight/2 * Constants.SCALE) + (Terrain.STARBASE_1_CORRIDOR_1.image.imageHeight/2 * Constants.SCALE) * 7)
		);
		main.engine.addEntity(
			MapUtils.createTerrainObject(main.engine, main.world, Terrain.STARBASE_1_CORRIDOR_3, 
					250 + (Terrain.STARBASE_1_CORRIDOR_1.image.imageWidth/2 * Constants.SCALE) * 4, startY + (Terrain.STARBASE_1.image.imageHeight/2 * Constants.SCALE) + (Terrain.STARBASE_1_CORRIDOR_1.image.imageHeight/2 * Constants.SCALE) * 7)
		);
		main.engine.addEntity(
			MapUtils.createTerrainObject(main.engine, main.world, Terrain.STARBASE_1_CORRIDOR_3, 
					250 + (Terrain.STARBASE_1_CORRIDOR_1.image.imageWidth/2 * Constants.SCALE) * 6, startY + (Terrain.STARBASE_1.image.imageHeight/2 * Constants.SCALE) + (Terrain.STARBASE_1_CORRIDOR_1.image.imageHeight/2 * Constants.SCALE) * 7)
		);
		// Right horizontal corridors
		main.engine.addEntity(
			MapUtils.createTerrainObject(main.engine, main.world, Terrain.STARBASE_1_CORRIDOR_3, 
					250 + (Terrain.STARBASE_1.image.imageHeight/2 * Constants.SCALE) + (Terrain.STARBASE_1_CORRIDOR_1.image.imageWidth/2 * Constants.SCALE) * 6, startY + (Terrain.STARBASE_1.image.imageHeight/2 * Constants.SCALE) + (Terrain.STARBASE_1_CORRIDOR_1.image.imageHeight/2 * Constants.SCALE) * 7)
		);
		main.engine.addEntity(
			MapUtils.createTerrainObject(main.engine, main.world, Terrain.STARBASE_1_CORRIDOR_3, 
					250 + (Terrain.STARBASE_1.image.imageHeight/2 * Constants.SCALE) + (Terrain.STARBASE_1_CORRIDOR_1.image.imageWidth/2 * Constants.SCALE) * 8, startY + (Terrain.STARBASE_1.image.imageHeight/2 * Constants.SCALE) + (Terrain.STARBASE_1_CORRIDOR_1.image.imageHeight/2 * Constants.SCALE) * 7)
		);
		main.engine.addEntity(
			MapUtils.createTerrainObject(main.engine, main.world, Terrain.STARBASE_1_CORRIDOR_3, 
					250 + (Terrain.STARBASE_1.image.imageHeight/2 * Constants.SCALE) + (Terrain.STARBASE_1_CORRIDOR_1.image.imageWidth/2 * Constants.SCALE) * 10, startY + (Terrain.STARBASE_1.image.imageHeight/2 * Constants.SCALE) + (Terrain.STARBASE_1_CORRIDOR_1.image.imageHeight/2 * Constants.SCALE) * 7)
		);
		main.engine.addEntity(
			MapUtils.createTerrainObject(main.engine, main.world, Terrain.STARBASE_1_CORRIDOR_3, 
					250 + (Terrain.STARBASE_1.image.imageHeight/2 * Constants.SCALE) + (Terrain.STARBASE_1_CORRIDOR_1.image.imageWidth/2 * Constants.SCALE) * 12, startY + (Terrain.STARBASE_1.image.imageHeight/2 * Constants.SCALE) + (Terrain.STARBASE_1_CORRIDOR_1.image.imageHeight/2 * Constants.SCALE) * 7)
		);
		// Top middle spawner
		addTopSwarmerSpawner(main.engine, main.world, main.projectileFactory, Ship.SWARMER_4, player, Swarmer.class, MovementType.FOLLOW_TARGET, "Swarmer 4", 100f, range, 30f, 600f, 10f, Terrain.STARBASE_1, 700f, 
				250 + (Terrain.STARBASE_1_CORRIDOR_1.image.imageHeight/2 * Constants.SCALE) * 8, startY + (Terrain.STARBASE_1.image.imageHeight/2 * Constants.SCALE) + (Terrain.STARBASE_1_CORRIDOR_1.image.imageHeight/2 * Constants.SCALE) * 7);
		// Right corner corridor
		main.engine.addEntity(
			MapUtils.createTerrainObject(main.engine, main.world, Terrain.STARBASE_1_CORRIDOR_4, 
					250 + (Terrain.STARBASE_1.image.imageHeight/2 * Constants.SCALE) + (Terrain.STARBASE_1_CORRIDOR_1.image.imageWidth/2 * Constants.SCALE) * 14, startY + (Terrain.STARBASE_1.image.imageHeight/2 * Constants.SCALE) + (Terrain.STARBASE_1_CORRIDOR_1.image.imageHeight/2 * Constants.SCALE) * 7)
		);
		// Right vertical corridors
		main.engine.addEntity(
			MapUtils.createTerrainObject(main.engine, main.world, Terrain.STARBASE_1_CORRIDOR_1, 
					250 + (Terrain.STARBASE_1.image.imageHeight/2 * Constants.SCALE) + (Terrain.STARBASE_1_CORRIDOR_1.image.imageWidth/2 * Constants.SCALE) * 14, startY + (Terrain.STARBASE_1.image.imageHeight/2 * Constants.SCALE) + (Terrain.STARBASE_1_CORRIDOR_1.image.imageHeight/2 * Constants.SCALE) * 5)
		);
		main.engine.addEntity(
			MapUtils.createTerrainObject(main.engine, main.world, Terrain.STARBASE_1_CORRIDOR_1, 
					250 + (Terrain.STARBASE_1.image.imageHeight/2 * Constants.SCALE) + (Terrain.STARBASE_1_CORRIDOR_1.image.imageWidth/2 * Constants.SCALE) * 14, startY + (Terrain.STARBASE_1.image.imageHeight/2 * Constants.SCALE) + (Terrain.STARBASE_1_CORRIDOR_1.image.imageHeight/2 * Constants.SCALE) * 3)
		);
		main.engine.addEntity(
			MapUtils.createTerrainObject(main.engine, main.world, Terrain.STARBASE_1_CORRIDOR_1, 
					250 + (Terrain.STARBASE_1.image.imageHeight/2 * Constants.SCALE) + (Terrain.STARBASE_1_CORRIDOR_1.image.imageWidth/2 * Constants.SCALE) * 14, startY + (Terrain.STARBASE_1.image.imageHeight/2 * Constants.SCALE) + (Terrain.STARBASE_1_CORRIDOR_1.image.imageHeight/2 * Constants.SCALE))
		);
		// Bottom right spawner
		addBottomSwarmerSpawner(main.engine, main.world, main.projectileFactory, Ship.SWARMER_4, player, Swarmer.class, MovementType.FOLLOW_TARGET, "Swarmer 4", 100f, range, 30f, 600f, 10f, Terrain.SPAWNER_1, 700f, 
			250 + (Terrain.STARBASE_1.image.imageHeight/2 * Constants.SCALE) + (Terrain.STARBASE_1_CORRIDOR_1.image.imageWidth/2 * Constants.SCALE) * 14, startY);
		
		// Turret in the middle
		main.engine.addEntity(
			EntityUtils.newEnemyShip(main.engine, main.world, main.projectileFactory, Ship.TURRET_1, player, BasicTurret.class, "Turret", 
					250 + (Terrain.STARBASE_1_CORRIDOR_1.image.imageHeight/2 * Constants.SCALE) * 8, startY + ((Terrain.STARBASE_1.image.imageHeight/2 * Constants.SCALE) + (Terrain.STARBASE_1_CORRIDOR_1.image.imageHeight/2 * Constants.SCALE) * 7)/2, 250f, 200f)
		);
		
		
		// Add door
		//MapUtils.createDoor(main.engine, main.world, Map.OUTPOST, 0, 300, 1000, 10);
		
		// Set player location
		player.getComponent(HitboxComponent.class).body.setTransform(new Vector2(300f, 60f), player.getComponent(HitboxComponent.class).body.getAngle());
		
		// Bounds
		MapUtils.createBounds(main, 600f, 600f);
	}
	
	// Creates a swarmer spawner that spawns swarmers on the left, right, and bottom of the spawner
	private static void addBottomSwarmerSpawner(Engine engine, World world, ProjectileFactory projectileFactory, Ship ship, Entity target, Class<?> AI, MovementType movementType, 
			String name, float hp, float range, float maxSpeed, float force, float interval,
			Terrain spawner, float spawnerHP, float spawnerX, float spawnerY) {
		Entity e = new Entity();
		
		float[] x = {
			spawnerX,
			spawnerX + (spawner.image.imageWidth/2 * Constants.SCALE),
			spawnerX - (spawner.image.imageWidth/2 * Constants.SCALE)
		};
		float[] y = {
			spawnerY - (spawner.image.imageHeight/2 * Constants.SCALE),
			spawnerY,
			spawnerY
		};
		
		e.add(new EnemyShipSpawnerComponent(interval, engine, world, projectileFactory, ship, target, AI, movementType, name, x, y, hp, range, maxSpeed, force));
		e.add(new HealthComponent(spawnerHP));
		e.add(new HitboxComponent(world, e, spawner, spawner.image.imageWidth, spawnerX, spawnerY, true));
		e.add(new VisualComponent(spawner.image.sprite));
		
		engine.addEntity(e);
	}
	
	// Creates a swarmer spawner that spawns swarmers on the bottom of the spawner
	private static void addTopSwarmerSpawner(Engine engine, World world, ProjectileFactory projectileFactory, Ship ship, Entity target, Class<?> AI, MovementType movementType, 
			String name, float hp, float range, float maxSpeed, float force, float interval,
			Terrain spawner, float spawnerHP, float spawnerX, float spawnerY) {
		Entity e = new Entity();
		
		e.add(new EnemyShipSpawnerComponent(interval, engine, world, projectileFactory, ship, target, AI, movementType, name, spawnerX, spawnerY - (spawner.image.imageHeight/2 * Constants.SCALE), hp, range, maxSpeed, force));
		e.add(new HealthComponent(spawnerHP));
		e.add(new HitboxComponent(world, e, spawner, spawner.image.imageWidth, spawnerX, spawnerY, true));
		e.add(new VisualComponent(spawner.image.sprite));
		
		engine.addEntity(e);
	}
}
