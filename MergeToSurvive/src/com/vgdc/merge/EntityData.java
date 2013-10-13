package com.vgdc.merge;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Animation;

public class EntityData {
	public int maxHealth;
	public float jumpHeight;
	public float moveSpeed;
	public Controller controller;
	public ArrayList<Ability> defaultAbilities;
	public ArrayList<Animation> animations;
	public ArrayList<ArrayList<SoundFx>> sounds;
}
