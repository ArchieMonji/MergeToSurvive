package com.vgdc.merge.test;

import com.vgdc.merge.Controller;
import com.vgdc.merge.Entity;

public class TestProjectileController extends Controller{
	
	private float timeAlive = 1;
	
	@Override
	public void setEntity(Entity entity)
	{
		super.setEntity(entity);
	}

	@Override
	public Controller copy() {
		return new TestProjectileController();
	}

	@Override
	public void onUpdate(float delta) {
		timeAlive-=delta;
		if(timeAlive<=0)
		{
			getEntity().getWorld().getEntityManager().removeEntity(getEntity());
		}
		if(getEntity().getRenderer().isFlipped())
			getEntity().moveLeft(delta);
		else
			getEntity().moveRight(delta);
	}

}
