package com.vgdc.merge.entities.controllers;

import com.badlogic.gdx.math.Vector2;
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
	
	public void onDeath()
	{
		getEntity().getWorld().getEntityManager().removeEntity(getEntity());
	}
	
	public Entity getEntity()
	{
		return entity;
	}
	
	public abstract Controller copy();
	
	public abstract void onCreate();
	
	public abstract void onUpdate(float delta);
	
	public void setPosition(float x, float y){
		entity.setPosition(new Vector2(x, y));
	}
	
	public Vector2 getPosition(){
		return entity.getPosition();
	}
	
	public void setAcceleration(float x, float y){
		entity.getMovingBody().setVelocity(new Vector2(x, y));
	}
	
	public Vector2 getAcceleration(){
		return entity.getMovingBody().getAcceleration();
	}
	
	public void setVelocity(float x, float y){
		entity.getMovingBody().setVelocity(new Vector2(x, y));
	}
	
	public Vector2 getVelocity(){
		return entity.getMovingBody().getVelocity();
	}
	
	public void onEntityCollision(Entity entity){
	}
	public void onPlatformCollision(Platform platform){
		
	}
	
	
}
