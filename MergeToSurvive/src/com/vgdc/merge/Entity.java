package com.vgdc.merge;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Entity extends BaseEntity{
	
	private final EntityData data;
	private ArrayList<Ability> abilities = new ArrayList<Ability>();
	private int health;
	private Controller controller;
	private boolean moved = false;
	
	public Entity(EntityData data, World world)
	{
		this.data = data;
		setWorld(world);
		if(data.defaultAbilities!=null)
			for(Ability a : data.defaultAbilities)
				abilities.add(a);
		this.controller = data.controller.copy();
		controller.setEntity(this);
		this.health = data.maxHealth;
	}
	
	public Entity(EntityData data, World world, Renderer renderer, PhysicsBody body, SoundComponent sound)
	{
		this.data = data;
		setWorld(world);
		if(data.defaultAbilities!=null)
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
		nRenderer.setState(0);
	}
	
	public void setSoundComponent(SoundComponent sound)
	{
		super.setSoundComponent(sound);
		sound.setSounds(data.sounds);
	}
	
	@Override
	public void onUpdate(float delta) {
		controller.onUpdate(delta);
		if(moved)
		{
			if(getRenderer().getState()==0)
				getRenderer().setState(1);
		}
		else
		{
			if(getRenderer().getState()!=0)
				getRenderer().setState(0);
				
		}
		getPhysicsBody().onUpdate(delta);
		moved = false;
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
	
	public void jump(float delta)
	{
		//TODO implement this later, when PhysicsBody is more fleshed out
	}
	
	public void moveLeft(float delta)
	{
		getPosition().add(-data.moveSpeed*delta, 0f);
		getRenderer().flip(true);
		moved = true;
	}
	
	public void moveRight(float delta)
	{
		getPosition().add(data.moveSpeed*delta, 0f);
		getRenderer().flip(false);
		moved = true;
	}

	public void onEntityCollision(Entity other) {
		
	}
	
	public void setState(int state){
		getRenderer().setState(state);
		getSoundComponent().playSound(state);
	}
	
	public int getState(){
		return getRenderer().getState();
	}

}
