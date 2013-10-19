package com.vgdc.merge.assets;

import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.utils.Array;

public class ScriptLoader extends AsynchronousAssetLoader<PyObject, ScriptLoader.ScriptParameter> {
	
	private PythonInterpreter interpreter;
	
	private PyObject object;
	
	public ScriptLoader(FileHandleResolver resolver, PythonInterpreter interpreter) {
		super(resolver);
		this.interpreter = interpreter;
	}

	static public class ScriptParameter extends
	AssetLoaderParameters<PyObject> {
	
}

	@Override
	public Array<AssetDescriptor> getDependencies(String fileName,
			ScriptParameter parameter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void loadAsync(AssetManager manager, String fileName,
			ScriptParameter parameter) {
		interpreter.execfile(resolve(fileName).path());
		object = interpreter.get(fileName);
	}

	@Override
	public PyObject loadSync(AssetManager manager, String fileName,
			ScriptParameter parameter) {
		return object;
	}

}
