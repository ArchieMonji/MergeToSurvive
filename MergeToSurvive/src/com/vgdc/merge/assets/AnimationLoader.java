package com.vgdc.merge.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

//TODO: Fix
public class AnimationLoader extends
		AsynchronousAssetLoader<Animation, AnimationLoader.AnimationParameter> {

	private AnimationData data;

	public AnimationLoader(FileHandleResolver resolver) {
		super(resolver);
	}

	static public class AnimationParameter extends
			AssetLoaderParameters<Animation> {
		public AnimationData animationData;
	}

	@Override
	public void loadAsync(AssetManager manager, String fileName,
			AnimationParameter parameter) {
		// TODO Auto-generated method stub

	}

	@Override
	public Animation loadSync(AssetManager manager, String fileName,
			AnimationParameter parameter) {
		FileHandle handle = resolve(fileName);

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

		return new Animation(data.frameDuration, frames);
	}

	@Override
	public Array<AssetDescriptor> getDependencies(String fileName,
			AnimationParameter parameter) {
		Array<AssetDescriptor> deps = new Array<AssetDescriptor>();
		if (parameter != null && parameter.animationData != null) {
			data = parameter.animationData;
			return deps;
		}
		FileHandle handle = resolve(fileName);
		data = new Json().fromJson(AnimationData.class, fileName);
		deps.add(new AssetDescriptor(data.path, AnimationData.class));
		return deps;
	}

	private static class AnimationData {
		public String name;
		public String path;
		public float frameDuration;
		public int rows;
		public int cols;
	}
}
