package com.vgdc.merge.assets;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Json;
import com.vgdc.merge.EntityData;

public class AssetLoader {
	Json json = new Json();

	AssetManager manager = new AssetManager();

	HashMap<String, EntityData> entityDataMap = new HashMap<String, EntityData>();
	HashMap<String, Animation> animationMap = new HashMap<String, Animation>();

	public <T> T createObjectFromJson(Class<T> type, String path) {
		return json.fromJson(type, Gdx.files.internal(path));
	}

	public void loadEntityData(String name, String path) {
		EntityData data = createObjectFromJson(EntityData.class, path);

		entityDataMap.put(name, data);
	}

	public void loadAnimation(String path) {
		AnimationData data = json.fromJson(AnimationData.class,
				Gdx.files.internal(path));

		Texture sheet = manager.get(data.path, Texture.class);

		// break sheet into individual frames
		TextureRegion[][] tmp = TextureRegion.split(sheet, sheet.getWidth()
				/ data.cols, sheet.getHeight() / data.rows);

		// repack into 1D array
		TextureRegion[] frames = new TextureRegion[data.cols * data.rows];
		int index = 0;
		for (int i = 0; i < data.rows; i++) {
			for (int j = 0; j < data.cols; j++) {
				frames[index++] = tmp[i][j];
			}
		}

		animationMap.put(data.name, new Animation(data.frameDuration, frames));
	}

	public void hasAnimation(String name) {
		animationMap.containsKey(name);
	}

	public Animation getAnimation(String name) {
		return animationMap.get(name);
	}

	public void dispose() {
		manager.dispose();
	}
}