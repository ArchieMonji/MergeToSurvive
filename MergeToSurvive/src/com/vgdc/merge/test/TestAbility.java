package com.vgdc.merge.test;

import com.badlogic.gdx.math.Vector2;
import com.vgdc.merge.entities.Entity;
import com.vgdc.merge.entities.EntityData;
import com.vgdc.merge.entities.abilities.Ability;
import com.vgdc.merge.entities.rendering.Renderer;

public class TestAbility extends Ability {
	
	//private EntityData projectileData = new EntityData();
	
	public TestAbility()
	{
//		Texture texture = new Texture(Gdx.files.internal("data/colorboxes.png"));
//		TextureRegion[] frames = new TextureRegion[4];
//		int x = texture.getWidth()/4;
//		for(int i = 0; i < 4; i++)
//		{
//			frames[i] = new TextureRegion(texture, i*x, 0, x, x);
//		}
//		ArrayList<Animation> animations = new ArrayList<Animation>();
//		animations.add(new Animation(0.05f, frames));
//		animations.add(animations.get(0));
//		projectileData.moveSpeed = 10;
//		projectileData.animations = animations;
//		projectileData.controller = new TestProjectileController();
	}

	@Override
	public void onUse(Entity entity) {
		EntityData testData = entity.getWorld().getAssets().entityDataMap.get("testprojectile");
		Entity projectile = new Entity(testData, entity.getWorld());
		projectile.setPosition(new Vector2(entity.getPosition().x, entity.getPosition().y));
		projectile.getRenderer().flip(entity.getRenderer().isFlipped());
		entity.getWorld().getEntityManager().addEntity(projectile);
	}
	
	

}
