package com.vgdc.merge.assets;

import java.util.ArrayList;

import org.python.core.PyObject;

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
import com.vgdc.merge.entities.controllers.Controller;

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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Array<AssetDescriptor> getDependencies(String fileName,
			EntityLoadDataParameter parameter) {
		loadData = json.fromJson(EntityLoadData.class, resolve(fileName));
		Array<AssetDescriptor> deps = new Array<AssetDescriptor>();
		for(String s : loadData.animations)
			deps.add(new AssetDescriptor(s, Animation.class));
		for(String s : loadData.sounds.keys())
		{
			for(SoundFxData a : loadData.sounds.get(s))
				deps.add(new AssetDescriptor(a.path, Sound.class));
		}
		if(loadData.abilities!=null)
			for(String s : loadData.abilities)
				deps.add(new AssetDescriptor(s, PyObject.class));
		if(loadData.controller!=null)
			deps.add(new AssetDescriptor(loadData.controller, PyObject.class));
		return deps;
	}

	@Override
	public void loadAsync(AssetManager manager, String fileName,
			EntityLoadDataParameter parameter) {
		
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
			Array<SoundFxData> soundNames = loadData.sounds.get(state.name());
			if (soundNames != null) {
				ArrayList<SoundFx> soundList = new ArrayList<SoundFx>();
				data.sounds.add(soundList);

				if (soundNames != null) {
					for (SoundFxData soundFxData : soundNames) {
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
		
		PyObject object = manager.get(loadData.controller, PyObject.class).__call__();
		data.controller = (Controller) (object.__tojava__(Controller.class));
		
		data.defaultAbilities = new ArrayList<Ability>();
		if(loadData.abilities!=null) {
			for(String s : loadData.abilities) {
				object = manager.get(s, PyObject.class).__call__();
				data.defaultAbilities.add((Ability) object.__tojava__(Ability.class));
			}
		}
		
		return data;
	}

}
