package com.vgdc.merge.screens;

import com.vgdc.merge.MainGame;
import com.vgdc.merge.assets.AssetsHandler;

public class LoadScreen extends AbstractScreen {
	
	private AssetsHandler handler;

	public LoadScreen(MainGame game, AssetsHandler handler) {
		super(game);
		this.handler = handler;
	}

	@Override
	public void render(float delta) {
		if(handler.update())
		{
			
		}

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

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
