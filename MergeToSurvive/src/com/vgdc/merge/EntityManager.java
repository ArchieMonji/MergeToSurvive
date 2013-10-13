package com.vgdc.merge;

import java.util.ArrayList;

public class EntityManager {
	
	private ArrayList<BaseEntity> entities = new ArrayList<BaseEntity>();
	
	public ArrayList<BaseEntity> getEntities()
	{
		return entities;
	}
	
	public void addEntity(BaseEntity entity)
	{
		entities.add(entity);
	}

}
