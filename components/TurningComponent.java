package components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

public class TurningComponent implements Component {
	// Who the entity is turning towards
	public Entity focus;
	
	public TurningComponent(Entity focus) {
		this.focus = focus;
	}
}
