package com.vgdc.merge;

import com.badlogic.gdx.Game;
import com.vgdc.merge.assets.Assets;
import com.vgdc.merge.screens.TitleScreen;

public class MainGame extends Game {
	
	private Assets assets;
	@Override
	public void create() {
		assets = new Assets();
		assets.loadAssets("data/test/another_test.json");
		setScreen(new TitleScreen(this));
	}
	
	public Assets getAssets()
	{
		return assets;
	}
}
