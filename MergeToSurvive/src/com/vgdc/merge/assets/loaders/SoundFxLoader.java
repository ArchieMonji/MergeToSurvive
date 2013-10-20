package com.vgdc.merge.assets.loaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.vgdc.merge.entities.audio.SoundFx;

public class SoundFxLoader extends
		SynchronousAssetLoader<SoundFx, SoundFxLoader.SoundFxParameter> {
	public SoundFxLoader(FileHandleResolver resolver) {
		super(resolver);
	}

	@Override
	public SoundFx load(AssetManager assetManager, String fileName,
			SoundFxParameter parameter) {
		SoundFx sfx = new SoundFx();
		Json json = new Json();
		System.out.println(fileName);
		SoundFxParameter data = json.fromJson(SoundFxParameter.class, resolve(fileName));
		sfx.sound = Gdx.audio.newSound(resolve(data.path));
		sfx.looping = data.looping;
		return sfx;
	}

	static public class SoundFxParameter extends AssetLoaderParameters<SoundFx> {
		public String name;
		public String path;
		public boolean looping;
	}

	@Override
	public Array<AssetDescriptor> getDependencies(String fileName,
			SoundFxParameter parameter) {
		// TODO Auto-generated method stub
		return null;
	}
}
