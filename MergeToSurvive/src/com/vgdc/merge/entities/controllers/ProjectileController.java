package com.vgdc.merge.entities.controllers;

import com.vgdc.merge.entities.Entity;

public class ProjectileController extends Controller{
	
	private float timeAlive = 1;
	
	@Override
	public void setEntity(Entity entity)
	{
		super.setEntity(entity);
	}

	@Override
	public Controller copy() {
		return new ProjectileController();
	}

	@Override
	public void onUpdate(float delta) {
		timeAlive-=delta;
		if(timeAlive<=0)
		{
			getEntity().getWorld().getEntityManager().removeEntity(getEntity());
		}
		if(getEntity().getRenderer().isFlipped())
			getEntity().moveLeft(delta);
		else
			getEntity().moveRight(delta);
	}

}
