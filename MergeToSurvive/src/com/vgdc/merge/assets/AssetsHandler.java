package com.vgdc.merge.assets;

import java.io.File;

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
import com.vgdc.merge.assets.loaders.LevelLoader;
import com.vgdc.merge.assets.loaders.PlatformDataLoader;
import com.vgdc.merge.assets.loaders.ScriptLoader;
import com.vgdc.merge.assets.loaders.SoundFxLoader;
import com.vgdc.merge.assets.loaders.data.DescriptorData;
import com.vgdc.merge.assets.loaders.data.SoundFxData;
import com.vgdc.merge.entities.EntityData;
import com.vgdc.merge.entities.PlatformData;
import com.vgdc.merge.entities.abilities.Ability;
import com.vgdc.merge.entities.audio.SoundFx;
import com.vgdc.merge.entities.controllers.Controller;
import com.vgdc.merge.world.level.Act;
import com.vgdc.merge.world.level.LevelData;

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
	private JsonDirectoryHandler platformDataDirectory;
	private DirectoryHandler levelDataDirectory;
	
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
		platformDataDirectory = new JsonDirectoryHandler(folder + "/platforms", ".json");
		levelDataDirectory = new DirectoryHandler(folder + "/levels");
		populateJsons();
		interpreter.getSystemState().path.append(new PyString(scriptsDirectory.getPath().getAbsolutePath()));
		manager.setLoader(Texture.class, new TextureLoader(textureDirectory));
		manager.setLoader(Sound.class, new SoundLoader(soundDirectory));
		manager.setLoader(Music.class, new MusicLoader(musicDirectory));
		manager.setLoader(Animation.class, new AnimationLoader(animationDirectory, animationDirectory.getJson()));
		manager.setLoader(EntityData.class, new EntityDataLoader(entityDataDirectory, entityDataDirectory.getJson()));
		manager.setLoader(PyObject.class, new ScriptLoader(scriptsDirectory, interpreter));
		manager.setLoader(SoundFx.class, new SoundFxLoader(soundFxDirectory, soundFxDirectory.getJson()));
		manager.setLoader(BitmapFont.class, new BitmapFontLoader(fontsDirectory));
		manager.setLoader(PlatformData.class, new PlatformDataLoader(platformDataDirectory, platformDataDirectory.getJson()));
		manager.setLoader(LevelData.class, new LevelLoader(levelDataDirectory));
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
	
	public PlatformData getPlatformData(String path){
		return manager.get(path, PlatformData.class);
	}
	
	public LevelData getLevelData(String path){
		return manager.get(path, LevelData.class);
	}
	
	public Act getAct(String path){
		return manager.get(path, Act.class);
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
		manager.load(filename, LevelData.class);
	}
	
	/**
	 * loads just the files necessary to run the given act
	 * @param filename
	 */
	public void loadBasedOnAct(String filename)
	{
		//TODO implement this later
		System.exit(0);
	}
	
	public void loadEntity(String filename)
	{
		manager.load(filename, EntityData.class);
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
