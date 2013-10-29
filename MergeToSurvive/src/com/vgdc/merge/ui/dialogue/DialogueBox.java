package com.vgdc.merge.ui.dialogue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Json;
import com.vgdc.merge.events.Event;
import com.vgdc.merge.events.EventSystem;

public class DialogueBox extends Window {

	private DialogueLabel dialogueLabel;
	private Image image;
	private String[] script;
	private Event onCloseEvent;
	private Skin skin;
	private EventSystem eventSystem;
	private boolean closed;
	private boolean canSkip = true;
	private ScrollPane scrollPane;

	public DialogueBox(String scriptPath, Skin skin) {
		super("NARRATOR FOR NOW", skin);
		this.skin = skin;
		setScript(scriptPath);
		create();
	}

	public void setScript(String scriptPath) {
		Json json = new Json();
		script = json.fromJson(String[].class, Gdx.files.internal(scriptPath));
	}

	private void create() {
		this.setSize(450, 125);
		Texture imageTexture = new Texture(Gdx.files.internal("data/dialogue/chibi.png"));
		imageTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		image = new Image(imageTexture);

		this.row();
		add(image).top().size(32, 75);
		dialogueLabel = new DialogueLabel(skin);
		{
			// this.add(textLabel).top().left().expand().size(500, 100);
			dialogueLabel.setWrap(true);
			dialogueLabel.setAlignment(Align.top | Align.left);
		}

		scrollPane = new ScrollPane(dialogueLabel, skin);
		add(scrollPane).top().fill().expand();

		this.row();

		final TextButton closeButton = new TextButton("CLOSE", skin);
		{
			closeButton.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					close();
				}
			});
			this.add(closeButton).left().size(50,25);
		}

		final TextButton continueButton = new TextButton("NEXT", skin);
		{
			continueButton.addListener(new ContinueButtonListener());
			this.add(continueButton).right().size(50,25);
		}

		this.setMovable(true);
		// this.debug();

		dialogueLabel.setScriptLine(script[0]);
	}

	@Override
	public void act(float delta) {
		// scroll to end
		if (!dialogueLabel.isEndOfLineReached()) {
			scrollPane.scrollTo(0, 0, dialogueLabel.getWidth(), dialogueLabel.getHeight());
		}

		super.act(delta);
	}

	public void setSkip(boolean canSkip) {
		this.canSkip = canSkip;
	}

	public void setScrollSpeed(float charsPerSecond) {
		this.dialogueLabel.setScrollSpeed(charsPerSecond);
	}

	// Can optionally specify a event to be called when this dialogue box is
	// closed. Allows for an event chain.
	public void setOnCloseEvent(Event onCloseEvent, EventSystem eventSystem) {
		this.onCloseEvent = onCloseEvent;
		this.eventSystem = eventSystem;
	}

	public void close() {
		this.remove();
		this.closed = true;
		if (onCloseEvent != null) {
			eventSystem.addEvent(onCloseEvent);
		}
	}

	public boolean isClosed() {
		return closed;
	}

	private class ContinueButtonListener extends ClickListener {
		int scriptPointer = 0;

		@Override
		public void clicked(InputEvent event, float x, float y) {
			if (dialogueLabel.isEndOfLineReached()) {
				scriptPointer++;
				if (scriptPointer < script.length) {
					dialogueLabel.setScriptLine(script[scriptPointer]);
				}
				else {
					close();
				}
			}
			else {
				if (canSkip) {
					dialogueLabel.finishLine();
				}
			}
		}
	}
}
