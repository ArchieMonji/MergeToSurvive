package com.vgdc.merge.entities.controllers;

import com.badlogic.gdx.math.Vector2;
import com.vgdc.merge.entities.BaseEntity;
import com.vgdc.merge.entities.Entity;
import com.vgdc.merge.entities.abilities.Ability;
import com.vgdc.merge.entities.physics.MovingBody;

public class ItemController extends Controller {
	
	Vector2 spawnLocation;
	float floatRange = 15;
	
	@Override
	public Controller copy() {
		return new ItemController();
	}

	@Override
	public void onUpdate(float delta) {
		if(spawnLocation == null){
			spawnLocation = new Vector2(getEntity().getPosition());
			getEntity().getMovingBody().setAcceleration(Vector2.Zero);
			getEntity().getMovingBody().setVelocity(new Vector2(0,getEntity().getMoveSpeed()));
		}

		//System.out.println(body.getVelocity());
		// decrement lifespan
		if(Math.abs(getEntity().getPosition().y - spawnLocation.y) > floatRange){
			MovingBody body = getEntity().getMovingBody();
			body = getEntity().getMovingBody();
			body.setVelocity(body.getVelocity().mul(0, -1));
		}
	}
	
	@Override
	public void onEntityCollision(Entity entity) {
		if(entity.getTeam() == BaseEntity.PLAYER_TEAM){
			System.out.println("Item picked up");
			//Ability ability = getEntity().getAbilities().get(0);
			//entity.getAbilities().set(0,ability);
			getEntity().getWorld().getEntityManager().removeEntity(getEntity());
		}
	}

}
