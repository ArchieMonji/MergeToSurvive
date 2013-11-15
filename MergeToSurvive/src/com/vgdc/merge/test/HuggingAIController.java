package com.vgdc.merge.test;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.vgdc.merge.entities.Entity;
import com.vgdc.merge.entities.EntityData;
import com.vgdc.merge.entities.Item;
import com.vgdc.merge.entities.Platform;
import com.vgdc.merge.entities.controllers.Controller;
import com.vgdc.merge.entities.controllers.UnitController;
import com.vgdc.merge.events.DialogueBubbleEvent;
import com.vgdc.merge.events.Event;
import com.vgdc.merge.events.EventSystem;
import com.vgdc.merge.world.World;

public class HuggingAIController extends UnitController {
	@Override
	public Controller copy() {
		return new HuggingAIController();
	}

	private Entity player;

	private boolean isBlocked;

	private boolean movingLeft;

	@Override
	public void onUpdate(float delta) {
		Entity entity = getEntity();
		if (player == null) {
			player = getEntity().getWorld().getPlayer();
		}
		else {
			Vector2 pos = entity.getMovingBody().getPosition();
			Vector2 playerPos = player.getMovingBody().getPosition();
			if (isBlocked) {
				tryJump(delta);
				isBlocked = false;
			}

			if (pos.x > playerPos.x) {
				moveLeft(delta);
				movingLeft = true;
			}
			else {
				moveRight(delta);
				movingLeft = false;
			}
		}

		stateMachine.affectState(delta);
	}

	@Override
	public void onPlatformCollision(Platform platform) {
		float platformX = platform.getPhysicsBody().getPosition().x;
		float platformWidth = platform.getPhysicsBody().getSize().x;

		float entityX = getEntity().getMovingBody().getPosition().x;
		float entityWidth = getEntity().getMovingBody().getSize().x;
		if (movingLeft) {
			float platformRight = platformX + platformWidth / 2;
			float entityLeft = entityX - entityWidth / 2;

			if (platformRight <= entityLeft) {
				isBlocked = true;
			}
		}
		else {
			float platformLeft = platformX - platformWidth / 2;
			float entityRight = entityX + entityWidth / 2;
			
			if (platformLeft >= entityRight) {
				isBlocked = true;
			}
		}
	}

	public void onDamage(Entity entity) {
		super.onDamage(entity);
		if (getEntity().isDead()) {
			getEntity().getWorld().getEntityManager().removeEntity(getEntity());

			String itemDropName = getEntity().getData().itemDrop;
			if (itemDropName != null) {
				World world = getEntity().getWorld();
				Item itemDrop = new Item(world.getHandler().getEntityData(itemDropName), world);
				itemDrop.getPhysicsBody().setPosition(getEntity().getPosition());
				itemDrop.getController().onCreate();
				getEntity().getWorld().getEntityManager().addEntity(itemDrop);
			}
			

			EventSystem system = getEntity().getWorld().getEventSystem();
			// event to be played when entity dies
			// this event is a dialogue even which will bring up a dialogue
			// bubble
			DialogueBubbleEvent onDeathEvent = new DialogueBubbleEvent("HuggingAIController: On Death Event");
			// dialogue script is the script that will be read by the dialogue
			// box
			onDeathEvent.position = new Vector2(entity.getPosition().x - 250, entity.getPosition().y);
			onDeathEvent.dialogueScript = "data/dialogue/dialogue_bubble_test.json";

			// dialogueBox & Bubble can trigger another event after the last
			// line of text
			// from the script is read
			// this is optional
			Event afterDialogClosesEvent = new Event("HuggingAIController: afterDialogClosesEvent") {

				// example continuation event, spawns a bunch of crap
				@Override
				public void onTrigger() {
					for (int i = 0; i < 10; i++) {
						String itemDropName = getEntity().getData().itemDrop;
						World world = getEntity().getWorld();
						EntityData itemData = world.getHandler().getEntityData(itemDropName);
						if (itemDropName != null) {
							Item itemDrop = new Item(itemData, world);
							Vector2 pos = new Vector2(MathUtils.random(world.dimensions.x),
									MathUtils.random(world.dimensions.y));
							itemDrop.getPhysicsBody().setPosition(pos);
							itemDrop.getController().onCreate();
							getEntity().getWorld().getEntityManager().addEntity(itemDrop);
						}
					}
				}

				private float time;

				@Override
				public void onUpdate(float delta) {
					time += delta;
				}

				// detonates (triggers) after 3 seconds
				@Override
				public boolean checkConditions() {
					return time >= 3;
				}
			};

			onDeathEvent.onCloseEvent = afterDialogClosesEvent;

			// trigger the event, can pass the event object itself, or an alias
			// which then looks up a pre-loaded event in the EventSystem's
			// eventMap
			// (similar to how EntityData is looked up in Assets)
			system.addEvent(onDeathEvent);
		}
	}

	@Override
	public void onCreate() {

	}

}
