package com.vgdc.merge.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.vgdc.merge.assets.Assets;
import com.vgdc.merge.entities.BaseEntity;
import com.vgdc.merge.entities.Entity;
import com.vgdc.merge.events.EventSystem;
import com.vgdc.merge.ui.UIManager;

public class World {
	private EntityManager entityManager = new EntityManager();

	private OrthographicCamera camera;

	private Assets assets;

	public Vector2 dimensions = new Vector2(800, 600);

	private SpriteBatch batch;

	private UIManager uiManager;

	private EventSystem eventSystem;

	private Entity player;
	
	public World() {
		batch = new SpriteBatch();
		eventSystem = new EventSystem(this);
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void onRender(float delta) {
		batch.begin();
		batch.setProjectionMatrix(camera.combined);
		for (BaseEntity e : entityManager.getEntities()) {
			e.onRender(batch, delta);
		}
		batch.end();

		uiManager.onRender(camera, delta);
	}

	public void onUpdate(float delta) {
		entityManager.onUpdate();
		camera.update();
		for (BaseEntity e : entityManager.getEntities())
			e.onUpdate(delta);
		eventSystem.onUpdate(delta);
	}

	public void setAssets(Assets nAssets) {
		assets = nAssets;
	}

	public Assets getAssets() {
		return assets;
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
}
