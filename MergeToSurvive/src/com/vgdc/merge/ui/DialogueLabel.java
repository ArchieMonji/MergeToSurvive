package com.vgdc.merge.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class DialogueLabel extends Label {
	public static final float DEFAULT_CHARS_PER_SECOND = 25;
	private int characterLimit;

	private boolean limitCharacters;

	private float timePassed;
	private float textSpeed;

	// for unlimited char display (DialogueBox)
	private StringBuilder currentText;
	// line to display
	private String line;
	// for limited char display (DialogueBubble)
	private int breakPosition;
	// tracks current place in line
	private int textPointer;
	// utility variable for clean wrapping with unlimited char display
	// (DialogueBox)
	private int currLineCount;
	// utility variable for clean wrapping with unlimited char display
	// (DialogueBox)
	private int lastAppend;

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
		updateCharacterLimit();
	}

	@Override
	public void act(float delta) {
		int lastPointer = textPointer;
		if (!isEndOfLineReached()) {
			timePassed += delta;
			textPointer = (int) Math.min(timePassed * textSpeed, line.length());
			// only attempt to update when the pointer has advanced
			if (textPointer != lastPointer) {
				updateText();
			}
		}
		super.act(delta);
	}

	private void updateText() {
		if (limitCharacters) {
			updateCharacterLimit();
			if (textPointer >= characterLimit + breakPosition) {
				breakPosition = textPointer - characterLimit;
				// break at last complete word
				if (!Character.isWhitespace(line.charAt(breakPosition))) {
					for (int i = textPointer; i > breakPosition; i--) {
						if (Character.isWhitespace(line.charAt(i))) {
							breakPosition = i - 1;
						}
					}
				}
				this.setText(line.substring(breakPosition, textPointer));
			}
			else {
				this.setText(line.substring(breakPosition, textPointer));
			}
		}
		else {
			// if the text pointer moved more than 1, then evaluate and
			// add all char 1 at a time
			for (int i = lastAppend; i <= textPointer; i++) {
				// check if next word will overflow line.
				if (lastAppend < line.length()) {
					// find length of next word
					int wordLength = 1;
					for (int c = lastAppend + 1; c < line.length(); c++) {
						if (Character.isWhitespace(line.charAt(c))) {
							break;
						}
						else {
							wordLength++;
						}
					}
					// if wordLength > charLimit, then let word wrapping handle
					// breaking up really long word on its own
					if (wordLength < characterLimit) {
						// if over flow, append line separator
						if (wordLength + currLineCount >= characterLimit) {
							currentText.append(System.lineSeparator());
							currLineCount = 0;
						}
					}

					// append new characters, uses string builder since we need
					// to
					// append line separator
					currentText.append(line.charAt(lastAppend));
					// track current chars on line
					currLineCount++;
					// only append new characters for next update
					lastAppend++;
				}

			}
			this.setText(currentText);
		}
	}

	public boolean isEndOfLineReached() {
		return textPointer >= line.length();
	}

	public void setScriptLine(String line) {
		currentText = new StringBuilder();
		this.line = line;
		timePassed = 0;
		textPointer = 0;
		breakPosition = 0;
		currLineCount = 0;
		lastAppend = 0;
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
