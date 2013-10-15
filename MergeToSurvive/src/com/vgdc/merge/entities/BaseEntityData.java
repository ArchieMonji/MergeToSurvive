package com.vgdc.merge.entities;

import com.badlogic.gdx.math.Vector2;
import com.vgdc.merge.entities.controllers.Controller;

public class BaseEntityData {
	public int maxHealth = 1;
	public int damage;
	public int defaultTeam;
	public Vector2 dimensions;
	public float jumpHeight;
	public float moveSpeed;
	public boolean mergeable;
	public Controller controller;
	
	public void copyInto(BaseEntityData other)
	{
		other.maxHealth = maxHealth;
		other.damage = damage;
		other.defaultTeam = defaultTeam;
		other.dimensions = dimensions;
		other.jumpHeight = jumpHeight;
		other.moveSpeed = moveSpeed;
		other.mergeable = mergeable;
		other.controller = controller;
	}
}
