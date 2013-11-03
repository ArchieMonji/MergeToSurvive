package com.vgdc.merge.ui.dialogue;

public class DialogueScript{
	Page[] pages;
	String event;

	public static class Page {
		String speaker;
		Emotion[] emotions;
	}
	
	public static class Emotion{
		String image;
		String[] lines;
	}
}
