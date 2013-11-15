package com.vgdc.merge.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.vgdc.merge.MainGame;

public class LoadScreen extends AbstractScreen {
	
	private static final String[] stateLookup = new String[] { "Loading", "Loading.", "Loading..", "Loading..." };
	private static final float stateDuration = 0.25f;
	
	private Sprite loadingSprite;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private BitmapFont font;
	private int state;
	private float stateTime = stateDuration;

	public LoadScreen(MainGame game) {
		super(game);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		if(game.getHandler().update())
		{
			game.setScreen(new TitleScreen(game));
			return;
		}
		stateTime-=delta;
		if(stateTime<=0)
		{
			stateTime+=stateDuration;
			if(++state>=stateLookup.length)
				state = 0;
		}
		batch.setProjectionMatrix(camera.combined);
		camera.update();


		batch.begin();
		font.draw(batch, stateLookup[state], loadingSprite.getX(),
				loadingSprite.getY());
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		
		int w = Gdx.graphics.getWidth();
		int h = Gdx.graphics.getHeight();
		batch = new SpriteBatch();
		camera = new OrthographicCamera(w, h);


		camera.position.x = w / 2;
		camera.position.y = h / 2;



		loadingSprite = new Sprite();
		font = new BitmapFont(Gdx.files.internal("data/fonts/consolas.fnt"),
				false);
		font.setScale(2);
		TextBounds textBounds = font.getBounds("Loading...");
		loadingSprite.setPosition(camera.position.x
				- textBounds.width / 2, camera.position.y - textBounds.height / 2);
		
		//game.getHandler().loadAll();
		game.getHandler().loadBasedOnDescription("assets/test_descriptor.json");
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

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
