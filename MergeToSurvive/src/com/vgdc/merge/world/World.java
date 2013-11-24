package com.vgdc.merge.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.vgdc.merge.assets.Assets;
import com.vgdc.merge.assets.AssetsHandler;
import com.vgdc.merge.entities.BaseEntity;
import com.vgdc.merge.entities.Entity;
import com.vgdc.merge.events.EventSystem;
import com.vgdc.merge.ui.UIManager;
import com.vgdc.merge.world.level.Act;
import com.vgdc.merge.world.level.Level;

public class World {
	
	private Act currentAct;
	
	//private EntityManager entityManager = new EntityManager();
	private ParticleManager particleManager;

	private OrthographicCamera camera;

	private Assets assets;

	public Vector2 dimensions = new Vector2(800, 600);

	private SpriteBatch batch;

	private UIManager uiManager;

	private EventSystem eventSystem;

	private Entity player;
	
	private AssetsHandler handler;
	
	private boolean isRunning;
	
	private Texture background;

	public World() {
		batch = new SpriteBatch();
		eventSystem = new EventSystem(this);
		particleManager = new ParticleManager(this);
		isRunning = true;
		currentAct = new Act(this);
		currentAct.addLevel(new Level(this));
		//currentAct.setLevel(0);
	}
	
	public World(Act act) {
		batch = new SpriteBatch();
		eventSystem = new EventSystem(this);
		particleManager = new ParticleManager(this);
		isRunning = true;
		currentAct = act;
		act.setWorld(this);
		//currentAct.setLevel(0);
	}

	public EntityManager getEntityManager() {
		return currentAct.getCurrentLevel().getEntityManager();
	}

	public ParticleManager getParticleManager() {
		return particleManager;
	}

	public void onUpdate(float delta) {
		if (isRunning) {
			getEntityManager().onUpdate();
			for (BaseEntity e : getEntityManager().getEntities())
				e.onUpdate(delta);
			particleManager.onUpdate(delta);
			eventSystem.onUpdate(delta);
			camera.update();
		}
	}

	public void onRender(float delta) {
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(getBackground(), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Color c = batch.getColor();
		particleManager.onRender_Back(batch);
		batch.setColor(c);
		for (BaseEntity e : getEntityManager().getEntities()) {
			e.onRender(batch, delta);
		}
		particleManager.onRender_Front(batch);
		batch.end();
		uiManager.onRender(camera, delta);
	}

//	public void setAssets(Assets nAssets) {
//		assets = nAssets;
//	}
//
//	public Assets getAssets() {
//		return assets;
//	}
	
//	public void setAssets(Assets nAssets)
//	{
//		assets = nAssets;
//	}
//	
//	public Assets getAssets()
//	{
//		return assets;
//	}
	
	public AssetsHandler getHandler()
	{
		return handler;
	}
	
	public void setHandler(AssetsHandler handler)
	{
		this.handler = handler;
	}

	public OrthographicCamera getCamera() {
		return camera;
	}

	public void setDimensions(int width, int height) {
		dimensions.x = width;
		dimensions.y = height;
	}

	public void setCamera(OrthographicCamera camera) {
		this.camera = camera;
	}

	public void dispose() {
		assets.dispose();
		batch.dispose();
	}

	public void setUIManager(UIManager uiManager) {
		this.uiManager = uiManager;
	}

	public UIManager getUIManager() {
		return uiManager;
	}

	public EventSystem getEventSystem() {
		return eventSystem;
	}

	public Entity getPlayer() {
		return player;
	}

	public void setPlayer(Entity player) {
		this.player = player;
		uiManager.setPlayer(player);
	}

	public void stop() {
		isRunning = false;
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void run() {
		isRunning = true;
	}

	public void setBackground(Texture background) {
		this.background = background;
	}
	
	public Texture getBackground(){
		return background;
	}
	
	public Act getCurrentAct()
	{
		return currentAct;
	}
	
	public Level getCurrentLevel()
	{
		return currentAct.getCurrentLevel();
	}
}
