package com.vgdc.merge.world;

import java.util.ArrayList;

public class Act {
	public ArrayList<Level> levels;
	public int currentLevel;
	
	public Level nextLevel()
	{
		if(++currentLevel>levels.size())
			currentLevel = 0;
		return levels.get(currentLevel);
	}
}
