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
	public Controller controller;
}
