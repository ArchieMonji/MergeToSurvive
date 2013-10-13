package com.vgdc.merge.test;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.vgdc.merge.Ability;
import com.vgdc.merge.Controls;
import com.vgdc.merge.Entity;
import com.vgdc.merge.EntityData;
import com.vgdc.merge.PlayerController;

public class TestMainGame implements ApplicationListener{
	
	private static Entity testEntity;

	@Override
	public void create() {
		ArrayList<Ability> abilities = new ArrayList<Ability>();
		abilities.add(new TestAbility());
		abilities.add(null);
		
		Controls controls = new Controls();
		controls.down = Keys.S;
		controls.up = Keys.W;
		controls.left = Keys.A;
		controls.right = Keys.D;
		controls.useAbility = Buttons.LEFT;
		controls.toggleAbility = Buttons.RIGHT;
		
		EntityData testData = new EntityData();
		testData.jumpHeight = 5;
		testData.moveSpeed = 5;
		testData.maxHealth = 1;
		testData.defaultAbilities = abilities;
		testData.controller = new PlayerController(controls);
		
		testEntity = new Entity(testData);
		testEntity.setPhysicsBody(new TestPhysicsBody());
		testEntity.setRenderer(new TestRenderer());
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() {
		testEntity.onUpdate(Gdx.graphics.getDeltaTime());
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
