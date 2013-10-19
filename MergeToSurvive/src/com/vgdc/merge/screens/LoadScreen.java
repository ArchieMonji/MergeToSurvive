package com.vgdc.merge.screens;

import com.vgdc.merge.MainGame;
import com.vgdc.merge.assets.AssetsHandler;

public class LoadScreen extends AbstractScreen {

	public LoadScreen(MainGame game) {
		super(game);
	}

	@Override
	public void render(float delta) {
		if(game.getHandler().update())
		{
			game.setScreen(new TestGameScreen(game));
			return;
		}

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		game.getHandler().loadAll();

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
