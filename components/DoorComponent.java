package components;

import com.badlogic.ashley.core.Component;

import maps.Maps.Map;

public class DoorComponent implements Component {
	// Where the door leads to; which map loads when player makes contact with door
	public Map leadsTo;
	
	public DoorComponent(Map leadsTo) {
		this.leadsTo = leadsTo;
	}
}
