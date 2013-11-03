package com.vgdc.merge.assets.loaders.data;

import java.util.HashMap;

import org.python.core.PyBoolean;
import org.python.core.PyDictionary;
import org.python.core.PyFloat;
import org.python.core.PyJavaType;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyString;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.OrderedMap;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.vgdc.merge.entities.BaseEntityData;

public class EntityLoadMapData extends BaseEntityData{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String controller;
	public Array<String> abilities;
	public Array<String> animations;
	public OrderedMap<String, Array<String>> sounds;
	
	public HashMap<String, PyObject> dynamicVariables = new HashMap<String, PyObject>();
	
	private static final String ANIM = "animations";
	private static final String ABIL = "abilities";
	private static final String CONT = "controller";
	private static final String SOUND = "sounds";
	
	public void copyFrom(HashMap<String, Object> map)
	{
		super.copyFrom(map);
		controller = loadString(map, CONT);
		abilities = loadArray(map, ABIL);
		animations = loadArray(map, ANIM);
		sounds = loadArrayMap(map, SOUND);
		for(java.util.Map.Entry<String, Object> e : map.entrySet())
		{
			dynamicVariables.put(e.getKey(), getObject(e.getValue()));
		}
	}
	
	@SuppressWarnings("unchecked")
	private PyObject getObject(Object obj)
	{
		if(obj instanceof Float)
		{
			return new PyFloat((Float)obj);
		}
		if(obj instanceof String)
		{
			return new PyString((String)obj);
		}
		if(obj instanceof Boolean)
		{
			return new PyBoolean((Boolean)obj);
		}
		if(obj instanceof Array)
		{
			PyList list = new PyList();
			for(Object o : (Array<Object>)obj)
				list.add(getObject(o));
			return list;
		}
		if(obj instanceof OrderedMap)
		{
			PyDictionary dict = new PyDictionary();
			for(Entry<String, Object> e : ((OrderedMap<String, Object>)obj).entries())
				dict.put(e.key, getObject(e.value));
			return dict;
		}
		return PyJavaType.wrapJavaObject(obj);
	}
	
	@SuppressWarnings("unchecked")
	public Array<String> loadArray(HashMap<String, Object> map, String key)
	{
		return (Array<String>) map.remove(key);
	}
	
	@SuppressWarnings("unchecked")
	public OrderedMap<String, Array<String>> loadArrayMap(HashMap<String, Object> map, String key)
	{
		return (OrderedMap<String, Array<String>>) map.remove(key);
	}

}
