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
		NoLayer, PlatformLayer, EntityLayer
	}
	
	private class TempLevelData extends DefaultHandler
	{
		public LevelData data;
		
		public Vector2 dimensions;
		
		private LayerType currentLayer = LayerType.NoLayer;
		
		public void startElement(String uri, String localName,String qName, Attributes attributes) throws SAXException
        {
			if(qName.equals("level"))
			{
				dimensions = new Vector2();
				dimensions.x = Float.parseFloat(attributes.getValue("width"));
				dimensions.y = Float.parseFloat(attributes.getValue("height"));
			}
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
			case EntityLayer:
				LevelEntityData ent = new LevelEntityData();
				ent.entityData = qName;
				ent.location = new Vector2();
				ent.location.x = Float.parseFloat(attributes.getValue("x"));
				ent.location.y = Float.parseFloat(attributes.getValue("y"));
				data.entities.add(ent);
				break;
			case PlatformLayer:
				LevelPlatformData plat = new LevelPlatformData();
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
//		for(LevelEntityData d : levelData.entities)
//			deps.add(new AssetDescriptor(d.entityData, EntityData.class));
//		for(LevelPlatformData d : levelData.platforms)
//			deps.add(new AssetDescriptor(d.imageFileName, Texture.class));
		
	}

	@Override
	public LevelData loadSync(AssetManager manager, String fileName,
			LevelParameter parameter) {
		return levelData.data;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Array<AssetDescriptor> getDependencies(String fileName,
			LevelParameter parameter) {
		levelData = new TempLevelData();
		try {
			parser.parse(resolve(fileName).file(), levelData);
			Array<AssetDescriptor> deps = new Array<AssetDescriptor>();
			for(LevelEntityData d : levelData.data.entities)
				deps.add(new AssetDescriptor(d.entityData, EntityData.class));
			for(LevelPlatformData d : levelData.data.platforms)
				deps.add(new AssetDescriptor(d.imageFileName, Texture.class));
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
