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
import com.vgdc.merge.Ability;
import com.vgdc.merge.Controller;
import com.vgdc.merge.Controls;
import com.vgdc.merge.EntityData;
import com.vgdc.merge.test.TestAbility;

public class Assets {
	public AssetManager manager = new AssetManager();
	public HashMap<String, EntityData> entityDataMap = new HashMap<String, EntityData>();
	public HashMap<String, Animation> animationMap = new HashMap<String, Animation>();

	private Json json = new Json();

	//Test
	public static void main(String[] args) {
		Assets a = new Assets();
		Assets.AssetList l = new AssetList();
		l.animations = new ArrayList<String>();
		l.sounds = new ArrayList<String>();
		l.textures = new ArrayList<String>();
		l.entities = new HashMap<String,String>();
		l.entities.put("player","player.json");
		System.out.println(a.json.prettyPrint(a.json.toJson(l)));
		ArrayList<Ability> abs = new ArrayList<Ability>();
		abs.add(new TestAbility());
		System.out.println(a.json.prettyPrint(a.json.toJson(abs)));
		
		Controls c = new Controls();
		c.down = Keys.S;
		c.up = Keys.W;
		c.left = Keys.A;
		c.right = Keys.D;
		c.useAbility = Buttons.LEFT;
		c.toggleAbility = Buttons.LEFT;
		System.out.println(a.json.prettyPrint(a.json.toJson(c)));
		
	}
	
	public <T> T createObjectFromJson(Class<T> type, String path) {
		return json.fromJson(type, Gdx.files.internal(path));
	}

	public void loadAssets(String path) {
		AssetList assets = createObjectFromJson(AssetList.class, path);

		for (String soundPath : assets.sounds) {
			manager.load(soundPath, Sound.class);
		}

		for (String texturePath : assets.textures) {
			manager.load(texturePath, Texture.class);
		}
		

		//TODO: Change to support asynchronous loading
		manager.finishLoading();

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
		EntityTemplate template = createObjectFromJson(EntityTemplate.class, path);

		EntityData data = new EntityData();
		data.maxHealth = template.maxHealth;
		data.jumpHeight = template.jumpHeight;
		data.moveSpeed = template.moveSpeed;
		
		data.animations = new ArrayList<Animation>();
		
		for(String animationName: template.animations){
			data.animations.add(animationMap.get(animationName));
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

		// break sheet into individual frames
		TextureRegion[][] tmp = TextureRegion.split(sheet, sheet.getWidth()
				/ data.cols, sheet.getHeight() / data.rows);

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
		public ArrayList<String> sounds; // path
		public ArrayList<String> textures; // path
		public ArrayList<String> animations; // path
		public HashMap<String, String> entities; // name, path
	}
	
	private static class EntityTemplate{
		public int maxHealth;
		public float jumpHeight;
		public float moveSpeed;
		public Controller controller;
		public ArrayList<Ability> abilities;
		public ArrayList<String> animations;
	}
}