package com.vgdc.merge.ui;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenAccessor;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Json;
import com.vgdc.merge.event.Event;
import com.vgdc.merge.event.EventSystem;

public class DialogueBubble extends DialogueLabel {
	private static float FADE_IN_TIME = 1.5f;
	private static float FADE_OUT_TIME = 1.5f;
	private static float SCRIPT_LINE_DELAY = 1;

	private String[] script;

	private boolean closed;

	private TweenManager tweenManager;
	private boolean isRunning;

	private Event onCloseEvent;
	private EventSystem eventSystem;

	private int scriptPointer = 0;
	private float delayTime;

	public DialogueBubble(String scriptPath, Skin skin) {
		super(skin, "dialogue-bubble");
		setScript(scriptPath);
		create();
	}

	public void setScript(String scriptPath) {
		Json json = new Json();
		script = json.fromJson(String[].class, Gdx.files.internal(scriptPath));
	}

	public void create() {
		this.setSize(500, 15);
		// characters are limited by size dimensions
		limitCharacters(true);

		tweenManager = new TweenManager();
		Tween.registerAccessor(DialogueBubble.class, new DialogueBubbleTween());

		// fade-in
		TweenCallback callback = new TweenCallback() {
			// start displaying text normally
			@Override
			public void onEvent(int type, BaseTween<?> source) {
				isRunning = true;
			}
		};
		Tween.to(this, DialogueBubbleTween.ALPHA, FADE_IN_TIME).target(1).ease(TweenEquations.easeInQuad)
				.setCallback(callback).setCallbackTriggers(TweenCallback.COMPLETE).start(tweenManager);

		this.setScriptLine(script[0]);
	}

	@Override
	public void act(float delta) {
		tweenManager.update(delta);
		if (isRunning) {
			super.act(delta);
			// delay between end of current line and next line
			if (this.isEndOfLineReached()) {
				delayTime += delta;
				if (delayTime >= SCRIPT_LINE_DELAY) {
					scriptPointer++;
					if (scriptPointer < script.length) {
						this.setScriptLine(script[scriptPointer]);
					}
					delayTime = 0;
				}
			}
			// fade-out
			if (scriptPointer >= script.length) {
				TweenCallback callback = new TweenCallback() {
					// close properly after finished fading out
					@Override
					public void onEvent(int type, BaseTween<?> source) {
						close();
					}
				};

				Tween.to(this, DialogueBubbleTween.ALPHA, FADE_OUT_TIME).target(0).ease(TweenEquations.easeInQuad)
						.setCallback(callback).setCallbackTriggers(TweenCallback.COMPLETE).start(tweenManager);
				isRunning = false;
			}
		}
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, alpha);
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

	// for fading in and out
	private float alpha;

	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}

	public float getAlpha() {
		return alpha;
	}

	public class DialogueBubbleTween implements TweenAccessor<DialogueBubble> {

		public static final int ALPHA = 1;

		@Override
		public int getValues(DialogueBubble target, int tweenType, float[] returnValues) {
			switch (tweenType) {
			case ALPHA:
				returnValues[0] = target.getAlpha();
				return 1;
			default:
				return 0;
			}
		}

		@Override
		public void setValues(DialogueBubble target, int tweenType, float[] newValues) {
			switch (tweenType) {
			case ALPHA:
				target.setAlpha(newValues[0]);
				break;
			default:
				break;
			}
		}
	}
}
