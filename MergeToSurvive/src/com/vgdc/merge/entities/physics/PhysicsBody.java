package com.vgdc.merge.entities.physics;

import com.badlogic.gdx.math.Vector2;

public interface PhysicsBody {
	public Vector2 getPosition();
	public void setPosition(Vector2 position);
	public void onUpdate(float delta);
}
