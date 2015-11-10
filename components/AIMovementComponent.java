package components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

public class AIMovementComponent implements Component {
	public static enum MovementType {
		FOLLOW_TARGET,
		STALK_TARGET;
	}
	
	public MovementType type;
	public transient Entity target;
	// Used only when movement type is STALK_TARGET; how far away the enemy stays near the player
	public float stalkRange;
	// How close the player has to be for the entity to begin its movement AI
	public float beginRange;
	
	// If movement AI has begun
	public boolean activated;
	
	public AIMovementComponent(MovementType type, Entity target, float beginRange) {
		this.type = type;
		this.target = target;
		this.beginRange = beginRange;
	}
	
	public AIMovementComponent(MovementType type, Entity target, float beginRange, float stalkRange) {
		this.type = type;
		this.target = target;
		this.stalkRange = stalkRange;
		this.beginRange = beginRange;
	}
}
