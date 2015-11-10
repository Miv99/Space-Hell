package com.miv;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import com.miv.Constants.ENTITY;
import com.miv.Images.Image;

import components.ActorComponent;
import components.HitboxComponent;
import components.OnDeathComponent;
import components.OnExpireComponent;
import components.ProjectileComponent;
import components.VisualComponent;
import onDeath.Events.OnDeathEvent;

public class ProjectileFactory {
	public static final String PROJECTILE_BODY_SOURCE = "Projectiles";
	
	public static enum Projectile {
		// Orange
		PHASER_1("Phaser 1", 0f, 2f, 100f, 2f, Image.PHASER_1),
		PHASER_2("Phaser 2", 0f, 3f, 110f, 2f, Image.PHASER_2),
		PHASER_3("Phaser 3", 0f, 4f, 120f, 2f, Image.PHASER_3),
		
		GREEN_CIRCLE_1("Green Circle 1", 0.05f, 10f, 40f, 8f, Image.GREEN_CIRCLE_1),
		GREEN_CIRCLE_2("Green Circle 2", 0.075f, 14f, 30f, 8f, Image.GREEN_CIRCLE_2),
		GREEN_CIRCLE_3("Green Circle 3", 0.1f, 18f, 20f, 8f, Image.GREEN_CIRCLE_3),
		
		// Starts the web (OnDeathEvent of projectile explosion of same projectile for recursion web)
		GREEN_CIRCLE_3_WEB_STARTER("Green Circle 3", 0.1f, 18f, 20f, 0f, Image.GREEN_CIRCLE_3),
		GREEN_CIRCLE_3_WEB_1("Green Circle 3", 0.1f, 18f, 20f, 1.5f, Image.GREEN_CIRCLE_3),
		GREEN_CIRCLE_3_WEB_2("Green Circle 3", 0.1f, 18f, 20f, 3f, Image.GREEN_CIRCLE_3),
		
		// Yellow = High knockback
		YELLOW_CIRCLE_1("Yellow Circle 1", 0.2f, 10f, 40f, 8f, Image.YELLOW_CIRCLE_1),
		YELLOW_CIRCLE_2("Yellow Circle 2", 0.4f, 14f, 30f, 8f, Image.YELLOW_CIRCLE_2),
		YELLOW_CIRCLE_3("Yellow Circle 3", 0.6f, 18f, 20f, 8f, Image.YELLOW_CIRCLE_3),
		
		// Red = High damage
		RED_CIRCLE_1("Red Circle 1", 0.05f, 18f, 40f, 8f, Image.RED_CIRCLE_1),
		RED_CIRCLE_2("Red Circle 2", 0.075f, 20f, 30f, 8f, Image.RED_CIRCLE_2),
		RED_CIRCLE_3("Red Circle 3", 0.1f, 22f, 20f, 8f, Image.RED_CIRCLE_3),
		RED_CIRCLE_4("Red Circle 4", 0.6f, 50f, 20f, 8f, Image.RED_CIRCLE_4),
		
		// TODO: make them slow the player
		BLUE_CIRCLE_1("Blue Circle 1", 0.05f, 10f, 40f, 8f, Image.BLUE_CIRCLE_1),
		BLUE_CIRCLE_2("Blue Circle 2", 0.075f, 14f, 30f, 8f, Image.BLUE_CIRCLE_2),
		BLUE_CIRCLE_3("Blue Circle 3", 0.1f, 18f, 20f, 8f, Image.BLUE_CIRCLE_3);
		
		public String name;
		public float speed, time, damage, density;
		public Image image;
				
		/**
		 * Projectiles fired out of weapons
		 * @param name - name of the projectile
		 * @param damage - how much damage the projectile does
		 * @param speed - speed of the projectile
		 * @param time - time the projectile stays alive in seconds
		 * @param xTexture - x location of the sprite in Projectile.png
		 * @param yTexture - y location of the sprite in Projectile.png
		 * @param imageWidth - width of the sprite
		 * @param imageHeight - height of the sprite
		 */
		Projectile(String name, float density, float damage, float speed, float time, Image image) {
			// For some reason 0 density is the same as infinite density or something
			if(density == 0f || density == 0) density = 0.00001f;
			
			this.name = name;
			this.damage = damage;
			this.speed = speed;
			this.time = time;
			this.density = density;
			this.image = image;
		}
	}
	
