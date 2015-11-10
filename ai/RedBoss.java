package ai;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.MathUtils;
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

public class RedBoss {
	public static void setPhaseTimes(PhaseTimes phaseTimes) {
		phaseTimes.phaseTimes = new float[] {
				10f, //10
				6f, //6
				3.25f, //3.25
				2.25f, //2.25
				5.5f, //5.5
				10f, //10
				8f, //8
		};
	}
	
	public static void createTasks(Engine engine, World world, ProjectileFactory projectileFactory, Entity target, Vector2 position, Timers timers, Integer phase, ProjectileFiringComponent p) {
		if(phase == 0) {
			timers.tasks = new CustomTask[] {
				// Predictive shotgun blasts
				new CustomTask(
					new Task() {
						@Override
						public void run() {
							p.fire(Weapon.RED_BOSS_SHOTGUN_1, false);
							p.fireProjectile(Projectile.RED_CIRCLE_4, false);
						}
					}
					, 0f
					, 0.5f
				)
			};
		}
		else if(phase == 1) {
			timers.tasks = new CustomTask[] {
				// Charge shot
				new CustomTask(
					new Task() {
						@Override
						public void run() {
							p.fire(Weapon.RED_CIRCLE_1_SHOTGUN_1, false);
						}
					}
					, 2f
					, 0.1f
					, 40
				)
			};
		}
		else if(phase == 2) {
			timers.tasks = new CustomTask[] {
				// Swirl
				new CustomTask(
					new Task() {
						private float angle = 0f;
						
						@Override
						public void run() {
							p.fireProjectile(Projectile.GREEN_CIRCLE_3, angle, false);
							angle += MathUtils.degreesToRadians * 4;
						}
					}
					, 1f
					, 0.05f
				),
				// Swirl
				new CustomTask(
					new Task() {
						private float angle = MathUtils.degreesToRadians * 90;
						
						@Override
						public void run() {
							p.fireProjectile(Projectile.BLUE_CIRCLE_3, angle, false);
							angle += MathUtils.degreesToRadians * 4;
						}
					}
					, 1f
					, 0.05f
				),
				// Swirl
				new CustomTask(
					new Task() {
						private float angle = MathUtils.degreesToRadians * 180;
						
						@Override
						public void run() {
							p.fireProjectile(Projectile.RED_CIRCLE_3, angle, false);
							angle += MathUtils.degreesToRadians * 4;
						}
					}
					, 1f
					, 0.05f
				),
				// Swirl
				new CustomTask(
					new Task() {
						private float angle = MathUtils.degreesToRadians * 270;
						
						@Override
						public void run() {
							p.fireProjectile(Projectile.YELLOW_CIRCLE_3, angle, false);
							angle += MathUtils.degreesToRadians * 4;
						}
					}
					, 1f
					, 0.05f
				)
			};
		}
		else if(phase == 3) {
			timers.tasks = new CustomTask[] {
				// Swirl
				new CustomTask(
					new Task() {
						private float angle = 0f;
						
						@Override
						public void run() {
							p.fireProjectile(Projectile.GREEN_CIRCLE_3, angle, false);
							angle -= MathUtils.degreesToRadians * 4;
						}
					}
					, 0f
					, 0.05f
				),
				// Swirl
				new CustomTask(
					new Task() {
						private float angle = MathUtils.degreesToRadians * 90;
						
						@Override
						public void run() {
							p.fireProjectile(Projectile.BLUE_CIRCLE_3, angle, false);
							angle -= MathUtils.degreesToRadians * 4;
						}
					}
					, 0f
					, 0.05f
				),
				// Swirl
				new CustomTask(
					new Task() {
						private float angle = MathUtils.degreesToRadians * 180;
						
						@Override
						public void run() {
							p.fireProjectile(Projectile.RED_CIRCLE_3, angle, false);
							angle -= MathUtils.degreesToRadians * 4;
						}
					}
					, 0f
					, 0.05f
				),
				// Swirl
				new CustomTask(
					new Task() {
						private float angle = MathUtils.degreesToRadians * 270;
						
						@Override
						public void run() {
							p.fireProjectile(Projectile.YELLOW_CIRCLE_3, angle, false);
							angle -= MathUtils.degreesToRadians * 4;
						}
					}
					, 0f
					, 0.05f
				)
			};
		}
		else if(phase == 4) {
			timers.tasks = new CustomTask[] {
				// Swirl
				new CustomTask(
					new Task() {
						private float angle = 0f;
						
						@Override
						public void run() {
							p.fireProjectile(Projectile.GREEN_CIRCLE_3, angle, false);
							angle += MathUtils.degreesToRadians * 4;
						}
					}
					, 0f
					, 0.05f
				),
				// Swirl
				new CustomTask(
					new Task() {
						private float angle = MathUtils.degreesToRadians * 90;
						
						@Override
						public void run() {
							p.fireProjectile(Projectile.BLUE_CIRCLE_3, angle, false);
							angle += MathUtils.degreesToRadians * 4;
						}
					}
					, 0f
					, 0.05f
				),
				// Swirl
				new CustomTask(
					new Task() {
						private float angle = MathUtils.degreesToRadians * 180;
						
						@Override
						public void run() {
							p.fireProjectile(Projectile.RED_CIRCLE_3, angle, false);
							angle += MathUtils.degreesToRadians * 4;
						}
					}
					, 0f
					, 0.05f
				),
				// Swirl
				new CustomTask(
					new Task() {
						private float angle = MathUtils.degreesToRadians * 270;
						
						@Override
						public void run() {
							p.fireProjectile(Projectile.YELLOW_CIRCLE_3, angle, false);
							angle += MathUtils.degreesToRadians * 4;
						}
					}
					, 0f
					, 0.05f
				),
				// Normals
				new CustomTask(
					new Task() {						
						@Override
						public void run() {
							p.fireProjectile(Projectile.RED_CIRCLE_2, false);
						}
					}
					, 0f
					, 0.25f
				)
			};
		}
		else if(phase == 5) {
			timers.tasks = new CustomTask[] {
				// Huge shot
				new CustomTask(
					new Task() {
						@Override
						public void run() {
							p.fireProjectile(Projectile.RED_CIRCLE_4, false);
						}
					}
					, 1.5f
					, 5f
					, 0
				),
				// Shotguns
				new CustomTask(
					new Task() {
						@Override
						public void run() {
							p.leadShot(Weapon.RED_CIRCLE_1_STREAM_1, false);
							p.fire(Weapon.RED_CIRCLE_1_STREAM_1, p.getAngleToTarget() + (MathUtils.degreesToRadians * -20), false);
							p.fire(Weapon.RED_CIRCLE_1_STREAM_1, p.getAngleToTarget() + (MathUtils.degreesToRadians * 20), false);
						}
					}
					, 2f
					, 0.2f
				)
			};
		}
		else if(phase == 6) {
			timers.tasks = new CustomTask[] {
				// Swirl
				new CustomTask(
					new Task() {
						private float angle = 0f;
						
						@Override
						public void run() {
							p.fireProjectile(Projectile.GREEN_CIRCLE_2, angle, false);
							angle += MathUtils.degreesToRadians * 4;
						}
					}
					, 1f
					, 0.1f
				),
				// Swirl
				new CustomTask(
					new Task() {
						private float angle = MathUtils.degreesToRadians * 90;
						
						@Override
						public void run() {
							p.fireProjectile(Projectile.BLUE_CIRCLE_2, angle, false);
							angle += MathUtils.degreesToRadians * 4;
						}
					}
					, 1f
					, 0.1f
				),
				// Swirl
				new CustomTask(
					new Task() {
						private float angle = MathUtils.degreesToRadians * 180;
						
						@Override
						public void run() {
							p.fireProjectile(Projectile.RED_CIRCLE_2, angle, false);
							angle += MathUtils.degreesToRadians * 4;
						}
					}
					, 1f
					, 0.1f
				),
				// Swirl
				new CustomTask(
					new Task() {
						private float angle = MathUtils.degreesToRadians * 270;
						
						@Override
						public void run() {
							p.fireProjectile(Projectile.YELLOW_CIRCLE_2, angle, false);
							angle += MathUtils.degreesToRadians * 4;
						}
					}
					, 1f
					, 0.1f
				),
				new CustomTask(
					new Task() {						
						@Override
						public void run() {
							p.fireProjectile(Projectile.RED_CIRCLE_3, MathUtils.random(MathUtils.PI2), false);
						}
					}
					, 2f
					, 0.04f
				)
			};
		}
		else if(phase == 7) {
			timers.tasks = new CustomTask[] {
				new CustomTask(
					new Task() {						
						@Override
						public void run() {
							p.fireProjectile(Projectile.RED_CIRCLE_3, MathUtils.random(MathUtils.PI2), false);
						}
					}
					, 2f
					, 0.1f
				),
				new CustomTask(
					new Task() {						
						@Override
						public void run() {
							p.fireProjectile(Projectile.GREEN_CIRCLE_3, MathUtils.random(MathUtils.PI2), false);
						}
					}
					, 2f
					, 0.1f
				),
				new CustomTask(
					new Task() {						
						@Override
						public void run() {
							p.fireProjectile(Projectile.YELLOW_CIRCLE_3, MathUtils.random(MathUtils.PI2), false);
						}
					}
					, 2f
					, 0.1f
				),
				new CustomTask(
					new Task() {						
						@Override
						public void run() {
							p.fireProjectile(Projectile.BLUE_CIRCLE_3, MathUtils.random(MathUtils.PI2), false);
						}
					}
					, 2f
					, 0.1f
				)
			};
		}
	}
}
