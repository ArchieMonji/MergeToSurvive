package com.vgdc.merge.event;

public class DialogueEvent extends Event {

	public String dialogueScript;
	public Event onCloseEvent;

	public DialogueEvent(String name, EventSystem eventSystem) {
		super(name, eventSystem);
	}
	
	public DialogueEvent(EventSystem eventSystem) {
		super(eventSystem);
	}

	@Override
	public void onEvent() {
		getEventSystem().getWorld().getUIManager().getDialogueManager()
				.createDialogue(dialogueScript, onCloseEvent);
	}

}
