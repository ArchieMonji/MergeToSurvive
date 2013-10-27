package com.vgdc.merge.assets;

import java.util.ArrayList;

import com.badlogic.gdx.files.FileHandle;

/**
 * just like a normal DirectoryHandler, except it only returns files of the given extension
 * @author Team Merge
 *
 */
public class ExclusiveDirectoryHandler extends DirectoryHandler {

	public ExclusiveDirectoryHandler(String directory, String extension) {
		super(directory, extension);
	}
	
	public FileHandle[] getFilesInDirectory()
	{
		FileHandle[] list = super.getFilesInDirectory();
		ArrayList<FileHandle> alist = new ArrayList<FileHandle>();
		for(FileHandle h : list)
		{
			if(h.extension().equals(getExtension()))
				alist.add(h);
		}
		FileHandle[] ans = new FileHandle[alist.size()];
		for(int i = 0; i < alist.size(); i++)
		{
			ans[i] = alist.get(i);
		}
		return ans;
	}

}
