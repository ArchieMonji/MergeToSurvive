package com.vgdc.merge;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;

public class PlayerController extends UnitController {
	
	private Controls controls;
	
	private boolean fired = false;
	private boolean toggled = false;
	
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
			getEntity().jump(delta);
			System.out.println("jump!");
		}
		if(Gdx.input.isKeyPressed(controls.left))
		{
			//this is test, change later
			moveLeft(delta);
		}
		if(Gdx.input.isKeyPressed(controls.right))
		{
			//this is test, change later
			moveRight(delta);
		}
		if(Gdx.input.isButtonPressed(controls.useAbility))
		{
			if(!fired)
			{
				getEntity().getAbilities().get(0).onUse(getEntity());
				fired = true;
			}
		}
		else if(fired)
		{
			fired = false;
		}
		if(Gdx.input.isButtonPressed(controls.toggleAbility))
		{
			if(!toggled)
			{
				Entity e = getEntity();
				ArrayList<Ability> abilities = e.getAbilities();
				if(abilities.size()>=2&&abilities.get(1)!=null)
				{
					Ability ability = e.getAbilities().get(0);
					e.getAbilities().set(0, e.getAbilities().get(1));
					e.getAbilities().set(1, ability);
				}
				toggled = true;
			}
		}
		else if(toggled)
		{
			toggled = false;
		}
		stateMachine.affectState(delta);
	}

	@Override
	public Controller copy() {
		PlayerController controller = new PlayerController();
		controller.setControls(controls);
		return controller;
	}

}
