package com.vgdc.merge.ui;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.vgdc.merge.entities.Entity;
import com.vgdc.merge.world.World;

public class PlayerMoodDisplay extends Widget {
	private World world;
	private Entity player;

	private Texture borderTexture;
	private Texture barBgTexture;
	private Texture mood;
	private Vector2 borderSize = new Vector2(4, 4);
	private HashMap<String, Texture> moods = new HashMap<String, Texture>();

	public PlayerMoodDisplay(World world) {
		this.world = world;
		borderTexture = new Texture("data/ui/moods/border.png");
		mood = new Texture("data/ui/moods/proto_neutral.png");
	}

	public void setPlayer(Entity player) {
		this.player = player;
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		batch.setColor(1, 1, 1, parentAlpha);

		// draw border
		batch.draw(borderTexture, getX(), getY(), getWidth(), getHeight());

		// draw image animation
		if (player != null) {
			if (mood != null) {
				batch.draw(mood, getX() + borderSize.x, getY() + borderSize.y, getWidth() - 2 * borderSize.x,
						getHeight() - 2 * borderSize.y);
			}
		}
	}

	public void dispose() {
		borderTexture.dispose();
		barBgTexture.dispose();
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public void setMood(String moodName) {
		this.mood = moods.get(moodName);
	}
}
