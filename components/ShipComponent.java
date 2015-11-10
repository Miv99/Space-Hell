package components;

import com.badlogic.ashley.core.Component;
import items.Ships.Ship;

public class ShipComponent implements Component {	
	// Maximum speed of the player when moving in a straight line (horizontal and vertical speed are independent)
	public float speed;
	// Force applied to player when moving
	public float force;
	// Braking power; max of 1f
	public float brakePower;
	
	// Type of ship
	public Ship ship;
	
	public ShipComponent(Ship ship) {
		this.ship = ship;
	}
	
	public ShipComponent() {}
}
