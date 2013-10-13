package com.vgdc.merge;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.vgdc.merge.test.TestMainGame;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "MergeToSurvive";
		cfg.useGL20 = true;
		cfg.width = 480;
		cfg.height = 320;
		
		new LwjglApplication(new MainGame(), cfg);
	}
}
