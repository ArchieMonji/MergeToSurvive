package com.vgdc.merge.test;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.vgdc.merge.entities.Entity;
import com.vgdc.merge.entities.EntityData;
import com.vgdc.merge.entities.Item;
import com.vgdc.merge.entities.controllers.Controller;
import com.vgdc.merge.entities.controllers.UnitController;
import com.vgdc.merge.event.DialogueBubbleEvent;
import com.vgdc.merge.event.Event;
import com.vgdc.merge.event.EventSystem;
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
		Item itemDrop = new Item(world.getAssets().entityDataMap.get(itemDropName), world);
		itemDrop.getPhysicsBody().setPosition(getEntity().getPosition());
		itemDrop.getController().onCreate();
		getEntity().getWorld().getEntityManager().addEntity(itemDrop);
	    }

	    EventSystem system = getEntity().getWorld().getEventSystem();
	    // event to be played when entity dies
	    // this event is a dialogue even which will bring up a dialogue
	    // bubble
	    DialogueBubbleEvent onDeathEvent = new DialogueBubbleEvent("TestAIController: On Death Event");
	    // dialogue script is the script that will be read by the dialogue
	    // box
	    onDeathEvent.position = new Vector2(entity.getPosition());
	    onDeathEvent.dialogueScript = "data/dialogue/dialogue_bubble_test.json";

	    // dialogueBox & Bubble can trigger another event after the last
	    // line of text
	    // from the script is read
	    // this is optional
	    Event afterDialogClosesEvent = new Event("TestAIController: afterDialogClosesEvent") {

		// example continuation event, spawns a bunch of crap
		@Override
		public void onTrigger() {
		    for (int i = 0; i < 10; i++) {
			String itemDropName = getEntity().getData().itemDrop;
			World world = getEntity().getWorld();
			EntityData itemData = world.getAssets().entityDataMap.get(itemDropName);
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
