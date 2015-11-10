package components;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.World;
import com.miv.ProjectileFactory;

import onDeath.Events.CustomData;
import onDeath.Events.OnDeathEvent;

/**
 * For use by projectiles only
 * Is called when the projectil expires (projectile.time reaches 0)
 * @author Miv
 *
 */
public class OnExpireComponent implements Component {
	private transient Engine engine;
	private transient World world;
	private transient ProjectileFactory projectileFactory;
	private transient Entity parent;
	public OnDeathEvent event;
	
	public CustomData customData = new CustomData();
	
	public OnExpireComponent(Engine engine, World world, ProjectileFactory projectileFactory, Entity parent, OnDeathEvent event) {
		this.engine = engine;
		this.world = world;
		this.projectileFactory = projectileFactory;
		this.parent = parent;
		this.event = event;
	}
	
	public OnExpireComponent setCustomData(CustomData customData) {
		this.customData = customData;
		return this;
	}
	
	public void onExpire() {
		for(Class<?> type : event.events) {
			try {
				Method m = type.getMethod("onDeath", Engine.class, World.class, ProjectileFactory.class, Entity.class, CustomData.class);
				m.invoke(null, engine, world, projectileFactory, parent, customData);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		}
	}
}
