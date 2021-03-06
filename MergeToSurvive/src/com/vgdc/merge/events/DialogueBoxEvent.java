package com.vgdc.merge.events;

import com.badlogic.gdx.math.Vector2;
import com.vgdc.merge.ui.dialogue.DialogueBox;

public class DialogueBoxEvent extends Event {

	public String dialogueScript;
	public Event onCloseEvent;
	public Vector2 position;

	public DialogueBoxEvent(String name) {
		super(name);
	}

	@Override
	public void onTrigger() {
		DialogueBox db = getWorld().getUIManager().getDialogueManager().createDialogueBox(dialogueScript, onCloseEvent);
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
