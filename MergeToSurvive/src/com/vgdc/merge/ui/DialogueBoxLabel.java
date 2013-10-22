package com.vgdc.merge.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class DialogueBoxLabel extends Label {
    public static final float CHARS_PER_SECOND = 30;

    private String text;
    private float textPointer;

    public DialogueBoxLabel(Skin skin, String styleName) {
	super("", skin, styleName);
    }

    public DialogueBoxLabel(Skin skin) {
	super("", skin);
    }

    @Override
    public void act(float delta) {
	textPointer += delta * DialogueBoxLabel.CHARS_PER_SECOND;
	textPointer = Math.min(textPointer, text.length());
	this.setText(text.substring(0, (int) textPointer));
	super.act(delta);
    }

    public boolean isEndOfLineReached() {
	return textPointer >= text.length() - 1;
    }

    public void setScriptLine(String line) {
	this.text = line;
	textPointer = 0;
    }
}
