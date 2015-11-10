package items;

import com.miv.Constants.Group;
import com.miv.Images.Image;

public class Ships {
	public static enum Ship {
		// Player ships
		PLAYER_1("Players", "Player", Image.PLAYER_1, Group.PLAYER, 0.1f),
		
		// Enemy ships
		SWARMER_4("Enemies", "Swarmer 4", Image.SWARMER_4, Group.ENEMY, 1f),
		
		BLACK_ORANGE_1("Enemies", "Black Orange 1", Image.BLACK_ORANGE_1, Group.ENEMY, 0.1f),
		BLACK_ORANGE_2("Enemies", "Black Orange 2", Image.BLACK_ORANGE_2, Group.ENEMY, 0.1f),
		BLACK_ORANGE_3("Enemies", "Black Orange 3", Image.BLACK_ORANGE_3, Group.ENEMY, 0.1f),
		BLACK_ORANGE_4("Enemies", "Black Orange 4", Image.BLACK_ORANGE_4, Group.ENEMY, 0.1f),
		
		// Boss ships
		RED_BOSS("Enemies", "Red Boss", Image.RED_BOSS, Group.ENEMY, true),
		STARBASE_BOSS("Enemies", "Starbase Boss", Image.STARBASE_BOSS, Group.ENEMY, true),
		
		// Turrets
		TURRET_1("Enemies", "Turret 1", Image.TURRET_1, Group.ENEMY, 100f);
		
		public String source, name;
		public Image image;
		public Group group;
		public float density;
		// Static body
		public boolean isStatic;
		
		Ship(String source, String name, Image image, Group group, float density) {
			this.source = source;
			this.name = name;
			this.image = image;
			this.group = group;
			this.density = density;
		}
		
		Ship(String source, String name, Image image, Group group, boolean isStatic) {
			this.source = source;
			this.name = name;
			this.image = image;
			this.group = group;
			this.isStatic = isStatic;
		}
	}
}
