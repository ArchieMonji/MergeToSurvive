package com.vgdc.merge.ui;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.vgdc.merge.event.Event;
import com.vgdc.merge.world.World;

public class DialogueBoxManager {
	private Stage stage;
	private Skin skin;
	private World world;
	private ArrayList<DialogueBox> boxes = new ArrayList<DialogueBox>();

	public DialogueBoxManager(World world) {
		stage = new Stage();
		skin = new Skin(Gdx.files.internal("data/ui/dialogue/uiskin.json"));
		Gdx.input.setInputProcessor(stage);
		this.world = world;
	}

	public void onRender() {
		for (int i = 0; i < boxes.size(); i++) {
			DialogueBox box = boxes.get(i);
			if (box.isClosed()) {
				boxes.remove(i--);
			} else {
				box.update(Gdx.graphics.getDeltaTime());
			}
		}
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

	public void createDialogue(String scriptPath, Event onCloseEvent) {
		DialogueBox db = new DialogueBox("DIALOGUE BOX", skin);
		stage.addActor(db);
		db.setScript(scriptPath);
		db.setOnCloseEvent(onCloseEvent, world.getEventSystem());
		boxes.add(db);
		db.create();
	}
}