	public Engine engine;
	public World world;
	
	public ProjectileFactory(Engine engine, World world) {
		this.engine = engine;
		this.world = world;
	}
	
	/**
	 * Fire a projectile
	 * @param projectile - projectile type
	 * @param speedChange - how much is added to speed of projectile
	 * @param x - x origin of projectile
	 * @param y - y origin of projectile
	 * @param angle - angle of fire in radians
	 * @param isPlayer - projectile belongs to player
	 */
	public Entity newProjectile(Projectile projectile, float x, float y, float angle, boolean isPlayer, boolean antiLag) {
		// Create the projectile
		Entity e = new Entity();
		
		// Create hitbox
		HitboxComponent hitboxComponent = null;
		if(isPlayer) {
			hitboxComponent = new HitboxComponent(world, e, PROJECTILE_BODY_SOURCE, projectile.name, projectile.image.imageWidth, x, y, projectile.density, ENTITY.CATEGORY_PLAYER_PROJECTILE, ENTITY.MASK_PLAYER_PROJECTILE, antiLag);
		} else {
			hitboxComponent = new HitboxComponent(world, e, PROJECTILE_BODY_SOURCE, projectile.name, projectile.image.imageWidth, x, y, projectile.density, ENTITY.CATEGORY_ENEMY_PROJECTILE, ENTITY.MASK_ENEMY_PROJECTILE, antiLag);
		}
		
		// Add components
		e.add(hitboxComponent);
		e.add(new ActorComponent(projectile.name));
		e.add(new VisualComponent(new Sprite(projectile.image.sprite)));
		e.add(new ProjectileComponent(projectile, projectile.time));
		
		// Rotate projectile
		hitboxComponent.body.setTransform(hitboxComponent.body.getPosition(), angle);
		
		// Set projectile speed
		hitboxComponent.body.setLinearVelocity(MathUtils.cos(angle) * projectile.speed, MathUtils.sin(angle) * projectile.speed);
		
		engine.addEntity(e);
		
		return e;
	}
	
	/**
	 * Fire a projectile
	 * @param projectile - projectile type
	 * @param speedChange - how much is added to speed of projectile
	 * @param x - x origin of projectile
	 * @param y - y origin of projectile
	 * @param time - time the projectile is alive
	 * @param angle - angle of fire in radians
	 */
	public Entity newProjectile(Projectile projectile, float x, float y, float time, float angle, boolean isPlayer, boolean antiLag) {
		// Create the projectile
		Entity e = new Entity();
		
		// Create hitbox
		HitboxComponent hitboxComponent = null;
		if(isPlayer) {
			hitboxComponent = new HitboxComponent(world, e, PROJECTILE_BODY_SOURCE, projectile.name, projectile.image.imageWidth, x, y, projectile.density, ENTITY.CATEGORY_PLAYER_PROJECTILE, ENTITY.MASK_PLAYER_PROJECTILE, antiLag);
		} else {
			hitboxComponent = new HitboxComponent(world, e, PROJECTILE_BODY_SOURCE, projectile.name, projectile.image.imageWidth, x, y, projectile.density, ENTITY.CATEGORY_ENEMY_PROJECTILE, ENTITY.MASK_ENEMY_PROJECTILE, antiLag);
		}
		
		// Add components
		e.add(hitboxComponent);
		e.add(new ActorComponent(projectile.name));
		e.add(new VisualComponent(new Sprite(projectile.image.sprite)));
		e.add(new ProjectileComponent(projectile, time));
		
		// Rotate projectile
		hitboxComponent.body.setTransform(hitboxComponent.body.getPosition(), angle);
		
		// Set projectile speed
		hitboxComponent.body.setLinearVelocity(MathUtils.cos(angle) * projectile.speed, MathUtils.sin(angle) * projectile.speed);
		
		engine.addEntity(e);
		
		return e;
	}
	
