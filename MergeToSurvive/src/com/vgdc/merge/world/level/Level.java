package com.vgdc.merge.world.level;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.vgdc.merge.entities.Entity;
import com.vgdc.merge.entities.EntityData;
import com.vgdc.merge.entities.Item;
import com.vgdc.merge.entities.Platform;
import com.vgdc.merge.entities.PlatformData;
import com.vgdc.merge.entities.rendering.PlatformRenderer;
import com.vgdc.merge.world.EntityManager;
import com.vgdc.merge.world.EventBox;
import com.vgdc.merge.world.Spawner;
import com.vgdc.merge.world.World;

public class Level {
	
	private EntityManager entities= new EntityManager();
	private Vector2 playerStart;
	private Vector2 dimensions = new Vector2();
	private Vector2 cameraDimensions = new Vector2(800, 600);
	
	//might move these into their own classes, we'll see
	private ArrayList<Spawner> spawners;
	private ArrayList<EventBox> regions;
	
	private World myWorld;
	private Texture myBackground;
	private OrthographicCamera backgroundCamera;
	
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
		if(data.background!=null&&!"".equals(data.background))
			setBackground(myWorld.getHandler().getTexture(data.background));
		playerStart = data.playerStart.cpy();
		dimensions.set(data.dimensions);
		cameraDimensions.set(data.cameraDimensions);
		for(LevelPlatformData d : data.platforms)
		{
			PlatformData platdata = myWorld.getHandler().getPlatformData(d.platformDataName);
			Platform platform = new Platform(myWorld);
			platform.getPlatformBody().setPlatformType(d.type);
			platform.setRenderer(new PlatformRenderer(myWorld.getHandler().getTexture(platdata.texture), platdata.left, platdata.right, platdata.top, platdata.bottom));
			platform.getPhysicsBody().setPosition(d.location);
			platform.getPhysicsBody().setSize(d.dimensions);
			entities.addEntity(platform);
		}
		if(myWorld.getPlayer()!=null)
			entities.addEntity(myWorld.getPlayer());
		for(LevelEntityData d : data.entities)
		{
			EntityData e = myWorld.getHandler().getEntityData(d.entityData);
			switch(e.type)
			{
			case Entity:
				Entity entity = new Entity(e, myWorld);
				entity.getMovingBody().setElasticity(0);
				entity.setPosition(d.location);
				entities.addEntity(entity);
				break;
			case Item:
				Item item = new Item(e, myWorld);
				item.getMovingBody().setElasticity(0);
				item.setPosition(d.location);
				item.getController().onCreate();
				entities.addEntity(item);
				break;
			default:
				break;
			}
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
		backgroundCamera = new OrthographicCamera(myBackground.getWidth(), myBackground.getHeight());
		backgroundCamera.position.x = myBackground.getWidth()/2;
		backgroundCamera.position.y = myBackground.getHeight()/2;
		backgroundCamera.update();
	}
	
	public void drawBackground(SpriteBatch batch)
	{
		if(myBackground==null)
			return;
		batch.setProjectionMatrix(backgroundCamera.combined);
		batch.draw(myBackground, 0, 0);
	}
	
	public void setStart(Vector2 position)
	{
		playerStart.set(position);
	}
	
	public void setDimensions(Vector2 dimensions)
	{
		this.dimensions.set(dimensions);
	}
	
	public Vector2 getDimensions()
	{
		return dimensions;
	}
	
	public Vector2 getCameraDimensions()
	{
		return cameraDimensions;
	}

}
