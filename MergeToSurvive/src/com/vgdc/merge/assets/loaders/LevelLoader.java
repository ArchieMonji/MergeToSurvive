package com.vgdc.merge.assets.loaders;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.vgdc.merge.entities.EntityData;
import com.vgdc.merge.entities.PlatformData;
import com.vgdc.merge.world.level.LevelData;
import com.vgdc.merge.world.level.LevelEntityData;
import com.vgdc.merge.world.level.LevelPlatformData;

public class LevelLoader extends AsynchronousAssetLoader<LevelData, LevelLoader.LevelParameter> {
	
	private SAXParser parser;
	private SAXParserFactory factory;
	
	private TempLevelData levelData;

	public LevelLoader(FileHandleResolver resolver) {
		super(resolver);
		factory = SAXParserFactory.newInstance();
		try {
			parser = factory.newSAXParser();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
	}
	
	private static enum LayerType {
		NoLayer, Platform, Entity
	}
	
	private class TempLevelData extends DefaultHandler
	{
		public LevelData data = new LevelData();
		
		private LayerType currentLayer = LayerType.NoLayer;
		
		public void startElement(String uri, String localName,String qName, Attributes attributes) throws SAXException
        {
			if(qName.equals("level"))
			{
				data.dimensions = new Vector2();
				data.dimensions.x = Float.parseFloat(attributes.getValue("width"));
				data.dimensions.y = Float.parseFloat(attributes.getValue("height"));
				data.background = attributes.getValue("Background");
			}
			else if(qName.equals("camera"))
			{
				data.cameraDimensions = new Vector2();
				data.cameraDimensions.x = Float.parseFloat(attributes.getValue("width"));
				data.cameraDimensions.y = Float.parseFloat(attributes.getValue("height"));
			}
			else if(qName.equals("start"))
			{
				data.playerStart = new Vector2();
				data.playerStart.x = Float.parseFloat(attributes.getValue("x"));
				data.playerStart.y = Float.parseFloat(attributes.getValue("y"));
			}
			else
			{
				switch(currentLayer){
				case NoLayer:
					try{
						currentLayer = LayerType.valueOf(qName);
					}
					catch(Exception e)
					{
						throw new SAXException("Invalid Layer : " + qName);
					}
					break;
				case Entity:
					LevelEntityData ent = new LevelEntityData();
					ent.entityData = qName;
					ent.location = new Vector2();
					ent.location.x = Float.parseFloat(attributes.getValue("x"));
					ent.location.y = Float.parseFloat(attributes.getValue("y"));
					data.entities.add(ent);
					break;
				case Platform:
					LevelPlatformData plat = new LevelPlatformData();
					plat.platformDataName = qName;
					plat.location = new Vector2();
					plat.location.x = Float.parseFloat(attributes.getValue("x"));
					plat.location.y = Float.parseFloat(attributes.getValue("y"));
					plat.dimensions = new Vector2();
					plat.dimensions.x = Float.parseFloat(attributes.getValue("width"));
					plat.dimensions.y = Float.parseFloat(attributes.getValue("height"));
					data.platforms.add(plat);
					break;
				}
			}
        }
		
		public void endElement(String uri, String localName, String qName) throws SAXException
		{
			if(qName.equals(currentLayer.name()))
				currentLayer = LayerType.NoLayer;
		}
	}

	public class LevelParameter extends AssetLoaderParameters<LevelData> {

	}

	@Override
	public void loadAsync(AssetManager manager, String fileName,
			LevelParameter parameter) {
		
	}

	@Override
	public LevelData loadSync(AssetManager manager, String fileName,
			LevelParameter parameter) {
		System.out.println("finish : " + fileName);
		return levelData.data;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Array<AssetDescriptor> getDependencies(String fileName,
			LevelParameter parameter) {
		levelData = new TempLevelData();
		try {
			System.out.println("start : " + fileName);
			System.out.println(resolve(fileName).file().getAbsolutePath());
			System.out.println(fileName);
			parser.parse(resolve(fileName).file(), levelData);
			Array<AssetDescriptor> deps = new Array<AssetDescriptor>();
			for(LevelEntityData d : levelData.data.entities)
				deps.add(new AssetDescriptor(d.entityData, EntityData.class));
			for(LevelPlatformData d : levelData.data.platforms)
				deps.add(new AssetDescriptor(d.platformDataName, PlatformData.class));
			deps.add(new AssetDescriptor(levelData.data.background, Texture.class));
			return deps;
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
