package com.vgdc.merge.assets;

import java.util.ArrayList;
import java.util.HashMap;

import com.vgdc.merge.entities.BaseEntityData;
import com.vgdc.merge.entities.EntityData;
import com.vgdc.merge.entities.UnitStateEnum;
import com.vgdc.merge.entities.abilities.Ability;
import com.vgdc.merge.entities.audio.BaseSoundFx;

public class EntityLoadData extends BaseEntityData{
	
	public static class SoundFxData extends BaseSoundFx
	{
		public String path;
	}
	
	public ArrayList<Ability> abilities;
	public ArrayList<String> animations;
	public HashMap<UnitStateEnum, ArrayList<SoundFxData>> sounds;
	
	public EntityData convert(AssetsHandler handler)
	{
		return null;
	}

}
