package com.vgdc.merge.assets.loaders.data;

import java.util.ArrayList;
import java.util.HashMap;

import org.python.core.PyObject;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.OrderedMap;

public class EntityLoadMapData {
	
	public String controller;
	public ArrayList<String> abilities;
	public ArrayList<String> animations;
	public OrderedMap<String, Array<String>> sounds;
	
	public HashMap<String, PyObject> dynamicVariables;

}
