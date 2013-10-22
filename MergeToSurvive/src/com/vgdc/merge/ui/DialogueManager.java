package com.vgdc.merge.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.vgdc.merge.event.Event;
import com.vgdc.merge.world.World;

public class DialogueManager {
	private Stage stage;
	private Skin skin;
	private World world;

	public DialogueManager(World world) {
		stage = new Stage();
		skin = new Skin(
				Gdx.files.internal("data/ui/dialogue/uiskin_example.json"));
		Gdx.input.setInputProcessor(stage);
		this.world = world;
	}

	public void onRender() {
		stage.act();
		stage.draw();
		Table.drawDebug(stage);
	}

	public void resize(int width, int height) {
		// stage.setViewport(width, height, true);
	}

	public void dispose() {
		stage.dispose();
		skin.dispose();
	}

	public DialogueBox createDialogueBox(String scriptPath, Event onCloseEvent) {
		DialogueBox db = new DialogueBox(scriptPath, skin);
		db.setOnCloseEvent(onCloseEvent, world.getEventSystem());

		stage.addActor(db);

		return db;
	}

	public DialogueBubble createDialogueBubble(String scriptPath,
			Event onCloseEvent) {
		DialogueBubble db = new DialogueBubble(scriptPath, skin);
		db.setOnCloseEvent(onCloseEvent, world.getEventSystem());
		System.out.println("T");
		stage.addActor(db);

		return db;
	}
}
