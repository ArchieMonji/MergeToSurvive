package com.vgdc.merge;

import com.badlogic.gdx.Gdx;

public class PlayerController extends Controller {
	
	private Controls controls;
	
	public PlayerController()
	{
		
	}
	
	public PlayerController(Controls controls)
	{
		this.controls = controls;
	}
	
	public Controls getControls()
	{
		return controls;
	}
	
	public void setControls(Controls nControls)
	{
		controls = nControls;
	}

	@Override
	public void onUpdate(float delta) {
		if(Gdx.input.isKeyPressed(controls.down))
		{
			//this is test, change later
			System.out.println("down!");
		}
		if(Gdx.input.isKeyPressed(controls.up))
		{
			//this is test, change later
			getEntity().jump();
			System.out.println("jump!");
		}
		if(Gdx.input.isKeyPressed(controls.left))
		{
			//this is test, change later
			getEntity().moveLeft();
			System.out.println("new location " + getEntity().getPosition());
		}
		if(Gdx.input.isKeyPressed(controls.right))
		{
			//this is test, change later
			getEntity().moveRight();
			System.out.println("new location " + getEntity().getPosition());
		}
	}

	@Override
	public Controller copy() {
		PlayerController controller = new PlayerController();
		controller.setControls(controls);
		return controller;
	}

}
