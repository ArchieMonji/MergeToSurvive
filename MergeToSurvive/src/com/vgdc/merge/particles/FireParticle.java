package com.vgdc.merge.particles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class FireParticle extends Particle {
    float lerptransrate;
    float sizechange;
    
	public FireParticle(){
		super("data/particles/fire.png",15,2);
	}
	
	public void onRegister(){
		color = new Color(1,1,1,1f);
		System.out.println(color);
		sizechange = .99f+manager.random.nextFloat()*.02f;
		lerptransrate = .95f+manager.random.nextFloat()*.03f;
		rotvelocity = (manager.random.nextFloat()-.5f)*2;
	}

	@Override
	public float getAerodynamics(){
		return .9f;
	}

	static final Vector2 acceleration = new Vector2(0,.07f);	
	@Override
	public void onUpdate(float dt){
		//TODO add glow?
		super.onUpdate(dt);
		diameter *= sizechange;
		addTransparency(lerptransrate,true);
		velocity.add(acceleration);
	}
}
