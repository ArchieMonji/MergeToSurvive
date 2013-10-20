package com.vgdc.merge.assets;

import java.util.HashMap;
import java.util.TreeMap;

import org.python.core.PyDictionary;
import org.python.core.PyObject;
import org.python.core.PyTuple;
import org.python.util.PythonInterpreter;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.Array;
import com.vgdc.merge.entities.EntityData;

public class ScriptLoader extends AsynchronousAssetLoader<PyObject, ScriptLoader.ScriptParameter> {
	
	@SuppressWarnings("rawtypes")
	private HashMap<String, Class> nameClassMap = new HashMap<String, Class>();
	
	private PythonInterpreter interpreter;
	
	private TreeMap<String, PyObject> objectMap = new TreeMap<String, PyObject>();
	
	public ScriptLoader(FileHandleResolver resolver, PythonInterpreter interpreter) {
		super(resolver);
		this.interpreter = interpreter;
		nameClassMap.put("entitydata", EntityData.class);
		nameClassMap.put("animation", Animation.class);
		nameClassMap.put("ability", PyObject.class);
		nameClassMap.put("controller", PyObject.class);
	}

	static public class ScriptParameter extends
	AssetLoaderParameters<PyObject> {
	
}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Array<AssetDescriptor> getDependencies(String fileName,
			ScriptParameter parameter) {
		System.out.println("start : " + fileName);
		interpreter.set("requirements", null);
		interpreter.execfile(resolve(fileName).path());
		objectMap.put(fileName, interpreter.get(fileName));
		PyDictionary dictionary = interpreter.get("requirements", PyDictionary.class);
		System.out.println(dictionary);
		if(dictionary==null)
			return null;
		Array<AssetDescriptor> deps = new Array<AssetDescriptor>();
		for(Object o : dictionary.items())
		{
			PyTuple tuple = (PyTuple) o;
			String name = (String) tuple.get(0);
			String cls = (String) tuple.get(1);
			deps.add(new AssetDescriptor(name, nameClassMap.get(cls.toLowerCase())));
		}
		return deps;
	}

	@Override
	public void loadAsync(AssetManager manager, String fileName,
			ScriptParameter parameter) {
	}

	@Override
	public PyObject loadSync(AssetManager manager, String fileName,
			ScriptParameter parameter) {
		System.out.println("finish : " + fileName);
		return objectMap.remove(fileName);
	}

}
