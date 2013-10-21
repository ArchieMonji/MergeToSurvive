package com.vgdc.merge.ui;

import sun.font.TextLabel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Json;
import com.vgdc.merge.event.Event;
import com.vgdc.merge.event.EventSystem;

public class DialogueBox extends Window {
	private Label textLabel;
	private Image image;
	private Script script;
	private Event onCloseEvent;
	private Skin skin;
	private EventSystem eventSystem;
	private boolean closed;

	public DialogueBox(String title, Skin skin) {
		super(title, skin);
		this.skin = skin;
	}

	public void setScript(String scriptPath) {
		Json json = new Json();
		script = json.fromJson(Script.class, Gdx.files.internal(scriptPath));
	}

	public void create() {
		// /Texture imageTexture = new
		// Texture(Gdx.files.internal("data/test/item_icon.png"));
		// image = new Image(imageTexture);

		// table.add(image).top().left();
		currText = script.pages[0];
		textLabel = new Label("", skin);
		{
			this.add(textLabel).top().left().expand().minSize(500, 100);
			textLabel.setWrap(true);
		}

		this.row();

		final TextButton button = new TextButton("->", skin);
		{
			button.addListener(new ContinueButtonListener());
			this.add(button).size(50, 25).right().bottom();
		}
		this.setMovable(true);
		this.debug();
		this.pack();
	}

	private String currText;
	private float textPointer;
	

	public void update(float delta) {
		textPointer += delta * 10;
		textPointer = Math.min(textPointer, currText.length());
		textLabel.setText(currText.substring(0,(int) textPointer));
	}
	
	// Can optionally specify a event to be called when this dialogue box is
	// closed. Allows for an event chain.
	public void setOnCloseEvent(Event onCloseEvent, EventSystem eventSystem) {
		this.onCloseEvent = onCloseEvent;
		this.eventSystem = eventSystem;
	}

	private static class Script {
		String[] pages;
	}

	public boolean isClosed() {
		return closed;
	}

	private class ContinueButtonListener extends ClickListener {
		int scriptPointer = 0;

		@Override
		public void clicked(InputEvent event, float x, float y) {
			scriptPointer++;
			if (scriptPointer < script.pages.length) {
				currText = script.pages[scriptPointer];
				textPointer = 0;
			} else {
				DialogueBox.this.remove();
				DialogueBox.this.closed = true;
				if (onCloseEvent != null) {
					eventSystem.addEvent(onCloseEvent);
				}
			}
		}
	}
}
