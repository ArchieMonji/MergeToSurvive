package com.vgdc.merge.entities;

import java.util.HashMap;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.OrderedMap;

public class BaseEntityData{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public int maxHealth = 1; //*
	public int damage;
	public int defaultTeam;
	public Vector2 dimensions; //*
	public float jumpHeight;
	public float moveSpeed;
	public boolean mergeable;
	public String itemDrop;
	
	public void copyInto(BaseEntityData other)
	{
		other.maxHealth = maxHealth;
		other.damage = damage;
		other.defaultTeam = defaultTeam;
		other.dimensions = dimensions;
		other.jumpHeight = jumpHeight;
		other.moveSpeed = moveSpeed;
		other.mergeable = mergeable;
		other.itemDrop = itemDrop;
	}
	
	public int loadInt(HashMap<String, Object> map, String key)
	{
		Float f = (Float) map.remove(key);
		if(f!=null)
			return f.intValue();
		return 0;
	}
	
	public int loadInt(HashMap<String, Object> map, String key, int def)
	{
		Float f = (Float) map.remove(key);
		if(f!=null)
			return f.intValue();
		return def;
	}
	
	public float loadFloat(HashMap<String, Object> map, String key)
	{
		Float f = (Float) map.remove(key);
		if(f!=null)
			return f.floatValue();
		return 0.0f;
	}
	
	public String loadString(HashMap<String, Object> map, String key)
	{
		return (String) map.remove(key);
	}
	
	@SuppressWarnings("unchecked")
	public Vector2 loadVector(HashMap<String, Object> map, String key)
	{
		OrderedMap<String, Object> vector = (OrderedMap<String, Object>) map.remove(key);
		if(vector!=null)
		{
			Vector2 ans = new Vector2();
			ans.x = (Float) vector.get("x");
			ans.y = (Float) vector.get("y");
			return ans;
		}
		return new Vector2(0, 0);
	}
	
	public boolean loadBoolean(HashMap<String, Object> map, String key)
	{
		Boolean b = Boolean.parseBoolean((String) map.get(key));
		if(b!=null)
			return b.booleanValue();
		return false;
	}
	
}
