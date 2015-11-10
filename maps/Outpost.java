package maps;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;

import com.miv.Main;

import components.HitboxComponent;
import maps.Maps.Terrain;
import utils.MapUtils;

public class Outpost {
	public static void create(Main main, Entity player) {
		// Add starbase
		main.engine.addEntity(MapUtils.createTerrainObject(main.engine, main.world, Terrain.STARBASE_1, 300, 0));
		
		// Add asteroids
		/**
		for(int i = 0; i < MathUtils.random(20, 50); i++) { 
			float sizeMultiplier = MathUtils.random(1.0f, 5.0f);
			main.engine.addEntity(MapUtils.createTerrainObject(main.engine, main.world, Terrain.ASTEROID_1, sizeMultiplier, sizeMultiplier, sizeMultiplier * 4, MathUtils.random(500), MathUtils.random(500)));
		}
		*/
		
		// Add enemies
		//main.engine.addEntity(EntityUtils.newEnemyShip(main.engine, main.world, main.projectileFactory, Ship.RED_BOSS, player, RedBoss.class, null, "Red Boss", 100, 100, 2000f, 500f, 40f, 600f));
		//main.engine.addEntity(EntityUtils.newEnemyShip(main.engine, main.world, main.projectileFactory, Ship.STARBASE_BOSS, player, StarbaseBoss.class, null, "Starbase Boss", 100, 100, 5000f, 500f, 40f, 600f, false));
		
		// Add door
		//MapUtils.createDoor(main.engine, main.world, Map.OUTPOST, 550, 520, 100, 10);
		
		MapUtils.createBounds(main, 200f, 200f);
		
		// Set player location
		player.getComponent(HitboxComponent.class).body.setTransform(new Vector2(100, 50), player.getComponent(HitboxComponent.class).body.getAngle());
	}
}
