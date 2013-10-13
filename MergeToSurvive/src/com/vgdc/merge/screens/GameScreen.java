package com.vgdc.merge.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.vgdc.merge.MainGame;
import com.vgdc.merge.assets.JsonTest;

public class GameScreen extends AbstractScreen {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private Sprite sprite;

	public GameScreen(MainGame game) {
		super(game);
	}

	@Override
	public void dispose() {
		batch.dispose();
		texture.dispose();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		sprite.translate(1,0);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		sprite.draw(batch);
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

		camera = new OrthographicCamera(w,h);
		batch = new SpriteBatch();

		texture = new Texture(Gdx.files.internal("data/libgdx.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		TextureRegion region = new TextureRegion(texture, 0, 0, 512, 275);

		sprite = new Sprite(region);
		sprite.setPosition(-sprite.getWidth()/2, -sprite.getHeight()/2);
		JsonTest test = new JsonTest();
		test.runTest();
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}
}
