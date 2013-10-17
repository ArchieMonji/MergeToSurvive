package com.vgdc.merge.entities.abilities;

import com.badlogic.gdx.math.Vector2;
import com.vgdc.merge.entities.Entity;
import com.vgdc.merge.entities.EntityData;
import com.vgdc.merge.entities.rendering.BlankRenderer;

public class HitBoxAbility extends Ability {
	
	//these 3 variables might be unneccessary, solve later
//	private float duration;
//	private Vector2 dimensions;
//	private int damage;
	//they are
	
	private EntityData data;

	@Override
	public void onUse(Entity entity, boolean retrievable) {
		Entity hitbox = createEntity(data, entity, retrievable);
		hitbox.setRenderer(new BlankRenderer());
		hitbox.getMovingBody().setAcceleration(new Vector2(0, 0));
	}

}
