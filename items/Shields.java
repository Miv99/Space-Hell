package items;

import com.miv.Images.Image;

public class Shields {
	public static enum Shield {
		STARTER(2f, 1/16f, Image.SHIELD_1);
		
		public float strength, rechargeMultiplier;
		public Image image;
				
		/**
		 * Shields equipped by ships
		 * @param strength - strength in seconds; decreases at a 1strength:1second ratio
		 * @param rechargeMultiplier - how much strength is recovered every deltaTime; eg a value of 1/4 would mean +0.25strength/1second
		 * @param xTexture - x location of the sprite in Shields.png
		 * @param yTexture - y location of the sprite in Shields.png
		 * @param imageWidth - width of the sprite
		 * @param imageHeight - height of the sprite
		 */
		Shield(float strength, float rechargeMultiplier, Image image) {
			this.strength = strength;
			this.rechargeMultiplier = rechargeMultiplier;
			this.image = image;
		}
	}
}
