package com.vgdc.merge.math;

import com.badlogic.gdx.math.Vector2;

public abstract class VectorMath {//Static
    static final float pi = (float)Math.PI;
    
    public float x,y;
    
    //Static values
    public static Vector2 zero(){return new Vector2();}
    public static Vector2 one(){return new Vector2(1,1);}
    
    //Methods
    public static boolean equals(Vector2 v1,Vector2 v2){return distance(v1,v2) < .1f;}
    
    public static float distance(Vector2 v1,Vector2 v2){return magnitude(sub(v1,v2));}
    public static boolean distanceLessThan(Vector2 v1,Vector2 v2,float dist){//Optimized
    	return magnitude(new Vector2(v1.x-v2.x,v1.y-v2.y)) < dist;
    	/*
    	float x = v1.x-v2.x;
    	if(Math.abs(x) >= dist) return false;
    	float y = v2.y-v1.y;
    	return Math.abs(y) < dist && x*x+y*y < dist*dist;*/
    }
    	
    public static float magnitude(Vector2 v){return (float)Math.sqrt(v.x*v.x+v.y*v.y);}
    
    //Use radians
    public static float toAngle(Vector2 v){
    	float r = (float)Math.atan(v.y/v.x);
    	return v.x < 0 ? pi+r : r;
    }
    public static float toAngle(Vector2 v1,Vector2 v2){
    	return toAngle(sub(v2,v1));
    }
    public static Vector2 fromAngle(float angle){return new Vector2((float)Math.cos(angle),(float)Math.sin(angle));}
    public static Vector2 fromAngle(float angle,float magnitude){return mul(fromAngle(angle),magnitude);}
    
    //Gets a Vector2 between two points
    public static Vector2 lerp(Vector2 v1,Vector2 v2,float t){return add(v1,mul(sub(v2,v1),t));}
    
    //Normalize
    //Gives a vector that will have a length of one.
    //Example:
    //Vector2.normalize(new Vector2(0,10)).magnitude() -->1
    //Vector2.normalize(new Vector2(23409,234987)).magnitude() -->1
    public static Vector2 normalize(Vector2 v){return fromAngle(toAngle(v));}
    public static Vector2 normalize(Vector2 v,float magnitude){return mul(normalize(v),magnitude);}//Will multiply for you
    public static Vector2 normalize(Vector2 v1,Vector2 v2){return normalize(sub(v2,v1));}
    
    //Rotates a vector
    public static Vector2 rotate(Vector2 v,float angle){return mul(fromAngle(toAngle(v)+angle),magnitude(v));}
    
    //Arithmetic
    public static Vector2 add(Vector2 v1,Vector2 v2){return new Vector2(v1.x+v2.x,v1.y+v2.y);}
    public static Vector2 sub(Vector2 v1,Vector2 v2){return new Vector2(v1.x-v2.x,v1.y-v2.y);}
    public static Vector2 mul(Vector2 v1,Vector2 v2){return new Vector2(v1.x*v2.x,v1.y*v2.y);}
    public static Vector2 div(Vector2 v1,Vector2 v2){return new Vector2(v1.x/v2.x,v1.y/v2.y);}
    public static Vector2 add(Vector2 v1,float f){return new Vector2(v1.x+f,v1.y+f);}
    public static Vector2 sub(Vector2 v1,float f){return new Vector2(v1.x-f,v1.y-f);}
    public static Vector2 mul(Vector2 v1,float f){return new Vector2(v1.x*f,v1.y*f);}
    public static Vector2 div(Vector2 v1,float f){return new Vector2(v1.x/f,v1.y/f);}
    
    //Getting line collisions with horizontal/vertical lines
    public static Vector2 getLineCollision_Horizontal(Vector2 p1,Vector2 p2,float y){
    	float disttoy = y-p1.y;
    	float slope = (p2.x-p1.x)/(p2.y-p1.y);
    	return new Vector2(p1.x+disttoy*slope,y);
    }
    public static Vector2 getLineCollision_Vertical(Vector2 p1,Vector2 p2,float x){
    	float disttox = x-p1.x;
    	float slope = (p2.y-p1.y)/(p2.x-p1.x);
    	return new Vector2(x,p1.y+disttox*slope);
    }
    
    //
    public static Vector2 minVector(Vector2 v1,Vector2 v2){
    	return new Vector2(v1.x < v2.x ? v1.x : v2.x,v1.y < v2.y ? v1.y : v2.y);
    }
    public static Vector2 maxVector(Vector2 v1,Vector2 v2){
    	return new Vector2(v1.x >= v2.x ? v1.x : v2.x,v1.y >= v2.y ? v1.y : v2.y);
    }
}
