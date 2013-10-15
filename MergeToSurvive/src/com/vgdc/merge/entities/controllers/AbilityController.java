package com.vgdc.merge.entities.controllers;

public abstract class AbilityController extends Controller{
	
	public void onDeath(){
		getEntity().getWorld().getEntityManager().removeEntity(getEntity());
	}

}
