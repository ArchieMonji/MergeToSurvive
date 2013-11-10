package com.vgdc.merge.entities.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class PlatformRenderer extends BaseRenderer{
	private NinePatch patch;
	
	public PlatformRenderer(){}
//	public PlatformRenderer(String texturepath,int left,int right,int top,int bottom){
//		setNinePatchFrom(texturepath,left,right,top,bottom);
//	}
	
	public PlatformRenderer(Texture texture, int left, int right, int top, int bottom){
		setNinePatchFrom(texture, left, right, top, bottom);
	}
	
	public void setNinePatchFrom(String texturepath,int left,int right,int top,int bottom){
		setNinePatchFrom(new Texture(Gdx.files.internal(texturepath)),left,right,top,bottom);
	}
	
	public void setNinePatchFrom(Texture texture,int left,int right,int top,int bottom){
		setNinePatch(new NinePatch(texture,left,right,top,bottom));
	}
	
	public void setNinePatch(NinePatch nPatch){
		patch = nPatch;
	}
	
	public NinePatch getNinePatch()
	{
		return patch;
	}

	@Override
	public void onRender(SpriteBatch batch, float delta) {
		Vector2 pos = entity.getPosition();
		Vector2 dim = entity.getPhysicsBody().getSize();
		patch.draw(batch, pos.x - dim.x/2, pos.y - dim.y/2, dim.x, dim.y);
		
	}

}
