package com.vgdc.merge.events;

public abstract class Event {
	private EventSystem eventSystem;
	private String name;
	
	public Event(String name){
		this.name = name;
	}
	
	public abstract void onTrigger();
	public abstract void onUpdate(float delta);
	public abstract boolean checkConditions();
	
	public EventSystem getEventSystem() {
		return eventSystem;
	}
	
	public void setEventSystem(EventSystem eventSystem) {
		this.eventSystem = eventSystem;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
}
