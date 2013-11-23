package com.vgdc.merge.world.level;

import java.util.ArrayList;

import com.vgdc.merge.world.World;

public class Act {
	public ArrayList<Level> levels = new ArrayList<Level>();
	public ArrayList<LevelData> datas = new ArrayList<LevelData>();
	public int currentLevel = -1;
	
	public World myWorld;
	
	public Act()
	{
		
	}
	
	public Act(World world)
	{
		myWorld = world;
	}
	
	public void setWorld(World world)
	{
		myWorld = world;
	}
	
	public Level setLevel(int level)
	{
		if(currentLevel!=-1)
			levels.get(currentLevel).setStart(myWorld.getPlayer().getPosition());
		//level = 2, size = 2, needs to be 3
		System.out.println(levels.size());
		if(level>=levels.size()||levels.get(level)==null)
		{
			while(levels.size()<=level)
				levels.add(null);
			levels.set(level, new Level(myWorld, datas.get(level)));
		}
		currentLevel = level;
		myWorld.getPlayer().setPosition(levels.get(currentLevel).getStart());
		return levels.get(currentLevel);
	}
	
	public Level nextLevel()
	{
		return setLevel(currentLevel+1);
	}
	
	public Level previousLevel()
	{
		return setLevel(currentLevel-1);
	}
	
	public Level getCurrentLevel()
	{
		return levels.get(currentLevel);
	}
	
	public int getCurrentLevelId()
	{
		return currentLevel;
	}
	
	public void addLevel(LevelData data)
	{
		datas.add(data);
	}
	
	public void addLevel(Level level)
	{
		levels.add(level);
	}
}
