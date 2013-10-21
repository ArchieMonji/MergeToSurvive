package com.vgdc.merge.event;

public class DialogueEvent extends Event {

	public String dialogueScript;
	public Event onCloseEvent;

	public DialogueEvent(String name){
		super(name);
	}

	@Override
	public void onTrigger() {
		getEventSystem().getWorld().getUIManager().getDialogueManager()
				.createDialogue(dialogueScript, onCloseEvent);
	}

	@Override
	public void onUpdate(float delta) {
	}

	@Override
	public boolean checkConditions() {
		return true;
	}
}
