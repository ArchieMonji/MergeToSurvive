package com.vgdc.merge.test;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.vgdc.merge.Ability;
import com.vgdc.merge.Controls;
import com.vgdc.merge.Entity;
import com.vgdc.merge.EntityData;
import com.vgdc.merge.PlayerController;
import com.vgdc.merge.Renderer;

public class TestMainGame implements ApplicationListener{
	
	private static Entity testEntity;
	
	private static SpriteBatch batch;

	@Override
	public void create() {
		batch = new SpriteBatch();
		ArrayList<Ability> abilities = new ArrayList<Ability>();
		abilities.add(new TestAbility());
		abilities.add(null);
		
		Texture animTexture = new Texture(Gdx.files.internal("data/animation_sheet.png"));
		TextureRegion[] regions = new TextureRegion[30];
		int x = animTexture.getWidth()/6;
		int y = animTexture.getHeight()/5;
		for(int i = 0; i < 5; i++)
		{
			for(int j = 0; j < 6; j++)
			{
				regions[i*6 + j] = new TextureRegion(animTexture, j*x, i*y, x, y);
			}
		}
		Animation idle = new Animation(10, new TextureRegion(animTexture, 0, 0, x, y));
		Animation run = new Animation(0.01f, regions);
		
		ArrayList<Animation> animations = new ArrayList<Animation>();
		animations.add(idle);
		animations.add(run);
		
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
		testData.animations = animations;
		
//		testEntity = new Entity(testData);
//		testEntity.setPhysicsBody(new TestPhysicsBody());
//		testEntity.setRenderer(new Renderer());
//		testEntity.setPosition(new Vector2(x/2, y/2));
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();
		testEntity.onUpdate(Gdx.graphics.getDeltaTime());
		testEntity.onRender(batch, Gdx.graphics.getDeltaTime());
		batch.end();
		
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
