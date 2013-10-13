package com.vgdc.merge;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class BaseEntity {
	
	private PhysicsBody body;
	private Renderer renderer;
	private SoundComponent sound;
	private World world;
	
	public void setPhysicsBody(PhysicsBody nBody){
		body = nBody;
	}
	
	public PhysicsBody getPhysicsBody(){
		return body;
	}
	
	public void setRenderer(Renderer nRenderer){
		renderer = nRenderer;
		renderer.setEntity(this);
	}
	
	public Renderer getRenderer(){
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
	
}
