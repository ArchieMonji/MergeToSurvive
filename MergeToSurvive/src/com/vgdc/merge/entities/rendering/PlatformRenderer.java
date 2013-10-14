package com.vgdc.merge.entities.rendering;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class PlatformRenderer extends BaseRenderer{
	
	private NinePatch patch;
	
	public void setNinePatch(NinePatch nPatch)
	{
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
