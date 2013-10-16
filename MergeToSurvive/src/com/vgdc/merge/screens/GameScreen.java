package com.vgdc.merge.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.vgdc.merge.MainGame;
import com.vgdc.merge.entities.Entity;
import com.vgdc.merge.entities.Platform;
import com.vgdc.merge.entities.physics.PlatformType;
import com.vgdc.merge.entities.rendering.HitboxRenderer;
import com.vgdc.merge.entities.rendering.PlatformRenderer;
import com.vgdc.merge.world.World;

public class GameScreen extends AbstractScreen {
	public static final boolean SHOWHITBOXES = true;

	private SpriteBatch batch;

	private World myWorld;

	public GameScreen(MainGame game) {
		super(game);

	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	float max;
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		if (delta < 0.5f) {
			batch.begin();
			myWorld.onUpdate();
			myWorld.onRender(batch);
			batch.end();
			if (SHOWHITBOXES)
				hrenderer.onRender(myWorld.getCamera());
		}
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

	HitboxRenderer hrenderer;

	@Override
	public void show() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		myWorld = new World();
		myWorld.setCamera(new OrthographicCamera(800, 600));
		myWorld.getCamera().position.x = 400;
		myWorld.getCamera().position.y = 300;
		myWorld.setAssets(game.getAssets());
		myWorld.setDimensions(800, 600);

		if (SHOWHITBOXES) {
			hrenderer = new HitboxRenderer(myWorld);
			hrenderer.renderEntityPositions(true);
		}

		batch = new SpriteBatch();

		Entity testEntity = null;
		testEntity = new Entity(
				game.getAssets().entityDataMap.get("testenemy"), myWorld);
		testEntity.setPosition(new Vector2(500, 0));
		myWorld.getEntityManager().addEntity(testEntity);
		testEntity = new Entity(
				game.getAssets().entityDataMap.get("testplayer"), myWorld);
		testEntity.setPosition(new Vector2(58, 58));
		testEntity.getMovingBody().setElasticity(0);
		myWorld.getEntityManager().addEntity(testEntity);

		Entity item = new Entity(
				game.getAssets().entityDataMap.get("testitem"), myWorld);
		item.setPosition(new Vector2(450, 275));
		item.getMovingBody().setElasticity(0);
		myWorld.getEntityManager().addEntity(item);

		/*
		 * Platform platform = new Platform(myWorld); platform.setRenderer(new
		 * PlatformRenderer("data/test/Steel.png",5,5,26,26));
		 * platform.getPhysicsBody().setPosition(new Vector2(400,200));
		 * platform.getPhysicsBody().setSize(new Vector2(100,400));
		 * myWorld.getEntityManager().addEntity(platform);
		 */
		// *
		for (Vector2 pos : new Vector2[] { new Vector2(250, 50),
				new Vector2(650, 50), new Vector2(450, 200),
				new Vector2(650, 400) }) {
			Platform platform = new Platform(myWorld);
			platform.getPlatformBody().setPlatformType(PlatformType.Rectangle);
			platform.setRenderer(new PlatformRenderer(
					"data/test/PlatformTest.png", 13, 13, 13, 13));
			platform.getPhysicsBody().setPosition(pos);
			platform.getPhysicsBody().setSize(new Vector2(200, 50));
			myWorld.getEntityManager().addEntity(platform);
		}// */
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}
}
