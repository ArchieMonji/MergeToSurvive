package com.vgdc.merge.world;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.vgdc.merge.math.VectorMath;
import com.vgdc.merge.particles.FireParticle;
import com.vgdc.merge.particles.Particle;

public class ParticleManager{
	public Random random;
	ArrayList<Particle> particles;
	World world;
	public ParticleManager(World world){
		this.world = world;
		random = new Random();
		particles = new ArrayList<Particle>();
	}
	
	/////Basic add/remove
	public void addParticle(Particle p){
		particles.add(p);
		p.setParticleManager(this);
		p.onRegister();
	}
	public void removeParticle(Particle p){
		particles.remove(p);
	}
	
	/////Update/Render
	public void onUpdate(float dt){
		Particle[] array = new Particle[particles.size()];
		particles.toArray(array);
		for(Particle p : array)
			p.onUpdate(dt);
	}
	public void onRender_Back(SpriteBatch batch){
		for(Particle p : particles)
			if(p.shouldBeBehind())
				p.onRender(batch);
	}
	public void onRender_Front(SpriteBatch batch){
		for(Particle p : particles)
			if(!p.shouldBeBehind())
				p.onRender(batch);
	}
	
	/////Helpful randomization methods
	public Vector2 getRandomDirection(){
		return VectorMath.fromAngle(random.nextFloat()*VectorMath.pi*2);
	}
	
	/////Particle Creation Methods
	public void create_Fire(Vector2 pos){
		FireParticle p = new FireParticle();
		p.position = pos.cpy();
		p.velocity = VectorMath.mul(getRandomDirection(),1);
		addParticle(p);
	}
}
