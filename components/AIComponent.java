package components;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;
import com.miv.ProjectileFactory;

import utils.CustomTask;
import utils.Utils;

public class AIComponent implements Component {
	public class PhaseTimes {
		public float[] phaseTimes;
	}
	public class Timers {
		public CustomTask[] tasks;
	}
	
	private ComponentMapper<HitboxComponent> hm = ComponentMapper.getFor(HitboxComponent.class);
	private ComponentMapper<ProjectileFiringComponent> pm = ComponentMapper.getFor(ProjectileFiringComponent.class);
	
	// The range of the enemy (to see the player)
	private float range;
	
	// Max number of phases; eg PHASES = 3 means currentPhase can be 0, 1, 2
	private int PHASES;
	// Current phase
	private int currentPhase = -1;
	
	// The amount of time in seconds spent in each phase; initialized but never changed
	PhaseTimes phaseTimes = new PhaseTimes();
	
	// Timers used by AI
	public Timers timers = new Timers();
	
	// The amount of time left in this phase
	private float time;
	
	// The entity the AI belongs to and the target (usually the player)
	private transient Entity ai, target;
	
	// Type of AI
	private Class<?> type;
	
	private transient Engine engine;
	private transient World world;
	private transient ProjectileFactory projectileFactory;
	
	public AIComponent(Class<?> type, Entity ai, Entity target, float range, Engine engine, World world, ProjectileFactory projectileFactory) {
		this.type = type;
		this.ai = ai;
		this.target = target;
		this.range = range;
		
		this.engine= engine;
		this.world = world;
		this.projectileFactory = projectileFactory;
		
		// Set the amount of time spent on each phase
		try {
			Method m = type.getMethod("setPhaseTimes", PhaseTimes.class);
			m.invoke(null, phaseTimes);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		PHASES = phaseTimes.phaseTimes.length;
	}
		
	public void nextPhase() {
		currentPhase++;
		if(currentPhase >= PHASES) {
			currentPhase = 0;
		}
		time = phaseTimes.phaseTimes[currentPhase];
		newPhase();
	}
	
	public void newPhase() {
		// Stop all tasks
		if(timers.tasks != null) {
			for(CustomTask t : timers.tasks) {
				t.task.cancel();
			}
		}
		
		// Create tasks for the current phase
		try {
			Method m = type.getMethod("createTasks", Engine.class, World.class, ProjectileFactory.class, Entity.class, Vector2.class, Timers.class, Integer.class, ProjectileFiringComponent.class);
			m.invoke(null, engine, world, projectileFactory, target, hm.get(ai).body.getPosition(), timers, currentPhase, pm.get(ai));
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		
		// Execute all tasks
		if(timers.tasks != null) {
			for(int i = 0; i < timers.tasks.length; i++) {
				if(timers.tasks[i].repeatCount < 0) {
					Timer.schedule(timers.tasks[i].task, timers.tasks[i].delay, timers.tasks[i].interval);
				} else {
					Timer.schedule(timers.tasks[i].task, timers.tasks[i].delay, timers.tasks[i].interval, timers.tasks[i].repeatCount);
				}
			}
		}
	}
	
	public void update(float deltaTime, Vector2 currentPos) {
		if(Utils.inRange(currentPos, hm.get(target).body.getWorldCenter(), range)) {
			time -= deltaTime;
			if(time <= 0) {
				nextPhase();
			}
		}
	}
}
