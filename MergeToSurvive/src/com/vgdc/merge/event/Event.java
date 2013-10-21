package com.vgdc.merge.event;

public abstract class Event {
	private EventSystem eventSystem;
	private String name;
	
	public Event(String name, EventSystem eventSystem){
		this.eventSystem = eventSystem;
		this.name = name;
	}
	
	public Event(EventSystem eventSystem){
		this.eventSystem = eventSystem;
	}
	
	public EventSystem getEventSystem() {
		return eventSystem;
	}
	
	public void setEventSystem(EventSystem eventSystem) {
		this.eventSystem = eventSystem;
	}
	
	public abstract void onEvent();

	public String getName() {
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
}
