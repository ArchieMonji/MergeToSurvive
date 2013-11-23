package com.vgdc.merge.ui.dialogue;

public class DialogueScript{
	public Page[] pages;
	public String event;

	public static class Page {
		public String speaker;
		public Emotion[] emotions;
	}
	
	public static class Emotion{
		public String image;
		public String[] lines;
	}
}
