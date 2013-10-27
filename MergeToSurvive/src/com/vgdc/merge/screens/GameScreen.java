package com.vgdc.merge.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.vgdc.merge.MainGame;
import com.vgdc.merge.entities.Entity;
import com.vgdc.merge.entities.Item;
import com.vgdc.merge.entities.Platform;
import com.vgdc.merge.entities.physics.PlatformType;
import com.vgdc.merge.entities.rendering.PlatformRenderer;
import com.vgdc.merge.event.DialogueBoxEvent;
import com.vgdc.merge.event.EventSystem;
import com.vgdc.merge.ui.UIManager;
import com.vgdc.merge.world.World;

public class GameScreen extends AbstractScreen {
	private World myWorld;
	private UIManager uiManager;

	public GameScreen(MainGame game) {
		super(game);

	}

	@Override
	public void dispose() {
		myWorld.dispose();
	}

	@Override
	public void render(float delta) {
		if (delta < 0.05f) {
			Gdx.gl.glClearColor(1, 1, 1, 1);
			Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			myWorld.onUpdate(delta);
			myWorld.onRender(delta);
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
		
		uiManager = new UIManager(myWorld);
		myWorld.setUIManager(uiManager);

		Entity testEntity = null;
		testEntity = new Entity(game.getAssets().entityDataMap.get("hugging_enemy"), myWorld);
		testEntity.setPosition(new Vector2(500, 0));
		myWorld.getEntityManager().addEntity(testEntity);
		testEntity = new Entity(game.getAssets().entityDataMap.get("testplayer"), myWorld);
		testEntity.setPosition(new Vector2(58, 58));
		testEntity.getMovingBody().setElasticity(0);
		myWorld.getEntityManager().addEntity(testEntity);
		myWorld.setPlayer(testEntity);

		Item item = new Item(game.getAssets().entityDataMap.get("testitem"), myWorld);
		item.setPosition(new Vector2(450, 275));
		item.getMovingBody().setElasticity(0);
		item.getController().onCreate();
		myWorld.getEntityManager().addEntity(item);

		item = new Item(game.getAssets().entityDataMap.get("spear_i_e"), myWorld);
		item.setPosition(new Vector2(650, 275));
		item.getMovingBody().setElasticity(0);
		item.getController().onCreate();
		myWorld.getEntityManager().addEntity(item);

		for (Vector2 pos : new Vector2[] { new Vector2(250, 50), new Vector2(650, 50), new Vector2(750, 500),
				new Vector2(-50/* 650 */, 300) }) {
			Platform platform = new Platform(myWorld);
			platform.getPlatformBody().setPlatformType(PlatformType.Rectangle);
			platform.setRenderer(new PlatformRenderer("data/test/PlatformTest.png", 13, 13, 13, 13));
			platform.getPhysicsBody().setPosition(pos);
			platform.getPhysicsBody().setSize(new Vector2(200, 50));
			myWorld.getEntityManager().addEntity(platform);
		}
		// Tall platform
		Platform platform = new Platform(myWorld);
		platform.getPlatformBody().setPlatformType(PlatformType.Rectangle);
		platform.setRenderer(new PlatformRenderer("data/test/PlatformTest.png", 13, 13, 13, 13));
		platform.getPhysicsBody().setPosition(new Vector2(400, 75));
		platform.getPhysicsBody().setSize(new Vector2(50, 100));
		myWorld.getEntityManager().addEntity(platform);

		EventSystem system = myWorld.getEventSystem();
		// event to be played when entity dies
		// this event is a dialogue even which will bring up a dialogue box
		DialogueBoxEvent welcomeDialogEvent = new DialogueBoxEvent("GameScreen: welcomeDialogEvent");
		// dialogue script is the script that will be read by the dialogue
		// box
		welcomeDialogEvent.dialogueScript = "data/dialogue/dialogue_box_test.json";
		welcomeDialogEvent.position = new Vector2(0, h);
		system.addEvent(welcomeDialogEvent);

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}
}
