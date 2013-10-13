package com.vgdc.merge;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Entity extends BaseEntity{
	
	private final EntityData data;
	private ArrayList<Ability> abilities = new ArrayList<Ability>();
	private int health;
	private Controller controller;
	
	public Entity(EntityData data)
	{
		this.data = data;
		for(Ability a : data.defaultAbilities)
			abilities.add(a);
		this.controller = data.controller.copy();
		controller.setEntity(this);
		this.health = data.maxHealth;
	}
	
	public Entity(EntityData data, Renderer renderer, PhysicsBody body)
	{
		this.data = data;
		for(Ability a : data.defaultAbilities)
			abilities.add(a);
		this.controller = data.controller.copy();
		controller.setEntity(this);
		this.health = data.maxHealth;
		setRenderer(renderer);
		setPhysicsBody(body);
	}
	
	public void setRenderer(Renderer nRenderer)
	{
		super.setRenderer(nRenderer);
		nRenderer.setAnimations(data.animations);
	}
	
	@Override
	public void onUpdate(float delta) {
		controller.onUpdate(delta);
		getPhysicsBody().onUpdate(delta);
	}
	
	@Override
	public void onRender(SpriteBatch batch, float delta){
		getRenderer().onRender(batch, delta);
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

	public void onEntityCollision(Entity other) {
		
	}

}
