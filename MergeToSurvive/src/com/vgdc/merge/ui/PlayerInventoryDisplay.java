package com.vgdc.merge.ui;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.vgdc.merge.entities.Entity;
import com.vgdc.merge.world.World;

public class PlayerInventoryDisplay extends WidgetGroup {
	private World world;
	private ArrayList<ItemPanel> itemPanels = new ArrayList<ItemPanel>();
	private Vector2 panelSize = new Vector2(50, 50);
	boolean isVerticalOrientation = true;
	private float panelPadding = 10;

	public PlayerInventoryDisplay(World world) {
		this.world = world;
	}

	public PlayerInventoryDisplay(World world, int numItems) {
		this.world = world;
		createPanels(numItems);
	}

	public PlayerInventoryDisplay(World world, int numItems, boolean isVertical) {
		this.world = world;
		this.isVerticalOrientation = isVertical;
		createPanels(numItems);
	}

	public void setPanelSize(float width, float height) {
		this.panelSize = new Vector2(width, height);
		invalidate();
		validate();
	}

	public void setPanelPadding(float pad) {
		this.panelPadding = pad;
		invalidate();
		validate();
	}

	public void setWorld(World world) {
		this.world = world;
		for (ItemPanel ip : itemPanels) {
			ip.setWorld(world);
		}
	}

	public void setOrientationVertical(boolean isVerticalOrientation) {
		this.isVerticalOrientation = isVerticalOrientation;
		invalidate();
		validate();
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		for (ItemPanel ip : itemPanels) {
			ip.act(delta);
		}
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		for (ItemPanel ip : itemPanels) {
			ip.draw(batch, parentAlpha);
		}
	}

	@Override
	public float getPrefWidth() {
		if (isVerticalOrientation) {
			return panelSize.x;
		}
		else {
			return panelSize.x * itemPanels.size() + (itemPanels.size() - 1) * panelPadding;
		}
	}

	@Override
	public float getPrefHeight() {
		if (isVerticalOrientation) {
			return panelSize.y * itemPanels.size() + (itemPanels.size() - 1) * panelPadding;
		}
		else {
			return panelSize.y;
		}
	}

	@Override
	public void layout() {
		if (isVerticalOrientation) {
			for (int i = itemPanels.size() - 1; i >= 0; i--) {
				ItemPanel ip = itemPanels.get(itemPanels.size() - i - 1);
				ip.setPosition(getX(), getY() + i * (panelSize.y + panelPadding));
				ip.setSize(panelSize.x, panelSize.y);
				ip.invalidate();
			}
		}
		else {
			for (int i = 0; i < itemPanels.size(); i++) {
				ItemPanel ip = itemPanels.get(i);
				ip.setPosition(getX() + i * (panelSize.x + panelPadding), getY());
				ip.setSize(panelSize.x, panelSize.y);
				ip.invalidate();
			}
		}
	}

	public void createPanels(int numItems) {
		itemPanels.clear();
		for (int i = 0; i < numItems; i++) {
			ItemPanel ip = new ItemPanel(world, i);
			itemPanels.add(ip);
		}
		layout();
	}

	public void setPlayer(Entity player) {
		for (ItemPanel ip : itemPanels) {
			ip.setPlayer(player);
		}
	}

	public void dispose() {
		for (ItemPanel ip : itemPanels) {
			ip.dispose();
		}
	}
}
