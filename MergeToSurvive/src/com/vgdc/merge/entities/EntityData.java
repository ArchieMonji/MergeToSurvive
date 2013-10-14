package com.vgdc.merge.entities;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.vgdc.merge.entities.abilities.Ability;
import com.vgdc.merge.entities.audio.SoundFx;
import com.vgdc.merge.entities.controllers.Controller;

public class EntityData {
	public int maxHealth;
	public int damage;
	public int defaultTeam;
	public float jumpHeight;
	public float moveSpeed;
	public Controller controller;
	public ArrayList<Ability> defaultAbilities;
	public ArrayList<Animation> animations;
	public ArrayList<ArrayList<SoundFx>> sounds;
}
