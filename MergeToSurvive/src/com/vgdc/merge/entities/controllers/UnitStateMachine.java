package com.vgdc.merge.entities.controllers;

import com.vgdc.merge.entities.Entity;
import com.vgdc.merge.entities.UnitStateEnum;

public class UnitStateMachine {
	
	public boolean movedRecently;
	
	private Entity entity;
	
	public void setEntity(Entity entity)
	{
		this.entity = entity;
	}
	
	public void affectState(float delta)
	{
		if(movedRecently)
		{
			if(entity.getState()==UnitStateEnum.IDLE.value)
				entity.setState(UnitStateEnum.MOVE.value);
		}
		else
		{
			if(entity.getState()!=UnitStateEnum.IDLE.value)
				entity.setState(UnitStateEnum.IDLE.value);
		}
		movedRecently = false;
	}

}
