package com.vgdc.merge.assets.loaders;

import org.python.core.PyObject;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.vgdc.merge.assets.loaders.data.DescriptorData;
import com.vgdc.merge.entities.EntityData;
import com.vgdc.merge.entities.audio.SoundFx;

public class DescriptorLoader extends AsynchronousAssetLoader<DescriptorData, DescriptorLoader.DescriptorParamater>{
	
	private Json json;
	
	private DescriptorData data;
	
	public DescriptorLoader(FileHandleResolver resolver, Json json) {
		super(resolver);
		this.json = json;
	}

	public class DescriptorParamater extends AssetLoaderParameters<DescriptorData>
	{
		
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Array<AssetDescriptor> getDependencies(String fileName,
			DescriptorParamater parameter) {
		data = json.fromJson(DescriptorData.class, resolve(fileName));
		Array<AssetDescriptor> deps = new Array<AssetDescriptor>();
		
		//load sounds
		if(data.sounds!=null)
			for(String s : data.sounds)
				deps.add(new AssetDescriptor<Sound>(s, Sound.class));
		
		//load soundfx
		if(data.soundfxs!=null)
			for(String s : data.soundfxs)
				deps.add(new AssetDescriptor<SoundFx>(s, SoundFx.class));
		
		//load images
		if(data.images!=null)
			for(String s : data.images)
				deps.add(new AssetDescriptor<Texture>(s, Texture.class));
		
		//load animations
		if(data.animations!=null)
			for(String s : data.animations)
				deps.add(new AssetDescriptor<Animation>(s, Animation.class));
		
		//load music
		if(data.music!=null)
			for(String s : data.music)
				deps.add(new AssetDescriptor<Music>(s, Music.class));
		
		//load scripts
		if(data.scripts!=null)
			for(String s : data.scripts)
				deps.add(new AssetDescriptor<PyObject>(s, PyObject.class));
		
		//load entitydata
		if(data.entitydata!=null)
			for(String s : data.entitydata)
				deps.add(new AssetDescriptor<EntityData>(s, EntityData.class));
		
		//load fonts
		if(data.fonts!=null)
			for(String s : data.fonts)
				deps.add(new AssetDescriptor<BitmapFont>(s, BitmapFont.class));
		
		return deps;
	}

	@Override
	public void loadAsync(AssetManager manager, String fileName,
			DescriptorParamater parameter) {
		
	}

	@Override
	public DescriptorData loadSync(AssetManager manager, String fileName,
			DescriptorParamater parameter) {
		return data;
	}

	

}
