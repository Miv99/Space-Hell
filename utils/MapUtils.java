package utils;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.World;
import com.miv.Images.Image;
import com.miv.Main;

import components.DoorComponent;
import components.HealthComponent;
import components.HitboxComponent;
import components.VisualComponent;
import maps.Maps.Map;
import maps.Maps.Terrain;

public class MapUtils {
	
	/**
	 * Creates bounds for the map
	 */
	public static void createBounds(Main main, float width, float height) {
		Entity left = new Entity();
		left.add(new HitboxComponent(main.world, left, 0f, height/2, 2f, height + 2, false));
		left.add(new VisualComponent(Image.MAP_BOUNDARY.sprite, 2f, height + 2));
		
		Entity right = new Entity();
		right.add(new HitboxComponent(main.world, right, width, height/2, 2f, height + 2, false));
		right.add(new VisualComponent(Image.MAP_BOUNDARY.sprite, 2f, height + 2));
		
		Entity top = new Entity();
		top.add(new HitboxComponent(main.world, top, width/2, height, width, 2f, false));
		top.add(new VisualComponent(Image.MAP_BOUNDARY.sprite, width, 2f));
		
		Entity bottom = new Entity();
		bottom.add(new HitboxComponent(main.world, bottom, width/2, 0f, width, 2f, false));
		bottom.add(new VisualComponent(Image.MAP_BOUNDARY.sprite, width, 2f));
		
		main.engine.addEntity(left);
		main.engine.addEntity(right);
		main.engine.addEntity(top);
		main.engine.addEntity(bottom);
	}
	
	/**
	 * Creates door for the map
	 * When player makes contact with door, a new map loads
	 */
	public static Entity createDoor(Engine engine, World world, Map leadsTo, float x, float y, float width, float height) {
		Entity e = new Entity();
		e.add(new DoorComponent(leadsTo));
		e.add(new HitboxComponent(world, e, x, y, width, height, true));
		
		return e;
	}
	
	/**
	 * Creates a new terrain object for the map
	 * Has hitbox component and visual component
	 */
	public static Entity createTerrainObject(Engine engine, World world, Terrain terrain, float x, float y) {
		Entity e = new Entity();
		e.add(new HitboxComponent(world, e, terrain, terrain.image.imageWidth, x, y));
		e.add(new VisualComponent(terrain.image.sprite));
		
		if(terrain.hp > 0) {
			e.add(new HealthComponent(terrain.hp));
		}
		
		return e;
	}
	
	/**
	 * Creates a new terrain object for the map
	 * Has hitbox component and visual component
	 */
	public static Entity createTerrainObject(Engine engine, World world, Terrain terrain, float sizeMultiplier, float x, float y) {
		Entity e = new Entity();
		e.add(new HitboxComponent(world, e, terrain, terrain.image.imageWidth * sizeMultiplier, x, y));
		e.add(new VisualComponent(terrain.image.sprite, sizeMultiplier));
		
		if(terrain.hp > 0) {
			e.add(new HealthComponent(terrain.hp));
		}
		
		return e;
	}
	
	/**
	 * Creates a new terrain object for the map
	 * Has hitbox component and visual component
	 */
	public static Entity createTerrainObject(Engine engine, World world, Terrain terrain, float sizeMultiplier, float densityMultiplier, float x, float y) {
		Entity e = new Entity();
		e.add(new HitboxComponent(world, e, terrain, terrain.image.imageWidth * sizeMultiplier, terrain.density * densityMultiplier, x, y));
		e.add(new VisualComponent(terrain.image.sprite, sizeMultiplier));
		
		if(terrain.hp > 0) {
			e.add(new HealthComponent(terrain.hp));
		}
		
		return e;
	}
	
	/**
	 * Creates a new terrain object for the map
	 * Has hitbox component and visual component
	 */
	public static Entity createTerrainObject(Engine engine, World world, Terrain terrain, float sizeMultiplier, float densityMultiplier, float hpMultiplier, float x, float y) {
		Entity e = new Entity();
		e.add(new HitboxComponent(world, e, terrain, terrain.image.imageWidth * sizeMultiplier, terrain.density * densityMultiplier, x, y));
		e.add(new VisualComponent(terrain.image.sprite, sizeMultiplier));
		
		if(terrain.hp > 0) {
			e.add(new HealthComponent(terrain.hp * hpMultiplier));
		}
		
		return e;
	}
}
