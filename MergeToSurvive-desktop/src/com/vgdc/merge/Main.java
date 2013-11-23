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
		
		if(args!=null&&args.length>0)
		{
			if(args.length>=2)
			{
				if(args[0]=="-level")
				{
					
				}
				else if(args[0]=="-act")
				{
					
				}
				else
				{
					//error or something
				}
			}
			else
			{
				//error or something
			}
		}
		else
			new LwjglApplication(new MainGame(), cfg);
	}
}
