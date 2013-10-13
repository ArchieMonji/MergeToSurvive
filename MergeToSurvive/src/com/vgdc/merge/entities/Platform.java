package com.vgdc.merge.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Platform extends BaseEntity {

	@Override
	public void onUpdate(float delta) {
		

	}

	@Override
	public void onRender(SpriteBatch batch, float delta) {
		getRenderer().onRender(batch, delta);

	}

}
