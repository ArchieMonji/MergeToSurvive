package com.vgdc.merge;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class BaseEntity {
	
	
	public abstract void onUpdate(float delta);
	public abstract void onRender(SpriteBatch batch, float delta);
	public abstract Vector2 getPosition();
	public abstract void setPosition(Vector2 nVector);
}
