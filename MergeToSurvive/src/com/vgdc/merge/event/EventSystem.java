package com.vgdc.merge.event;

import java.util.HashMap;

import com.vgdc.merge.world.World;

public class EventSystem {
	private HashMap<String,Event> eventMap = new HashMap<String, Event>();
	private World world;
	
	public EventSystem(World world) {
		this.world = world;
	}
	
	public void loadEvents(){
		//TODO: JSON? PYTHON? JYTHONON!
	}

	public void trigger(Event event) {
		System.out.println("Triggering: " + event.getName());
		event.onEvent();
	}
	
	public void trigger(String eventName){
		Event event = eventMap.get(eventName);
		System.out.println("Triggering: " + event.getName());
		event.onEvent();
	}

	public void setWorld(World world) {
		this.world = world;
	}
	
	public World getWorld() {
		return world;
	}

}
