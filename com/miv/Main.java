package com.miv;

import java.util.Iterator;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;

import items.Ships.Ship;

import components.AIComponent;
import components.ActorComponent;
import components.HealthComponent;
import components.HitboxComponent;
import components.HitboxComponent.UserData;
import components.OnDeathComponent;
import components.PlayerComponent;
import components.ShieldComponent;
import components.ShipComponent;
import components.VisualComponent;
import components.WeaponComponent;
import items.Shields.Shield;
import items.Weapons.Weapon;
import maps.Maps;
import maps.Maps.Map;
import maps.ParallaxSystem;
import systems.AIMovementSystem;
import systems.AIPhaseSystem;
import systems.EnemyShipSpawnerSystem;
import systems.ProjectileSystem;
import systems.RenderSystem;
import systems.ShieldSystem;
import systems.TurningSystem;
import systems.WeaponSystem;
import utils.CustomTask;

public class Main extends Game {
	
	// Debug stuff
	SpriteBatch debugBatch;
	Box2DDebugRenderer debugRenderer;
	boolean debugMode = true;
	BitmapFont font;
	
	public World world;
	public Engine engine;
	public OrthographicCamera camera;
	
	public ProjectileFactory projectileFactory;
	
	public Map map = Map.OUTPOST;
	
	EventHandler inputProcessor;
	GUI gui;
	
	InputMultiplexer im;
	
	public Entity player;
				
	boolean paused;
	
	public ParallaxSystem parallaxSystem;
		
	// Map queued for loading
	private Map queuedMap;
	
	private ComponentMapper<HitboxComponent> hm = ComponentMapper.getFor(HitboxComponent.class);
	private ComponentMapper<HealthComponent> sm = ComponentMapper.getFor(HealthComponent.class);
	private ComponentMapper<ShieldComponent> shm = ComponentMapper.getFor(ShieldComponent.class);
	private ComponentMapper<AIComponent> am = ComponentMapper.getFor(AIComponent.class);
	private ComponentMapper<OnDeathComponent> dm = ComponentMapper.getFor(OnDeathComponent.class);
		
	@Override
	public void create() {
		// Debugging stuff
		if(debugMode) {
			debugBatch = new SpriteBatch();
			//font = new BitmapFont(Gdx.files.internal("Calibri.fnt"),Gdx.files.internal("Calibri.png"),false);
			font = new BitmapFont();
		}
				
		// Load textures
		Images.loadImages();
		
		// Create input multiplexer
		im = new InputMultiplexer();
		
		// Create GUI
		gui = new GUI();
		gui.create(im, this);	
		
		// Create world
		world = new World(new Vector2(0, 0), true);
		world.setContinuousPhysics(false);
		
		// Create camera to follow player
        camera = new OrthographicCamera(Constants.METERS_PER_SCREEN_WIDTH/Constants.SCALE, Constants.METERS_PER_SCREEN_WIDTH * (Constants.SCREEN_HEIGHT / Constants.SCREEN_WIDTH)/Constants.SCALE);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();
		
		// Create engine
		engine = new Engine();
		
		// Create projectileFactory
		projectileFactory = new ProjectileFactory(engine, world);
		
		// Add player
		player = new Entity();
		player.add(new ActorComponent("Player"));
		player.add(new PlayerComponent());
		
		HitboxComponent playerHitbox = new HitboxComponent(world, player, Ship.PLAYER_1, 500, 500);
		player.add(playerHitbox);
		player.add(new VisualComponent(Ship.PLAYER_1.image.sprite));
		player.add(new ShieldComponent(Shield.STARTER, Ship.PLAYER_1.image.shieldWidth, Ship.PLAYER_1.image.shieldHeight));
		player.add(new WeaponComponent(Weapon.PHASER_3, true));
		player.add(new HealthComponent(1000f));
		
		// Player stats
		ShipComponent playerShipComponent = new ShipComponent(Ship.PLAYER_1);
		playerShipComponent.speed = 40f;
		playerShipComponent.force = 50f;
		playerShipComponent.brakePower = 0.90f; // 0.94
		player.add(playerShipComponent);
		
		// Add player to engine
		engine.addEntity(player);
		
		// Add contact listener
		world.setContactListener(new CollisionListener(this));
		
		inputProcessor = new EventHandler(this);
		im.addProcessor(inputProcessor);
		Gdx.input.setInputProcessor(im);
		
		// Create systems
		parallaxSystem = new maps.ParallaxSystem(new maps.ParallaxLayer[] { new maps.ParallaxLayer(new TextureRegion(Maps.getRandomBackground(), 0, 0, 1024, 1024), new Vector2(5, 5), new Vector2(0, 0)) }, 1024, 1024, playerHitbox.body);
		RenderSystem renderSystem = new RenderSystem(camera, player);
		ProjectileSystem projectileSystem = new ProjectileSystem(engine, world);
		WeaponSystem weaponSystem = new WeaponSystem(projectileFactory);
		ShieldSystem shieldSystem = new ShieldSystem();
		TurningSystem turningSystem = new TurningSystem();
		AIPhaseSystem aiPhaseSystem = new AIPhaseSystem();
		AIMovementSystem aiMovementSystem = new AIMovementSystem();
		EnemyShipSpawnerSystem enemyShipSpawnerSystem = new EnemyShipSpawnerSystem();

		// Add systems
		engine.addSystem(renderSystem);
		engine.addSystem(weaponSystem);
		engine.addSystem(shieldSystem);
		engine.addSystem(projectileSystem);
		engine.addSystem(turningSystem);
		engine.addSystem(aiPhaseSystem);
		engine.addSystem(aiMovementSystem);
		engine.addSystem(enemyShipSpawnerSystem);
		
		debugRenderer = new Box2DDebugRenderer();
		
		Maps.loadMap(this, player, map);
	}
	
