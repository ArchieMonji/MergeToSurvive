package com.vgdc.merge.world;

import com.badlogic.gdx.math.Vector2;
import com.vgdc.merge.entities.Entity;
import com.vgdc.merge.entities.EntityData;

public class Spawner {
	
	public EntityData entityToSpawn;
	public int numSpawn;
	public Vector2 position;
	
	private World myWorld;
	
	public Spawner(World world)
	{
		myWorld = world;
	}
	
	public void spawn()
	{
		for(int i = 0; i < numSpawn; i++)
		{
			Entity entity = new Entity(entityToSpawn, myWorld);
			entity.setPosition(position);
		}
	}

}
