package com.vgdc.merge.entities.controllers;

public class HitBoxController extends AbilityController {
	
	private float duration;
	
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
		return controller;
	}

	@Override
	public void onUpdate(float delta) {
		duration-=delta;
		if(duration<=0)
			onDeath();

	}

}
