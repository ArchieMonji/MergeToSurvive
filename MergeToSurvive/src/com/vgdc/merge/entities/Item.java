package com.vgdc.merge.entities;

import com.vgdc.merge.world.World;

public class Item extends Entity{
	public Item(EntityData entityData, World world) {
		super(entityData, world);
	}
	
	@Override
	public EntityType getEntityType(){
		return EntityType.Item;
	}
}
