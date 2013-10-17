package com.vgdc.merge.entities.controllers;

import com.badlogic.gdx.math.Vector2;
import com.vgdc.merge.entities.Entity;
import com.vgdc.merge.entities.EntityType;
import com.vgdc.merge.entities.abilities.Ability;
import com.vgdc.merge.entities.physics.MovingBody;

public class ItemController extends Controller {

	private Vector2 spawnLocation;
	private float floatRange = 15;

	@Override
	public Controller copy() {
		return new ItemController();
	}

	@Override
	public void onUpdate(float delta) {
		Entity e = getEntity();

		if (spawnLocation == null) {
			spawnLocation = new Vector2(e.getPosition());
			e.getMovingBody().setAcceleration(Vector2.Zero);
			e.getMovingBody().setVelocity(
					new Vector2(0, getEntity().getMoveSpeed()));
			
			e.getMovingBody().setCollidableWith(EntityType.Entity, true);
			e.getMovingBody().setCollidableWith(EntityType.Platform, false);
			e.getMovingBody().setCollidableWith(EntityType.Projectile, false);
			e.getMovingBody().setCollidableWith(EntityType.Item, false);
		}

		// System.out.println(body.getVelocity());
		// decrement lifespan
		MovingBody body = e.getMovingBody();
		if (body.getVelocity().y > 0) {
			if (body.getPosition().y - spawnLocation.y > floatRange) {
				body.setVelocity(body.getVelocity().mul(0, -1));
			}
		} else {
			if (body.getPosition().y - spawnLocation.y < -floatRange) {
				body.setVelocity(body.getVelocity().mul(0, -1));
			}
		}

		/**
		 * bug where if the position is outside the floatRange, it will
		 * oscillate in place since the y velocity is being reversed every frame
		 * if(Math.abs(e.getPosition().y - spawnLocation.y) > floatRange){
		 * MovingBody body = e.getMovingBody();
		 * body.setVelocity(body.getVelocity().mul(0, -1)); }
		 **/
	}

	@Override
	public void onEntityCollision(Entity entity) {
		if (entity.canMerge()) {
			System.out.println("Item picked up");
			Ability ability = getEntity().getAbilities().get(0);
			entity.getAbilities().set(0, ability);
			getEntity().getWorld().getEntityManager().removeEntity(getEntity());
		}
	}

}
