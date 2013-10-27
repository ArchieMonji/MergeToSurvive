package com.vgdc.merge.event;

import com.badlogic.gdx.math.Vector2;
import com.vgdc.merge.ui.dialogue.DialogueBubble;

public class DialogueBubbleEvent extends Event {
	public String dialogueScript;
	public Event onCloseEvent;
	public Vector2 position;

	public DialogueBubbleEvent(String name) {
		super(name);
	}

	@Override
	public void onTrigger() {
		DialogueBubble db = getEventSystem().getWorld().getUIManager().createDialogueBubble(dialogueScript, onCloseEvent);
		db.setPosition(position.x, position.y);
	}

	@Override
	public void onUpdate(float delta) {
	}

	@Override
	public boolean checkConditions() {
		return true;
	}
}
