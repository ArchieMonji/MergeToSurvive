package com.vgdc.merge;

import com.badlogic.gdx.Game;
import com.vgdc.merge.assets.AssetsHandler;
import com.vgdc.merge.entities.EntityData;
import com.vgdc.merge.screens.FinishLoadingAction;
import com.vgdc.merge.screens.GameScreen;
import com.vgdc.merge.screens.LoadScreen;
import com.vgdc.merge.world.level.Act;
import com.vgdc.merge.world.level.LevelData;

public class MainGame extends Game {
	
	private AssetsHandler handler;
	//temporary, move somewhere else later, maybe
	private String player;
	private String level;
	private String act;
	
	private static class FinishLoadingLevel implements FinishLoadingAction
	{
		private String player;
		private String level;
		public FinishLoadingLevel(String level, String player)
		{
			this.level = level;
			this.player = player;
		}
		@Override
		public void finishedLoading(MainGame game) {
			LevelData data = game.getHandler().getLevelData(level);
			EntityData play = game.getHandler().getEntityData(player);
			Act act = new Act();
			act.addLevel(data);
			GameScreen screen = new GameScreen(game, act);
			screen.setPlayerData(play);
			game.setScreen(screen);
		}
	}
	
	private static class FinishLoadingAct implements FinishLoadingAction
	{
		private String player;
		private String act;
		public FinishLoadingAct(String act, String player)
		{
			this.act = act;
			this.player = player;
		}
		@Override
		public void finishedLoading(MainGame game) {
			EntityData play = game.getHandler().getEntityData(player);
			Act act = game.getHandler().getAct(this.act);
			GameScreen screen = new GameScreen(game, act);
			screen.setPlayerData(play);
			game.setScreen(screen);
		}
	}
	@Override
	public void create() {
		handler  = new AssetsHandler();
		if(level!=null)
		{
			handler.loadEntity(player);
			handler.loadBasedOnLevel(level);
			setScreen(new LoadScreen(this, new FinishLoadingLevel(level, player)));
		}
		else if(act!=null)
		{
			handler.loadEntity(player);
			handler.loadBasedOnAct(act);
			setScreen(new LoadScreen(this, new FinishLoadingAct(level, player)));
		}
		else
		{
			handler.loadBasedOnDescription("assets/test_descriptor.json");
			setScreen(new LoadScreen(this));
		}
	}
	
	public MainGame setPlayer(String player)
	{
		this.player = player;
		return this;
	}
	
	public MainGame setLevel(String level)
	{
		this.level = level;
		return this;
	}
	
	public MainGame setAct(String act)
	{
		this.act = act;
		return this;
	}
	
	public AssetsHandler getHandler()
	{
		return handler;
	}
}
