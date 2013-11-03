package com.vgdc.merge.ui.dialogue;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenAccessor;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Json;
import com.vgdc.merge.events.Event;
import com.vgdc.merge.events.EventSystem;
import com.vgdc.merge.world.World;

public class DialogueBox extends Window {

	private World world;
	private DialogueLabel dialogueLabel;
	private ContinueLabel continueLabel;
	private Image leftSpeaker;
	private Image rightSpeaker;
	private String[] script;
	private Event onCloseEvent;
	private Skin skin;
	private EventSystem eventSystem;
	private boolean closed;
	private boolean canSkip = true;
	private ScrollPane scrollPane;

	public DialogueBox(World world, String scriptPath, Skin skin) {
		super("NARRATOR FOR NOW", skin);
		this.world = world;
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
		leftSpeaker = new Image(imageTexture);

		this.row();
		add(leftSpeaker).top().size(32, 75);
		dialogueLabel = new DialogueLabel(skin);
		{
			// this.add(textLabel).top().left().expand().size(500, 100);
			dialogueLabel.setWrap(true);
			dialogueLabel.setAlignment(Align.top | Align.left);
			dialogueLabel.setDialogueListener(new DialogueLabelListener() {
				@Override
				public void lineFinished(DialogueLabel label) {
					continueLabel.setText("Click RMB to continue...");
					continueLabel.startFadeTween();

					scrollPane.setScrollY(dialogueLabel.getHeight());
				}
			});
		}

		scrollPane = new ScrollPane(dialogueLabel, skin);
		add(scrollPane).top().fill().expand();

		this.row();

		continueLabel = new ContinueLabel("", skin);
		{
			continueLabel.setAlignment(Align.right);
			this.add(continueLabel).right().colspan(2).size(300, 25);
		}
		// final TextButton closeButton = new TextButton("CLOSE", skin);
		// {
		// closeButton.addListener(new ClickListener() {
		// @Override
		// public void clicked(InputEvent event, float x, float y) {
		// close();
		// }
		// });
		// this.add(closeButton).left().size(50, 25);
		// }
		//
		// final TextButton continueButton = new TextButton("NEXT", skin);
		// {
		// continueButton.addListener(new ContinueButtonListener());
		// this.add(continueButton).right().size(50, 25);
		// }

		this.setMovable(true);
		// this.debug();

		dialogueLabel.setScriptLine(script[0]);
		world.stop();
	}

	boolean buttonPressed;

	@Override
	public void act(float delta) {
		if (Gdx.input.isButtonPressed(Buttons.RIGHT)) {
			if (buttonPressed == false) {
				if (dialogueLabel.isEndOfLineReached()) {
					scriptPointer++;
					if (scriptPointer < script.length) {
						dialogueLabel.setScriptLine(script[scriptPointer]);
						continueLabel.setText("");
						continueLabel.stopFadeTween();
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
			buttonPressed = true;
		}
		else {
			buttonPressed = false;
		}

		// scroll to end
		if (!dialogueLabel.isEndOfLineReached()) {
			scrollPane.setScrollY(dialogueLabel.getHeight());
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
		world.run();
	}

	public boolean isClosed() {
		return closed;
	}

	public void setLeftSpeaker(TextureRegion image){
		leftSpeaker.setDrawable(new TextureRegionDrawable(image));
	}
	
	public void setRightSpeaker(TextureRegion image){
		rightSpeaker.setDrawable(new TextureRegionDrawable(image));
	}
	
	int scriptPointer = 0;

	private class ContinueLabel extends Label {
		private Tween tween;
		private TweenManager tweenManager;

		public ContinueLabel(CharSequence text, Skin skin) {
			super(text, skin);
			create();
		}

		public void startFadeTween() {
			this.setColor(1, 1, 1, 0);
			tweenManager.add(tween);
			tweenManager.resume();
		}

		public void stopFadeTween() {
			tweenManager.pause();
		}

		@Override
		public void act(float delta) {
			super.act(delta);
			tweenManager.update(delta);
		}

		private void create() {
			tweenManager = new TweenManager();
			Tween.registerAccessor(Label.class, new LabelAlphaTween());

			tween = Tween.to(this, LabelAlphaTween.ALPHA, 1f).target(1).ease(TweenEquations.easeInQuad)
					.repeatYoyo(Tween.INFINITY, 0.5f);
		}

		private class LabelAlphaTween implements TweenAccessor<Label> {
			public static final int ALPHA = 1;

			@Override
			public int getValues(Label target, int tweenType, float[] returnValues) {
				switch (tweenType) {
				case ALPHA:
					returnValues[0] = target.getColor().a;
					return ALPHA;
				default:
					return 0;
				}
			}

			@Override
			public void setValues(Label target, int tweenType, float[] newValues) {
				switch (tweenType) {
				case ALPHA:
					target.setColor(1, 1, 1, newValues[0]);
					break;
				default:
					break;
				}
			}
		}
	}
}
