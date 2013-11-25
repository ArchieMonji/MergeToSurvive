package com.vgdc.merge;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "MergeToSurvive";
		cfg.useGL20 = true;
		cfg.width = 800;
		cfg.height = 600;
		
		String player = "test_player";
		String act = null;
		String level = null;
		
		if(args!=null)
		{
			for(int i = 0; i < args.length; i++)
			{
				if("-level".equals(args[i]))
					level = args[++i];
				else if("-act".equals(args[i]))
					act = args[++i];
				else if("-player".equals(args[i]))
					player = args[++i];
			}
		}
		System.out.println(player);
		System.out.println(level);
		System.out.println(act);
		MainGame game = new MainGame();
		game.setPlayer(player);
		game.setAct(act);
		game.setLevel(level);
		new LwjglApplication(game, cfg);
	}
}
