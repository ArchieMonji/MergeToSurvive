package com.vgdc.merge.world;

import java.util.ArrayList;

import com.vgdc.merge.entities.BaseEntity;

public class EntityManager {
	
	private ArrayList<BaseEntity> entities = new ArrayList<BaseEntity>();
	
	private ArrayList<BaseEntity> toAdd = new ArrayList<BaseEntity>();
	
	private ArrayList<BaseEntity> toRemove = new ArrayList<BaseEntity>();
	
	public ArrayList<BaseEntity> getEntities()
	{
		return entities;
	}
	
	public void addEntity(BaseEntity entity)
	{
		toAdd.add(entity);
	}
	
	public void removeEntity(BaseEntity entity)
	{
		toRemove.add(entity);
	}
	
	public void onUpdate()
	{
		for(BaseEntity e : toRemove)
		{
			for(int i = 0; i < entities.size(); i++)
				if(entities.get(i).equals(e))
					entities.remove(i--);
		}
		entities.addAll(toAdd);
		toAdd.clear();
		toRemove.clear();
	}

}
