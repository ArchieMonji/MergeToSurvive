package com.vgdc.merge.entities.controllers;

import com.vgdc.merge.entities.Entity;
import com.vgdc.merge.entities.EntityType;
import com.vgdc.merge.entities.Platform;

public class ProjectileController extends AbilityController{
	
	private float timeAlive = 1;
	
	@Override
	public void setEntity(Entity entity)
	{
		super.setEntity(entity);
	}

	@Override
	public Controller copy() {
		ProjectileController controller = new ProjectileController();
		controller.timeAlive = timeAlive;
		return controller;
	}

	@Override
	public void onUpdate(float delta) {
		timeAlive-=delta;
		if(timeAlive<=0)
		{
			System.out.println("Death by Time-Out!");
			if(!getEntity().isDead()){
				onDeath();
			}
		}
//		if(getEntity().getRenderer().isFlipped())
//			getEntity().moveLeft(delta);
//		else
//			getEntity().moveRight(delta);
	}
	
	@Override
	public void onPlatformCollision(Platform platform){
		if(!getEntity().isDead()){
			System.out.println("Platform Collision!");
			onDeath();
		}
	}

	@Override
	public void onEntityCollision(Entity entity){
		if(entity.getTeam()!=getEntity().getTeam()){
			System.out.println("Entity Collision!");
			if(!getEntity().isDead()){
				onDeath();
			}
		}
	}

	@Override
	public void onCreate() {
		
	}

}
