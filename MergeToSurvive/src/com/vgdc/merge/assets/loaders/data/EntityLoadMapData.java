package com.vgdc.merge.assets.loaders.data;

import java.util.HashMap;

import org.python.core.PyObject;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.OrderedMap;

public class EntityLoadMapData {
	
	public String controller;
	public Array<String> abilities;
	public Array<String> animations;
	public OrderedMap<String, Array<String>> sounds;
	
	public HashMap<String, PyObject> dynamicVariables = new HashMap<String, PyObject>();

}
