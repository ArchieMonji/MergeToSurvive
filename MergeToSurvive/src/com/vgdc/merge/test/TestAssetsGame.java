package com.vgdc.merge.test;


import java.util.ArrayList;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.vgdc.merge.Ability;
import com.vgdc.merge.Controls;
import com.vgdc.merge.Entity;
import com.vgdc.merge.EntityData;
import com.vgdc.merge.PlayerController;
import com.vgdc.merge.Renderer;
import com.vgdc.merge.assets.Assets;

public class TestAssetsGame implements ApplicationListener{
	
	private static Entity testEntity;
	
	private static SpriteBatch batch;

	@Override
	public void create() {
		batch = new SpriteBatch();
		
		Assets assets = new Assets();
		assets.loadAssets("data/test/assets_list.json");
		
		
		
		testEntity = new Entity(assets.entityDataMap.get("player"));
		testEntity.setPhysicsBody(new TestPhysicsBody());
		System.out.println(assets.entityDataMap.get("player").animations);
		testEntity.setRenderer(new Renderer());
		testEntity.getRenderer().setAnimations(assets.entityDataMap.get("player").animations);
		testEntity.setPosition(new Vector2(100, 100));
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();
		testEntity.onUpdate(Gdx.graphics.getDeltaTime());
		testEntity.onRender(batch, Gdx.graphics.getDeltaTime());
		batch.end();
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
