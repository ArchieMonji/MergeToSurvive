package com.vgdc.merge.test;

import com.badlogic.gdx.math.Vector2;
import com.vgdc.merge.PhysicsBody;

public class TestPhysicsBody implements PhysicsBody {
	
	private Vector2 position = new Vector2();
	
	@Override
	public Vector2 getPosition() {
		return position;
	}

	@Override
	public void setPosition(Vector2 position) {
		this.position = position;

	}

	@Override
	public void onUpdate(float delta) {
		//intentionally left blank
	}

}
