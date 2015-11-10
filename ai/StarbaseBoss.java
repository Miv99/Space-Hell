package ai;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer.Task;
import com.miv.ProjectileFactory;
import com.miv.ProjectileFactory.Projectile;

import ai.BlackOrange.BlackOrange3;
import ai.BlackOrange.BlackOrange4;
import components.OnDeathComponent;
import components.ProjectileFiringComponent;
import items.Ships.Ship;
import items.Weapons.Weapon;
import onDeath.Events;
import onDeath.Events.OnDeathEvent;
import components.AIComponent.PhaseTimes;
import components.AIComponent.Timers;
import components.AIMovementComponent.MovementType;
import utils.CustomTask;
import utils.EntityUtils;

public class StarbaseBoss {
	static ComponentMapper<OnDeathComponent> dm = ComponentMapper.getFor(OnDeathComponent.class);
	
	public static void setPhaseTimes(PhaseTimes phaseTimes) {
		phaseTimes.phaseTimes = new float[] {
				10f, //10
				30f, //30
				20f, //20
				30f, //30
				6f, // 6
				20f //20
		};
	}
	
	public static void createTasks(Engine engine, World world, ProjectileFactory projectileFactory, Entity target, Vector2 position, Timers timers, Integer phase, ProjectileFiringComponent p) {
		if(phase == 0) {
			timers.tasks = new CustomTask[] {
				// Shockwave
				new CustomTask(
					new Task() {
						@Override
						public void run() {
							// Choose 4 angles where projectiles won't be fired at; 1 in each quadrant
							int skip1 = MathUtils.random(0, 45);
							int skip2 = skip1 + 1;
							int skip3 = MathUtils.random(45, 90);
							int skip4 = skip3 + 1;
							int skip5 = MathUtils.random(90, 135);
							int skip6 = skip5 + 1;
							int skip7 = MathUtils.random(135, 180);
							int skip8 = skip7 + 1;
							
							// Fire a projectile every 2 degrees
							int i = 0;
							for(float angle = 0; angle <= MathUtils.PI2; angle += MathUtils.degreesToRadians * 2) {
								if(i != skip1 && i != skip2 && i != skip3 && i != skip4 && i != skip5 && i != skip6 && i != skip7 && i != skip8) {
									p.fireProjectile(Projectile.BLUE_CIRCLE_3, angle, true);
								}
								i++;
							}
						}
					}
					, 2f
					, 2f
					, 2
				)
			};
		}
		else if(phase == 1) {
			timers.tasks = new CustomTask[] {
				// Spawn enemies
				new CustomTask(
					new Task() {
						@Override
						public void run() {
							engine.addEntity(
								EntityUtils.newEnemyShip(engine, world, projectileFactory, Ship.BLACK_ORANGE_3, target, BlackOrange3.class, MovementType.FOLLOW_TARGET, "Carrier", 
										position.x + 120, position.y, 100f, 200f, 25f, 600f)
							);
							engine.addEntity(
								EntityUtils.newEnemyShip(engine, world, projectileFactory, Ship.BLACK_ORANGE_3, target, BlackOrange3.class, MovementType.FOLLOW_TARGET, "Carrier", 
										position.x - 120, position.y, 100f, 200f, 25f, 600f)
							);
							engine.addEntity(
								EntityUtils.newEnemyShip(engine, world, projectileFactory, Ship.BLACK_ORANGE_3, target, BlackOrange3.class, MovementType.FOLLOW_TARGET, "Carrier", 
										position.x, position.y + 120, 100f, 200f, 25f, 600f)
							);
							engine.addEntity(
								EntityUtils.newEnemyShip(engine, world, projectileFactory, Ship.BLACK_ORANGE_3, target, BlackOrange3.class, MovementType.FOLLOW_TARGET, "Carrier", 
										position.x, position.y - 120, 100f, 200f, 25f, 600f)
							);
						}
					}
					, 0f
					, 2f
					, 0
				),
				// Small shockwave
				new CustomTask(
					new Task() {
						@Override
						public void run() {
							// Fire a projectile every 10 degrees
							for(float angle = 0; angle <= MathUtils.PI2; angle += MathUtils.degreesToRadians * 10) {
								p.fireProjectile(Projectile.BLUE_CIRCLE_3, angle, true);
							}
						}
					}
					, 0f
					, 2f
				)
			};
		}
		else if(phase == 2) {
			timers.tasks = new CustomTask[] {
				// Random bullets everywhere
				new CustomTask(
					new Task() {
						@Override
						public void run() {
							p.fireProjectile(Projectile.BLUE_CIRCLE_3, MathUtils.random(MathUtils.PI2), false);
						}
					}
					, 2f
					, 0.04f
				),
				// Small shockwave
				new CustomTask(
					new Task() {
						@Override
						public void run() {
							// Fire a projectile every 4-10 degrees
							for(float angle = 0; angle <= MathUtils.PI2; angle += MathUtils.degreesToRadians * MathUtils.random(4, 10)) {
								p.fireProjectile(Projectile.BLUE_CIRCLE_3, angle, true);
							}
						}
					}
					, 2f
					, 2f
				)
			};
		}
		else if(phase == 3) {
			timers.tasks = new CustomTask[] {
				// Spawn enemies
				new CustomTask(
					new Task() {
						@Override
						public void run() {
							engine.addEntity(
								EntityUtils.newEnemyShip(engine, world, projectileFactory, Ship.BLACK_ORANGE_4, target, BlackOrange4.class, MovementType.FOLLOW_TARGET, "Carrier", 
										position.x + 120, position.y, 150f, 500f, 25f, 300f)
							);
							engine.addEntity(
								EntityUtils.newEnemyShip(engine, world, projectileFactory, Ship.BLACK_ORANGE_4, target, BlackOrange4.class, MovementType.FOLLOW_TARGET, "Carrier", 
										position.x - 120, position.y, 150f, 500f, 25f, 300f)
							);
						}
					}
					, 0f
					, 2f
					, 0
				),
				// Lasers
				new CustomTask(
					new Task() {						
						@Override
						public void run() {
							p.fire(Weapon.BLUE_CIRCLE_2_STREAM_1, false);
						}
					}
					, 2f
					, 0.5f
				)
			};
		}
		else if(phase == 4) {
			timers.tasks = new CustomTask[] {
				// Exploding shots
				new CustomTask(
					new Task() {						
						@Override
						public void run() {
							p.fireAngledOnExpireProjectile(Projectile.GREEN_CIRCLE_3_WEB_STARTER, false, OnDeathEvent.EIGHT_PROJECTILE_EXPLOSION, 
									new Events.CustomData().setIsPlayer(false).setExplosionProjectile(Projectile.GREEN_CIRCLE_3_WEB_1).setOnExpireEvent(OnDeathEvent.FOUR_PROJECTILE_EXPLOSION).setRecursionLimit(4),
									0f);
						}
					}
					, 2f
					, 0.5f
					, 0
				)
			};
		}
		else if(phase == 5) {
			timers.tasks = new CustomTask[] {
				// Exploding shots
				new CustomTask(
					new Task() {						
						@Override
						public void run() {
							p.fireAngledOnExpireProjectile(Projectile.GREEN_CIRCLE_3_WEB_STARTER, false, OnDeathEvent.EIGHT_PROJECTILE_EXPLOSION, 
									new Events.CustomData().setIsPlayer(false).setExplosionProjectile(Projectile.GREEN_CIRCLE_3_WEB_2).setOnExpireEvent(OnDeathEvent.EIGHT_PROJECTILE_EXPLOSION).setRecursionLimit(3),
									0f);
						}
					}
					, 2f
					, 7.5f
					, 1
				),
				new CustomTask(
					new Task() {						
						@Override
						public void run() {
							// Fire a projectile every 10 degrees
							float spacing = MathUtils.random(10, 20);
							for(float angle = 0; angle <= MathUtils.PI2; angle += MathUtils.degreesToRadians * spacing) {
								p.fireProjectile(Projectile.BLUE_CIRCLE_3, angle, true);
							}
						}
					}
					, 2f
					, 1f
					, 20
				)
			};
		}
	}
}
