package com.vgdc.merge.events;

import com.vgdc.merge.world.World;

public abstract class Event {
	private World world;
	private String name;
	
	public Event(String name){
		this.name = name;
	}
	
	public abstract void onTrigger();
	public abstract void onUpdate(float delta);
	public abstract boolean checkConditions();
	
	public World getWorld() {
		return world;
	}
	
	public void setWorld(World world) {
		this.world = world;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
}
