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
 * Is called when an entity is cleaned up by {@link com.miv.Main#sweepBodies()}
 * @author Miv
 *
 */
public class OnDeathComponent implements Component {
	private transient Engine engine;
	private transient World world;
	private transient ProjectileFactory projectileFactory;
	private transient Entity parent;
	public OnDeathEvent event;
	
	public CustomData customData = new CustomData();
	
	public OnDeathComponent(Engine engine, World world, ProjectileFactory projectileFactory, Entity parent, OnDeathEvent event) {
		this.engine = engine;
		this.world = world;
		this.projectileFactory = projectileFactory;
		this.parent = parent;
		this.event = event;
	}
	
	public OnDeathComponent setCustomData(CustomData customData) {
		this.customData = customData;
		return this;
	}
	
	public void onDeath() {
		for(Class<?> type : event.events) {
			try {
				Method m = type.getMethod("onDeath", Engine.class, World.class, ProjectileFactory.class, Entity.class, CustomData.class);
				System.out.println(customData.onDeathEvent);
				m.invoke(null, engine, world, projectileFactory, parent, customData);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		}
	}
}
