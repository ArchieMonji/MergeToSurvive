package com.vgdc.merge.entities.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;
import com.vgdc.merge.entities.BaseEntity;
import com.vgdc.merge.entities.physics.PhysicsBody;
import com.vgdc.merge.world.World;

public class HitboxRenderer {
	private World world;
	private BitmapFont font;
	private ShapeRenderer sr = new ShapeRenderer();
	private SpriteBatch batch = new SpriteBatch();
	private boolean shouldRenderPositions;
	private int decimalPlaces;
	private boolean shouldRenderFPS;
	private boolean shouldRenderHitboxes;

	public HitboxRenderer(World world) {
		this.world = world;
		font = new BitmapFont(Gdx.files.internal("data/fonts/consolas.fnt"), false);

		font.setColor(Color.RED);

		decimalPlaces = 2;
		shouldRenderPositions = true;
		shouldRenderFPS = true;
	}

	public void onRender(Camera camera) {
		batch.setProjectionMatrix(camera.combined);

		if (shouldRenderHitboxes) {
			renderHitboxes(camera);
		}

		if (shouldRenderPositions) {
			renderEntityPositions(camera);
		}

		if (shouldRenderFPS) {
			renderFPS(camera);
		}
	}
	
	public void renderHitboxes(boolean shouldRenderHitboxes) {
		this.shouldRenderHitboxes = shouldRenderHitboxes;
	}

	private void renderHitboxes(Camera camera) {
		sr.setProjectionMatrix(camera.combined);
		sr.begin(ShapeType.Rectangle);

		sr.setColor(Color.RED);
		for (BaseEntity entity : world.getEntityManager().getEntities()) {
			PhysicsBody body = entity.getPhysicsBody();
			float x = body.getPosition().x;
			float y = body.getPosition().y;
			float w = body.getSize().x;
			float h = body.getSize().y;

			sr.rect(x - w / 2, y - h / 2, w, h);
		}
		sr.end();
	}
	public void renderFPS(boolean shouldRenderFPS) {
		this.shouldRenderFPS = shouldRenderFPS;
	}

	public void setDecimalPlaces(int decimalPlaces) {
		this.decimalPlaces = decimalPlaces;
	}
	
	public void renderEntityPositions(boolean shouldRenderPositions) {
		this.shouldRenderPositions = shouldRenderPositions;
	}

	private void renderEntityPositions(Camera camera) {
		this.batch.setProjectionMatrix(camera.combined);
		this.batch.begin();
		for (BaseEntity entity : world.getEntityManager().getEntities()) {
			PhysicsBody body = entity.getPhysicsBody();
			float x = body.getPosition().x;
			float y = body.getPosition().y;
			float h = body.getSize().y;

			CharSequence msg = String.format("[%." + decimalPlaces + "f, %." + decimalPlaces + "f]", x, y);

			TextBounds bounds = font.getBounds(msg);
			font.draw(this.batch, msg, x - bounds.width / 2, y - h / 2);
		}
		float mouseX = Gdx.input.getX();
		float mouseY = Gdx.input.getY();
		Vector3 mCoord = new Vector3(mouseX, mouseY, 0);
		camera.unproject(mCoord);

		CharSequence msg = String.format("[%." + decimalPlaces + "f, %." + decimalPlaces + "f]", mCoord.x, mCoord.y);
		TextBounds bounds = font.getBounds(msg);
		font.draw(this.batch, msg, mCoord.x, mCoord.y + bounds.height);

		this.batch.end();
	}

	private void renderFPS(Camera camera) {
		batch.begin();
		Vector3 coord = new Vector3(0, Gdx.graphics.getHeight(), 0);
		// camera.unproject(coord);
		// render fps topleft
		font.draw(batch, "fps:" + Gdx.graphics.getFramesPerSecond(), coord.x, coord.y);
		batch.end();
	}

	public void dispose() {
		sr.dispose();
		font.dispose();
	}

}
