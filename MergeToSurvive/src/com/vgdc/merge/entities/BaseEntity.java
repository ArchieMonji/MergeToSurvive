package com.vgdc.merge.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.vgdc.merge.entities.audio.SoundComponent;
import com.vgdc.merge.entities.physics.PhysicsBody;
import com.vgdc.merge.entities.rendering.BaseRenderer;
import com.vgdc.merge.world.World;

public abstract class BaseEntity {
	
	public static final int PLAYER_TEAM = 1;
	
	private PhysicsBody body;
	private BaseRenderer renderer;
	private SoundComponent sound;
	private World world;
	
//	public BaseEntity(){
//		setPhysicsBody(createPhysicsBody());
//	}
	
	protected PhysicsBody createPhysicsBody(){
		return new PhysicsBody();
	}
	
	public void setPhysicsBody(PhysicsBody nBody){
		body = nBody;
		body.setHost(this);
	}
	
	public PhysicsBody getPhysicsBody(){
		return body;
	}
	
	public void setRenderer(BaseRenderer nRenderer){
		renderer = nRenderer;
		renderer.setEntity(this);
	}
	
	public BaseRenderer getRenderer(){
		return renderer;
	}
	
	public SoundComponent getSoundComponent(){
		return sound;
	}
	
	public void setSoundComponent(SoundComponent soundComponent){
		sound = soundComponent;
	}
	
	public void setWorld(World world)
	{
		this.world = world;
	}
	
	public World getWorld()
	{
		return world;
	}
	
	public abstract void onUpdate(float delta);
	public abstract void onRender(SpriteBatch batch, float delta);
	public Vector2 getPosition()
	{
		return body.getPosition();
	}
	public void setPosition(Vector2 nVector)
	{
		body.setPosition(nVector);
	}

	public abstract EntityType getEntityType();
}
