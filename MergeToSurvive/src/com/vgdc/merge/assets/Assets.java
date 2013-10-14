package com.vgdc.merge.assets;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Logger;
import com.vgdc.merge.entities.EntityData;
import com.vgdc.merge.entities.UnitStateEnum;
import com.vgdc.merge.entities.abilities.Ability;
import com.vgdc.merge.entities.audio.SoundFx;
import com.vgdc.merge.entities.controllers.Controller;
import com.vgdc.merge.entities.controllers.Controls;
import com.vgdc.merge.test.TestAbility;

public class Assets {
	public AssetManager manager = new AssetManager();
	public HashMap<String, EntityData> entityDataMap = new HashMap<String, EntityData>();
	public HashMap<String, Animation> animationMap = new HashMap<String, Animation>();
	public HashMap<String, SoundFx> soundMap = new HashMap<String, SoundFx>();

	private AssetList assets;
	private Json json = new Json();

	// Test
	public static void main(String[] args) {
		Assets a = new Assets();
		Assets.AssetList l = new AssetList();
		l.animations = new ArrayList<String>();
		l.sounds = new ArrayList<SoundData>();
		SoundData sd = new SoundData();
		l.sounds.add(sd);
		sd.name = "TESTNAME";
		sd.path = "Test_path.ogg";
		sd.looping = true;

		l.textures = new ArrayList<String>();
		l.entities = new HashMap<String, String>();
		l.entities.put("player", "player.json");
		System.out.println(a.json.prettyPrint(a.json.toJson(l)));
		ArrayList<Ability> abs = new ArrayList<Ability>();
		abs.add(new TestAbility());
		System.out.println(a.json.prettyPrint(a.json.toJson(abs)));

		EntityTemplate edt = new EntityTemplate();
		edt.sounds = new HashMap<UnitStateEnum, ArrayList<String>>();
		ArrayList<String> soundNameList = new ArrayList<String>();
		soundNameList.add("SOUND NAME");
		soundNameList.add("SOUND NAME2");
		edt.sounds.put(UnitStateEnum.MOVE, soundNameList);
		edt.sounds.put(UnitStateEnum.ATTACK, soundNameList);

		System.out.println(a.json.prettyPrint(a.json.toJson(edt)));

		Controls c = new Controls();
		c.down = Keys.S;
		c.up = Keys.W;
		c.left = Keys.A;
		c.right = Keys.D;
		c.useAbility = Buttons.LEFT;
		c.toggleAbility = Buttons.LEFT;
		System.out.println(a.json.prettyPrint(a.json.toJson(c)));

		TestEnum te = new TestEnum();
		te.state = State.TEST;

		System.out.println(a.json.prettyPrint(a.json.toJson(te)));
		EntityTemplate temp = a.json.fromJson(EntityTemplate.class,
				a.json.toJson(edt));
		System.out.println(temp.sounds.get(UnitStateEnum.MOVE.name()));

	}

	public static class TestEnum {
		State state;
	}

	public enum State {
		TEST(1);
		int v;

		private State(int v) {
			this.v = v;
		}
	}

	public <T> T createObjectFromJson(Class<T> type, String path) {
		return json.fromJson(type, Gdx.files.internal(path));
	}
	
	public void loadAssets(String path) {
		assets = createObjectFromJson(AssetList.class, path);

		for (SoundData soundData : assets.sounds) {
			manager.load(soundData.path, Sound.class);
		}

		for (String texturePath : assets.textures) {
			manager.load(texturePath, Texture.class);
		}

		// TODO: Change to support asynchronous loading
		manager.finishLoading();	
		
		createObjectData();
	}
	
	public void createObjectData(){
		for (SoundData soundData : assets.sounds) {
			SoundFx sfx = new SoundFx();
			sfx.looping = soundData.looping;
			sfx.sound = manager.get(soundData.path);
			soundMap.put(soundData.name, sfx);
		}

		for (String animationPath : assets.animations) {
			loadAnimation(animationPath);
		}

		for (String name : assets.entities.keySet()) {
			loadEntityData(name, assets.entities.get(name));
		}

		System.out.println("Loading Finished");
	}

	public void dispose() {
		manager.dispose();
	}

	private void loadEntityData(String name, String path) {
		System.out.println("Loading Entity '" + name + "' from " + path);

		EntityTemplate template = createObjectFromJson(EntityTemplate.class,
				path);

		EntityData data = new EntityData();
		data.maxHealth = template.maxHealth;
		data.jumpHeight = template.jumpHeight;
		data.moveSpeed = template.moveSpeed;

		// attach animations
		data.animations = new ArrayList<Animation>();

		for (String animationName : template.animations) {
			data.animations.add(animationMap.get(animationName));
		}

		// attach sounds
		data.sounds = new ArrayList<ArrayList<SoundFx>>();
		for (UnitStateEnum state : UnitStateEnum.values()) {
			json.setElementType(EntityTemplate.class, "sounds", ArrayList.class);
			ArrayList<String> soundNames = template.sounds.get(state.name());
			if (soundNames != null) {
				ArrayList<SoundFx> soundList = new ArrayList<SoundFx>();
				data.sounds.add(soundList);

				if (soundNames != null) {
					for (String soundName : soundNames) {
						soundList.add(soundMap.get(soundName));
					}
				}
			}
			else{
				data.sounds.add(null);
			}
		}

		data.defaultAbilities = new ArrayList<Ability>(template.abilities);

		data.controller = template.controller;

		entityDataMap.put(name, data);

		System.out.println("EntityData Loaded : " + name);
	}

	private void loadAnimation(String path) {
		AnimationData data = json.fromJson(AnimationData.class,
				Gdx.files.internal(path));

		Texture sheet = manager.get(data.path, Texture.class);

		TextureRegion[][] tmp = null;
		// break sheet into individual frames
		if (data.frameWidth == 0 || data.frameHeight == 0) {
			tmp = TextureRegion.split(sheet, sheet.getWidth() / data.cols,
					sheet.getHeight() / data.rows);
		} else {
			tmp = TextureRegion.split(sheet, data.frameWidth, data.frameHeight);
		}

		// pack into 1D array
		TextureRegion[] frames = new TextureRegion[data.frameCount];
		int index = 0;
		for (int i = 0; i < data.rows && index < data.frameCount; i++) {
			for (int j = 0; j < data.cols && index < data.frameCount; j++) {
				frames[index++] = tmp[i][j];
			}
		}

		animationMap.put(data.name, new Animation(data.frameDuration, frames));

		System.out.println("Animation Loaded : " + data.name);
	}

	private static class AssetList {
		public ArrayList<SoundData> sounds; // path
		public ArrayList<String> textures; // path
		public ArrayList<String> animations; // path
		public HashMap<String, String> entities; // name, path
	}

	private static class SoundData {
		public String name;
		public String path;
		public boolean looping;
	}

	private static class EntityTemplate {
		public int maxHealth;
		public float jumpHeight;
		public float moveSpeed;
		public Controller controller;
		public ArrayList<Ability> abilities;
		public ArrayList<String> animations;
		public HashMap<UnitStateEnum, ArrayList<String>> sounds;
	}
}