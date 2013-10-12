package com.vgdc.merge;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Entity extends BaseEntity{
	
	private final EntityData data;
	private ArrayList<Ability> abilities = new ArrayList<Ability>();
	private int health;
	private Controller controller;
	private PhysicsBody body;
	private Renderer renderer;
	
	public Entity(EntityData data)
	{
		this.data = data;
		for(Ability a : data.defaultAbilities)
			abilities.add(a);
		this.controller = data.controller.copy();
		controller.setEntity(this);
		this.health = data.maxHealth;
		//renderer.setAnimations(data.animations);
	}
	
	public void setRenderer(Renderer nRenderer)
	{
		renderer = nRenderer;
	}
	
	public void setPhysicsBody(PhysicsBody nPhysicsBody)
	{
		body = nPhysicsBody;
	}
	
	@Override
	public void onUpdate(float delta) {
		controller.onUpdate(delta);
		body.onUpdate(delta);
	}
	
	@Override
	public void onRender(SpriteBatch batch, float delta){
		renderer.onRender(batch, delta);
	}

	@Override
	public Vector2 getPosition() {
		return body.getPosition();
	}

	@Override
	public void setPosition(Vector2 nVector) {
		body.setPosition(nVector);
	}
	
	public void damage(int amount)
	{
		health-=amount;
	}
	
	public void heal(int amount)
	{
		health = Math.min(health+amount, data.maxHealth);
	}
	
	public ArrayList<Ability> getAbilities()
	{
		return abilities;
	}
	
	public void jump()
	{
		//TODO implement this later, when PhysicsBody is more fleshed out
	}
	
	public void moveLeft()
	{
		getPosition().add(-data.moveSpeed, 0f);
	}
	
	public void moveRight()
	{
		getPosition().add(data.moveSpeed, 0f);
	}

}
