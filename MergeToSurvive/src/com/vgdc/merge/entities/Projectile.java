package com.vgdc.merge.entities;

import com.vgdc.merge.world.World;

public class Projectile extends Entity{
	public Projectile(EntityData data, World world) {
		super(data, world);
		// TODO Auto-generated constructor stub
	}

	@Override
	public EntityType getEntityType(){
		return EntityType.Projectile;
	}
}
