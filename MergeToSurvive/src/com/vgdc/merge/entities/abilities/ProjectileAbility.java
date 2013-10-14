package com.vgdc.merge.entities.abilities;

import com.badlogic.gdx.math.Vector2;
import com.vgdc.merge.entities.Entity;
import com.vgdc.merge.entities.EntityData;

public class ProjectileAbility extends Ability {
	
	//private EntityData projectileData = new EntityData();
	
	private String projectile;
	
	public ProjectileAbility()
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
		EntityData testData = entity.getWorld().getAssets().entityDataMap.get(projectile);
		Entity projectile = new Entity(testData, entity.getWorld());
		projectile.setTeam(entity.getTeam());
		projectile.setPosition(new Vector2(entity.getPosition().x, entity.getPosition().y));
		projectile.getRenderer().flip(entity.getRenderer().isFlipped());
		entity.getWorld().getEntityManager().addEntity(projectile);
		projectile.getMovingBody().setVelocity(new Vector2(0, testData.jumpHeight));
	}
	
	

}
