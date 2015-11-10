package ai;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer.Task;
import com.miv.ProjectileFactory;
import com.miv.ProjectileFactory.Projectile;

import components.ProjectileFiringComponent;
import components.AIComponent.PhaseTimes;
import components.AIComponent.Timers;
import items.Weapons.Weapon;
import utils.CustomTask;

public class BlackOrange {
	public static class BlackOrange3 {
		public static void setPhaseTimes(PhaseTimes phaseTimes) {
			phaseTimes.phaseTimes = new float[] {
					15f
			};
		}
		
		public static void createTasks(Engine engine, World world, ProjectileFactory projectileFactory, Entity target, Vector2 position, Timers timers, Integer phase, ProjectileFiringComponent p) {
			if(phase == 0) {
				timers.tasks = new CustomTask[] {
					new CustomTask(
						new Task() {
							@Override
							public void run() {
								p.fireProjectile(Projectile.PHASER_1, false);
							}
						}
						, 3.5f
						, 0.2f
					)
				};
			}
		}
	}
	public static class BlackOrange4 {
		public static void setPhaseTimes(PhaseTimes phaseTimes) {
			phaseTimes.phaseTimes = new float[] {
					15f
			};
		}
		
		public static void createTasks(Engine engine, World world, ProjectileFactory projectileFactory, Entity target, Vector2 position, Timers timers, Integer phase, ProjectileFiringComponent p) {
			if(phase == 0) {
				timers.tasks = new CustomTask[] {
					new CustomTask(
						new Task() {
							@Override
							public void run() {
								p.fire(Weapon.RED_CIRCLE_2_CONTROLLED_SHOTGUN_1, false);
							}
						}
						, 2.5f
						, 0.5f
					)
				};
			}
		}
	}
	public static class BlackOrange5 {
		public static void setPhaseTimes(PhaseTimes phaseTimes) {
			phaseTimes.phaseTimes = new float[] {
					15f
			};
		}
		
		public static void createTasks(Engine engine, World world, ProjectileFactory projectileFactory, Entity target, Vector2 position, Timers timers, Integer phase, ProjectileFiringComponent p) {
			if(phase == 0) {
				timers.tasks = new CustomTask[] {
					new CustomTask(
						new Task() {
							@Override
							public void run() {
								p.fire(Weapon.RED_CIRCLE_3_CONTROLLED_SHOTGUN_1, false);
							}
						}
						, 2.5f
						, 0.5f
					)
				};
			}
		}
	}
}
