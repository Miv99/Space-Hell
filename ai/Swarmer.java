package ai;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer.Task;
import com.miv.ProjectileFactory;

import components.ProjectileFiringComponent;
import components.AIComponent.PhaseTimes;
import components.AIComponent.Timers;
import items.Weapons.Weapon;
import utils.CustomTask;
import utils.Utils;

public class Swarmer {
	public static void setPhaseTimes(PhaseTimes phaseTimes) {
		phaseTimes.phaseTimes = new float[] {
				60f
		};
	}
	
	public static void createTasks(Engine engine, World world, ProjectileFactory projectileFactory, Entity target, Vector2 position, Timers timers, Integer phase, ProjectileFiringComponent p) {
		if(phase == 0) {
			timers.tasks = new CustomTask[] {
				new CustomTask(
					new Task() {
						@Override
						public void run() {
							// 20% chance of leading shot
							if(Utils.chance(20)) {
								p.leadShot(Weapon.PHASER_3, false);
							} else {
								p.fire(Weapon.PHASER_3, false);
							}
						}
					}
					, 0f
					, 0.5f
				)
			};
		}
	}
}
