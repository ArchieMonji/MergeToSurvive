package com.vgdc.merge.entities.controllers;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.vgdc.merge.entities.Entity;
import com.vgdc.merge.entities.EntityType;
import com.vgdc.merge.entities.Platform;
import com.vgdc.merge.entities.abilities.Ability;
import com.vgdc.merge.entities.physics.MovingBody;
import com.vgdc.merge.entities.rendering.Renderer;

public class ItemController extends Controller {

	private Vector2 spawnLocation;
	private static float floatRange = 8;
	private boolean isRelocating;

	@Override
	public Controller copy() {
		return new ItemController();
	}

	@Override
	public void onUpdate(float delta) {
		Entity e = getEntity();
		MovingBody body = e.getMovingBody();
		if (isRelocating && body.getVelocity().len() < 1 && body.checkTouchingGround()) {
			setRelocating(false);
			getEntity().getMovingBody().setVelocity(Vector2.Zero);
		}
		if (!isRelocating) {
			if (spawnLocation == null) {
				onCreate();
			}

			time += delta / rotTime;
			// System.out.println(body.getVelocity());
			// decrement lifespan

			if (time < 0.5f)
				body.getPosition().y = spawnLocation.y
						+ cosineInterpolation(0, floatRange, time * 2);
			else if (time < 1)
				body.getPosition().y = spawnLocation.y
						+ cosineInterpolation(floatRange, 0, (time - 0.5f) * 2);
			// else if(time < 1.5f)
			// body.getPosition().y = spawnLocation.y -
			// cosineInterpolation(0,15,(time-1)*2);
			// else if(time < 2)
			// body.getPosition().y = spawnLocation.y -
			// cosineInterpolation(15,0,(time-1.5f)*2);

			Renderer r = (Renderer) e.getRenderer();
			float angle = r.getRotation();
			if (time < 1) {
				r.setRotation(Cubic_Interpolate(100, 0, 360, 100, time));
			}
			// } else {
			// r.setRotation(cosineInterpolation(180, 360, time - 1));
			// }

			if (time > 1) {
				r.setRotation(0);
				time = 0;
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

	float rotTime = 1;
	float time;

	float cosineInterpolation(float a, float b, float t) {
		float t2 = (1 - MathUtils.cos(t * MathUtils.PI)) * 0.5f;
		return (a * (1 - t2) + b * t2);
	}

	Vector2 cubicInterpolation(Vector2 v0, Vector2 v1, Vector2 v2, Vector2 v3,
			float x) {
		Vector2 P = (v3.sub(v2)).sub(v0.sub(v1));
		Vector2 Q = (v0.sub(v1)).sub(P);
		Vector2 R = v2.sub(v0);
		Vector2 S = v1;

		return P.mul(x * x * x).add(Q.mul(x * x)).add(R.mul(x)).add(S);
	}

	float Cubic_Interpolate(float v0, float v1, float v2, float v3, float x) {
		float P = (v3 - v2) - (v0 - v1);
		float Q = (v0 - v1) - P;
		float R = v2 - v0;
		float S = v1;

		return P * x * x * x + Q * x * x + R * x + S;
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
	
	@Override
	public void onCreate() {
		MovingBody body = getEntity().getMovingBody();
		spawnLocation = new Vector2(body.getPosition());
		body.setAcceleration(Vector2.Zero);
		body.setElasticity(0);
		body.setCollidableWith(EntityType.Entity, true);
		body.setCollidableWith(EntityType.Platform, false);
		body.setCollidableWith(EntityType.Projectile, false);
		body.setCollidableWith(EntityType.Item, false);
	}

	public void setRelocating(boolean isRelocating) {
		this.isRelocating = isRelocating;
	}

}
