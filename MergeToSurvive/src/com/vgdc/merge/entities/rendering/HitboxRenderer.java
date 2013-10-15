package com.vgdc.merge.entities.rendering;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.vgdc.merge.entities.BaseEntity;
import com.vgdc.merge.entities.physics.PhysicsBody;
import com.vgdc.merge.world.World;

public class HitboxRenderer extends BaseRenderer{
	private World world;
	
	public HitboxRenderer(World world) {
		this.world = world;
	}

	ShapeRenderer sr = new ShapeRenderer();
	
	@Override
	public void onRender(SpriteBatch batch, float delta) {
		sr.setProjectionMatrix(batch.getProjectionMatrix());
		sr.begin(ShapeType.Rectangle);
		sr.setColor(Color.RED);
		for(BaseEntity entity: world.getEntityManager().getEntities()){
			PhysicsBody body = entity.getPhysicsBody();
			float x = body.getPosition().x;
			float y = body.getPosition().y;
			float w = body.getSize().x;
			float h = body.getSize().y;
			sr.rect(x-w/2, y-h/2, w, h);
		}
		sr.end();

	}

	public void dispose(){
		sr.dispose();
	}



}
