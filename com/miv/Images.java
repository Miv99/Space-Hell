package com.miv;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Images {
	private final static int PLAYER = 0, ENEMY = 1, SHIELD = 2, PROJECTILE = 3, TERRAIN = 4, ASTEROID = 5, WALL = 6;
	
	public static enum Image {
		// Walls
		MAP_BOUNDARY(0, 0, 1, 1, WALL),
		
		// Players
		PLAYER_1(0, 0, 64, 64, 96f, 96f, PLAYER),
		
		// Enemies
		SWARMER_4(334, 0, 164, 104, ENEMY),
		
		BLACK_ORANGE_1(0, 1134, 397, 397, ENEMY),
		BLACK_ORANGE_2(397, 1134, 470, 470, ENEMY),
		BLACK_ORANGE_3(0, 1531, 397, 397, ENEMY),
		BLACK_ORANGE_4(0, 1928, 478, 478, ENEMY),
		
		// Turrets
		TURRET_1(334, 104, 164, 164, ENEMY),
		
		// Bosses
		RED_BOSS(0, 0, 334, 334, 366f, 366f, ENEMY),
		STARBASE_BOSS(0, 334, 800, 800, ENEMY),
		
		// Shields
		SHIELD_1(0, 0, 556, 556, SHIELD),
		
		// Projectiles
		PHASER_1(332, 103, 88, 64, PROJECTILE),
		PHASER_2(332, 4, 79, 57, PROJECTILE),
		PHASER_3(332, 61, 60, 42, PROJECTILE),
		
		GREEN_CIRCLE_1(0, 4, 20, 20, PROJECTILE),
		GREEN_CIRCLE_2(21, 4, 30, 30, PROJECTILE),
		GREEN_CIRCLE_3(52, 4, 40, 40, PROJECTILE),
		
		YELLOW_CIRCLE_1(0, 44, 20, 20, PROJECTILE),
		YELLOW_CIRCLE_2(21, 44, 30, 30, PROJECTILE),
		YELLOW_CIRCLE_3(52, 44, 40, 40, PROJECTILE),
		
		RED_CIRCLE_1(0, 86, 20, 20, PROJECTILE),
		RED_CIRCLE_2(21, 86, 30, 30, PROJECTILE),
		RED_CIRCLE_3(52, 86, 40, 40, PROJECTILE),
		RED_CIRCLE_4(92, 4, 240, 240, PROJECTILE),
		
		BLUE_CIRCLE_1(0, 127, 20, 20, PROJECTILE),
		BLUE_CIRCLE_2(21, 127, 30, 30, PROJECTILE),
		BLUE_CIRCLE_3(52, 127, 40, 40, PROJECTILE),
		
		
		// Terrain
		STARBASE_1(0, 0, 1024, 1024, TERRAIN),
		STARBASE_2(1024, 0, 1024, 1024, TERRAIN),
		STARBASE_3(2048, 0, 1024, 1024, TERRAIN),
		STARBASE_4(3072, 0, 600, 600, TERRAIN),
		STARBASE_5(3672, 0, 600, 600, TERRAIN),
		STARBASE_6(4272, 0, 600, 600, TERRAIN),
		
		STARBASE_1_CORRIDOR_1(0, 2043, 526, 526, TERRAIN),
		STARBASE_1_CORRIDOR_2(526, 2043, 526, 526, TERRAIN),
		STARBASE_1_CORRIDOR_3(1052, 2043, 526, 526, TERRAIN),
		STARBASE_1_CORRIDOR_4(1578, 2043, 526, 526, TERRAIN),
		
		SPAWNER_1(0, 1024, 1024, 1019, TERRAIN),
		
		ASTEROID_1(128 * 0, 0, 128, 128, ASTEROID),
		ASTEROID_2(128 * 1, 0, 128, 128, ASTEROID),
		ASTEROID_3(128 * 2, 0, 128, 128, ASTEROID),
		ASTEROID_4(128 * 3, 0, 128, 128, ASTEROID),
		ASTEROID_5(128 * 4, 0, 128, 128, ASTEROID),
		ASTEROID_6(128 * 5, 0, 128, 128, ASTEROID),
		ASTEROID_7(128 * 6, 0, 128, 128, ASTEROID),
		ASTEROID_8(128 * 7, 0, 128, 128, ASTEROID),
		ASTEROID_9(128 * 8, 0, 128, 128, ASTEROID),
		ASTEROID_10(128 * 9, 0, 128, 128, ASTEROID),
		
		ASTEROID_11(128 * 10, 0, 128, 128, ASTEROID),
		ASTEROID_12(128 * 11, 0, 128, 128, ASTEROID),
		ASTEROID_13(128 * 12, 0, 128, 128, ASTEROID),
		ASTEROID_14(128 * 13, 0, 128, 128, ASTEROID),
		ASTEROID_15(128 * 14, 0, 128, 128, ASTEROID),
		ASTEROID_16(128 * 15, 0, 128, 128, ASTEROID),
		ASTEROID_17(128 * 16, 0, 128, 128, ASTEROID),
		ASTEROID_18(128 * 17, 0, 128, 128, ASTEROID),
		ASTEROID_19(128 * 18, 0, 128, 128, ASTEROID),
		ASTEROID_20(128 * 19, 0, 128, 128, ASTEROID),
		
		ASTEROID_21(128 * 20, 0, 128, 128, ASTEROID),
		ASTEROID_22(128 * 21, 0, 128, 128, ASTEROID),
		ASTEROID_23(128 * 22, 0, 128, 128, ASTEROID),
		ASTEROID_24(128 * 23, 0, 128, 128, ASTEROID),
		ASTEROID_25(128 * 24, 0, 128, 128, ASTEROID),
		ASTEROID_26(128 * 25, 0, 128, 128, ASTEROID),
		ASTEROID_27(128 * 26, 0, 128, 128, ASTEROID),
		ASTEROID_28(128 * 27, 0, 128, 128, ASTEROID),
		ASTEROID_29(128 * 28, 0, 128, 128, ASTEROID),
		ASTEROID_30(128 * 29, 0, 128, 128, ASTEROID),
		
		ASTEROID_31(128 * 30, 0, 128, 128, ASTEROID),
		ASTEROID_32(128 * 31, 0, 128, 128, ASTEROID),
		ASTEROID_33(128 * 32, 0, 128, 128, ASTEROID),
		ASTEROID_34(128 * 33, 0, 128, 128, ASTEROID),
		ASTEROID_35(128 * 34, 0, 128, 128, ASTEROID),
		ASTEROID_36(128 * 35, 0, 128, 128, ASTEROID),
		ASTEROID_37(128 * 36, 0, 128, 128, ASTEROID),
		ASTEROID_38(128 * 37, 0, 128, 128, ASTEROID),
		ASTEROID_39(128 * 38, 0, 128, 128, ASTEROID),
		ASTEROID_40(128 * 39, 0, 128, 128, ASTEROID),
		
		ASTEROID_41(128 * 40, 0, 128, 128, ASTEROID),
		ASTEROID_42(128 * 41, 0, 128, 128, ASTEROID),
		ASTEROID_43(128 * 42, 0, 128, 128, ASTEROID),
		ASTEROID_44(128 * 43, 0, 128, 128, ASTEROID),
		ASTEROID_45(128 * 44, 0, 128, 128, ASTEROID),
		ASTEROID_46(128 * 45, 0, 128, 128, ASTEROID),
		ASTEROID_47(128 * 46, 0, 128, 128, ASTEROID),
		ASTEROID_48(128 * 47, 0, 128, 128, ASTEROID),
		ASTEROID_49(128 * 48, 0, 128, 128, ASTEROID),
		ASTEROID_50(128 * 49, 0, 128, 128, ASTEROID),
		
		ASTEROID_51(128 * 50, 0, 128, 128, ASTEROID),
		ASTEROID_52(128 * 51, 0, 128, 128, ASTEROID),
		ASTEROID_53(128 * 52, 0, 128, 128, ASTEROID),
		ASTEROID_54(128 * 53, 0, 128, 128, ASTEROID),
		ASTEROID_55(128 * 54, 0, 128, 128, ASTEROID),
		ASTEROID_56(128 * 55, 0, 128, 128, ASTEROID),
		ASTEROID_57(128 * 56, 0, 128, 128, ASTEROID),
		ASTEROID_58(128 * 57, 0, 128, 128, ASTEROID),
		ASTEROID_59(128 * 58, 0, 128, 128, ASTEROID),
		ASTEROID_60(128 * 59, 0, 128, 128, ASTEROID),
		
		ASTEROID_61(128 * 60, 0, 128, 128, ASTEROID),
		ASTEROID_62(128 * 61, 0, 128, 128, ASTEROID),
		ASTEROID_63(128 * 62, 0, 128, 128, ASTEROID),
		ASTEROID_64(128 * 63, 0, 128, 128, ASTEROID);
		
		public int xTexture, yTexture, imageWidth, imageHeight;
		public float shieldWidth, shieldHeight;
		private int fileLocation;
		
		public Sprite sprite;
		
		// For entities that can use shields only
		Image(int xTexture, int yTexture, int imageWidth, int imageHeight, float shieldWidth, float shieldHeight, int fileLocation) {
			this.xTexture = xTexture;
			this.yTexture = yTexture;
			this.imageWidth = imageWidth;
			this.imageHeight = imageHeight;
			this.shieldWidth = shieldWidth;
			this.shieldHeight = shieldHeight;
			this.fileLocation = fileLocation;
		}
		
		// Everything else
		Image(int xTexture, int yTexture, int imageWidth, int imageHeight, int fileLocation) {
			this.xTexture = xTexture;
			this.yTexture = yTexture;
			this.imageWidth = imageWidth;
			this.imageHeight = imageHeight;
			this.fileLocation = fileLocation;
		}
	}
	
	/**
	 * Loads all sprites
	 */
	public static void loadImages() {
		Texture players = new Texture(Gdx.files.internal("assets\\Players.png"));
		Texture enemies = new Texture(Gdx.files.internal("assets\\Enemies.png"));
		Texture shields = new Texture(Gdx.files.internal("assets\\Shields.png"));
		Texture projectiles = new Texture(Gdx.files.internal("assets\\Projectiles.png"));
		Texture terrain = new Texture(Gdx.files.internal("assets\\Terrain\\Terrain.png"));
		Texture asteroids = new Texture(Gdx.files.internal("assets\\Terrain\\Asteroids.png"));
		Texture walls = new Texture(Gdx.files.internal("assets\\Walls.png"));
		
		for(Image i : Image.values()) {
			if(i.fileLocation == PLAYER)
				i.sprite = new Sprite(new TextureRegion(players, i.xTexture, i.yTexture, i.imageWidth, i.imageHeight));
			else if(i.fileLocation == ENEMY)
				i.sprite = new Sprite(new TextureRegion(enemies, i.xTexture, i.yTexture, i.imageWidth, i.imageHeight));
			else if(i.fileLocation == SHIELD)
				i.sprite = new Sprite(new TextureRegion(shields, i.xTexture, i.yTexture, i.imageWidth, i.imageHeight));
			else if(i.fileLocation == PROJECTILE)
				i.sprite = new Sprite(new TextureRegion(projectiles, i.xTexture, i.yTexture, i.imageWidth, i.imageHeight));
			else if(i.fileLocation == TERRAIN)
				i.sprite = new Sprite(new TextureRegion(terrain, i.xTexture, i.yTexture, i.imageWidth, i.imageHeight));
			else if(i.fileLocation == ASTEROID)
				i.sprite = new Sprite(new TextureRegion(asteroids, i.xTexture, i.yTexture, i.imageWidth, i.imageHeight));
			else if(i.fileLocation == WALL)
				i.sprite = new Sprite(new TextureRegion(walls, i.xTexture, i.yTexture, i.imageWidth, i.imageHeight));
		}
	}
}
