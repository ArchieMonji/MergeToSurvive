package com.vgdc.merge.entities.controllers;

import com.vgdc.merge.entities.Entity;
import com.vgdc.merge.entities.Platform;

public abstract class Controller{
	
	private Entity entity;
	
	public Controller(){
	}
	
	public void setEntity(Entity nEntity)
	{
		entity = nEntity;
	}
	
	public Entity getEntity()
	{
		return entity;
	}
	
	public abstract Controller copy();
	
	public abstract void onUpdate(float delta);
	
	public void onEntityCollision(Entity entity){
	}
	public void onPlatformCollision(Platform platform){
		
	}
}
