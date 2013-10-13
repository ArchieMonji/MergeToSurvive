package com.vgdc.merge;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Renderer {
	public ArrayList<Animation> animations = new ArrayList<Animation>();
	
	public float stateTime;
	public Animation currentAnimation;
	
	public BaseEntity entity;
	private int state; 
	private boolean flip;
	
	public void onRender(SpriteBatch batch, float delta){
		TextureRegion frame = currentAnimation.getKeyFrame(stateTime, true);
		int hh = frame.getRegionHeight()/2;
		int hw = frame.getRegionWidth()/2;
		batch.draw(frame, entity.getPosition().x-hw, entity.getPosition().y-hh, hw, hh, frame.getRegionWidth(), frame.getRegionHeight(), (flip ? -1 : 1), 1, 0);
		stateTime += delta;
	}
	public void setAnimations(ArrayList<Animation> animations){
		this.animations = animations;
	}
	
	public void setState(int state){
		this.state = state;
		this.stateTime = 0;
		currentAnimation = animations.get(state);
	}
	
	public void flip(boolean flag){
		flip = flag;
	}
	
	public boolean isFlipped(){
		return flip;
	}
	
	public int getState(){
		return state;
	}
	
	public void setEntity(BaseEntity entity)
	{
		this.entity = entity;
	}
}
