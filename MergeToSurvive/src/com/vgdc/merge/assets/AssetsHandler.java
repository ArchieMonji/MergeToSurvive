package com.vgdc.merge.assets;

import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.MusicLoader;
import com.badlogic.gdx.assets.loaders.SoundLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.vgdc.merge.assets.loaders.AnimationLoader;
import com.vgdc.merge.assets.loaders.EntityDataLoader;
import com.vgdc.merge.entities.EntityData;
import com.vgdc.merge.entities.abilities.Ability;
import com.vgdc.merge.entities.audio.SoundFx;
import com.vgdc.merge.entities.controllers.Controller;

public class AssetsHandler {
	
	private AssetManager manager = new AssetManager();
	
	private PythonInterpreter interpreter = new PythonInterpreter();
	
	private DirectoryHandler textureDirectory = new DirectoryHandler("test/art", ".png");
	private DirectoryHandler soundDirectory = new DirectoryHandler("test/sound", ".ogg");
	private JsonDirectoryHandler soundFxDirectory = new JsonDirectoryHandler("test/soundfx", ".json");
	private DirectoryHandler musicDirectory = new DirectoryHandler("test/music", ".ogg");
	private JsonDirectoryHandler animationDirectory = new JsonDirectoryHandler("test/animations", ".json");
	private JsonDirectoryHandler entityDataDirectory = new JsonDirectoryHandler("test/entities", ".json");
	private DirectoryHandler scriptsDirectory = new DirectoryHandler("test/scripts", ".py");
	
	public AssetsHandler()
	{
		populateJsons();
		interpreter.getSystemState().path.append(new PyString(scriptsDirectory.getPath().getPath()));
		manager.setLoader(Texture.class, new TextureLoader(textureDirectory));
		manager.setLoader(Sound.class, new SoundLoader(soundDirectory));
		manager.setLoader(Music.class, new MusicLoader(musicDirectory));
		manager.setLoader(Animation.class, new AnimationLoader(animationDirectory, animationDirectory.getJson()));
		manager.setLoader(EntityData.class, new EntityDataLoader(entityDataDirectory, entityDataDirectory.getJson()));
		manager.setLoader(PyObject.class, new ScriptLoader(scriptsDirectory, interpreter));
		manager.setLoader(SoundFx.class, new SoundFxLoader(soundFxDirectory, soundFxDirectory.getJson()));
	}
	
	private void populateJsons()
	{
		entityDataDirectory.getJson().addClassTag(SoundFxData.class.getSimpleName(), SoundFxData.class);
//		String[] packages = new String[] {
//				"com.vgdc.merge.entities.controllers",
//				"com.vgdc.merge.entities.abilities" };
//
//		for (String packageName : packages) {
//			for (Class<?> c : ClassFinder.getClassesInPackage(packageName)) {
//				entityDataDirectory.getJson().addClassTag(c.getSimpleName(), c);
//			}
//		}
	}
	
	public Texture getTexture(String path){
		return manager.get(path, Texture.class);
	}
	
	public Sound getSound(String path){
		return manager.get(path, Sound.class);
	}
	
	public Music getMusic(String path){
		return manager.get(path, Music.class);
	}
	
	public Animation getAnimation(String path){
		return manager.get(path, Animation.class);
	}
	
	public EntityData getEntityData(String path){
		return manager.get(path, EntityData.class);
	}
	
	public <T> T createScriptObject(String filename, Class<T> cls)
	{
		PyObject object = manager.get(filename, PyObject.class).__call__();
		return cls.cast(object.__tojava__(cls));
	}
	
	public Ability getAbility(String filename)
	{
		return createScriptObject(filename, Ability.class);
	}
	
	public Controller getController(String filename)
	{
		return createScriptObject(filename, Controller.class);
	}
	
	/**
	 * loads all files in all directories tracked by this AssetsHandler
	 */
	public void loadAll()
	{
		for(FileHandle h : textureDirectory.getFilesInDirectory())
		{
			manager.load(h.nameWithoutExtension(), Texture.class);
		}
		for(FileHandle h : soundDirectory.getFilesInDirectory())
		{
			manager.load(h.nameWithoutExtension(), Sound.class);
		}
		for(FileHandle h : musicDirectory.getFilesInDirectory())
		{
			manager.load(h.nameWithoutExtension(), Music.class);
		}
		for(FileHandle h : animationDirectory.getFilesInDirectory())
		{
			manager.load(h.nameWithoutExtension(), Animation.class);
		}
		for(FileHandle h : entityDataDirectory.getFilesInDirectory())
		{
			manager.load(h.nameWithoutExtension(), EntityData.class);
		}
		for(FileHandle h : scriptsDirectory.getFilesInDirectory())
		{
			manager.load(h.nameWithoutExtension(), PyObject.class);
		}
		for(FileHandle h : soundFxDirectory.getFilesInDirectory())
		{
			manager.load(h.nameWithoutExtension(), SoundFx.class);
		}
	}
	
	/**
	 * loads just the files necessary to run the given level
	 * @param filename
	 */
	public void loadBasedOnLevel(String filename)
	{
		
	}
	
	/**
	 * loads just the files described in the given json
	 * @param filename
	 */
	public void loadBasedOnDescription(String filename)
	{
		
	}
	
	public boolean update(){
		return manager.update();
	}
	
	public void dispose()
	{
		manager.dispose();
	}
	

}
