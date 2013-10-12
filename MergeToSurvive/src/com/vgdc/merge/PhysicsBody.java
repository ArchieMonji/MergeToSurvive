package com.vgdc.merge;

import com.badlogic.gdx.math.Vector2;

public interface PhysicsBody {
	public Vector2 getPosition();
	public void setPosition(Vector2 position);
	public void onUpdate(float delta);
}
