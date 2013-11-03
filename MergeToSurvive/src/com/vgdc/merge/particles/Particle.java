package com.vgdc.merge.particles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.vgdc.merge.world.ParticleManager;

public abstract class Particle{
	public ParticleManager manager;
	
	public Vector2 position,velocity;
	
	public float rotation,rotvelocity;
	
	public float diameter;
	
	public Texture texture;
	public Color color = Color.WHITE;
	public float lifetime;
	
	public Particle(String texturename,float diameter,float lifetime){
		setTextureFrom(texturename);
		this.diameter = diameter;
		this.lifetime = lifetime;
	}
	
	public abstract void onRegister();
	
	/////
	public void setParticleManager(ParticleManager manager){
		this.manager = manager;
	}
	public void remove(){
		System.out.println("remove");
		manager.removeParticle(this);
	}
	
	/////Rendering Stuff
	public void setTextureFrom(String texturename){
		texture = new Texture(Gdx.files.internal(texturename));
	}
	public boolean shouldBeBehind(){
		return true;
	}
	
	public float[] getTextureFrameData(){
		return new float[]{0,0,texture.getWidth(),texture.getHeight()};
	}
	
	/////Background Properties
	public float getDistanceToRemove(){//Not used yet; implement this when the camera system is ready.
		return 9999;
	}
	
	/////Physics Properties
	public abstract float getAerodynamics();
	
	/////Helpful Methods
	public void addTransparency(float amount,boolean removeontransparent){
		color.a = color.a*amount;
		if(color.a <= .03f){
			remove();
		}
	}

	/////Update
	public static final float TIMEMULT = 60;
	public void onUpdate(float dt){
		System.out.println("UPDATE!");
		lifetime -= dt;
		if(lifetime <= 0){
			remove();
		}
		dt *= TIMEMULT;
		position.add(new Vector2(velocity.x*dt,velocity.y*dt));
		velocity.mul(getAerodynamics());
		rotation += rotvelocity*dt;
	}
	
	/////Draw
	public void onRender(SpriteBatch batch){
		batch.setColor(color);
		float[] framerender = getTextureFrameData();
		if(framerender == null){
			batch.draw(texture, position.x-texture.getWidth()/4, position.y-texture.getHeight()/4, 0,0, diameter, diameter, 1, 1, rotation, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
		}else{
			batch.draw(texture, position.x-texture.getWidth()/4, position.y-texture.getHeight()/4, 0,0, diameter, diameter, 1, 1, rotation, (int)framerender[0], (int)framerender[1], (int)framerender[2], (int)framerender[3], false, false);
		}
		//batch.draw(texture, position.x-texture.getWidth()/2, position.y-texture.getHeight()/2, texture.getWidth()/2, texture.getHeight()/2, diameter, diameter, 1, 1, rotation, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
		//batch.draw(texture, 0, 0, 0,0, diameter, diameter, 1, 1, rotation, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
	}
}
