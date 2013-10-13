package com.vgdc.merge;

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

}
