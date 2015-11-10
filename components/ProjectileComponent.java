package components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.miv.ProjectileFactory.Projectile;

public class ProjectileComponent implements Component {
	// Time projectile has left until it disappears, in seconds
	public float time;
	// Type of projectile
	public Projectile type;
	
	// The entity that cannot be hit by this projectile; used to make sure the same bullet does not hit the same entity multiple times
	public Entity cannotHit;
	
	public ProjectileComponent(Projectile type, float time) {
		this.type = type;
		this.time = time;
	}
}
