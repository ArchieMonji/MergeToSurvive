package com.vgdc.merge.assets.loaders.data;

import java.util.ArrayList;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.OrderedMap;
import com.vgdc.merge.entities.BaseEntityData;
import com.vgdc.merge.entities.abilities.Ability;
import com.vgdc.merge.entities.controllers.Controller;

public class EntityLoadData extends BaseEntityData{
	
	/**
	 * 
	 */
	public Controller controller;
	public OrderedMap<String, Object> scontroller;
	
	public ArrayList<Ability> abilities;
	public ArrayList<OrderedMap<String, Object>> sabilities;
	
	public ArrayList<String> animations;
	public OrderedMap<String, Array<String>> sounds;

}
