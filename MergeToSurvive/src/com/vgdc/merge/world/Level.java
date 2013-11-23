package com.vgdc.merge.world;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

public class Level {
	
	public EntityManager entities;
	public Vector2 playerSpawn;
	
	//might move these into their own classes, we'll see
	public ArrayList<Spawner> spawners;
	public ArrayList<EventBox> regions;

}
