package com.vgdc.merge.entities.controllers;

import com.vgdc.merge.entities.Entity;
import com.vgdc.merge.entities.Platform;

public class ProjectileController extends Controller{
	
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
			getEntity().getWorld().getEntityManager().removeEntity(getEntity());
		}
//		if(getEntity().getRenderer().isFlipped())
//			getEntity().moveLeft(delta);
//		else
//			getEntity().moveRight(delta);
	}
	
	public void onPlatformCollision(Platform platform){
		System.out.println("Collision!");
		getEntity().getWorld().getEntityManager().removeEntity(getEntity());
	}
	
	public void onEntityCollision(Entity entity){
		if(entity.getTeam()!=getEntity().getTeam())
			getEntity().getWorld().getEntityManager().removeEntity(getEntity());
	}

}
