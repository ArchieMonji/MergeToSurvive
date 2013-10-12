package com.vgdc.merge;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Renderer {
	public void onRender(SpriteBatch batch, float delta);
	public void setAnimations(ArrayList<Animation> animations);
}
