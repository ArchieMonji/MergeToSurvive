package com.vgdc.merge.entities;

import org.python.core.PyObject;

import com.badlogic.gdx.math.Vector2;

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
}
