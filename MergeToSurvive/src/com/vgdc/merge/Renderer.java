package com.vgdc.merge;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Renderer {
	public ArrayList<Animation> animations = new ArrayList<Animation>();
	
	public float stateTime;
	public Animation currentAnimation;
	
	public Entity entity;
	private int state; 
	
	public void onRender(SpriteBatch batch, float delta){
		batch.draw(currentAnimation.getKeyFrame(stateTime), entity.getPosition().x, entity.getPosition().y);
		stateTime += delta;
	}
	public void setAnimations(ArrayList<Animation> animations){
		this.animations = animations;
	}
	
	public void setState(int state){
		this.state = state;
		this.stateTime = 0;
	}
	
	public int getState(){
		return state;
	}
}
