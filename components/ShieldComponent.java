package components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.miv.Constants;

import items.Shields.Shield;

public class ShieldComponent implements Component {
	// Shield strength -- time left until shield can no longer be held
	public float shieldStrength;
	// If shield is broken; If shield is broken, it cannot be used again until shieldStrength reaches maxShieldStrength
	public boolean shieldBroken;
	// Current shield equipped
	public Shield shieldEquipped;
	// If shield is in use
	public boolean shieldActivated;
	
	public transient Sprite sprite;
		
	public ShieldComponent(Shield starter, float width, float height) {
		equipShield(starter, width, height);
	}
	
	public ShieldComponent() {}
	
	// Equip shield
	public void equipShield(Shield shield, float width, float height) {
		shieldEquipped = shield;
		//shieldStrength = 0f;
		//shieldBroken = true;
		shieldStrength = shield.strength;
		shieldActivated = false;
		
		// Resize shield to fit size of the player using it
		sprite = new Sprite(shield.image.sprite);
		sprite.setSize((width*Constants.SCALE), (height*Constants.SCALE));
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
	}
	
	/**
	 * Shield takes damage
	 * @param damage - damage taken
	 */
	public void takeDamage(float damage) {
		shieldStrength -= damage*Constants.SHIELD_DAMAGE_MULTIPLIER;
		if(shieldStrength <= 0) {
			shieldBroken = true;
		}
	}
}
