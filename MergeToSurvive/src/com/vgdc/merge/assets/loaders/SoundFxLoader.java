package com.vgdc.merge.assets.loaders;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.vgdc.merge.assets.loaders.data.SoundFxData;
import com.vgdc.merge.entities.audio.SoundFx;

public class SoundFxLoader extends AsynchronousAssetLoader<SoundFx, SoundFxLoader.SoundFxParameter> {
	
	private Json json;
	
	private SoundFxData data;
	
	public SoundFxLoader(FileHandleResolver resolver, Json json) {
		super(resolver);
		this.json = json;
	}

	static public class SoundFxParameter extends
		AssetLoaderParameters<SoundFx> {
		
	}

	@Override
	public Array<AssetDescriptor> getDependencies(String fileName,
			SoundFxParameter parameter) {
		System.out.println("start : " + fileName);
		Array<AssetDescriptor> deps = new Array<AssetDescriptor>();
		data = json.fromJson(SoundFxData.class, resolve(fileName));
		deps.add(new AssetDescriptor<Sound>(data.path, Sound.class));
		return deps;
	}

	@Override
	public void loadAsync(AssetManager manager, String fileName,
			SoundFxParameter parameter) {
		
	}

	@Override
	public SoundFx loadSync(AssetManager manager, String fileName,
			SoundFxParameter parameter) {
		
		SoundFx fx = new SoundFx();
		fx.looping = data.looping;
		fx.sound = manager.get(data.path);
		System.out.println("finish : " + fileName);
		return fx;
	}
}

