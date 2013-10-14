package com.vgdc.merge.entities.physics;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.vgdc.merge.entities.BaseEntity;
import com.vgdc.merge.entities.Entity;
import com.vgdc.merge.entities.Platform;
import com.vgdc.merge.entities.EntityType;
import com.vgdc.merge.math.VectorMath;
import com.vgdc.merge.world.World;

public class MovingBody extends PhysicsBody{
	public static final Vector2 DEFAULTGRAVITY = new Vector2(0,-1);
	
	protected Vector2 lastPosition = new Vector2();
	protected Vector2 velocity = new Vector2(),acceleration = DEFAULTGRAVITY;
	
	protected float friction = .4f;
	protected float elasticity = .5f;
	
	public MovingBody(){}
	
	/////Get/Set
	public void setVelocity(Vector2 v){velocity = v;}
	public Vector2 getVelocity(){return velocity;}
	public void setAcceleration(Vector2 a){acceleration = a;}
	public Vector2 getAcceleration(){return acceleration;}
	public void setFriction(float f){friction = f;}
	public float getFriction(){return ((Entity)host).frictionIsApplied() ? friction : 0;}
	public void setElasticity(float e){elasticity = e;}
	public float getElasticity(){return elasticity;}
	
	public Vector2 getLastPosition(){return lastPosition;}
	
	//////////Collisions
	private boolean touchingGround;
	public boolean checkTouchingGround(){return touchingGround;}
	/////General Collisions
	private void applySideCollision(CollisionSide side){//up means this object hit the top of the other object with the bottom of this object
		if(side == CollisionSide.Up){
			applyVerticalCollision();
			touchingGround = true;
		}else if(side == CollisionSide.Down)
			applyVerticalCollision();
		else
			applyHorizontalCollision();
	}
	private void applyHorizontalCollision(){
		if(Math.abs(velocity.x) < 2)
			setVelocity(new Vector2(velocity.x,0));
		setVelocity(new Vector2(getVelocity().x*-elasticity,getVelocity().y*(1-getFriction())));
	}
	private void applyVerticalCollision(){
		setVelocity(new Vector2(getVelocity().x*(1-getFriction()),getVelocity().y*-elasticity));
		if(Math.abs(velocity.y) < 2)
			setVelocity(new Vector2(velocity.x,0));
	}
	/////Boundary Collisions
	void checkBoundaryCollisions(){
		Vector2 pos = getPosition();
		Vector2 siz = getSize();
		World world = host.getWorld();
		if(pos.x < siz.x/2){
			pos.x = siz.x/2;
			applySideCollision(CollisionSide.Left);
		}else if(pos.x > world.dimensions.x-siz.x/2){
			pos.x = world.dimensions.x-siz.x/2;
			applySideCollision(CollisionSide.Right);
		}
		if(pos.y < siz.y/2){
			pos.y = siz.y/2;
			applyVerticalCollision();
			applySideCollision(CollisionSide.Up);
		}else if(pos.y > world.dimensions.y-siz.y/2){
			pos.y = world.dimensions.y-siz.y/2;
			applySideCollision(CollisionSide.Down);
		}
	}

	/////Platform Collisions
	public void onPlatformCollision(Platform platform,CollisionData collision){
		((Entity)host).onPlatformCollision(platform);
		Vector2 finalposition = position;

		if(collision.side == CollisionSide.Down || collision.side == CollisionSide.Up){
			if(collision.side == CollisionSide.Up){
				touchingGround = true;
				setPosition(new Vector2(position.x,collision.position+size.y/2+.1f));
			}else
				setPosition(new Vector2(position.x,collision.position-size.y/2-.1f));
		}
		else if(collision.side == CollisionSide.Left || collision.side == CollisionSide.Right){
			
			if(collision.side == CollisionSide.Right)
				setPosition(new Vector2(collision.position+size.x/2+.1f,position.y));
			else
				setPosition(new Vector2(collision.position-size.x/2-.1f,position.y));
		}
		System.out.println("Collision: "+collision.side);
		applySideCollision(collision.side);
		/*Vector2 lp = lastPosition;
		for(float lerp = 0; lerp <= 1; lerp += 1f/10){
			setPosition(VectorMath.lerp(lastPosition,finalposition,lerp));
			if(platform.getPlatformBody().getCollision(this) != null){
				setPosition(lp);
				break;
			}
			lp = position;
		}*/
		//setPosition(VectorMath.add(position,velocity));
	}
	static final float MINCHECKDIST = 10;//If the object is going faster than this, it will do extra collision detection calculations
	private void checkSinglePlatformCollision(BaseEntity entity){
		if(entity.getEntityType() == EntityType.Platform){
			CollisionData collision = ((Platform)entity).getPlatformBody().getCollision(this);
			if(collision != null)
				onPlatformCollision((Platform)entity,collision);
		}
	}
	private void checkAllPlatformCollisions(){
		ArrayList<BaseEntity> entities = host.getWorld().getEntityManager().getEntities();
		for(int j = entities.indexOf(host)+1;j < entities.size();j++){
			checkSinglePlatformCollision(entities.get(j));
		}
	}
	public void checkPlatformCollisions(){
		float displacement = VectorMath.distance(lastPosition,position);
		if(displacement > MINCHECKDIST){
			Vector2 finalposition = position;
			for(float i = 0; i < 1; i += MINCHECKDIST/displacement){
				setPosition(VectorMath.lerp(lastPosition,finalposition,i));
				checkAllPlatformCollisions();
			}
			setPosition(finalposition);
			checkAllPlatformCollisions();
		}else{
			checkAllPlatformCollisions();
		}
	}
	
	/////Entity Collisions
	public void onEntityCollision(Entity entity){
		((Entity)host).onEntityCollision(entity);
		entity.onEntityCollision((Entity)host);
	}
	public void checkEntityCollisions(){
		ArrayList<BaseEntity> entities = host.getWorld().getEntityManager().getEntities();
		for(int i = entities.indexOf(host)+1;i < entities.size();i++){
			if(entities.get(i).getEntityType() == EntityType.Entity && checkCollidingWithEntity((MovingBody)entities.get(i).getPhysicsBody())){
				onEntityCollision((Entity)entities.get(i));
				((Entity)entities.get(i)).getMovingBody().onEntityCollision((Entity)host);
			}
		}
	}
	
	/////Update
	static final float TIMEMULT = 60;
	public void onUpdate(float delta){
		super.onUpdate(delta);
		delta *= TIMEMULT;
		touchingGround = false;
		lastPosition = new Vector2(position.x,position.y);
		setPosition(getPosition().add(VectorMath.mul(getVelocity(),delta)));
		setVelocity(getVelocity().add(VectorMath.mul(getAcceleration(),delta)));
		checkEntityCollisions();
		checkPlatformCollisions();
		checkBoundaryCollisions();
	}
}

