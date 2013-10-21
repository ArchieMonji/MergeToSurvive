package com.vgdc.merge.entities.controllers;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.vgdc.merge.entities.Entity;
import com.vgdc.merge.entities.abilities.Ability;

public class PlayerController extends UnitController {
	
	private Controls controls;
	private float invulnerableTime = 1f;
	private int numFlashes = 6;
	private float alpha = 0.5f;
	
	private float alphaToggle;
	private int num;
	
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
		Entity e = getEntity();
		if(Gdx.input.isKeyPressed(controls.down))
		{
			
		}
		if(Gdx.input.isKeyPressed(controls.up))
		{
			tryJump(delta);
		}
		if(Gdx.input.isKeyPressed(controls.left))
		{
			moveLeft(delta);
		}
		if(Gdx.input.isKeyPressed(controls.right))
		{
			moveRight(delta);
		}
		if(Gdx.input.isButtonPressed(controls.useAbility))
		{
			if(!fired)
			{
				ArrayList<Ability> abilities = e.getAbilities();
				if(abilities.size()>0&&abilities.get(0)!=null)
				{
					abilities.get(0).onUse(getEntity(), getEntity().canMerge());
					abilities.set(0, null);
				}
				else
				{
					e.getData().defaultAbilities.get(0).onUse(e);
				}
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
		if(alphaToggle>0)
		{
			alphaToggle-=delta;
			if(alphaToggle<=0&&num>0)
			{
				alphaToggle+=invulnerableTime/numFlashes/2;
				Color c = e.getRenderer().getColor();
				if(c.a!=1.0f)
				{
					e.getRenderer().setColor(c.r, c.g, c.b, 1.0f);
					num--;
				}
				else
				{
					e.getRenderer().setColor(c.r, c.g, c.b, alpha);
				}
			}
		}
	}
	
	
	public void onDamage(Entity entity)
	{
		if(alphaToggle>0)
			return;
		super.onDamage(entity);
		alphaToggle = invulnerableTime/numFlashes/2;
		num = numFlashes;
	}

	@Override
	public Controller copy() {
		PlayerController controller = new PlayerController();
		controller.setControls(controls);
		controller.invulnerableTime = invulnerableTime;
		controller.numFlashes = numFlashes;
		controller.alpha = alpha;
		return controller;
	}

	@Override
	public void onCreate() {
		
	}

}
