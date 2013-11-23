package com.vgdc.merge.assets;

import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader;
import com.badlogic.gdx.assets.loaders.MusicLoader;
import com.badlogic.gdx.assets.loaders.SoundLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.vgdc.merge.assets.loaders.AnimationLoader;
import com.vgdc.merge.assets.loaders.DescriptorLoader;
import com.vgdc.merge.assets.loaders.EntityDataLoader;
import com.vgdc.merge.assets.loaders.ScriptLoader;
import com.vgdc.merge.assets.loaders.SoundFxLoader;
import com.vgdc.merge.assets.loaders.data.DescriptorData;
import com.vgdc.merge.assets.loaders.data.SoundFxData;
import com.vgdc.merge.entities.EntityData;
import com.vgdc.merge.entities.abilities.Ability;
import com.vgdc.merge.entities.audio.SoundFx;
import com.vgdc.merge.entities.controllers.Controller;

public class AssetsHandler {
	
	private AssetManager manager = new AssetManager();
	
	private PythonInterpreter interpreter = new PythonInterpreter();
	
	private static final String defaultAssets = "assets";
	
	private DirectoryHandler textureDirectory;
	private DirectoryHandler soundDirectory;
	private JsonDirectoryHandler soundFxDirectory;
	private DirectoryHandler musicDirectory;
	private JsonDirectoryHandler animationDirectory;
	private JsonDirectoryHandler entityDataDirectory;
	private ExclusiveDirectoryHandler scriptsDirectory;
	private ExclusiveDirectoryHandler fontsDirectory;
	
	public AssetsHandler()
	{
		this(defaultAssets);
	}
	
	public AssetsHandler(String folder)
	{
		textureDirectory = new DirectoryHandler(folder + "/art", ".png");
		soundDirectory = new DirectoryHandler(folder + "/sound", ".ogg");
		soundFxDirectory = new JsonDirectoryHandler(folder + "/soundfx", ".json");
		musicDirectory = new DirectoryHandler(folder + "/music", ".ogg");
		animationDirectory = new JsonDirectoryHandler(folder + "/animations", ".json");
		entityDataDirectory = new JsonDirectoryHandler(folder + "/entities", ".json");
		scriptsDirectory = new ExclusiveDirectoryHandler(folder + "/scripts", ".py");
		fontsDirectory = new ExclusiveDirectoryHandler(folder + "/fonts", ".fnt");
		populateJsons();
		interpreter.getSystemState().path.append(new PyString(scriptsDirectory.getPath().getPath()));
		manager.setLoader(Texture.class, new TextureLoader(textureDirectory));
		manager.setLoader(Sound.class, new SoundLoader(soundDirectory));
		manager.setLoader(Music.class, new MusicLoader(musicDirectory));
		manager.setLoader(Animation.class, new AnimationLoader(animationDirectory, animationDirectory.getJson()));
		manager.setLoader(EntityData.class, new EntityDataLoader(entityDataDirectory, entityDataDirectory.getJson()));
		manager.setLoader(PyObject.class, new ScriptLoader(scriptsDirectory, interpreter));
		manager.setLoader(SoundFx.class, new SoundFxLoader(soundFxDirectory, soundFxDirectory.getJson()));
		manager.setLoader(BitmapFont.class, new BitmapFontLoader(fontsDirectory));
		manager.setLoader(DescriptorData.class, new DescriptorLoader(new InternalFileHandleResolver(), new Json()));
	}
	
	private void populateJsons()
	{
		entityDataDirectory.getJson().addClassTag(SoundFxData.class.getSimpleName(), SoundFxData.class);
		String[] packages = new String[] {
				"com.vgdc.merge.entities.controllers",
				"com.vgdc.merge.entities.abilities" };

		for (String packageName : packages) {
			for (Class<?> c : ClassFinder.getClassesInPackage(packageName)) {
				entityDataDirectory.getJson().addClassTag(c.getSimpleName(), c);
			}
		}
		entityDataDirectory.getJson().addClassTag(Vector2.class.getSimpleName(), Vector2.class);
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
		for(FileHandle h : fontsDirectory.getFilesInDirectory())
		{
			manager.load(h.nameWithoutExtension(), BitmapFont.class);
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
	 * loads just the files necessary to run the given act
	 * @param filename
	 */
	public void loadBasedOnAct(String filename)
	{
		
	}
	
	/**
	 * loads just the files described in the given json
	 * @param filename
	 */
	public void loadBasedOnDescription(String filename)
	{
		manager.load(filename, DescriptorData.class);
	}
	
	public boolean update(){
		return manager.update();
	}
	
	public void dispose()
	{
		manager.dispose();
	}
	

}
