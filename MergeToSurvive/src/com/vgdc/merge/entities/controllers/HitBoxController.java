package com.vgdc.merge.entities.controllers;

import com.badlogic.gdx.math.Vector2;
import com.vgdc.merge.entities.Entity;
import com.vgdc.merge.math.VectorMath;

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
		this.knockback = new Vector2(0, 0);
	}
	
	public HitBoxController(float duration, float x, float y)
	{
		this.duration = duration;
		this.knockback = new Vector2(x, y);
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
	
	@Override
	public void onEntityCollision(Entity entity)
	{
		super.onEntityCollision(entity);
		if(entity.getTeam()==getEntity().getTeam())
			return;
		//float x = entity.getPosition().x - getEntity().getPosition().x;
		//entity.getMovingBody().setVelocity(new Vector2((x>0 ? 1 : -1)*knockback.x, knockback.y));
		entity.getMovingBody().setVelocity(VectorMath.add(entity.getMovingBody().getVelocity(),this.getEntity().facingLeft() ? new Vector2(-15,10) : new Vector2(15,10)));
		onDeath();
	}

	@Override
	public void onCreate() {
		
	}

}
