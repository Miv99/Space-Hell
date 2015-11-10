package items;

import com.miv.ProjectileFactory.Projectile;

public class Weapons {
	public static enum Weapon {
		PHASER_1(Projectile.PHASER_1, 0f, 0.2f),
		PHASER_2(Projectile.PHASER_2, 0f, 0.1f),
		PHASER_3(Projectile.PHASER_3, 0f, 0.07f),
		
		SHOTGUN_1(Projectile.PHASER_1, 0f, 1.2f, 30f, 10, 0.005f),
		SHOTGUN_2(Projectile.PHASER_1, 0f, 1.4f, 25f, 20, 0.005f),
		SHOTGUN_3(Projectile.PHASER_1, 0f, 2.0f, 15f, 30, 0.005f),
		
		RED_CIRCLE_1_SHOTGUN_1(Projectile.RED_CIRCLE_1, 0f, 2.0f, 12f, 15, 0.005f),
		RED_CIRCLE_1_STREAM_1(Projectile.RED_CIRCLE_1, 0f, 2.0f, 10, 0.005f),
		
		RED_CIRCLE_2_CONTROLLED_SHOTGUN_1(Projectile.RED_CIRCLE_2, 0f, 0.5f, 3, 10f, true),
		RED_CIRCLE_3_CONTROLLED_SHOTGUN_1(Projectile.RED_CIRCLE_3, 0f, 0.5f, 5, 10f, true),
		
		BLUE_CIRCLE_2_STREAM_1(Projectile.BLUE_CIRCLE_1, 0f, 2.0f, 10, 0.01f),
		
		RED_BOSS_SHOTGUN_1(Projectile.RED_CIRCLE_1, 0f, 2.0f, 45f, 10, 0.005f),
		
		SUPER_OP(Projectile.PHASER_1, 0f, 0.1f, 15f, 30, 0.005f);
		
		public Projectile projectile;
		public float startup, cooldown, spread, time, controlledSpread;
		public int bullets;
		
		// -------------------------------------------------------------------------Player Weapons---------------------------------------------------------------
		
		/**
		 * Multiple fire weapons with no spread and time delay
		 * @param projectile - the type of projectile this weapon fires
		 * @param startup - time it takes in seconds for weapon to begin firing
		 * @param cooldown - time it takes in seconds for weapon to fire again
		 * @param bullets - number of bullets shot
		 * @param time - time inbetween each bullet fire; (time * bullets) should be less than cooldown
		 */
		Weapon(Projectile projectile, float startup, float cooldown, int bullets, float time) {
			this.projectile = projectile;
			this.startup = startup;
			this.cooldown = cooldown;
			this.bullets = bullets;
			this.time = time;
		}
		
		/**
		 * Multiple fire weapons with controlled spread
		 * @param projectile - the type of projectile this weapon fires
		 * @param startup - time it takes in seconds for weapon to begin firing
		 * @param cooldown - time it takes in seconds for weapon to fire again
		 * @param bullets - number of bullets shot
		 * @param controlledSpread - spread between each bullet in degrees; eg a spread of 5deg fired at 0deg with 5 projectiles would fire projectiles at: -10, -5, 0, 5, 10
		 * @param isControlledSpread - ignore this
		 */
		Weapon(Projectile projectile, float startup, float cooldown, int bullets, float controlledSpread, boolean isControlledSpread) {
			this.projectile = projectile;
			this.startup = startup;
			this.cooldown = cooldown;
			this.bullets = bullets;
			this.controlledSpread = controlledSpread;
		}
		
		/**
		 * Multiple fire weapons with spread and time delay
		 * @param projectile - the type of projectile this weapon fires
		 * @param startup - time it takes in seconds for weapon to begin firing
		 * @param cooldown - time it takes in seconds for weapon to fire again
		 * @param spread - maximum degree spread of bullet
		 * @param bullets - number of bullets shot
		 * @param time - time inbetween each bullet fire; (time * bullets) should be less than cooldown
		 */
		Weapon(Projectile projectile, float startup, float cooldown, float spread, int bullets, float time) {
			this.projectile = projectile;
			this.startup = startup;
			this.cooldown = cooldown;
			this.spread = spread;
			this.bullets = bullets;
			this.time = time;
		}
		
		/**
		 * Single fire weapons with no spread
		 * @param projectile - the type of projectile this weapon fires
		 * @param startup - time it takes in seconds for weapon to begin firing
		 * @param cooldown - time it takes in seconds for weapon to fire again
		 */
		Weapon(Projectile projectile, float startup, float cooldown) {
			this.projectile = projectile;
			this.startup = startup;
			this.cooldown = cooldown;
			bullets = 1;
		}
		
		/**
		 * Single fire weapons with spread
		 * @param projectile - the type of projectile this weapon fires
		 * @param startup - time it takes in seconds for weapon to begin firing
		 * @param cooldown - time it takes in seconds for weapon to fire again
		 * @param spread - maximum degree spread of bullet
		 */
		Weapon(Projectile projectile, float startup, float cooldown, float spread) {
			this.projectile = projectile;
			this.startup = startup;
			this.cooldown = cooldown;
			this.spread = spread;
			bullets = 1;
		}
		
		/**
		 * Multiple fire weapons with no spread
		 * @param projectile - the type of projectile this weapon fires
		 * @param startup - time it takes in seconds for weapon to begin firing
		 * @param cooldown - time it takes in seconds for weapon to fire again
		 * @param bullets - number of bullets shot
		 */
		Weapon(Projectile projectile, float startup, float cooldown, int bullets) {
			this.projectile = projectile;
			this.startup = startup;
			this.cooldown = cooldown;
			this.bullets = bullets;
		}
		
		/**
		 * Multiple fire weapons with spread
		 * @param projectile - the type of projectile this weapon fires
		 * @param startup - time it takes in seconds for weapon to begin firing
		 * @param cooldown - time it takes in seconds for weapon to fire again
		 * @param spread - maximum degree spread of bullet
		 * @param bullets - number of bullets shot
		 */
		Weapon(Projectile projectile, float startup, float cooldown, float spread, int bullets) {
			this.projectile = projectile;
			this.startup = startup;
			this.cooldown = cooldown;
			this.spread = spread;
			this.bullets = bullets;
		}
	}
}
