package utils;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.World;
import items.Ships.Ship;
import maps.Maps.Terrain;

import com.miv.ProjectileFactory;

import components.AIComponent;
import components.AIMovementComponent.MovementType;
import components.ActorComponent;
import components.EnemyComponent;
import components.EnemyShipSpawnerComponent;
import components.HealthComponent;
import components.HitboxComponent;
import components.AIMovementComponent;
import components.ProjectileFiringComponent;
import components.ShipComponent;
import components.TurningComponent;
import components.VisualComponent;

public class EntityUtils {
	/**
	 * Spawns enemy ships
	 */
	public static Entity newEnemyShipSpawner(Engine engine, World world, ProjectileFactory projectileFactory, Ship ship, Entity target, Class<?> AI, MovementType movementType, 
			String name, float x, float y, float hp, float range, float maxSpeed, float force, float interval,
			Terrain spawner, float spawnerHP, float spawnerX, float spawnerY) {
		Entity e = new Entity();
		
		e.add(new EnemyShipSpawnerComponent(interval, engine, world, projectileFactory, ship, target, AI, movementType, name, x, y, hp, range, maxSpeed, force));
		e.add(new HealthComponent(spawnerHP));
		e.add(new HitboxComponent(world, e, spawner, spawner.image.imageWidth, spawnerX, spawnerY, true));
		e.add(new VisualComponent(spawner.image.sprite));
		
		return e;
	}
	
	/**
	 * Ships with non-stalker movement type
	 */
	public static Entity newEnemyShip(Engine engine, World world, ProjectileFactory projectileFactory, Ship ship, Entity target, Class<?> AI, MovementType movementType, 
			String name, float x, float y, float hp, float range, float maxSpeed, float force) {
		// Create enemy
		Entity enemy = new Entity();
		enemy.add(new ActorComponent(name));
		HitboxComponent enemyHitbox = new HitboxComponent(world, enemy, ship, x, y);
		enemy.add(enemyHitbox);
		enemy.add(new VisualComponent(ship.image.sprite));
		enemy.add(new EnemyComponent());
		enemy.add(new HealthComponent(hp));
		enemy.add(new ProjectileFiringComponent(projectileFactory, enemy.getComponent(HitboxComponent.class).body, target.getComponent(HitboxComponent.class).body));
		enemy.add(new TurningComponent(target));
		enemy.add(new AIComponent(AI, enemy, target, range, engine, world, projectileFactory));
		
		if(movementType != null) {
			enemy.add(new AIMovementComponent(movementType, target, range));
		}
		
		// Enemy stats
		ShipComponent enemyShipComponent = new ShipComponent(ship);
		enemyShipComponent.speed = maxSpeed;
		enemyShipComponent.force = force;
		enemy.add(enemyShipComponent);
		
		return enemy;
	}
	
	/**
	 * Ships with non-stalker movement type and no turning component
	 */
	public static Entity newEnemyShip(Engine engine, World world, ProjectileFactory projectileFactory, Ship ship, Entity target, Class<?> AI, MovementType movementType, 
			String name, float x, float y, float hp, float range, float maxSpeed, float force, boolean noTurningComponent) {
		// Create enemy
		Entity enemy = new Entity();
		enemy.add(new ActorComponent(name));
		HitboxComponent enemyHitbox = new HitboxComponent(world, enemy, ship, x, y);
		enemy.add(enemyHitbox);
		enemy.add(new VisualComponent(ship.image.sprite));
		enemy.add(new EnemyComponent());
		enemy.add(new HealthComponent(hp));
		enemy.add(new ProjectileFiringComponent(projectileFactory, enemy.getComponent(HitboxComponent.class).body, target.getComponent(HitboxComponent.class).body));
		enemy.add(new AIComponent(AI, enemy, target, range, engine, world, projectileFactory));
		
		if(movementType != null) {
			enemy.add(new AIMovementComponent(movementType, target, range));
		}
		
		// Enemy stats
		ShipComponent enemyShipComponent = new ShipComponent(ship);
		enemyShipComponent.speed = maxSpeed;
		enemyShipComponent.force = force;
		enemy.add(enemyShipComponent);
		
		return enemy;
	}
	
	/**
	 * Ships with movement type stalker
	 */
	public static Entity newEnemyShip(Engine engine, World world, ProjectileFactory projectileFactory, Ship ship, Entity target, Class<?> AI, float stalkRange,
			String name, float x, float y, float hp, float maxSpeed, float force) {
		// Create enemy
		Entity enemy = new Entity();
		enemy.add(new ActorComponent(name));
		HitboxComponent enemyHitbox = new HitboxComponent(world, enemy, ship, x, y);
		enemy.add(enemyHitbox);
		enemy.add(new VisualComponent(ship.image.sprite));
		enemy.add(new EnemyComponent());
		enemy.add(new HealthComponent(hp));
		enemy.add(new ProjectileFiringComponent(projectileFactory, enemy.getComponent(HitboxComponent.class).body, target.getComponent(HitboxComponent.class).body));
		enemy.add(new TurningComponent(target));
		enemy.add(new AIComponent(AI, enemy, target, 500f, engine, world, projectileFactory));
		enemy.add(new AIMovementComponent(MovementType.STALK_TARGET, target, stalkRange));
		
		// Enemy stats
		ShipComponent enemyShipComponent = new ShipComponent(ship);
		enemyShipComponent.speed = maxSpeed;
		enemyShipComponent.force = force;
		enemy.add(enemyShipComponent);
		
		return enemy;
	}
	
	/**
	 * Ships with no movement AI
	 */
	public static Entity newEnemyShip(Engine engine, World world, ProjectileFactory projectileFactory, Ship ship, Entity target, Class<?> AI,
			String name, float x, float y, float hp, float range) {
		// Create enemy
		Entity enemy = new Entity();
		enemy.add(new ActorComponent(name));
		HitboxComponent enemyHitbox = new HitboxComponent(world, enemy, ship, x, y);
		enemy.add(enemyHitbox);
		enemy.add(new VisualComponent(ship.image.sprite));
		enemy.add(new EnemyComponent());
		enemy.add(new HealthComponent(hp));
		enemy.add(new ProjectileFiringComponent(projectileFactory, enemy.getComponent(HitboxComponent.class).body, target.getComponent(HitboxComponent.class).body));
		enemy.add(new TurningComponent(target));
		enemy.add(new AIComponent(AI, enemy, target, range, engine, world, projectileFactory));
		enemy.add(new ShipComponent(ship));
		
		return enemy;
	}
}
