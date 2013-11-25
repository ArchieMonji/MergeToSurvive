package com.vgdc.merge.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.vgdc.merge.MainGame;
import com.vgdc.merge.entities.Entity;
import com.vgdc.merge.entities.EntityData;
import com.vgdc.merge.ui.UIManager;
import com.vgdc.merge.world.World;
import com.vgdc.merge.world.level.Act;

public class GameScreen extends AbstractScreen {
	private World myWorld;
	private UIManager uiManager;
	private Act currentAct;
	private EntityData playerData;

	public GameScreen(MainGame game, Act act) {
		super(game);
		currentAct = act;
	}

	@Override
	public void dispose() {
		myWorld.dispose();
	}

	@Override
	public void render(float delta) {
		if (delta < 1){//0.05f) {
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
		
		myWorld = new World(currentAct);
		myWorld.setCamera(new OrthographicCamera(800, 600));
		myWorld.setHandler(game.getHandler());
		uiManager = new UIManager(myWorld);
		myWorld.setUIManager(uiManager);
		Entity testEntity = new Entity(playerData, myWorld);
		testEntity.getMovingBody().setElasticity(0);
		myWorld.setPlayer(testEntity);

		myWorld.getCurrentAct().setLevel(0);
		
		Music bgm = Gdx.audio.newMusic(Gdx.files.internal("data/test/Savant - Pirate Bay feat Twistex (Alchemist).mp3"));
		bgm.setLooping(true);
		bgm.play();
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}
	
	public void setAct(Act act)
	{
		currentAct = act;
	}
	
	public void setPlayerData(EntityData data)
	{
		playerData = data;
	}
}
