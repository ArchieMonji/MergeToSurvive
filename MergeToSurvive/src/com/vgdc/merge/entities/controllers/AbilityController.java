package com.vgdc.merge.entities.controllers;

import java.util.ArrayList;

import com.vgdc.merge.entities.abilities.Ability;

public abstract class AbilityController extends Controller{
	
	public void onDeath(){
		super.onDeath();
		ArrayList<Ability> a = getEntity().getAbilities();
		if(a!=null&&a.size()>0&&a.get(0)!=null)
		{
			System.out.println("drop it!");
		}
	}

}
