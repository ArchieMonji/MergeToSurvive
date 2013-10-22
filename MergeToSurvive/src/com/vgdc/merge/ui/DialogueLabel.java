package com.vgdc.merge.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class DialogueLabel extends Label {
	public static final float DEFAULT_CHARS_PER_SECOND = 30;
	private int characterLimit;
	private String line;
	private int breakPosition;
	private int textPointer;
	private boolean limitCharacters;
	private float time;
	private float textSpeed;

	public DialogueLabel(Skin skin, String styleName) {
		super("", skin, styleName);
		textSpeed = DEFAULT_CHARS_PER_SECOND;
	}

	public DialogueLabel(Skin skin) {
		super("", skin);
		textSpeed = DEFAULT_CHARS_PER_SECOND;
	}

	public void updateCharacterLimit() {
		TextBounds bounds = getStyle().font.getBounds("A");
		characterLimit = (int) (this.getWidth() / bounds.width * this.getFontScaleX());
	}

	public void setCharacterLimit(int limit) {
		this.characterLimit = limit;
	}

	@Override
	public void setSize(float width, float height) {
		super.setSize(width, height);
		updateCharacterLimit();
	}

	@Override
	public void setWidth(float width) {
		super.setWidth(width);
	}

	@Override
	public void act(float delta) {
		int lastPointer = textPointer;
		if (!isEndOfLineReached()) {
			time += delta;
			textPointer = (int) Math.min(time * textSpeed, line.length());
			// only attempt to update when the pointer has advanced
			if(textPointer != lastPointer){
				updateText();
			}
		}
		super.act(delta);
	}

	private void updateText() {
		if (limitCharacters) {
			updateCharacterLimit();
			if (textPointer >= characterLimit + breakPosition) {
				breakPosition = textPointer - (characterLimit / 3);
//				// break at last complete word
//				if (!Character.isWhitespace(line.charAt(textPointer))) {
//					for (int i = textPointer; i >= 0; i--) {
//						if (Character.isWhitespace(line.charAt(i))) {
//							breakPosition = i;
//						}
//					}
//					if (breakPosition < textPointer - characterLimit) {
//						breakPosition = textPointer;
//					}
//				}
				this.setText(line.substring(breakPosition, textPointer));
			}
			else {
				this.setText(line.substring(breakPosition, textPointer));
			}
		}
		else {
			this.setText(line.substring(0, (int) textPointer));
		}
	}

	public boolean isEndOfLineReached() {
		return textPointer >= line.length();
	}

	public void setScriptLine(String line) {
		this.line = line;
		time = 0;
		textPointer = 0;
		breakPosition = 0;
	}

	public void limitCharacters(boolean limit) {
		this.limitCharacters = limit;
	}

	public void finishLine() {
		textPointer = line.length();
		breakPosition = line.length() - characterLimit - 1;
		updateText();
	}

	public void setScrollSpeed(float charsPerSecond) {
		this.textSpeed = charsPerSecond;
	}
}
