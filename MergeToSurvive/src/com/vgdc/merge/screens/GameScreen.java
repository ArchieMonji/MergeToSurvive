package com.vgdc.merge.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.vgdc.merge.MainGame;
import com.vgdc.merge.entities.Entity;
import com.vgdc.merge.world.World;

public class GameScreen extends AbstractScreen {
	private SpriteBatch batch;
	
	private World myWorld;

	public GameScreen(MainGame game) {
		super(game);
		
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		myWorld.onUpdate();
		myWorld.onRender(batch);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void show() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		myWorld = new World();
		myWorld.setCamera(new OrthographicCamera(480,600));
		myWorld.getCamera().position.x = 240;
		myWorld.getCamera().position.y = 300;
		myWorld.setAssets(game.getAssets());
		myWorld.setDimensions(480, 600);
		
		batch = new SpriteBatch();
		
		Entity testEntity = null;
		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				testEntity = new Entity(game.getAssets().entityDataMap.get("testenemy"), myWorld);
				testEntity.setPosition(new Vector2(-58 + 58*i, -58+58*j));
				myWorld.getEntityManager().addEntity(testEntity);
			}
		}
		testEntity = new Entity(game.getAssets().entityDataMap.get("testplayer"), myWorld);
		testEntity.setPosition(new Vector2(58, 58));
		myWorld.getEntityManager().addEntity(testEntity);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}
}
