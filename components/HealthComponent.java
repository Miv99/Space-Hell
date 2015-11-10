package components;

import com.badlogic.ashley.core.Component;

import components.HitboxComponent.UserData;

public class HealthComponent implements Component {
	// Maximum hp
	public float maxHP;
	
	// Health remaining
	public float hp;
	
	public HealthComponent() {  }
	
	public HealthComponent(float hp) {
		maxHP = hp;
		this.hp = hp;
	}
	
	/**
	 * Take damage
	 * @param damage - damage taken
	 * @param userData - userData of the ship's body
	 */
	public void takeDamage(float damage, UserData userData) {
		hp -= damage;
		if(hp <= 0) {
			userData.queuedForDestruction = true;
		}
	}
}
