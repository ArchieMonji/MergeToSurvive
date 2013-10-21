package com.vgdc.merge.entities.abilities;

import com.badlogic.gdx.math.Vector2;
import com.vgdc.merge.entities.Entity;
import com.vgdc.merge.entities.EntityData;
import com.vgdc.merge.entities.Projectile;
import com.vgdc.merge.entities.rendering.BlankRenderer;

public class HitBoxAbility extends Ability {
	
	//these 3 variables might be unneccessary, solve later
//	private float duration;
//	private Vector2 dimensions;
//	private int damage;
	//they are
	
	private String hitbox;
	private Vector2 offset;
	private boolean blank;
	
	public HitBoxAbility(String hitbox)
	{
		this(hitbox, true);
	}
	
	public HitBoxAbility(String hitbox, boolean blank)
	{
		this.hitbox = hitbox;
		this.blank = blank;
		offset = new Vector2();
	}

	@Override
	public void onUse(Entity entity, boolean retrievable) {
		Entity box = createProjectile(entity.getWorld().getHandler().getEntityData(hitbox), entity, retrievable);
		if(blank)
			box.setRenderer(new BlankRenderer());
		box.getMovingBody().setAcceleration(new Vector2(0, 0));
	}
	
	public Projectile createProjectile(EntityData data, Entity entity, boolean retrievable)
	{
		Projectile hitbox = super.createProjectile(data, entity, retrievable);
		return hitbox;
	}

}
