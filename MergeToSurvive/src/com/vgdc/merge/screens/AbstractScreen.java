package com.vgdc.merge.screens;

import com.badlogic.gdx.Screen;
import com.vgdc.merge.MainGame;

public abstract class AbstractScreen implements Screen {
	protected MainGame game;

	public AbstractScreen(MainGame game) {
		this.game = game;
	}
}
