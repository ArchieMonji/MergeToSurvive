package com.vgdc.merge.entities.rendering;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.vgdc.merge.entities.BaseEntity;

public abstract class BaseRenderer {
	
	protected BaseEntity entity;
	protected Color color = new Color(Color.WHITE);
	
	public abstract void onRender(SpriteBatch batch, float delta);
	
	public void setState(int state){
		
	}
	
	public void flip(boolean flag){
		
	}
	
	public boolean isFlipped(){
		return false;
	}
	
	public int getState(){
		return 0;
	}
	
	public void setEntity(BaseEntity entity)
	{
		this.entity = entity;
	}
	
	public void setColor(Color nColor){
		color = nColor;
	}
	
	public void setColor(float r, float g, float b, float a){
		color.set(r, g, b, a);
	}
	
	public Color getColor(){
		return color;
	}

}
