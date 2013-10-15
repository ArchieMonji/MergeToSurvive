package com.vgdc.merge.entities.physics;

import com.badlogic.gdx.math.Vector2;
import com.vgdc.merge.math.VectorMath;

enum CollisionSide{Right,Up,Left,Down}

class CollisionData{
	public float position;
	public CollisionSide side;
	public CollisionData(float collisionpos,CollisionSide dir){
		this.position = collisionpos;
		this.side = dir;
	}
}

public class PlatformBody extends PhysicsBody{
	private PlatformType platformType = PlatformType.Rectangle;

	public PlatformBody(){
	}
	
	/////PlatformType
	public void setPlatformType(PlatformType p){platformType = p;}
	public PlatformType getPlatformType(){return platformType;}
	
	/////Collisions
	static final int NULLDIST = 9999;
	float raycast_verticalborder(Vector2 p1,Vector2 p2,float x){
		Vector2 collision = VectorMath.getLineCollision_Vertical(p1,p2,x);
		if(collision.y > position.y+size.y/2 || collision.y < position.y-size.y/2)
			return NULLDIST;
		return VectorMath.distance(p1,collision);
	}
	float raycast_horizontalborder(Vector2 p1,Vector2 p2,float y){
		Vector2 collision = VectorMath.getLineCollision_Horizontal(p1,p2,y);
		System.out.println("collision: "+collision);
		System.out.println("p1: "+p1+" : p2: "+p2+" : y: "+y);
		if(collision.y > position.y+size.y/2 || collision.y < position.y-size.y/2)
			return NULLDIST;
		return VectorMath.distance(p1,collision);
	}
	float raycast_verticalborder_other(MovingBody body,Vector2 p1,Vector2 p2,float x){
		Vector2 collision = VectorMath.getLineCollision_Vertical(p1,p2,x);
		if(collision.y > body.position.y+body.size.y/2 || collision.y < body.position.y-body.size.y/2)
			return NULLDIST;
		return VectorMath.distance(p1,collision);
	}
	float raycast_horizontalborder_other(MovingBody body,Vector2 p1,Vector2 p2,float y){
		Vector2 collision = VectorMath.getLineCollision_Horizontal(p1,p2,y);
		if(collision.y > body.position.y+body.size.y/2 || collision.y < body.position.y-body.size.y/2)
			return NULLDIST;
		return VectorMath.distance(p1,collision);
	}
	
