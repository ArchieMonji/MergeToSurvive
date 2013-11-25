package com.vgdc.merge.assets.loaders;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.vgdc.merge.entities.PlatformData;

public class PlatformDataLoader extends AsynchronousAssetLoader<PlatformData, PlatformDataLoader.PlatformDataParameter> {
	
	private Json json;
	
	private PlatformData data;

	public PlatformDataLoader(FileHandleResolver resolver, Json json) {
		super(resolver);
		this.json = json;
	}

	public class PlatformDataParameter extends AssetLoaderParameters<PlatformData>{

	}

	@Override
	public void loadAsync(AssetManager manager, String fileName,
			PlatformDataParameter parameter) {
		
	}

	@Override
	public PlatformData loadSync(AssetManager manager, String fileName,
			PlatformDataParameter parameter) {
		return data;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Array<AssetDescriptor> getDependencies(String fileName,
			PlatformDataParameter parameter) {
		data = json.fromJson(PlatformData.class, resolve(fileName));
		Array<AssetDescriptor> deps = new Array<AssetDescriptor>();
		deps.add(new AssetDescriptor(data.texture, Texture.class));
		return deps;
	}

}
