package com.vgdc.merge.entities.abilities;

import com.badlogic.gdx.math.Vector2;
import com.vgdc.merge.entities.Entity;
import com.vgdc.merge.entities.EntityData;
import com.vgdc.merge.entities.Projectile;

public class ProjectileAbility extends Ability {
	
	//private EntityData projectileData = new EntityData();
	
	public ProjectileAbility()
	{
//		
	}

	@Override
	public void onUse(Entity entity, boolean retrievable) {
		EntityData data = entity.getWorld().getAssets().entityDataMap.get(projectile);
		Projectile projectile = createProjectile(data, entity, retrievable);
		projectile.getMovingBody().setVelocity(new Vector2((projectile.getRenderer().isFlipped()? -data.moveSpeed : data.moveSpeed),data.jumpHeight));
	}
	
	

}
