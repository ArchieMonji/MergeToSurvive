package com.vgdc.merge.entities.physics;

import com.badlogic.gdx.math.Vector2;
import com.vgdc.merge.entities.BaseEntity;
import com.vgdc.merge.math.VectorMath;


public class PhysicsBody{
	protected BaseEntity host;
	
	protected Vector2 position = new Vector2(),size = new Vector2(20,20);
	
	protected float physicsRadius;//Used to optimize collision detection  
	
	/////Get/Set
	public void setPosition(Vector2 p){position = p;}
	public Vector2 getPosition(){return position;}
	public void setSize(Vector2 s){
		size = s;
		physicsRadius = VectorMath.div(s,2).len();
	}
	public Vector2 getSize(){return size;}

	/////Host
	public void setHost(BaseEntity h){host = h;}
	public BaseEntity getHost(){return host;}
	
	/////Extra Gets
	public Vector2 getCenter(){
		return new Vector2(position.x+size.x/2,position.y+size.y/2);
	}
	public Vector2 getRenderPosition(){//gets the bottom left
		return new Vector2(position.x-size.x/2,position.y-size.y/2);
	}
	
	public Vector2 getEllipsePosition(Vector2 normal){
		return VectorMath.mul(normal,new Vector2(getSize().x/2,getSize().y/2));
	}
	public Vector2 getEllipsePosition(float angle){
		return VectorMath.fromAngle(angle);
	}
	public float getEllipseRadius(Vector2 normal){
		return getEllipsePosition(normal).len();
	}
	public float getEllipseRadius(float angle){
		return getEllipseRadius(VectorMath.fromAngle(angle));
	}
	
	/////Collision Detection
	public boolean checkCollidingWithEntity(MovingBody other){
		Vector2 normal = VectorMath.normalize(getPosition(),other.getPosition());
		return VectorMath.distanceLessThan(getPosition(),other.getPosition(),getEllipseRadius(normal)+other.getEllipseRadius(normal));
	}
	
	/////Update
	public void onUpdate(float delta){}
}
