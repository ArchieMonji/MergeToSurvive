package com.vgdc.merge.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.vgdc.merge.entities.physics.PhysicsBody;
import com.vgdc.merge.entities.physics.PlatformBody;
import com.vgdc.merge.entities.rendering.PlatformRenderer;
import com.vgdc.merge.world.World;

public class Platform extends BaseEntity{
	
	public Platform(World world)
	{
		setWorld(world);
		setPhysicsBody(createPhysicsBody());
		setRenderer(new PlatformRenderer());
	}
	/////Properties
	public EntityType getEntityType(){
		return EntityType.Platform;
	}
	
	protected PhysicsBody createPhysicsBody(){
		return new PlatformBody();
	}
	
	public PlatformBody getPlatformBody(){
		return (PlatformBody)getPhysicsBody();
	}
	
	@Override
	public void onUpdate(float delta) {
		
	}

	@Override
	public void onRender(SpriteBatch batch, float delta) {
		getRenderer().onRender(batch, delta);

	}
}
