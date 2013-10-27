package com.vgdc.merge.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.utils.Array;
import com.vgdc.merge.entities.Entity;
import com.vgdc.merge.entities.EntityData;
import com.vgdc.merge.entities.abilities.Ability;
import com.vgdc.merge.world.World;

public class ItemPanel extends Widget {
	private World world;
	private Entity player;

	private float animationTime;
	private Animation itemAnimation;
	private String currentItem;

	private Texture borderTexture;
	private Texture barBgTexture;
	private Vector2 borderSize = new Vector2(4, 4);
	private int abilityIndex;

	public ItemPanel(World world, int abilityIndex) {
		this.world = world;
		borderTexture = new Texture("data/ui/inventory/border.png");
		barBgTexture = new Texture("data/ui/inventory/background.png");
		this.abilityIndex = abilityIndex;

		// for fun
		Array<TextureRegion> frames = new Array<TextureRegion>();
		frames.add(new TextureRegion(new Texture("data/ui/inventory/glass0.png")));
		frames.add(new TextureRegion(new Texture("data/ui/inventory/glass1.png")));
		frames.add(new TextureRegion(new Texture("data/ui/inventory/glass2.png")));
		frames.add(new TextureRegion(new Texture("data/ui/inventory/glass3.png")));
		frames.add(new TextureRegion(new Texture("data/ui/inventory/glass4.png")));
		frames.add(new TextureRegion(new Texture("data/ui/inventory/glass5.png")));
		glass = new Animation(.05f, frames);

	}

	private Animation glass;
	private float glassDelay, glassTime;

	public void setPlayer(Entity player) {
		this.player = player;
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		glassDelay += delta;
		if (glassDelay > 3) {
			glassTime += delta;
			if (glassTime > glass.animationDuration) {
				glassDelay = 0;
				glassTime = 0;
			}
		}

		if (itemAnimation != null) {
			animationTime += delta;
			animationTime %= itemAnimation.animationDuration;
		}
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		batch.setColor(1, 1, 1, parentAlpha);

		// draw border
		batch.draw(borderTexture, getX(), getY(), getWidth(), getHeight());

		// draw background
		batch.draw(barBgTexture, getX() + borderSize.x, getY() + borderSize.y, getWidth() - 2 * borderSize.x,
				getHeight() - 2 * borderSize.y);

		// draw image animation
		if (player != null) {
			if (player.getAbilities().size() > abilityIndex) {
				Ability a = player.getAbilities().get(abilityIndex);
				if (a != null) {
					if (a.itemName != null) {
						if (currentItem == null || !a.itemName.equals(currentItem)) {
							EntityData itemData = world.getAssets().entityDataMap.get(a.itemName);
							itemAnimation = itemData.animations.get(abilityIndex);
							currentItem = a.itemName;
							animationTime = 0;
							System.out.println("SWAP");
						}
						if (itemAnimation != null) {
							batch.setColor(1, 1, 1, 1);

							batch.draw(itemAnimation.getKeyFrame(animationTime), getX() + borderSize.x, getY()
									+ borderSize.y, getWidth() - 2 * borderSize.x, getHeight() - 2 * borderSize.y);
						}
					}
				}
			}
		}
		batch.setColor(1, 1, 1, 1);
		batch.draw(glass.getKeyFrame(glassTime, true), getX() + borderSize.x, getY() + borderSize.y, getWidth() - 2
				* borderSize.x, getHeight() - 2 * borderSize.y);

	}

	public void dispose() {
		borderTexture.dispose();
		barBgTexture.dispose();
	}

	public void setWorld(World world) {
		this.world = world;
	}
}