	/**
	 * Fire a projectile with an onDeath event
	 * @param projectile - projectile type
	 * @param speedChange - how much is added to speed of projectile
	 * @param x - x origin of projectile
	 * @param y - y origin of projectile
	 * @param angle - angle of fire in radians
	 * @param categoryBits
	 * @param maskBits
	 * @param onDeathEvent - event when projectile is killed
	 */
	public void newProjectile(Projectile projectile, float x, float y, float angle, boolean isPlayer, boolean antiLag, OnDeathEvent onDeathEvent) {
		// Create the projectile
		Entity e = new Entity();
		
		// Create hitbox
		HitboxComponent hitboxComponent = null;
		if(isPlayer) {
			hitboxComponent = new HitboxComponent(world, e, PROJECTILE_BODY_SOURCE, projectile.name, projectile.image.imageWidth, x, y, projectile.density, ENTITY.CATEGORY_PLAYER_PROJECTILE, ENTITY.MASK_PLAYER_PROJECTILE, antiLag);
		} else {
			hitboxComponent = new HitboxComponent(world, e, PROJECTILE_BODY_SOURCE, projectile.name, projectile.image.imageWidth, x, y, projectile.density, ENTITY.CATEGORY_ENEMY_PROJECTILE, ENTITY.MASK_ENEMY_PROJECTILE, antiLag);
		}
		
		// Add components
		e.add(hitboxComponent);
		e.add(new ActorComponent(projectile.name));
		e.add(new VisualComponent(new Sprite(projectile.image.sprite)));
		e.add(new ProjectileComponent(projectile, projectile.time));
		e.add(new OnDeathComponent(engine, world, this, e, onDeathEvent));
		
		// Rotate projectile
		hitboxComponent.body.setTransform(hitboxComponent.body.getPosition(), angle);
		
		// Set projectile speed
		hitboxComponent.body.setLinearVelocity(MathUtils.cos(angle) * projectile.speed, MathUtils.sin(angle) * projectile.speed);
		
		engine.addEntity(e);
	}
	
	/**
	 * Fire a projectile with an onDeath event
	 * @param projectile - projectile type
	 * @param speedChange - how much is added to speed of projectile
	 * @param x - x origin of projectile
	 * @param y - y origin of projectile
	 * @param time - time the projectile is alive
	 * @param angle - angle of fire in radians
	 * @param onDeathEvent - event when projectile is killed
	 */
	public void newProjectile(Projectile projectile, float x, float y, float time, float angle, boolean isPlayer, boolean antiLag, OnDeathEvent onDeathEvent) {
		// Create the projectile
		Entity e = new Entity();
		
		// Create hitbox
		HitboxComponent hitboxComponent = null;
		if(isPlayer) {
			hitboxComponent = new HitboxComponent(world, e, PROJECTILE_BODY_SOURCE, projectile.name, projectile.image.imageWidth, x, y, projectile.density, ENTITY.CATEGORY_PLAYER_PROJECTILE, ENTITY.MASK_PLAYER_PROJECTILE, antiLag);
		} else {
			hitboxComponent = new HitboxComponent(world, e, PROJECTILE_BODY_SOURCE, projectile.name, projectile.image.imageWidth, x, y, projectile.density, ENTITY.CATEGORY_ENEMY_PROJECTILE, ENTITY.MASK_ENEMY_PROJECTILE, antiLag);
		}
		
		// Add components
		e.add(hitboxComponent);
		e.add(new ActorComponent(projectile.name));
		e.add(new VisualComponent(new Sprite(projectile.image.sprite)));
		e.add(new ProjectileComponent(projectile, time));
		e.add(new OnDeathComponent(engine, world, this, e, onDeathEvent));
		
		// Rotate projectile
		hitboxComponent.body.setTransform(hitboxComponent.body.getPosition(), angle);
		
		// Set projectile speed
		hitboxComponent.body.setLinearVelocity(MathUtils.cos(angle) * projectile.speed, MathUtils.sin(angle) * projectile.speed);
		
		engine.addEntity(e);
	}
	
