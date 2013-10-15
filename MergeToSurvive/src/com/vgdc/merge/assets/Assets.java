package com.vgdc.merge.assets;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Json;
import com.vgdc.merge.entities.BaseEntityData;
import com.vgdc.merge.entities.EntityData;
import com.vgdc.merge.entities.UnitStateEnum;
import com.vgdc.merge.entities.abilities.Ability;
import com.vgdc.merge.entities.abilities.ProjectileAbility;
import com.vgdc.merge.entities.audio.SoundFx;
import com.vgdc.merge.entities.controllers.Controls;

public class Assets {
	public AssetManager manager = new AssetManager();
	public HashMap<String, EntityData> entityDataMap = new HashMap<String, EntityData>();
	public HashMap<String, Animation> animationMap = new HashMap<String, Animation>();
	public HashMap<String, SoundFx> soundMap = new HashMap<String, SoundFx>();

	private AssetList assets;
	private Json json = new Json();

	public Assets() {
		String[] packages = new String[] {
				"com.vgdc.merge.entities.controllers",
				"com.vgdc.merge.entities.abilities" };

		for (String packageName : packages) {
			for (Class<?> c : ClassFinder.getClassesInPackage(packageName)) {
				json.addClassTag(c.getSimpleName(), c);
			}
		}
	}

	// Test
	public static void main(String[] args) {
		Assets a = new Assets();
		Assets.AssetList l = new AssetList();
		// l.animations = new ArrayList<String>();
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
		abs.add(new ProjectileAbility());
		System.out.println(a.json.prettyPrint(a.json.toJson(abs)));

		BaseEntityLoadData edt = new BaseEntityLoadData();
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
		BaseEntityLoadData loadData = a.json.fromJson(BaseEntityLoadData.class,
				a.json.toJson(edt));
		System.out.println(loadData.sounds.get(UnitStateEnum.MOVE.name()));

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

	public void createObjectData() {
		for (SoundData soundData : assets.sounds) {
			SoundFx sfx = new SoundFx();
			sfx.looping = soundData.looping;
			sfx.sound = manager.get(soundData.path);
			soundMap.put(soundData.name, sfx);
		}

		for (String name : assets.animations.keySet()) {
			loadAnimation(name, assets.animations.get(name));
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

		BaseEntityLoadData loadData = createObjectFromJson(BaseEntityLoadData.class,
				path);

		EntityData data = new EntityData();
		data.maxHealth = loadData.maxHealth;
		data.jumpHeight = loadData.jumpHeight;
		data.moveSpeed = loadData.moveSpeed;
		data.damage = loadData.damage;
		data.defaultTeam = loadData.defaultTeam;
		data.dimensions = loadData.dimensions;

		// attach animations
		data.animations = new ArrayList<Animation>();

		for (String animationName : loadData.animations) {
			data.animations.add(animationMap.get(animationName));
		}

		// attach sounds
		data.sounds = new ArrayList<ArrayList<SoundFx>>();
		for (UnitStateEnum state : UnitStateEnum.values()) {
			json.setElementType(BaseEntityLoadData.class, "sounds", ArrayList.class);
			ArrayList<String> soundNames = loadData.sounds.get(state.name());
			if (soundNames != null) {
				ArrayList<SoundFx> soundList = new ArrayList<SoundFx>();
				data.sounds.add(soundList);

				if (soundNames != null) {
					for (String soundName : soundNames) {
						soundList.add(soundMap.get(soundName));
					}
				}
			} else {
				data.sounds.add(null);
			}
		}

		data.defaultAbilities = new ArrayList<Ability>(loadData.abilities);

		data.controller = loadData.controller;

		entityDataMap.put(name, data);

		System.out.println("EntityData Loaded : " + name);
	}

	private void loadAnimation(String name, String path) {
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

		animationMap.put(name, new Animation(data.frameDuration, frames));

		System.out.println("Animation Loaded : " + name);
	}

	private static class AssetList {
		public ArrayList<SoundData> sounds; // path
		public ArrayList<String> textures; // path
		public HashMap<String, String> animations; // name, path
		public HashMap<String, String> entities; // name, path
	}

	private static class SoundData {
		public String name;
		public String path;
		public boolean looping;
	}

	private static class BaseEntityLoadData extends BaseEntityData {
		// public int maxHealth;
		// public float jumpHeight;
		// public float moveSpeed;
		// public Controller controller;
		public ArrayList<Ability> abilities;
		public ArrayList<String> animations;
		public HashMap<UnitStateEnum, ArrayList<String>> sounds;
	}
}