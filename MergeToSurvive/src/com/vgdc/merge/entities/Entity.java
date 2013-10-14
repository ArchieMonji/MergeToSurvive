package com.vgdc.merge.entities;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.vgdc.merge.entities.abilities.Ability;
import com.vgdc.merge.entities.audio.SoundComponent;
import com.vgdc.merge.entities.controllers.Controller;
import com.vgdc.merge.entities.physics.MovingBody;
import com.vgdc.merge.entities.physics.PhysicsBody;
import com.vgdc.merge.entities.rendering.Renderer;
import com.vgdc.merge.math.VectorMath;
import com.vgdc.merge.world.World;

public class Entity extends BaseEntity{
	
	private static final float HALF_JUMP_DIR = 0.2f;
	
	private final EntityData data;
	private ArrayList<Ability> abilities = new ArrayList<Ability>();
	private int health;
	private Controller controller;
	private float halfJumpDir;
	//private boolean moved = false;
	
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
		setRenderer(new Renderer());
		setSoundComponent(new SoundComponent());
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
		setSoundComponent(sound);
	}
	
	protected PhysicsBody createPhysicsBody(){
		return new MovingBody();
	}
	
	public EntityType getEntityType(){
		return EntityType.Entity;
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
//		if(moved)
//		{
//			if(getRenderer().getState()==0)
//				getRenderer().setState(1);
//		}
//		else
//		{
//			if(getRenderer().getState()!=0)
//				getRenderer().setState(0);
//				
//		}
		getPhysicsBody().onUpdate(delta);
		if(halfJumpDir>0)
			halfJumpDir-=delta;
		//moved = false;
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
		if(getMovingBody().checkTouchingGround()&&halfJumpDir<=0)
		{
			Vector2 velocity = getMovingBody().getVelocity();
			getMovingBody().setVelocity(new Vector2((getMovingBody().getPosition().x-getMovingBody().getLastPosition().x)/delta*60/*velocity.x*/, data.jumpHeight));
			System.out.println(""+getMovingBody().getVelocity().x);
			halfJumpDir = HALF_JUMP_DIR;
		}
		else if(halfJumpDir>0)
		{
			getMovingBody().setVelocity(new Vector2(getMovingBody().getVelocity().x, data.jumpHeight));
			System.out.println(halfJumpDir);
		}
	}
	
	public MovingBody getMovingBody()
	{
		return (MovingBody)getPhysicsBody();
	}
	
	static final float JUMPCONTROL = .5f;
	static final float WALKFORCE = .2f;
	public void moveLeft(float delta)
	{
		if(!getMovingBody().checkTouchingGround())
			delta*=JUMPCONTROL;
		//Vector2 velo = getMovingBody().getVelocity();
		//getMovingBody().setVelocity(new Vector2(getMovingBody().getVelocity().x*(1-WALKFORCE)+-data.moveSpeed/30*WALKFORCE,getMovingBody().getVelocity().y));
		getPosition().add(-data.moveSpeed*delta, 0f);
		getRenderer().flip(true);
		//moved = true;
	}
	
	public void moveRight(float delta)
	{
		if(!getMovingBody().checkTouchingGround())
			delta*=JUMPCONTROL;
		getPosition().add(data.moveSpeed*delta, 0f);
		getRenderer().flip(false);
		//moved = true;
	}

	public void onEntityCollision(Entity other){
		controller.onEntityCollision(other);
	}
	public void onPlatformCollision(Platform other){
		controller.onPlatformCollision(other);
	}
	
	public void setState(int state){
		getRenderer().setState(state);
		getSoundComponent().playSound(state);
	}
	
	public int getState(){
		return getRenderer().getState();
	}

}
