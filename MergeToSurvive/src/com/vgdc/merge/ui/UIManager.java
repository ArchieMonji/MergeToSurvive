package com.vgdc.merge.ui;

import com.badlogic.gdx.graphics.Camera;
import com.vgdc.merge.entities.rendering.HealthBarRenderer;
import com.vgdc.merge.entities.rendering.HitboxRenderer;
import com.vgdc.merge.world.World;

public class UIManager {
	private boolean DEBUG = false;
	
	private DialogueManager dialogueManager;
	private HealthBarRenderer healthBarRenderer;
	private HitboxRenderer hitboxRenderer;

	public UIManager(World world) {
		dialogueManager = new DialogueManager(world);
		healthBarRenderer = new HealthBarRenderer(world);
		
		hitboxRenderer = new HitboxRenderer(world);
		hitboxRenderer.renderFPS(true);
		if (DEBUG) {
			hitboxRenderer.renderHitboxes(true);
			hitboxRenderer.renderEntityPositions(true);
		}
	}

	public void onRender(Camera camera, float delta) {
		dialogueManager.onRender();
		healthBarRenderer.onRender(delta);
		hitboxRenderer.onRender(camera);
	}

	public DialogueManager getDialogueManager() {
		return dialogueManager;
	}
}
