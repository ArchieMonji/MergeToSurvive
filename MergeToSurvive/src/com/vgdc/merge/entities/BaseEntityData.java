package com.vgdc.merge.entities;

import com.badlogic.gdx.math.Vector2;

public class BaseEntityData{
	
	
	public int maxHealth = 1; //*
	public int damage;
	public int defaultTeam;
	public Vector2 dimensions; //*
	public float jumpHeight;
	public float moveSpeed;
	public boolean mergeable;
	public EntityType type = EntityType.Entity;
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
		other.type = type;
	}
	
}
