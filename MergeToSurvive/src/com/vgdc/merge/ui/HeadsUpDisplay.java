package com.vgdc.merge.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.vgdc.merge.entities.Entity;
import com.vgdc.merge.world.World;

public class HeadsUpDisplay extends Table {
	private World world;
	private PlayerHealthDisplay health;
	private PlayerInventoryDisplay inventory;
	public Table table;

	public HeadsUpDisplay(World world) {
		this.world = world;
		create();
	}

	private void create() {
		health = new PlayerHealthDisplay();

		inventory = new PlayerInventoryDisplay(world, 2, true);
		this.setFillParent(true);
		this.add(inventory).top().left().pad(10);
		this.add(health).top().right().size(200, 25).pad(10).expand();
		// table.row();
		this.debug();
		this.setColor(1, 1, 1, 0.50f);
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
	}

	public void setHUDTransparency(float alpha) {
		setColor(1, 1, 1, alpha);
	}

	public void setWorld(World world) {
		this.world = world;

		inventory.setWorld(world);
	}

	public void setPlayer(Entity player) {
		health.setPlayer(player);
		inventory.setPlayer(player);
	}
	
	public void dispose(){
		health.dispose();
		inventory.dispose();
	}
}
