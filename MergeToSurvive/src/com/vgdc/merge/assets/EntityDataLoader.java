package com.vgdc.merge.assets;

import java.util.ArrayList;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.vgdc.merge.entities.EntityData;
import com.vgdc.merge.entities.UnitStateEnum;
import com.vgdc.merge.entities.abilities.Ability;
import com.vgdc.merge.entities.audio.SoundFx;

public class EntityDataLoader extends AsynchronousAssetLoader<EntityData, EntityDataLoader.EntityLoadDataParameter> {
	
	private Json json;
	
	private EntityLoadData loadData;

	public EntityDataLoader(FileHandleResolver resolver, Json json) {
		super(resolver);
		this.json = json;
	}
	
	static public class EntityLoadDataParameter extends
	AssetLoaderParameters<EntityData> {
}

	@Override
	public Array<AssetDescriptor> getDependencies(String fileName,
			EntityLoadDataParameter parameter) {
		loadData = json.fromJson(EntityLoadData.class, resolve(fileName));
		Array<AssetDescriptor> deps = new Array<AssetDescriptor>();
		for(String s : loadData.animations)
			deps.add(new AssetDescriptor(s, Animation.class));
		for(ArrayList<EntityLoadData.SoundFxData> a : loadData.sounds.values())
			for(EntityLoadData.SoundFxData s : a)
				deps.add(new AssetDescriptor(s.path, Sound.class));
		return deps;
	}

	@Override
	public void loadAsync(AssetManager manager, String fileName,
			EntityLoadDataParameter parameter) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public EntityData loadSync(AssetManager manager, String fileName,
			EntityLoadDataParameter parameter) {
		EntityData data = new EntityData();

		loadData.copyInto(data);
		// attach animations
		data.animations = new ArrayList<Animation>();

		for (String animationName : loadData.animations) {
			data.animations.add(manager.get(animationName, Animation.class));
		}

		// attach sounds
		data.sounds = new ArrayList<ArrayList<SoundFx>>();
		for (UnitStateEnum state : UnitStateEnum.values()) {
			//json.setElementType(BaseEntityLoadData.class, "sounds",
			//		ArrayList.class);
			ArrayList<EntityLoadData.SoundFxData> soundNames = loadData.sounds.get(state.name());
			if (soundNames != null) {
				ArrayList<SoundFx> soundList = new ArrayList<SoundFx>();
				data.sounds.add(soundList);

				if (soundNames != null) {
					for (EntityLoadData.SoundFxData soundFxData : soundNames) {
						SoundFx sound = new SoundFx();
						sound.sound = manager.get(soundFxData.path, Sound.class);
						sound.looping = soundFxData.looping;
						soundList.add(sound);
					}
				}
			} else {
				data.sounds.add(null);
			}

		}

		data.defaultAbilities = new ArrayList<Ability>(loadData.abilities);
		return data;
	}

}
