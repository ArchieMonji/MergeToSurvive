package com.vgdc.merge;

public abstract class Controller{
	
	private Entity entity;
	
	public Controller()
	{
		
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
	
	public void onCollision(Entity entity)
	{
		
	}
	
	
}
