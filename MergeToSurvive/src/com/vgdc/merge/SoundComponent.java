package com.vgdc.merge;

import java.util.ArrayList;

import com.badlogic.gdx.audio.Sound;

public class SoundComponent {
	
	private ArrayList<ArrayList<SoundFx>> sounds = new ArrayList<ArrayList<SoundFx>>();
	private Sound currentSound;
	private long soundId;
	
	private BaseEntity entity;
	
	public void setSounds(ArrayList<ArrayList<SoundFx>> nSounds)
	{
		sounds = nSounds;
	}
	
	public void setEntity(Entity nEntity)
	{
		entity = nEntity;
	}
	
	public void playSound(int state){
		if(currentSound!=null)
		{
			currentSound.stop(soundId);
		}
		if(sounds==null||state >= sounds.size()||sounds.get(state)==null)
			return;
		SoundFx sound = sounds.get(state).get((int)(Math.random()*sounds.get(state).size()));
		currentSound = sound.sound;
		soundId = currentSound.play();
		currentSound.setLooping(soundId, sound.looping);
	}

}
