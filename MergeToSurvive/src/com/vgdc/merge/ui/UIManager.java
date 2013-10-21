package com.vgdc.merge.ui;

import com.badlogic.gdx.graphics.Camera;
import com.vgdc.merge.entities.rendering.HealthBarRenderer;
import com.vgdc.merge.entities.rendering.HitboxRenderer;
import com.vgdc.merge.world.World;

public class UIManager {
	DialogueBoxManager dialogueManager;
	HealthBarRenderer healthBarRenderer;
	HitboxRenderer hitboxRenderer;

	public UIManager(World world) {
		dialogueManager = new DialogueBoxManager(world);
		healthBarRenderer = new HealthBarRenderer(world);
		hitboxRenderer = new HitboxRenderer(world);
	}

	public void onRender(Camera camera, float delta) {
		dialogueManager.onRender();
		healthBarRenderer.onRender(delta);
		hitboxRenderer.onRender(camera);
	}

	public DialogueBoxManager getDialogueManager() {
		return dialogueManager;
	}
}
