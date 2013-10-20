package com.vgdc.merge.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.vgdc.merge.assets.Assets;
import com.vgdc.merge.assets.AssetsHandler;
import com.vgdc.merge.entities.BaseEntity;

public class World {
	private EntityManager entityManager = new EntityManager();
	
	private OrthographicCamera camera;
	
	private AssetsHandler handler;
	
	//private Assets assets;
	
	public Vector2 dimensions = new Vector2(800,600);
	
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
		camera.update();
		for(BaseEntity e : entityManager.getEntities())
			e.onUpdate(Gdx.graphics.getDeltaTime());
	}
	
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
	
	public OrthographicCamera getCamera()
	{
		return camera;
	}
	
	public void setDimensions(int width, int height)
	{
		dimensions.x = width;
		dimensions.y = height;
	}
	
	public void setCamera(OrthographicCamera camera)
	{
		this.camera = camera;
	}
	
}
