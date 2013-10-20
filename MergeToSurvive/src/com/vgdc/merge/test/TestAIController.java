package com.vgdc.merge.test;

import com.vgdc.merge.entities.Entity;
import com.vgdc.merge.entities.Item;
import com.vgdc.merge.entities.controllers.Controller;
import com.vgdc.merge.entities.controllers.UnitController;
import com.vgdc.merge.world.World;

public class TestAIController extends UnitController {

	private static final float TIME = 2;

	private float moveTime = TIME;
	private boolean left = true;

	@Override
	public Controller copy() {
		return new TestAIController();
	}

	@Override
	public void onUpdate(float delta) {
		moveTime -= delta;
		if (moveTime <= 0) {
			moveTime += TIME;
			left = !left;
		}
		if (left)
			moveLeft(delta);
		else
			moveRight(delta);
		stateMachine.affectState(delta);
	}

	public void onDamage(Entity entity) {
		super.onDamage(entity);
		if (getEntity().isDead()) {
			getEntity().getWorld().getEntityManager().removeEntity(getEntity());

			String itemDropName = getEntity().getData().itemDrop;
			if (itemDropName != null) {
				World world = getEntity().getWorld();
				Item itemDrop = new Item(world.getAssets().entityDataMap.get(itemDropName),	world);
				itemDrop.getPhysicsBody().setPosition(getEntity().getPosition());
				itemDrop.getController().onCreate();
				getEntity().getWorld().getEntityManager().addEntity(itemDrop);
			}

		}
	}

	@Override
	public void onCreate() {
		
	}

}
