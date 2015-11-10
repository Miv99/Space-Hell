package components;

import com.badlogic.ashley.core.Component;

public class ActorComponent implements Component {
	public String name;
	
	public ActorComponent(String name) {
		this.name = name;
	}
}
