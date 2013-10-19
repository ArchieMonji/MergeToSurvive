package com.vgdc.merge.entities.controllers;

import java.util.ArrayList;

import com.vgdc.merge.entities.Entity;
import com.vgdc.merge.entities.abilities.Ability;

public abstract class UnitController extends Controller{
	
	public UnitStateMachine stateMachine = new UnitStateMachine();
	
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

	@Override
	public void onEntityCollision(Entity entity){
		if(entity.getTeam()!=getEntity().getTeam()&&entity.getDamage()>0)
		{
			onDamage(entity);
		}
	}
	
	public void onUpdate(float delta)
	{
		stateMachine.affectState(delta);
	}
	
	public void onDamage(Entity entity){
		getEntity().damage(entity.getDamage());
		System.out.println(getEntity().getHealth());
	}
	
	public boolean useAbility(int loc)
	{
		return useAbility(loc, false);
	}
	
	public boolean useAbility(int loc, boolean drop)
	{
		ArrayList<Ability> abilities = getEntity().getAbilities();
		if(abilities.size()>loc&&abilities.get(loc)!=null)
		{
			abilities.get(loc).onUse(getEntity(), drop);
			if(drop)
				abilities.set(loc, null);
			return true;
		}
		return false;
	}
	
	public void useDefault(int loc)
	{
		getEntity().getData().defaultAbilities.get(loc).onUse(getEntity());
	}
}
