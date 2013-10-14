package com.vgdc.merge.screens;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.vgdc.merge.MainGame;
import com.vgdc.merge.utils.tweens.SpriteTween;

public class TitleScreen extends AbstractScreen {
	public Texture splashTexture;
	public SpriteBatch batch;
	public Sprite splashSprite;
	public TweenManager tweenManager;
	public BitmapFont font;
	public Sprite continueSprite;
	public OrthographicCamera camera;

	public TitleScreen(MainGame game) {
		super(game);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
			game.setScreen(new GameScreen(game));
			return;
		}

		batch.setProjectionMatrix(camera.combined);
		camera.update();

		tweenManager.update(delta);

		batch.begin();
		splashSprite.draw(batch);
		font.setColor(continueSprite.getColor());
		font.draw(batch, "Press any key to continue", continueSprite.getX(),
				continueSprite.getY());
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		//camera.viewportWidth = width;
		//camera.viewportHeight = height;
	}

	@Override
	public void show() {
		int w = Gdx.graphics.getWidth();
		int h = Gdx.graphics.getHeight();
		batch = new SpriteBatch();
		camera = new OrthographicCamera(w, h);

		splashTexture = new Texture(
				Gdx.files.internal("data/images/splashscreen.png"));
		splashTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		splashSprite = new Sprite(splashTexture);

		camera.position.x = splashTexture.getWidth() / 2;
		camera.position.y = splashTexture.getHeight() / 2;

		tweenManager = new TweenManager();
		Tween.registerAccessor(Sprite.class, new SpriteTween());

		TweenCallback callback = new TweenCallback() {

			@Override
			public void onEvent(int type, BaseTween<?> source) {
				splashTweenCompleted();
			}

		};

		splashSprite.setColor(1, 1, 1, 0);
		Tween.to(splashSprite, SpriteTween.ALPHA, 2f)
				.target(1)
				.ease(TweenEquations.easeInQuad)
				// .repeatYoyo(1, 2)
				.setCallback(callback)
				.setCallbackTriggers(TweenCallback.COMPLETE)
				.start(tweenManager);

		continueSprite = new Sprite();
		font = new BitmapFont(Gdx.files.internal("data/fonts/consolas.fnt"),
				false);
		TextBounds textBounds = font.getBounds("Press any key to continue");
		continueSprite.setPosition(splashTexture.getWidth() / 2
				- textBounds.width / 2, splashTexture.getHeight() / 5);
		continueSprite.setColor(1, 1, 1, 0);
	}

	private void splashTweenCompleted() {
		Gdx.app.log("SplashScreen.show()", "Tween Completed");
		Tween.to(continueSprite, SpriteTween.ALPHA, 1f).target(1)
				.ease(TweenEquations.easeInQuad)
				.repeatYoyo(Tween.INFINITY, 0.5f).start(tweenManager);
	}

	@Override
	public void hide() {

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
		batch.dispose();
		splashTexture.dispose();
		font.dispose();
	}

}
