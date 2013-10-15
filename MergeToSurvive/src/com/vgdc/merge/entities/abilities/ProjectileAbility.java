package com.vgdc.merge.entities.abilities;

import com.badlogic.gdx.math.Vector2;
import com.vgdc.merge.entities.Entity;
import com.vgdc.merge.entities.EntityData;

public class ProjectileAbility extends Ability {
	
	//private EntityData projectileData = new EntityData();
	
	private String projectile;
	
	public ProjectileAbility()
	{
//		
	}

	@Override
	public void onUse(Entity entity) {
		EntityData data = entity.getWorld().getAssets().entityDataMap.get(projectile);
		Entity projectile = createEntity(data, entity);
		projectile.getMovingBody().setVelocity(new Vector2((projectile.getRenderer().isFlipped()? -data.moveSpeed : data.moveSpeed),data.jumpHeight));
	}
	
	

}
