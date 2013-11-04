package com.vgdc.merge.assets.loaders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import org.python.core.PyObject;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.vgdc.merge.assets.loaders.data.EntityLoadMapData;
import com.vgdc.merge.entities.EntityData;
import com.vgdc.merge.entities.UnitStateEnum;
import com.vgdc.merge.entities.abilities.Ability;
import com.vgdc.merge.entities.audio.SoundFx;
import com.vgdc.merge.entities.controllers.Controller;

public class EntityDataMapLoader extends AsynchronousAssetLoader<EntityData, EntityDataMapLoader.EntityDataParameter>{
	
	private Json json;
	
	//private EntityLoadData loadData;
	
	private TreeMap<String, EntityLoadMapData> loadDataMap = new TreeMap<String, EntityLoadMapData>();
	
	private EntityData data;

	public EntityDataMapLoader(FileHandleResolver resolver, Json json) {
		super(resolver);
		this.json = json;
	}
	
	static public class EntityDataParameter extends
	AssetLoaderParameters<EntityData> {
}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Array<AssetDescriptor> getDependencies(String fileName,
			EntityDataParameter parameter) {
		System.out.println("start : " + fileName);
		HashMap<String, Object> mapData = json.fromJson(HashMap.class, resolve(fileName));
		EntityLoadMapData loadData = new EntityLoadMapData();
		loadData.copyFrom(mapData);
		Array<AssetDescriptor> deps = new Array<AssetDescriptor>();
		if(loadData.animations != null)
			for(String s : loadData.animations)
				deps.add(new AssetDescriptor(s, Animation.class));
		if(loadData.sounds != null)
			for(Entry<String, Array<String>> s : loadData.sounds.entries())
				for(String a : s.value)
					deps.add(new AssetDescriptor(a, SoundFx.class));
		if(loadData.abilities!=null)
			for(String s : loadData.abilities)
				deps.add(new AssetDescriptor(s, PyObject.class));
		if(loadData.controller!=null)
			deps.add(new AssetDescriptor(loadData.controller, PyObject.class));
		loadDataMap.put(fileName, loadData);
		return deps;
	}

	@Override
	public void loadAsync(AssetManager manager, String fileName,
			EntityDataParameter parameter) {
		data = new EntityData();
		
		EntityLoadMapData loadData = loadDataMap.remove(fileName);

		loadData.copyInto(data);
		// attach animations

		if(loadData.animations!=null)
		{
			data.animations = new ArrayList<Animation>();
			for (String animationName : loadData.animations) {
				data.animations.add(manager.get(animationName, Animation.class));
			}
		}

		// attach sounds
		data.sounds = new ArrayList<ArrayList<SoundFx>>();
		for (UnitStateEnum state : UnitStateEnum.values()) {
			//json.setElementType(BaseEntityLoadData.class, "sounds",
			//		ArrayList.class);
			Array<String> soundNames = loadData.sounds.get(state.name());
			if (soundNames != null) {
				ArrayList<SoundFx> soundList = new ArrayList<SoundFx>();
				data.sounds.add(soundList);

				if (soundNames != null) {
					for (String soundFxData : soundNames) {
						soundList.add(manager.get(soundFxData, SoundFx.class));
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
//		data.dynamicVariableTable = loadData.dynamicVariables;
//		System.out.println("finish : " + fileName);
	}

	@Override
	public EntityData loadSync(AssetManager manager, String fileName,
			EntityDataParameter parameter) {
		
		return data;
	}
	
}
