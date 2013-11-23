package com.vgdc.merge.ui.dialogue.editor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.badlogic.gdx.utils.Json;


public class JsonTest {
	public static void main(String[] args) throws FileNotFoundException {
		Json j = new Json();
		Script s = j.fromJson(Script.class, new FileInputStream("Json Format.txt"));
		
		System.out.println(j.prettyPrint(j.toJson(s)));
	}
	
	static class Script{
		Page[] pages;
		String event;
	}
	
	static class Page{
		String speaker;
		String imageName;
		String[] lines;
	}
	
}
