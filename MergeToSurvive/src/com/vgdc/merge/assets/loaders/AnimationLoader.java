package com.vgdc.merge.assets.loaders;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.vgdc.merge.assets.loaders.data.AnimationData;

public class AnimationLoader extends AsynchronousAssetLoader<Animation, AnimationLoader.AnimationDataParameter> {
	
	private Json json;
	
	private AnimationData data;
	
	public AnimationLoader(FileHandleResolver resolver, Json json) {
		super(resolver);
		this.json = json;
	}

	static public class AnimationDataParameter extends
		AssetLoaderParameters<Animation> {
		
	}

	@Override
	public Array<AssetDescriptor> getDependencies(String fileName,
			AnimationDataParameter parameter) {
		System.out.println("start : " + fileName);
		Array<AssetDescriptor> deps = new Array<AssetDescriptor>();
		data = json.fromJson(AnimationData.class, resolve(fileName));
		deps.add(new AssetDescriptor<Texture>(data.path, Texture.class));
		return deps;
	}

	@Override
	public void loadAsync(AssetManager manager, String fileName,
			AnimationDataParameter parameter) {
		
	}

	@Override
	public Animation loadSync(AssetManager manager, String fileName,
			AnimationDataParameter parameter) {
		
		Texture sheet = manager.get(data.path, Texture.class);

		TextureRegion[][] tmp = null;
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
		System.out.println("finish : " + fileName);
		return new Animation(data.frameDuration, frames);
	}
}