	public CollisionData getCollision(MovingBody body){
		float halfsizex = size.x/2;
		float halfsizey = size.y/2;
		float bhalfsizex = body.size.x/2;
		float bhalfsizey = body.size.y/2;
		
		float right = position.x+halfsizex;
		float top = position.y+halfsizey;
		float bottom = position.y-halfsizey;
		float left = position.x-halfsizex;
		
		float bright = body.position.x+bhalfsizex;
		float btop = body.position.y+bhalfsizey;
		float bbottom = body.position.y-bhalfsizey;
		float bleft = body.position.x-bhalfsizex;
		
		Vector2 deltaposition = VectorMath.sub(body.position,body.lastPosition);

		boolean biggerX = size.x < body.size.x;
		boolean biggerY = size.y < body.size.y;
		boolean collide_left = deltaposition.x > 0 && biggerX ? (left <= bright && left >= bleft) : bright >= left && bright <= right;
		boolean collide_right = deltaposition.x < 0 && biggerX ? (right >= bleft && right <= bright) : bleft <= right && bleft >= left;
		if(!collide_left && !collide_right) return null;//The MovingBody isn't within the x coordinates to be able to hit the platform
		boolean collide_up = deltaposition.y < 0 && biggerY ? (top >= bbottom && top <= btop) : bbottom <= top && bbottom >= bottom;
		boolean collide_down = deltaposition.y > 0 && biggerY ? (bottom <= btop && bottom >= bbottom) : btop >= bottom && btop <= top;
		if(!collide_up && !collide_down) return null;//The MovingBody isn't within the y coordinates
		
		System.out.println(""+collide_left+":"+collide_right+":"+collide_up+":"+collide_down);

		float hitup = NULLDIST,hitdown = NULLDIST,hitleft = NULLDIST,hitright = NULLDIST;
		
		Vector2 bpositionchange = VectorMath.sub(body.position,body.lastPosition);

		if(collide_up){
			for(float x = -bhalfsizex; x <= bhalfsizex; x += body.size.x){
				System.out.println(""+body.lastPosition+":"+body.position);
				hitup = raycast_horizontalborder(VectorMath.add(body.lastPosition,new Vector2(x,-bhalfsizey)),
						VectorMath.add(body.position,new Vector2(x,-bhalfsizey)),
						top);
				System.out.println("hitup: "+hitup);
				if(hitup != NULLDIST) break;
			}
			if(hitup == NULLDIST){
				float cornerposy = position.y+halfsizey;
				for(float x = -halfsizex; x <= halfsizex; x += size.x){
					hitup = raycast_horizontalborder_other(body,
							new Vector2(x,cornerposy),
							VectorMath.sub(new Vector2(x,cornerposy),bpositionchange),
							position.y+halfsizey);
				}
			}
			collide_up = hitup != NULLDIST;
		}
		if(getPlatformType() == PlatformType.Rectangle){//Jumpable platforms don't need side or bottom collision
			if(collide_down){
				for(float x = -bhalfsizex; x <= bhalfsizex; x += body.size.x){
					hitdown = raycast_horizontalborder(VectorMath.add(body.lastPosition,new Vector2(x,bhalfsizey)),
							VectorMath.add(body.position,new Vector2(x,bhalfsizey)),
							bottom);
					if(hitdown != NULLDIST) break;
				}
				collide_down = hitdown != NULLDIST;
			}
			if(collide_right){
				for(float y = -bhalfsizey; y <= bhalfsizey; y += body.size.y){
					hitright = raycast_verticalborder(VectorMath.add(body.lastPosition,new Vector2(-bhalfsizex,y)),
							VectorMath.add(body.position,new Vector2(-bhalfsizex,y)),
							right);
					if(hitright != NULLDIST) break;
				}
				collide_right = hitright != NULLDIST;
			}
			if(collide_left){
				for(float y = -bhalfsizey; y <= bhalfsizey; y += body.size.y){
					hitleft = raycast_verticalborder(VectorMath.add(body.lastPosition,new Vector2(bhalfsizex,y)),
							VectorMath.add(body.position,new Vector2(bhalfsizex,y)),
							left);
					if(hitleft != NULLDIST) break;
				}
				collide_left = hitleft != NULLDIST;
			}

			if(collide_left && collide_right){
				collide_right = hitright < hitleft;
				collide_left = !collide_right;
			}
			if(collide_up && collide_down){
				collide_up = hitup < hitdown;
				collide_down = !collide_up;
			}
			
			if((collide_up ? hitup : hitdown) < (collide_left ? hitleft : hitright)){
				System.out.println("vert");
				collide_left = false;
				collide_right = false;
			}else{
				System.out.println("hori");
				collide_up = false;
				collide_down = false;
			}

			System.out.println(""+collide_left+":"+collide_right+":"+collide_up+":"+collide_down);

			if(collide_up)
				return new CollisionData(top,CollisionSide.Up);
			if(collide_down)
				return new CollisionData(bottom,CollisionSide.Down);
			if(collide_left)
				return new CollisionData(left,CollisionSide.Left);
			if(collide_right)
				return new CollisionData(right,CollisionSide.Right);
		}else if(!collide_up)
			return null;//Jumpable platforms only need collide_up
		else
			return new CollisionData(top,CollisionSide.Up);//Jumpables
		
		return null;
	}
	
	/////Update
	public void onUpdate(float delta){
		super.onUpdate(delta);
	}
}
