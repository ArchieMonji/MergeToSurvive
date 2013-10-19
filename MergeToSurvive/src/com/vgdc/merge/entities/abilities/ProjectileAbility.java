package com.vgdc.merge.entities.abilities;

import com.vgdc.merge.entities.Entity;

public class ProjectileAbility extends Ability {
	
	//private EntityData projectileData = new EntityData();
	
	private String projectile;
	
	public ProjectileAbility()
	{
//		
	}

	@Override
	public void onUse(Entity entity, boolean retrievable) {
//		EntityData data = entity.getWorld().getAssets().entityDataMap.get(projectile);
//		Entity projectile = createEntity(data, entity, retrievable);
//		projectile.getMovingBody().setVelocity(new Vector2((projectile.getRenderer().isFlipped()? -data.moveSpeed : data.moveSpeed),data.jumpHeight));
	}
	
	

}
