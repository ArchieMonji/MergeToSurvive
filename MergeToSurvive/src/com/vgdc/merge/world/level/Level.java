package com.vgdc.merge.world.level;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.vgdc.merge.entities.Entity;
import com.vgdc.merge.entities.EntityData;
import com.vgdc.merge.entities.Platform;
import com.vgdc.merge.entities.rendering.PlatformRenderer;
import com.vgdc.merge.world.EntityManager;
import com.vgdc.merge.world.EventBox;
import com.vgdc.merge.world.Spawner;
import com.vgdc.merge.world.World;

public class Level {
	
	private EntityManager entities= new EntityManager();
	private Vector2 playerStart;
	
	//might move these into their own classes, we'll see
	private ArrayList<Spawner> spawners;
	private ArrayList<EventBox> regions;
	
	private World myWorld;
	private Texture myBackground;
	
	public Level()
	{
		
	}
	
	public Level(World world)
	{
		myWorld = world;
	}
	
	public Level(World world, LevelData data)
	{
		myWorld = world;
		constructFrom(data);
	}
	
	public void setWorld(World world)
	{
		myWorld = world;
	}
	
	public void constructFrom(LevelData data)
	{
		myBackground = data.background;
		playerStart = data.playerStart.cpy();
		for(LevelPlatformData d : data.platforms)
		{
			Platform platform = new Platform(myWorld);
			platform.getPlatformBody().setPlatformType(d.type);
			platform.setRenderer(new PlatformRenderer(d.imageFileName, 1,1,1,1));
			platform.getPhysicsBody().setPosition(d.location);
			platform.getPhysicsBody().setSize(d.dimensions);
			entities.addEntity(platform);
		}
		if(myWorld.getPlayer()!=null)
			entities.addEntity(myWorld.getPlayer());
		for(LevelEntityData d : data.entities)
		{
			EntityData e = myWorld.getHandler().getEntityData(d.entityData);
			entities.addEntity(new Entity(e, myWorld));
		}
	}
	
	public EntityManager getEntityManager()
	{
		return entities;
	}
	
	public Vector2 getStart()
	{
		return playerStart;
	}
	
	public Texture getBackground()
	{
		return myBackground;
	}
	
	public void setBackground(Texture texture)
	{
		myBackground = texture;
	}
	
	public void setStart(Vector2 position)
	{
		playerStart.set(position);
	}

}
