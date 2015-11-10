package loadSave;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.miv.Main;

import components.ActorComponent;
import components.HealthComponent;
import components.HitboxComponent;
import components.PlayerComponent;
import components.ShieldComponent;
import components.ShipComponent;
import components.VisualComponent;
import components.WeaponComponent;
import maps.Maps;
import maps.Maps.Map;

public class LoadSave {
	public static class Data {
		public ShipComponent playerShipComponent;
		public Vector2 playerLocation;
		public HealthComponent healthComponent;
		public WeaponComponent weaponComponent;
		public ShieldComponent shieldComponent;
		public Map map;
		public int saveSlot;
				
		public Data(ShipComponent playerShipComponent, HealthComponent healthComponent, WeaponComponent weaponComponent, ShieldComponent shieldComponent, Map map, Vector2 playerLocation, int saveSlot) {
			this.playerShipComponent = playerShipComponent;
			this.healthComponent = healthComponent;
			this.playerLocation = playerLocation;
			this.weaponComponent = weaponComponent;
			this.shieldComponent = shieldComponent;
			this.map = map;
			this.saveSlot = saveSlot;
		}
		
		public Data() {
			
		}
	}
	
	static ComponentMapper<ShieldComponent> sm = ComponentMapper.getFor(ShieldComponent.class);
	
	/**
	 * Save game in specified save slot
	 * @param data
	 */
	public static void saveGame(Data data) {
		Json json = new Json();
		writeFile("game" + data.saveSlot + ".json", json.toJson(data));
	}
	
	/**
	 * Get save data from specified save slot
	 * @param saveSlot
	 * @return
	 */
	public static Data getSaveData(int saveSlot) {
		String save = readFile("game" + saveSlot + ".json");
		if(!save.isEmpty()) {	
			Json json = new Json();
			Data data = json.fromJson(Data.class, save);
			
			//Data ret = new Data(data.playerShipComponent, data.weaponComponent, data.shieldComponent, data.playerLocation, data.saveSlot);
 
			return data;
		}
		return null;
	}
	
	public static void loadGame(Main main, Data data) {
		System.out.println(data);
		if(data == null) return;
				
		Array<Body> bodies = new Array<Body>();
		main.world.getBodies(bodies);
		
		for(Body b : bodies) {
			main.world.destroyBody(b);
		}
		
		// Get player
		Entity player = new Entity();
		player.add(new ActorComponent("Player"));
		player.add(new PlayerComponent());
		player.add(data.healthComponent);
		player.add(data.shieldComponent);
		player.add(data.weaponComponent);
		player.add(data.playerShipComponent);
		player.add(new HitboxComponent(main.world, player, data.playerShipComponent.ship, data.playerLocation.x, data.playerLocation.y));
		player.add(new VisualComponent(data.playerShipComponent.ship.image.sprite));
		
		// Reload all shield images
		ImmutableArray<Entity> entities = main.engine.getEntitiesFor(Family.all(ShieldComponent.class).get());
		ShieldComponent shield;
		for(Entity e : entities) {
			shield = sm.get(e);
			shield.equipShield(shield.shieldEquipped, data.playerShipComponent.ship.image.shieldWidth, data.playerShipComponent.ship.image.shieldHeight);
		}
		
		Maps.loadMap(main, player, data.map);
	}
	


	public static void writeFile(String fileName, String s) {
		FileHandle file = Gdx.files.local(fileName);
		file.writeString(com.badlogic.gdx.utils.Base64Coder.encodeString(s), false);
	}

	public static String readFile(String fileName) {
		FileHandle file = Gdx.files.local(fileName);
		if (file != null && file.exists()) {
			String s = file.readString();
			if (!s.isEmpty()) {
				return com.badlogic.gdx.utils.Base64Coder.decodeString(s);
			}
		}
		return "";
	}
}
