package com.vgdc.merge.assets.loaders;

import java.util.HashMap;
import java.util.Map;

import org.python.core.PyBoolean;
import org.python.core.PyDictionary;
import org.python.core.PyFloat;
import org.python.core.PyJavaType;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyString;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.badlogic.gdx.utils.OrderedMap;
import com.vgdc.merge.entities.EntityData;

public class PyHelper {
	
	@SuppressWarnings("rawtypes")
	private static HashMap<String, Class> nameClassMap;
	
	@SuppressWarnings("unchecked")
	public static PyObject getObject(Object obj)
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
	
	@SuppressWarnings("rawtypes")
	public static void addDependencies(Map<String, String> req, Array<AssetDescriptor> deps, String filename)
	{
		if(req!=null)
		{
			for(java.util.Map.Entry<String, String> s : req.entrySet())
			{
				if(!s.getKey().equals(filename))
					deps.add(getDependencyDescriptor(s.getKey(), s.getValue()));
			}
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static AssetDescriptor getDependencyDescriptor(String filename, String classname)
	{
		return new AssetDescriptor(filename, lookupClass(classname));
	}
	
	@SuppressWarnings("rawtypes")
	public static Class lookupClass(String value)
	{
		if(nameClassMap==null)
			createNameClassMap();
		return nameClassMap.get(value);
	}
	
	@SuppressWarnings("rawtypes")
	private static void createNameClassMap()
	{
		nameClassMap = new HashMap<String, Class>();
		nameClassMap.put("entitydata", EntityData.class);
		nameClassMap.put("animation", Animation.class);
		nameClassMap.put("ability", PyObject.class);
		nameClassMap.put("controller", PyObject.class);
	}

}
