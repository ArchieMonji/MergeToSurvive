package com.vgdc.merge.assets;

import java.util.ArrayList;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.OrderedMap;
import com.vgdc.merge.entities.BaseEntityData;
import com.vgdc.merge.entities.EntityData;
import com.vgdc.merge.entities.audio.BaseSoundFx;

public class EntityLoadData extends BaseEntityData{
	
	public String controller;
	public ArrayList<String> abilities;
	public ArrayList<String> animations;
	public OrderedMap<String, Array<String>> sounds;
	
	public EntityData convert(AssetsHandler handler)
	{
		return null;
	}

}
