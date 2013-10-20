package com.vgdc.merge.entities.controllers;

import com.vgdc.merge.entities.Entity;
import com.vgdc.merge.entities.Platform;

public class ProjectileController extends AbilityController{
	
	public ProjectileController(float timeAlive)
	{
		this.timeAlive = timeAlive;
	}
	
	private float timeAlive;
	
	@Override
	public void setEntity(Entity entity)
	{
		super.setEntity(entity);
	}

	@Override
	public Controller copy() {
		ProjectileController controller = new ProjectileController(timeAlive);
		return controller;
	}

	@Override
	public void onUpdate(float delta) {
		timeAlive-=delta;
		if(timeAlive<=0)
		{
			onDeath();
		}
	}
	
	@Override
	public void onPlatformCollision(Platform platform){
		System.out.println("Collision!");
		onDeath();
	}

	@Override
	public void onEntityCollision(Entity entity){
		if(entity.getTeam()!=getEntity().getTeam())
			onDeath();
	}

}
