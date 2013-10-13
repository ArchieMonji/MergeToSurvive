package com.vgdc.merge;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class World {
	private EntityManager entityManager = new EntityManager();
	
	private OrthographicCamera camera;
	
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
		for(BaseEntity e : entityManager.getEntities())
			e.onUpdate(Gdx.graphics.getDeltaTime());
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
