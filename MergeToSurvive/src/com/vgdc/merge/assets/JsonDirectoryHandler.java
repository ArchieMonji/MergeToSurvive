package com.vgdc.merge.assets;

import com.badlogic.gdx.utils.Json;

public class JsonDirectoryHandler extends DirectoryHandler{
	
	public JsonDirectoryHandler(String directory) {
		super(directory);
	}
	
	public JsonDirectoryHandler(String directory, String ext)
	{
		super(directory, ext);
	}
	
	public Json getJson()
	{
		return json;
	}

	private Json json = new Json();

}
