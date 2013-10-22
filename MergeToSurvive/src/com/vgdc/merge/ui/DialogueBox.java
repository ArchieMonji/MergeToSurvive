package com.vgdc.merge.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Json;
import com.vgdc.merge.event.Event;
import com.vgdc.merge.event.EventSystem;

public class DialogueBox extends Window {

    private DialogueBoxLabel textLabel;
    //private Image image; TODO in future
    private String[] script;
    private Event onCloseEvent;
    private Skin skin;
    private EventSystem eventSystem;
    private boolean closed;

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

    public void create() {
	// /Texture imageTexture = new
	// Texture(Gdx.files.internal("data/test/item_icon.png"));
	// image = new Image(imageTexture);

	// table.add(image).top().left();

	textLabel = new DialogueBoxLabel(skin);
	{
	    this.add(textLabel).top().left().expand().minSize(500, 100);
	    textLabel.setWrap(true);
	    textLabel.setAlignment(Align.top | Align.left);
	}

	this.row();

	final Button button = new Button(skin);
	{
	    button.addListener(new ContinueButtonListener());
	    this.add(button).size(50, 25).right().bottom();
	}
	this.setMovable(true);
	// this.debug();
	this.pack();
	
	textLabel.setScriptLine(script[0]);
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
	    scriptPointer++;
	    if (scriptPointer < script.length) {
		textLabel.setScriptLine(script[scriptPointer]);
	    }
	    else{
		close();
	    }
	}
    }
}
