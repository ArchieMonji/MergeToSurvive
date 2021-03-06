package com.vgdc.merge.assets;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;

public class DirectoryHandler implements FileHandleResolver
{
	
	private FileHandle path;
	private FileHandle base;
	private String directory;
	private String extension;
	
	public DirectoryHandler(String directory)
	{
		this(directory, "");
	}
	
	public DirectoryHandler(String directory, String extension)
	{
		this.extension = extension;
		this.directory = directory;
		path = Gdx.files.internal(directory);
		base = Gdx.files.internal("");
		if(!path.isDirectory())
		{
			path = Gdx.files.internal("bin/" + directory);
			base = Gdx.files.internal("bin/");
			if(!path.isDirectory())
			{
				String s = AssetsHandler.class.getProtectionDomain().getCodeSource().getLocation().getPath();
				try {
					s = URLDecoder.decode(s, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				if(s.contains("\\"))
						s = s.substring(0, s.lastIndexOf("\\"));
				else if(s.contains("/"))
					s = s.substring(0, s.lastIndexOf("/"));
				base = new FileHandle(s);
				System.out.println(base.path());
				path = new FileHandle(s + "\\" + directory);
			}
		}
		//System.out.println(path.path());
	}

	@Override
	public FileHandle resolve(String filename) {
		if(!filename.contains("."))
			filename = filename + extension;
		FileHandle handle = new FileHandle(new File(path.file(), filename));
		if(!handle.exists())
			handle = Gdx.files.internal(directory + "/" + filename);
		if(!handle.exists())
			handle = Gdx.files.internal(filename);
		if(!handle.exists())
			handle = Gdx.files.absolute(filename);
		return handle;
	}
	
	public File getPath()
	{
		return path.file();
	}
	
	public File getBase()
	{
		return base.file();
	}
	
	public String getExtension()
	{
		return extension;
	}
	
	public FileHandle[] getFilesInDirectory()
	{
		return path.list();
	}
}
