package com.vgdc.merge.entities;

import java.util.ArrayList;
import java.util.HashMap;

import org.python.core.PyObject;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.vgdc.merge.entities.abilities.Ability;
import com.vgdc.merge.entities.audio.SoundFx;
import com.vgdc.merge.entities.controllers.Controller;

public class EntityData extends BaseEntityData{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Controller controller;
	public ArrayList<Ability> defaultAbilities;
	public ArrayList<Animation> animations;
	public ArrayList<ArrayList<SoundFx>> sounds;
	public HashMap<String, PyObject> dynamicVariableTable;
	
	@Override
	public PyObject __findattr_ex__(String name){
		PyObject object = super.__findattr_ex__(name);
		if(object!=null)
			return object;
		return dynamicVariableTable.get(name);
	}
	
}
