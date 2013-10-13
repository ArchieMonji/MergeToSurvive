package com.vgdc.merge;

import com.badlogic.gdx.Game;
import com.vgdc.merge.screens.TitleScreen;

public class MainGame extends Game {
	@Override
	public void create() {
		setScreen(new TitleScreen(this));
	}
}
