package com.vgdc.merge.assets.loaders;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.utils.Array;
import com.vgdc.merge.assets.LoadableAsset;

public class ScriptLoader extends AsynchronousAssetLoader<PyObject, ScriptLoader.ScriptParameter> {
	
	private PythonInterpreter interpreter;
	
	private TreeMap<String, PyObject> objectMap = new TreeMap<String, PyObject>();
	
	public ScriptLoader(FileHandleResolver resolver, PythonInterpreter interpreter) {
		super(resolver);
		this.interpreter = interpreter;
	}

	static public class ScriptParameter extends
	AssetLoaderParameters<PyObject> {
		
		public Map<String, Object> argsMap = null;
	
}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public Array<AssetDescriptor> getDependencies(String fileName,
			ScriptParameter parameter) {
		System.out.println("start : " + fileName);
		interpreter.set("requirements", null);
		interpreter.execfile(resolve(fileName).path());
		PyObject cls = interpreter.get(fileName);
		objectMap.put(fileName, cls);
		//PyDictionary dictionary = interpreter.get("requirements", PyDictionary.class);
		System.out.println(parameter.argsMap);
		if(parameter.argsMap==null)
			return null;
		PyObject object = cls.__call__();
		for(Entry<String, Object> e : parameter.argsMap.entrySet())
		{
			object.__setattr__(e.getKey(), PyHelper.getObject(e.getValue()));
		}
		Map<String, String> dependenciesMap = ((LoadableAsset) object.__tojava__(LoadableAsset.class)).getRequirements();
		if(dependenciesMap==null)
			return null;
		Array<AssetDescriptor> deps = new Array<AssetDescriptor>();
		PyHelper.addDependencies(dependenciesMap, deps, fileName);
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
