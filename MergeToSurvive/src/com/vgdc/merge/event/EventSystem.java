package com.vgdc.merge.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import com.vgdc.merge.world.World;

public class EventSystem {
	private ArrayList<Event> events = new ArrayList<Event>();
	private HashMap<String, Event> eventMap = new HashMap<String, Event>();
	private World world;

	public EventSystem(World world) {
		this.world = world;
	}

	public void loadEvents() {
		// TODO: JSON? PYTHON? JYTHONON!
		throw new NotImplementedException();
	}
	
	public void addEvent(Event event){
		event.setEventSystem(this);
		events.add(event);
	}

	public void onUpdate(float delta){
		for(Iterator<Event> i = events.iterator(); i.hasNext();){
			Event event = i.next();
			event.onUpdate(delta);
			if(event.checkConditions()){
				System.out.println("Triggering: " + event.getName());
				event.onTrigger();
				i.remove();
			}
		}
		/**
		 * Optimized update and remove, does not preserve object ordering
		 * O(n^2) -> O(n)
		System.out.println(events.size());
		for(int i = 0; i < events.size();){
			Event event = events.get(i);
			event.onUpdate(delta);
			if(event.checkConditions()){
				System.out.println("Triggering: " + event.getName());
				event.onTrigger();
				events.set(i, events.get(events.size() - 1));
				events.remove(events.size() - 1);
			}
			else{
				i++;
			}
		}
		**/
	}

	public void trigger(Event event) {
		event.setEventSystem(this);
		event.onTrigger();
	}

	public void trigger(String eventName) {
		Event event = eventMap.get(eventName);
		System.out.println("Triggering: " + event.getName());
		event.setEventSystem(this);
		event.onTrigger();
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public World getWorld() {
		return world;
	}
}
