package com.vgdc.merge.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.MusicLoader;
import com.badlogic.gdx.assets.loaders.SoundLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.vgdc.merge.entities.EntityData;

public class AssetsHandler {
	
	private AssetManager manager = new AssetManager();
	
	private DirectoryHandler textureDirectory = new DirectoryHandler("test/art", ".png");
	private DirectoryHandler soundDirectory = new DirectoryHandler("test/sound", ".ogg");
	private DirectoryHandler musicDirectory = new DirectoryHandler("test/music", ".ogg");
	private JsonDirectoryHandler animationDirectory = new JsonDirectoryHandler("test/anim", ".json");
	private JsonDirectoryHandler entityDataDirectory = new JsonDirectoryHandler("test/entity", ".json");
	
	public AssetsHandler()
	{
		populateJsons();
		manager.setLoader(Texture.class, new TextureLoader(textureDirectory));
		manager.setLoader(Sound.class, new SoundLoader(soundDirectory));
		manager.setLoader(Music.class, new MusicLoader(musicDirectory));
		manager.setLoader(Animation.class, new AnimationLoader(animationDirectory, animationDirectory.getJson()));
		manager.setLoader(EntityData.class, new EntityDataLoader(entityDataDirectory, entityDataDirectory.getJson()));
	}
	
	private void populateJsons()
	{
		String[] packages = new String[] {
				"com.vgdc.merge.entities.controllers",
				"com.vgdc.merge.entities.abilities" };

		for (String packageName : packages) {
			for (Class<?> c : ClassFinder.getClassesInPackage(packageName)) {
				entityDataDirectory.getJson().addClassTag(c.getSimpleName(), c);
			}
		}
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
			manager.load(h.nameWithoutExtension(), AnimationData.class);
		}
		for(FileHandle h : entityDataDirectory.getFilesInDirectory())
		{
			manager.load(h.nameWithoutExtension(), EntityLoadData.class);
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
	

}
