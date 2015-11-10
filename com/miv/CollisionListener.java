package com.miv;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

import components.ProjectileComponent;
import components.ShieldComponent;
import components.DoorComponent;
import components.HealthComponent;
import components.HitboxComponent.UserData;
import components.PlayerComponent;

public class CollisionListener implements ContactListener {
	
	private ComponentMapper<ProjectileComponent> prm = ComponentMapper.getFor(ProjectileComponent.class);
	private ComponentMapper<HealthComponent> hm = ComponentMapper.getFor(HealthComponent.class);
	private ComponentMapper<ShieldComponent> sm = ComponentMapper.getFor(ShieldComponent.class);
	private ComponentMapper<PlayerComponent> pm = ComponentMapper.getFor(PlayerComponent.class);
	private ComponentMapper<DoorComponent> dm = ComponentMapper.getFor(DoorComponent.class);
	
	private Main main;
	
	public CollisionListener(Main main) {
		this.main = main;
	}
	
	@Override
	public void beginContact(Contact contact) {
		
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		UserData userDataA = (UserData) contact.getFixtureA().getBody().getUserData();
		UserData userDataB = (UserData) contact.getFixtureB().getBody().getUserData();
		
		if(userDataA != null && userDataB != null) {
			// Player makes contact with a door
			// userDataA.e is the door
			if(pm.has(userDataB.e) && dm.has(userDataA.e)) {
				main.queueMapLoad(dm.get(userDataA.e).leadsTo);
			}
			// userDataB.e is the door
			else if(pm.has(userDataA.e) && dm.has(userDataB.e)) {
				main.queueMapLoad(dm.get(userDataB.e).leadsTo);
			}
			
			// userDataA.e is the projectile so userDataB.e takes damage
			else if(userDataA != null && prm.has(userDataA.e)) {
				if(hm.has(userDataB.e)) {
					if(prm.get(userDataA.e).cannotHit == null || !prm.get(userDataA.e).cannotHit.equals(userDataB.e)) {
						if(sm.has(userDataB.e) && sm.get(userDataB.e).shieldActivated && !sm.get(userDataB.e).shieldBroken) {
							sm.get(userDataB.e).takeDamage(prm.get(userDataA.e).type.damage);
						} else {
							hm.get(userDataB.e).takeDamage(prm.get(userDataA.e).type.damage, userDataB);
						}
						prm.get(userDataA.e).cannotHit = userDataB.e;
					}
					contact.setEnabled(false);
					userDataA.queuedForDestruction = true;
				} else if(prm.has(userDataA.e) && prm.has(userDataB.e)) {
					contact.setEnabled(false);
				} else {
					userDataA.queuedForDestruction = true;
				}
			}
			// userDataB.e is the projectile so userDataA.e takes damage
			else if(userDataB != null && prm.has(userDataB.e)) {
				if(hm.has(userDataA.e)) {
					if(prm.get(userDataB.e).cannotHit == null || !prm.get(userDataB.e).cannotHit.equals(userDataA.e)) {
						if(sm.has(userDataA.e) && sm.get(userDataA.e).shieldActivated && !sm.get(userDataA.e).shieldBroken) {
							sm.get(userDataA.e).takeDamage(prm.get(userDataB.e).type.damage);
						} else {
							hm.get(userDataA.e).takeDamage(prm.get(userDataB.e).type.damage, userDataA);
						}
						prm.get(userDataB.e).cannotHit = userDataA.e;
					}
					contact.setEnabled(false);
					userDataB.queuedForDestruction = true;
				} else if(prm.has(userDataA.e) && prm.has(userDataB.e)) {
					contact.setEnabled(false);
				} else {
					userDataB.queuedForDestruction = true;
				}
			}
		}
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		
	}

}
