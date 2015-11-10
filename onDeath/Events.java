package onDeath;

import com.miv.ProjectileFactory.Projectile;

import onDeath.ProjectileExplosion.EightProjectileExplosion;
import onDeath.ProjectileExplosion.FourProjectileExplosion;

public class Events {
	public static enum OnDeathEvent {
		// {@link CustomData} requirements listed in comments
		
		// Requires: isPlayer, explosionProjectile
		FOUR_PROJECTILE_EXPLOSION(FourProjectileExplosion.class),
		EIGHT_PROJECTILE_EXPLOSION(EightProjectileExplosion.class);
		
		public Class<?>[] events;
		
		OnDeathEvent(Class<?> event) {
			this.events = new Class<?>[] { event };
		}
		
		OnDeathEvent(Class<?>[] events) {
			this.events = events;
		}
	}
	
	public static class CustomData {
		// For use by ProjectileExplosion; whether or not the parent projectile was a player's or enemy's
		public boolean isPlayer;
		// For use by ProjectileExplosion; the type of projectile comes out of the explosion
		public Projectile explosionProjectile;
		
		public boolean hasRecursion = false;
		// If the onDeath or onExpire events call upon themselves, then recursionLimit is used to determine how many times they can call on themselves
		public int recursionLimit;
		
		// On death event
		public OnDeathEvent onDeathEvent;
		// On expire event
		public OnDeathEvent onExpireEvent;
		
		public CustomData clone() {
			CustomData clone = new CustomData();
			
			clone.isPlayer = clone.isPlayer;
			clone.explosionProjectile = explosionProjectile;
			clone.hasRecursion = hasRecursion;
			clone.recursionLimit = recursionLimit;
			clone.onDeathEvent = onDeathEvent;
			clone.onExpireEvent = onExpireEvent;
			
			return clone;
		}
		
		public CustomData setIsPlayer(boolean isPlayer) {
			this.isPlayer = isPlayer;
			return this;
		}
		public CustomData setExplosionProjectile(Projectile explosionProjectile) {
			this.explosionProjectile = explosionProjectile;
			return this;
		}
		public CustomData setOnDeathEvent(OnDeathEvent event) {
			this.onDeathEvent = event;
			return this;
		}
		public CustomData setOnExpireEvent(OnDeathEvent event) {
			this.onExpireEvent = event;
			return this;
		}
		public CustomData setRecursionLimit(int recursionLimit) {
			hasRecursion = true;
			this.recursionLimit = recursionLimit;
			return this;
		}
	}
}
