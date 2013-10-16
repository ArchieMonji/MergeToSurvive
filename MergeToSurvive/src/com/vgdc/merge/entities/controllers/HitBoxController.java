package com.vgdc.merge.entities.controllers;

import com.badlogic.gdx.math.Vector2;
import com.vgdc.merge.entities.Entity;

public class HitBoxController extends AbilityController {
	
	private float duration;
	private Vector2 knockback;
	
	public HitBoxController()
	{
		this(0.5f);
	}
	
	public HitBoxController(float duration)
	{
		this.duration = duration;
	}

	@Override
	public Controller copy() {
		HitBoxController controller = new HitBoxController();
		controller.duration = this.duration;
		controller.knockback = knockback;
		return controller;
	}

	@Override
	public void onUpdate(float delta) {
		duration-=delta;
		if(duration<=0)
			onDeath();

	}
	
	public void onEntityCollision(Entity entity)
	{
		super.onEntityCollision(entity);
		if(entity.getTeam()==getEntity().getTeam())
			return;
		float x = entity.getPosition().x - getEntity().getPosition().x;
		entity.getMovingBody().setVelocity(new Vector2((x>0 ? 1 : -1)*knockback.x, knockback.y));
		onDeath();
	}

}
