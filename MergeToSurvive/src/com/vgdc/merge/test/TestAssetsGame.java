package com.vgdc.merge.test;


import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.vgdc.merge.assets.Assets;
import com.vgdc.merge.entities.Entity;
import com.vgdc.merge.entities.rendering.Renderer;

public class TestAssetsGame implements ApplicationListener{
	
	private static Entity testEntity;
	
	private static SpriteBatch batch;

	@Override
	public void create() {
		batch = new SpriteBatch();
		
		Assets assets = new Assets();
		assets.loadAssets("data/test/assets_list.json");
		
		
		
		testEntity = new Entity(assets.entityDataMap.get("player"),null);
		//testEntity.setPhysicsBody(new TestPhysicsBody());
		System.out.println(assets.entityDataMap.get("player").animations);
		testEntity.setRenderer(new Renderer());
		//testEntity.getRenderer().setAnimations(assets.entityDataMap.get("player").animations);
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
