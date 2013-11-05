package com.vgdc.merge.assets.loaders;

import java.util.ArrayList;
import java.util.TreeMap;

import org.python.core.PyBoolean;
import org.python.core.PyDictionary;
import org.python.core.PyFloat;
import org.python.core.PyJavaType;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyString;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.badlogic.gdx.utils.OrderedMap;
import com.vgdc.merge.assets.loaders.data.EntityLoadData;
import com.vgdc.merge.entities.EntityData;
import com.vgdc.merge.entities.UnitStateEnum;
import com.vgdc.merge.entities.abilities.Ability;
import com.vgdc.merge.entities.audio.SoundFx;
import com.vgdc.merge.entities.controllers.Controller;

public class EntityDataLoader extends AsynchronousAssetLoader<EntityData, EntityDataLoader.EntityLoadDataParameter> {
	
	private class NoScriptException extends RuntimeException
	{
		
		public NoScriptException(String text)
		{
			super(text);
		}
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
	}
	
	private static final String script = "script";
	
	private Json json;
	
	//private EntityLoadData loadData;
	
	private TreeMap<String, EntityLoadData> loadDataMap = new TreeMap<String, EntityLoadData>();
	
	private EntityData data;

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
		System.out.println("start : " + fileName);
		EntityLoadData loadData = json.fromJson(EntityLoadData.class, resolve(fileName));
		Array<AssetDescriptor> deps = new Array<AssetDescriptor>();
		if(loadData.animations != null)
			for(String s : loadData.animations)
				deps.add(new AssetDescriptor(s, Animation.class));
		if(loadData.sounds != null)
			for(String s : loadData.sounds.keys())
			{
				for(String a : loadData.sounds.get(s))
				{
					System.out.println(a);
					deps.add(new AssetDescriptor(a, SoundFx.class));
				}
			}
		if(loadData.sabilities!=null)
		{
			for(OrderedMap<String, Object> m : loadData.sabilities)
			{
				String cont = (String) m.get(script);
				if(cont == null)
					throw new NoScriptException("the file " + fileName + " doesnt specify a python file for an ability");
				deps.add(new AssetDescriptor(cont, PyObject.class));
			}
		}
		if(loadData.scontroller!=null)
		{
			String cont = (String) loadData.scontroller.get(script);
			if(cont == null)
				throw new NoScriptException("the file " + fileName + " doesnt specify a python file for its controller");
			deps.add(new AssetDescriptor(cont, PyObject.class));
		}
		else if(loadData.controller==null)
		{
			throw new NoScriptException("the file " + fileName + " doesnt specify a java class or python script for its controller");
		}
		loadDataMap.put(fileName, loadData);
		return deps;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void loadAsync(AssetManager manager, String fileName,
			EntityLoadDataParameter parameter) {
		data = new EntityData();
		
		EntityLoadData loadData = loadDataMap.remove(fileName);

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
		
		//attach controller
		if(loadData.scontroller != null)
		{
			String cont = (String) loadData.scontroller.remove(script);
			PyObject object = manager.get(cont, PyObject.class).__call__();
			for(Entry<String, Object> entry : loadData.scontroller.entries())
			{
				object.__setattr__(entry.key, getObject(entry.value));
			}
			data.controller = ((Controller) object.__tojava__(Controller.class));
		}
		else
		{
			data.controller = loadData.controller;
		}
//		String cont = (String) loadData.controller.remove("script");
//		if(cont==null)
//		{
//			cont = (String) loadData.controller.remove("class");
//			if(cont == null)
//				throw new NoScriptException("No class or script field in controller");
//			Controller c = null;
//			try {
//				System.out.println(json.getClass(cont));
//				c = (Controller) json.getClass(cont).newInstance();
//			} catch (InstantiationException e) {
//				throw new NoScriptException("The class of controller could not be found");
//			} catch (IllegalAccessException e) {
//				throw new NoScriptException("The class of controller could not be found");
//			}
//			json.readFields(c, loadData.controller);
//			data.controller = c;
//		}
//		PyObject object = manager.get(cont, PyObject.class).__call__();
//		for(Entry<String, Object> o : loadData.controller.entries())
//		{
//			object.__setattr__(o.key, getObject(o.value));
//		}
//		data.controller = (Controller) (object.__tojava__(Controller.class));
		
		//attach abilities
		data.defaultAbilities = new ArrayList<Ability>();
		if(loadData.sabilities!=null)
		{
			for(OrderedMap<String, Object> m : loadData.sabilities)
			{
				String abil = (String) m.remove(script);
				PyObject object = manager.get(abil, PyObject.class).__call__();
				for(Entry<String, Object> entry : m.entries())
				{
					object.__setattr__(entry.key, getObject(entry.value));
				}
				data.defaultAbilities.add((Ability) object.__tojava__(Ability.class));
			}
		}
		if(loadData.abilities!=null)
		{
			data.defaultAbilities.addAll(loadData.abilities);
		}
//		if(loadData.abilities!=null) {
//			for(OrderedMap<String, Object> map : loadData.abilities) {
//				cont = (String) map.remove("script");
//				if(cont==null)
//				{
//					cont = (String) loadData.controller.remove("class");
//					if(cont == null)
//						throw new NoScriptException("No class or script field in controller");
//					Ability a = null;
//					try {
//						a = (Ability) json.getClass(cont).newInstance();
//					} catch (InstantiationException e) {
//						throw new NoScriptException("The class of ability could not be found");
//					} catch (IllegalAccessException e) {
//						throw new NoScriptException("The class of ability could not be found");
//					}
//					json.readFields(a, map);
//					data.defaultAbilities.add(a);
//				}
//				else
//				{
//					object = manager.get(cont, PyObject.class).__call__();
//					for(Entry<String, Object> o : loadData.controller.entries())
//					{
//						object.__setattr__(o.key, getObject(o.value));
//					}
//					data.defaultAbilities.add((Ability) object.__tojava__(Ability.class));
//				}
//			}
//		}
		System.out.println("finish : " + fileName);
	}
	
	@SuppressWarnings("unchecked")
	private PyObject getObject(Object obj)
	{
		if(obj instanceof Float)
		{
			return new PyFloat((Float)obj);
		}
		if(obj instanceof String)
		{
			return new PyString((String)obj);
		}
		if(obj instanceof Boolean)
		{
			return new PyBoolean((Boolean)obj);
		}
		if(obj instanceof Array)
		{
			PyList list = new PyList();
			for(Object o : (Array<Object>)obj)
				list.add(getObject(o));
			return list;
		}
		if(obj instanceof OrderedMap)
		{
			PyDictionary dict = new PyDictionary();
			for(Entry<String, Object> e : ((OrderedMap<String, Object>)obj).entries())
				dict.put(e.key, getObject(e.value));
			return dict;
		}
		return PyJavaType.wrapJavaObject(obj);
	}

	@Override
	public EntityData loadSync(AssetManager manager, String fileName,
			EntityLoadDataParameter parameter) {
		
		return data;
	}

}