	@Override
	public void render() {
		// Clear screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// Get delta time
		float deltaTime = 1/60f;
		
		// Draw the map tiles
		parallaxSystem.render(deltaTime);
		//mapRenderer.setView(backgroundCamera);
		//mapRenderer.render();
		
		// Do not update game if paused
		if(!paused) {
			// Step world
			world.step(deltaTime, 10, 10);
			
			// Check for dead bodies
			sweepBodies();
			
			// Update player related inputs
			inputProcessor.frameUpdate(deltaTime, camera);
			
			// Update systems
			engine.update(deltaTime);
			
			// Check map loading queue
			checkMapQueue();
		} else {
			// Only run the render system if paused
			engine.getSystem(RenderSystem.class).update(Gdx.graphics.getDeltaTime());
		}
		gui.render(deltaTime);
		
		if(debugMode) {
			debugBatch.begin();
			// Player position
			font.draw(debugBatch, hm.get(player).body.getPosition().toString(), 10, 20);
			// FPS
			font.draw(debugBatch, "FPS: "+String.valueOf(Gdx.graphics.getFramesPerSecond()), 10, 40);
			//debugRenderer.render(world, camera.combined);
			debugBatch.end();
		}
	}
	
	/**
	 * Delete any entities marked for destruction
	 */
	public void sweepBodies() {
		ImmutableArray<Entity> entities = engine.getEntitiesFor(Family.all(HitboxComponent.class).get());
		
		for(Iterator<Entity> iter = entities.iterator(); iter.hasNext();) {
			Entity e = iter.next();
			
			UserData userData = (UserData) hm.get(e).body.getUserData();
			if(userData != null) {
				if(userData.queuedForDestruction) {
					// Stop all AI timers, if any
					if(am.has(e)) {
						for(CustomTask t : am.get(e).timers.tasks) {
							t.task.cancel();
						}
					}
					
					// Do any onDeaths
					if(dm.has(e)) {
						dm.get(e).onDeath();
					}
					
					engine.removeEntity(e);
					world.destroyBody(hm.get(e).body);
				}
			}
		}
	}
	
	public void queueMapLoad(Map map) {
		queuedMap = map;
	}
	
	public void checkMapQueue() {
		if(queuedMap != null) {
			Maps.loadMap(this, player, queuedMap);
			queuedMap = null;
		}
	}
	
	/**
	 * Get the player's HP percent of max
	 * @return
	 */
	public float getPlayerHPPercent() {
		ImmutableArray<Entity> entities = engine.getEntitiesFor(Family.all(PlayerComponent.class, HealthComponent.class).get());
		
		float hp = 0f;
		float maxHP = 0f;
		
		for(Entity e : entities) {
			hp += sm.get(e).hp;
			maxHP += sm.get(e).maxHP;
		}
		
		return hp/maxHP;
	}
	
	/**
	 * Get the player's shield percent of max
	 * @return
	 */
	public float getPlayerShieldPercent() {
		ImmutableArray<Entity> entities = engine.getEntitiesFor(Family.all(PlayerComponent.class, ShieldComponent.class).get());
		
		float shield = 0f;
		float shieldMax = 0f;
		
		for(Entity e : entities) {
			shield += shm.get(e).shieldStrength;
			shieldMax += shm.get(e).shieldEquipped.strength;
		}
		
		return shield/shieldMax;
	}
	
	/**
	 * Pause the game
	 */
	public void pause() {
		paused = true;
		// Pause all timers
		Timer.instance().stop();
	}
	
	/**
	 * Resume the game
	 */
	public void resume() {
		paused = false;
		// Resume all timers
		Timer.instance().start();
	}
}
