package com.vgdc.merge.world.level;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;


public class LevelData {
	
	public ArrayList<LevelEntityData> entities = new ArrayList<LevelEntityData>();
	public ArrayList<LevelPlatformData> platforms = new ArrayList<LevelPlatformData>();
	
	public String background;
	
	public Vector2 playerStart;
	
	public Vector2 dimensions;
	public Vector2 cameraDimensions;

}
