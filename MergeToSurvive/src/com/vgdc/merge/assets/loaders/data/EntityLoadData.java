package com.vgdc.merge.assets.loaders.data;

import java.util.ArrayList;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.OrderedMap;
import com.vgdc.merge.assets.AssetsHandler;
import com.vgdc.merge.entities.BaseEntityData;
import com.vgdc.merge.entities.EntityData;

public class EntityLoadData extends BaseEntityData{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String controller;
	public ArrayList<String> abilities;
	public ArrayList<String> animations;
	public OrderedMap<String, Array<String>> sounds;
	
	public EntityData convert(AssetsHandler handler)
	{
		return null;
	}

}