	/**
	 * Fire a projectile with an onExpire event
	 * @param projectile - projectile type
	 * @param speedChange - how much is added to speed of projectile
	 * @param x - x origin of projectile
	 * @param y - y origin of projectile
	 * @param angle - angle of fire in radians
	 * @param categoryBits
	 * @param maskBits
	 * @param onExpireEvent - the class in package onDeath with the desired onDeath event
	 */
	public void newProjectile(Projectile projectile, float x, float y, float angle, boolean isPlayer, boolean antiLag, OnDeathEvent onExpireEvent, boolean onExpireIdentifier) {
		// Create the projectile
		Entity e = new Entity();
		
		// Create hitbox
		HitboxComponent hitboxComponent = null;
		if(isPlayer) {
			hitboxComponent = new HitboxComponent(world, e, PROJECTILE_BODY_SOURCE, projectile.name, projectile.image.imageWidth, x, y, projectile.density, ENTITY.CATEGORY_PLAYER_PROJECTILE, ENTITY.MASK_PLAYER_PROJECTILE, antiLag);
		} else {
			hitboxComponent = new HitboxComponent(world, e, PROJECTILE_BODY_SOURCE, projectile.name, projectile.image.imageWidth, x, y, projectile.density, ENTITY.CATEGORY_ENEMY_PROJECTILE, ENTITY.MASK_ENEMY_PROJECTILE, antiLag);
		}
		
		// Add components
		e.add(hitboxComponent);
		e.add(new ActorComponent(projectile.name));
		e.add(new VisualComponent(new Sprite(projectile.image.sprite)));
		e.add(new ProjectileComponent(projectile, projectile.time));
		e.add(new OnExpireComponent(engine, world, this, e, onExpireEvent));
		
		// Rotate projectile
		hitboxComponent.body.setTransform(hitboxComponent.body.getPosition(), angle);
		
		// Set projectile speed
		hitboxComponent.body.setLinearVelocity(MathUtils.cos(angle) * projectile.speed, MathUtils.sin(angle) * projectile.speed);
		
		engine.addEntity(e);
	}
	
	/**
	 * Fire a projectile with an onExpire event
	 * @param projectile - projectile type
	 * @param speedChange - how much is added to speed of projectile
	 * @param x - x origin of projectile
	 * @param y - y origin of projectile
	 * @param time - time the projectile is alive
	 * @param angle - angle of fire in radians
	 * @param categoryBits
	 * @param maskBits
	 */
	public void newProjectile(Projectile projectile, float x, float y, float time, float angle, boolean isPlayer, boolean antiLag, OnDeathEvent onExpireEvent, boolean onExpireIdentifier) {
		// Create the projectile
		Entity e = new Entity();
		
		// Create hitbox
		HitboxComponent hitboxComponent = null;
		if(isPlayer) {
			hitboxComponent = new HitboxComponent(world, e, PROJECTILE_BODY_SOURCE, projectile.name, projectile.image.imageWidth, x, y, projectile.density, ENTITY.CATEGORY_PLAYER_PROJECTILE, ENTITY.MASK_PLAYER_PROJECTILE, antiLag);
		} else {
			hitboxComponent = new HitboxComponent(world, e, PROJECTILE_BODY_SOURCE, projectile.name, projectile.image.imageWidth, x, y, projectile.density, ENTITY.CATEGORY_ENEMY_PROJECTILE, ENTITY.MASK_ENEMY_PROJECTILE, antiLag);
		}
		
		// Add components
		e.add(hitboxComponent);
		e.add(new ActorComponent(projectile.name));
		e.add(new VisualComponent(new Sprite(projectile.image.sprite)));
		e.add(new ProjectileComponent(projectile, time));
		e.add(new OnExpireComponent(engine, world, this, e, onExpireEvent));
		
		// Rotate projectile
		hitboxComponent.body.setTransform(hitboxComponent.body.getPosition(), angle);
		
		// Set projectile speed
		hitboxComponent.body.setLinearVelocity(MathUtils.cos(angle) * projectile.speed, MathUtils.sin(angle) * projectile.speed);
		
		engine.addEntity(e);
	}
}
