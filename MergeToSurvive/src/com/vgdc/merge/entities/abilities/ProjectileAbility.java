package com.vgdc.merge.entities.abilities;

import com.badlogic.gdx.math.Vector2;
import com.vgdc.merge.entities.Entity;
import com.vgdc.merge.entities.EntityData;

public class ProjectileAbility extends Ability {
	
	public String projectile;
	
	public ProjectileAbility()
	{
//		
	}
	
	public ProjectileAbility(String projectile)
	{
		this.projectile = projectile;
	}

	@Override
	public void onUse(Entity entity, boolean retrievable) {
		EntityData data = entity.getWorld().getHandler().getEntityData(projectile);
		Entity projectile = createProjectile(data, entity, retrievable);
		projectile.getMovingBody().setVelocity(new Vector2((projectile.getRenderer().isFlipped()? -data.moveSpeed : data.moveSpeed),data.jumpHeight));
	}
	
	

}
