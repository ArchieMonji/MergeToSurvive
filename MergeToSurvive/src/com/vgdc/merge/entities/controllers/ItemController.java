package com.vgdc.merge.entities.controllers;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.vgdc.merge.entities.Entity;
import com.vgdc.merge.entities.EntityType;
import com.vgdc.merge.entities.abilities.Ability;
import com.vgdc.merge.entities.physics.MovingBody;
import com.vgdc.merge.entities.rendering.Renderer;
import com.vgdc.merge.math.VectorMath;

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

			e.getMovingBody().setCollidableWith(EntityType.Entity, true);
			e.getMovingBody().setCollidableWith(EntityType.Platform, false);
			e.getMovingBody().setCollidableWith(EntityType.Projectile, false);
			e.getMovingBody().setCollidableWith(EntityType.Item, false);
		}


		time += delta/rotTime;
		// System.out.println(body.getVelocity());
		// decrement lifespan
		MovingBody body = e.getMovingBody();

		if(time < 0.5f)
			body.getPosition().y = spawnLocation.y + cosineInterpolation(0,15,time*2);
		else if(time < 1)
			body.getPosition().y = spawnLocation.y + cosineInterpolation(15,0,(time-0.5f)*2);
//		else if(time < 1.5f)
//			body.getPosition().y = spawnLocation.y - cosineInterpolation(0,15,(time-1)*2);
//		else if(time < 2)
//			body.getPosition().y = spawnLocation.y - cosineInterpolation(15,0,(time-1.5f)*2);
		
		Renderer r = (Renderer) e.getRenderer();
		float angle = r.getRotation();
		if (time < 1) {
			r.setRotation(cosineInterpolation(0, 360, time));
		}
//		} else {
//			r.setRotation(cosineInterpolation(180, 360, time - 1));
//		}

		if (time > 1) {
			r.setRotation(0);
			time = 0;
		}

		/**
		 * bug where if the position is outside the floatRange, it will
		 * oscillate in place since the y velocity is being reversed every frame
		 * if(Math.abs(e.getPosition().y - spawnLocation.y) > floatRange){
		 * MovingBody body = e.getMovingBody();
		 * body.setVelocity(body.getVelocity().mul(0, -1)); }
		 **/
	}
	float rotTime = 1.5f;
	float time;

	float cosineInterpolation(float a, float b, float t) {
		float t2 = (1 - MathUtils.cos(t * MathUtils.PI)) / 2;
		return (a * (1 - t2) + b * t2);
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
