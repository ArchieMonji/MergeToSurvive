package com.vgdc.merge.test;

import com.vgdc.merge.Ability;
import com.vgdc.merge.Entity;

public class TestAbility extends Ability {

	@Override
	public void onUse(Entity entity) {
		System.out.println("" + entity + " just cast the test ability!");
		
	}
	
	

}
