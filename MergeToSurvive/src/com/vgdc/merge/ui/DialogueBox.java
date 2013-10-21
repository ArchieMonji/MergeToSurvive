package com.vgdc.merge.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
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
		textLabel = new Label(script.pages[0], skin);
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

	// Can optionally specify a event to be called when this dialogue box is
	// closed. Allows for an event chain.
	public void setOnCloseEvent(Event onCloseEvent) {
		this.onCloseEvent = onCloseEvent;
	}

	private static class Script {
		String[] pages;
	}

	private class ContinueButtonListener extends ClickListener {
		int scriptPointer = 0;

		@Override
		public void clicked(InputEvent event, float x, float y) {
			scriptPointer++;
			if (scriptPointer < script.pages.length) {
				String text = script.pages[scriptPointer];
				textLabel.setText(text);
			} else {
				DialogueBox.this.remove();
				if (onCloseEvent != null) {
					onCloseEvent.getEventSystem().trigger(onCloseEvent);
				}
			}
		}
	}
}
