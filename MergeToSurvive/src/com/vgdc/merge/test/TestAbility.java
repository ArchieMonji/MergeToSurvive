package com.vgdc.merge.test;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.vgdc.merge.Ability;
import com.vgdc.merge.Entity;
import com.vgdc.merge.EntityData;
import com.vgdc.merge.Renderer;

public class TestAbility extends Ability {
	
	private EntityData projectileData = new EntityData();
	
	public TestAbility()
	{
		Texture texture = new Texture(Gdx.files.internal("data/colorboxes.png"));
		TextureRegion[] frames = new TextureRegion[4];
		int x = texture.getWidth()/4;
		for(int i = 0; i < 4; i++)
		{
			frames[i] = new TextureRegion(texture, i*x, 0, x, x);
		}
		ArrayList<Animation> animations = new ArrayList<Animation>();
		animations.add(new Animation(0.05f, frames));
		animations.add(animations.get(0));
		projectileData.moveSpeed = 10;
		projectileData.animations = animations;
		projectileData.controller = new TestProjectileController();
	}

	@Override
	public void onUse(Entity entity) {
		Entity projectile = new Entity(projectileData, entity.getWorld());
		projectile.setPhysicsBody(new TestPhysicsBody());
		projectile.setPosition(new Vector2(entity.getPosition().x, entity.getPosition().y));
		projectile.setRenderer(new Renderer());
		projectile.getRenderer().flip(entity.getRenderer().isFlipped());
		entity.getWorld().getEntityManager().addEntity(projectile);
	}
	
	

}
