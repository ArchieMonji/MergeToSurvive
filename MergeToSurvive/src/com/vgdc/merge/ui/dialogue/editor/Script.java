package com.vgdc.merge.ui.dialogue.editor;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Script implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Page[] pages;
	String event;

	public static class Page {
		String speaker;
		String imageName;
		String[] lines;
	}

	public static void main(String[] args) {
		try {
			FileOutputStream fileOut = new FileOutputStream(new File("e.ser"));
			Script s = new Script();
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(s);
			out.close();
			fileOut.close();
			System.out.printf("Serialized data is saved in /tmp/employee.ser");
		} catch (IOException i) {
			i.printStackTrace();
		}
	}
}
