package components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.World;
import com.miv.ProjectileFactory;

import components.AIMovementComponent.MovementType;
import items.Ships.Ship;
import utils.EntityUtils;

public class EnemyShipSpawnerComponent implements Component {
	private transient Engine engine;
	private transient World world;
	private transient ProjectileFactory projectileFactory;
	
	// Interval between each entity spawn in seconds
	public float[] interval;
	// Time left until next spawn in seconds
	public float[] time;
	
	// Enemy ships stats
	private Ship[] ship;
	private Class<?>[] AI;
	private MovementType[] movementType;
	private float[] stalkRange;
	private String[] name;
	private float[] hp, range, maxSpeed, force;
	private float[] x, y;
	
	// Who the enemy ship targets
	private transient Entity target;
	
	// For spawning single enemies
	public EnemyShipSpawnerComponent(float interval, Engine engine, World world, ProjectileFactory projectileFactory, Ship ship, Entity target, Class<?> AI, MovementType movementType, 
			String name, float x, float y, float hp, float range, float maxSpeed, float force) {
		this.interval = new float[] { interval };
		this.engine = engine;
		this.world = world;
		this.projectileFactory = projectileFactory;
		this.ship = new Ship[] { ship };
		this.target = target;
		this.AI = new Class<?>[] { AI };
		this.movementType = new MovementType[] { movementType };
		this.stalkRange = new float [] { 0f };
		this.name = new String[] { name };
		this.x = new float[] { x };
		this.y = new float[] { y };
		this.hp = new float[] { hp };
		this.range = new float[] { range };
		this.maxSpeed = new float[] { maxSpeed };
		this.force = new float[] { force };
		time = new float[1];
	}
	
	// For spawning single stalker enemies
	public EnemyShipSpawnerComponent(float interval, Engine engine, World world, ProjectileFactory projectileFactory, Ship ship, Entity target, Class<?> AI, float stalkRange, 
			String name, float x, float y, float hp, float range, float maxSpeed, float force) {
		this.interval = new float[] { interval };
		this.engine = engine;
		this.world = world;
		this.projectileFactory = projectileFactory;
		this.ship = new Ship[] { ship };
		this.target = target;
		this.AI = new Class<?>[] { AI };
		this.movementType = new MovementType[] { MovementType.STALK_TARGET };
		this.stalkRange = new float [] { stalkRange };
		this.name = new String[] { name };
		this.x = new float[] { x };
		this.y = new float[] { y };
		this.hp = new float[] { hp };
		this.range = new float[] { range };
		this.maxSpeed = new float[] { maxSpeed };
		this.force = new float[] { force };
		time = new float[1];
	}
	
	// For spawning multiple of the same type of enemy
	public EnemyShipSpawnerComponent(float interval, Engine engine, World world, ProjectileFactory projectileFactory, Ship ship, Entity target, Class<?> AI, MovementType movementType, 
			String name, float[] x, float[] y, float hp, float range, float maxSpeed, float force) {
		int num = x.length;
		
		this.engine = engine;
		this.world = world;
		this.projectileFactory = projectileFactory;
		this.target = target;
		
		time = new float[num];
		
		this.interval = new float[num];
		this.ship = new Ship[num];
		this.AI = new Class<?>[num];
		this.movementType = new MovementType[num];
		this.stalkRange = new float[num];
		this.name = new String[num];
		this.hp = new float[num];
		this.range = new float[num];
		this.maxSpeed = new float[num];
		this.force = new float[num];
		
		for(int i = 0; i < num; i++) {
			this.interval[i] = interval;
			this.ship[i] = ship;
			this.AI[i] = AI;
			this.movementType[i] = movementType;
			this.stalkRange[i] = 0f;
			this.name[i] = name;
			this.hp[i] = hp;
			this.range[i] = range;
			this.maxSpeed[i] = maxSpeed;
			this.force[i] = force;
			
			time[i] = 0f;
		}
		
		this.x = x;
		this.y = y;
	}
	
	// For spawning multiple of the same type of stalker enemy
	public EnemyShipSpawnerComponent(float interval, Engine engine, World world, ProjectileFactory projectileFactory, Ship ship, Entity target, Class<?> AI, float stalkeRange,
			String name, float[] x, float[] y, float hp, float range, float maxSpeed, float force) {
		int num = x.length;
		
		this.engine = engine;
		this.world = world;
		this.projectileFactory = projectileFactory;
		this.target = target;
		
		time = new float[num];
		
		this.interval = new float[num];
		this.ship = new Ship[num];
		this.AI = new Class<?>[num];
		this.movementType = new MovementType[num];
		this.stalkRange = new float[num];
		this.name = new String[num];
		this.hp = new float[num];
		this.range = new float[num];
		this.maxSpeed = new float[num];
		this.force = new float[num];
		
		for(int i = 0; i < num; i++) {
			this.interval[i] = interval;
			this.ship[i] = ship;
			this.AI[i] = AI;
			this.movementType[i] = MovementType.STALK_TARGET;
			this.stalkRange[i] = stalkeRange;
			this.name[i] = name;
			this.hp[i] = hp;
			this.range[i] = range;
			this.maxSpeed[i] = maxSpeed;
			this.force[i] = force;
			
			time[i] = 0f;
		}
		
		this.x = x;
		this.y = y;
	}
	
	// For spawning multiple different enemies
	public EnemyShipSpawnerComponent(float[] interval, Engine engine, World world, ProjectileFactory projectileFactory, Ship[] ship, Entity target, Class<?>[] AI, MovementType[] movementType, float[] stalkRange,
			String[] name, float[] x, float[] y, float[] hp, float[] range, float[] maxSpeed, float[] force) {
		this.interval = interval;
		this.engine = engine;
		this.world = world;
		this.projectileFactory = projectileFactory;
		this.ship = ship;
		this.target = target;
		this.AI = AI;
		this.movementType = movementType;
		this.stalkRange = stalkRange;
		this.name = name;
		this.x = x;
		this.y = y;
		this.hp = hp;
		this.range = range;
		this.maxSpeed = maxSpeed;
		this.force = force;
		
		time = new float[interval.length];
		for(int i = 0; i < time.length; i++) {
			time[i] = 0f;
		}
	}
	
	/**
	 * Spawns in a new enemy ship
	 */
	public void spawn(int index) {
		if(stalkRange[index] <= 0) {
			engine.addEntity(EntityUtils.newEnemyShip(engine, world, projectileFactory, ship[index], target, AI[index], movementType[index], 
					name[index], x[index], y[index], hp[index], range[index], maxSpeed[index], force[index]));
		} else {
			engine.addEntity(EntityUtils.newEnemyShip(engine, world, projectileFactory, ship[index], target, AI[index], stalkRange[index], 
					name[index], x[index], y[index], hp[index], maxSpeed[index], force[index]));
		}
	}
}
