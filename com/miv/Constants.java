package com.miv;

public class Constants {	
	public static final float SCREEN_WIDTH = 1280, SCREEN_HEIGHT = 720;
	
	public static final float SCALE = 0.05f;
	
	// Number of background maps that exist
	public static final int NUMBER_OF_BACKGROUND_IMAGES = 6;
	
	// Speed the background scrolls at (how many times faster than player's movement)
	public static final float BACKGROUND_SCROLL_SPEED = 1/4f;
	
	public static final float METERS_PER_SCREEN_WIDTH = 8;
	
	// Damage taken by shield; eg If a projectile does 100 damage normally, it does 100*SHIELD_DAMAGE_MULTIPLIER damage to shieldStrength if shield is activated
	public static final float SHIELD_DAMAGE_MULTIPLIER = 1/50f;
	
	// Brake power of enemies
	public static final float ENEMY_BRAKE_POWER = 0.98f;
	
	// For body collision
	public static class ENTITY {
		public static final short CATEGORY_DOOR = 0x0032;
		public static final short CATEGORY_WALL = 0x0016;
		public static final short CATEGORY_PLAYER = 0x0001;
		public static final short CATEGORY_ENEMY = 0x0002;
		public static final short CATEGORY_PLAYER_PROJECTILE = 0x0004;
		public static final short CATEGORY_ENEMY_PROJECTILE = 0x0008;
		
		public static final short MASK_DOOR = 0x0001;
		public static final short MASK_WALL = 0x0001;
		public static final short MASK_PLAYER = 0x0016 | 0x0001 | 0x0002 | 0x0008 | 0x0032;
		public static final short MASK_ENEMY = 0x0001 | 0x0002 | 0x0004;
		public static final short MASK_PLAYER_PROJECTILE = 0x0002;
		public static final short MASK_ENEMY_PROJECTILE = 0x0001;
	}
	
	public static enum Group {
		PLAYER(ENTITY.CATEGORY_PLAYER, ENTITY.MASK_PLAYER),
		ENEMY(ENTITY.CATEGORY_ENEMY, ENTITY.MASK_ENEMY),
		PLAYER_PROJECTILE(ENTITY.CATEGORY_PLAYER_PROJECTILE, ENTITY.MASK_PLAYER_PROJECTILE),
		ENEMY_PROJECTILE(ENTITY.CATEGORY_ENEMY_PROJECTILE, ENTITY.MASK_ENEMY_PROJECTILE);
		
		public short categoryBits, maskBits;
		
		Group(short categoryBits, short maskBits) {
			this.categoryBits = categoryBits;
			this.maskBits = maskBits;
		}
	}
}
