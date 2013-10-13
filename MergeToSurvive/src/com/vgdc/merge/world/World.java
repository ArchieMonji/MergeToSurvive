package com.vgdc.merge.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.vgdc.merge.assets.Assets;
import com.vgdc.merge.entities.BaseEntity;

public class World {
	private EntityManager entityManager = new EntityManager();
	
	private OrthographicCamera camera;
	
	private Assets assets;
	
	public EntityManager getEntityManager(){
		return entityManager;
	}
	
	public void onRender(SpriteBatch batch)
	{
		batch.setProjectionMatrix(camera.combined);
		for(BaseEntity e : entityManager.getEntities())
			e.onRender(batch, Gdx.graphics.getDeltaTime());
	}
	
	public void onUpdate()
	{
		entityManager.onUpdate();
		for(BaseEntity e : entityManager.getEntities())
			e.onUpdate(Gdx.graphics.getDeltaTime());
	}
	
	public void setAssets(Assets nAssets)
	{
		assets = nAssets;
	}
	
	public Assets getAssets()
	{
		return assets;
	}
	
	public OrthographicCamera getCamera()
	{
		return camera;
	}
	
	public void setCamera(OrthographicCamera camera)
	{
		this.camera = camera;
	}
	
}
