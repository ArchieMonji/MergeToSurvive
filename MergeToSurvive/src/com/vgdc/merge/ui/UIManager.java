package com.vgdc.merge.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.vgdc.merge.entities.Entity;
import com.vgdc.merge.entities.rendering.HealthBarRenderer;
import com.vgdc.merge.entities.rendering.HitboxRenderer;
import com.vgdc.merge.event.Event;
import com.vgdc.merge.ui.dialogue.DialogueBox;
import com.vgdc.merge.ui.dialogue.DialogueBubble;
import com.vgdc.merge.world.World;

public class UIManager {
	private static final boolean DEBUG_WORLD = false;
	private static final boolean DEBUG_UI = false;

	private Stage stage;
	private World world;
	private Skin skin;

	private HealthBarRenderer healthBarRenderer;
	private HitboxRenderer hitboxRenderer;

	private HeadsUpDisplay headsUpDisplay;

	public UIManager(World world) {
		this.world = world;
		world.setUIManager(this);
		
		create();
	}
	
	private void create(){
		skin = new Skin(Gdx.files.internal("data/ui/dialogue/uiskin_example.json"));

		healthBarRenderer = new HealthBarRenderer(world);

		hitboxRenderer = new HitboxRenderer(world);
		if (DEBUG_WORLD) {
			hitboxRenderer.renderFPS(true);
			hitboxRenderer.renderHitboxes(true);
			hitboxRenderer.renderEntityPositions(true);
		}

		headsUpDisplay = new HeadsUpDisplay(world);
		
		Table table = new Table();
		table.setFillParent(true);
		table.add(headsUpDisplay).bottom().left().expand();

		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		stage.addActor(table);
	}

	public void onRender(Camera camera, float delta) {
		stage.setCamera(camera);
		stage.act();
		stage.draw();
		if (DEBUG_WORLD) {
			healthBarRenderer.onRender(delta);
			hitboxRenderer.onRender(camera);
		}
		if (DEBUG_UI) {
			Table.drawDebug(stage);
		}
	}

	public void dispose() {
		stage.dispose();
		skin.dispose();
		healthBarRenderer.dispose();
		hitboxRenderer.dispose();
		headsUpDisplay.dispose();
	}

	public DialogueBox createDialogueBox(String scriptPath, Event onCloseEvent) {
		DialogueBox db = new DialogueBox(scriptPath, skin);
		db.setOnCloseEvent(onCloseEvent, world.getEventSystem());

		stage.addActor(db);

		return db;
	}

	public DialogueBubble createDialogueBubble(String scriptPath, Event onCloseEvent) {
		DialogueBubble db = new DialogueBubble(scriptPath, skin);
		db.setOnCloseEvent(onCloseEvent, world.getEventSystem());
		
		stage.addActor(db);

		return db;
	}

	public void setWorld(World world) {
		this.world = world;
		world.setUIManager(this);
		headsUpDisplay.setWorld(world);
	}

	public void setPlayer(Entity player) {
		headsUpDisplay.setPlayer(player);
	}

	public void addActor(Actor actor) {
		stage.addActor(actor);
	}
}
