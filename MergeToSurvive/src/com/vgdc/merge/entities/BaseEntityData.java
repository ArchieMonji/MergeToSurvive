package com.vgdc.merge.entities;

import java.util.HashMap;

import org.python.core.PyObject;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.OrderedMap;

// I have no idea what I'm doing anymore
public class BaseEntityData extends PyObject{
	
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
	
	private static final String HEALTH = "maxHealth";
	private static final String DMG = "damage";
	private static final String TEAM = "defaultTeam";
	private static final String JUMP = "jumpHeight";
	private static final String MOVE = "moveSpeed";
	private static final String MERGE = "mergeable";
	private static final String ITEM = "itemDrop";
	
	public void copyFrom(HashMap<String, Object> map)
	{
		
	}
	
	private int loadInt(HashMap<String, Object> map, String key)
	{
		Float f = (Float) map.get(key);
		if(f!=null)
			return f.intValue();
		return 0;
	}
	
	private float loadFloat(HashMap<String, Object> map, String key)
	{
		Float f = (Float) map.get(key);
		if(f!=null)
			return f.floatValue();
		return 0.0f;
	}
	
	private String loadString(HashMap<String, Object> map, String key)
	{
		return (String) map.get(key);
	}
	
	private Vector2 loadVector(HashMap<String, Object> map, String key)
	{
		OrderedMap<String, Object> vector = (OrderedMap<String, Object>) map.get(key);
		if(vector!=null)
		{
			Vector2 ans = new Vector2();
			ans.x = (Float) vector.get("x");
			ans.y = (Float) vector.get("y");
			return ans;
		}
		return new Vector2(0, 0);
	}
	
}
