package com.vgdc.merge.entities.controllers;

import com.vgdc.merge.entities.Entity;

public abstract class UnitController extends Controller{
	
	protected UnitStateMachine stateMachine = new UnitStateMachine();
	
	public void setEntity(Entity nEntity)
	{
		super.setEntity(nEntity);
		stateMachine.setEntity(nEntity);
	}
	
	public void moveLeft(float delta)
	{
		getEntity().moveLeft(delta);
		stateMachine.movedRecently = true;
	}
	
	public void moveRight(float delta)
	{
		getEntity().moveRight(delta);
		stateMachine.movedRecently = true;
	}

	public void tryJump(float delta){
		getEntity().tryJump(delta);
	}
	
	public void onEntityCollision(Entity entity){
		if(entity.getTeam()!=getEntity().getTeam()&&entity.getDamage()>0)
		{
			onDamage(entity);
		}
	}
	
	public void onDamage(Entity entity){
		getEntity().damage(entity.getDamage());
		if(getEntity().isDead())
			System.out.println("i am dead");
	}
}
