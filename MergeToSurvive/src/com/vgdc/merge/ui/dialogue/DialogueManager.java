package com.vgdc.merge.ui.dialogue;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.vgdc.merge.events.Event;
import com.vgdc.merge.ui.UIManager;
import com.vgdc.merge.world.World;

public class DialogueManager {
	private World world;
	private HashMap<String, TextureRegionDrawable> speakerPortraits = new HashMap<String, TextureRegionDrawable>();
	private Skin skin;
	private UIManager manager;

	public DialogueManager(UIManager manager, World world, Skin skin) {
		this.manager = manager;
		this.world = world;
		this.skin = skin;

		loadPortraits();
	}

	private void loadPortraits() {
		speakerPortraits.put("proto_neutral", createPortrait("data/ui/dialogue/portraits/proto_neutral.png"));
		speakerPortraits.put("proto_angry", createPortrait("data/ui/dialogue/portraits/proto_angry.png"));
		speakerPortraits.put("chibi", createPortrait("data/ui/dialogue/portraits/chibi.png"));
		speakerPortraits.put("chibi_neutral", createPortrait("data/ui/dialogue/portraits/chibi_neutral.png"));
		speakerPortraits.put("chibi_laugh", createPortrait("data/ui/dialogue/portraits/chibi_laugh.png"));
	}

	private TextureRegionDrawable createPortrait(String filename) {
		return new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(filename))));
	}

	public TextureRegionDrawable getPortrait(String name) {
		return speakerPortraits.get(name);
	}

	public DialogueBox createDialogueBox(String scriptPath, Event onCloseEvent) {
		DialogueBox db = new DialogueBox(this, world, scriptPath, skin);
		db.setOnCloseEvent(onCloseEvent, world.getEventSystem());

		manager.addActor(db);

		return db;
	}

	public DialogueBubble createDialogueBubble(String scriptPath, Event onCloseEvent) {
		DialogueBubble db = new DialogueBubble(scriptPath, skin);
		db.setOnCloseEvent(onCloseEvent, world.getEventSystem());

		manager.addActor(db);

		return db;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public void dispose() {
		for (TextureRegionDrawable drawable : speakerPortraits.values()) {
			drawable.getRegion().getTexture().dispose();
		}
	}

}
