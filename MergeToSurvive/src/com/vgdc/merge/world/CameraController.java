package com.vgdc.merge.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.vgdc.merge.entities.Entity;
import com.vgdc.merge.entities.physics.MovingBody;

public class CameraController {
	OrthographicCamera camera;
	Entity player;
	
	public void setCamera(OrthographicCamera camera) {
		this.camera = camera;
	}
	
	public void setPlayer(Entity player) {
		this.player = player;
	}
	
	float maxDist = 800;
	
	float leadingOffset;
	
	float leadSpeed = 50f;
	
	public void update(float delta){
		MovingBody body = player.getMovingBody();
		
		if(body.getVelocity().x > player.getMoveSpeed() / 2){
			leadingOffset += leadSpeed * delta;
		}
		else if(body.getVelocity().x < -player.getMoveSpeed() / 2){
			leadingOffset -= leadSpeed * delta;
		}
		else{
			if(leadingOffset > leadSpeed / 8){
				
				leadingOffset -= leadSpeed * delta;
			}
			else if(leadingOffset < -leadSpeed / 8){
				leadingOffset += leadSpeed * delta;
			}
			else{
				leadingOffset = 0;
			}
		}
		
		MathUtils.clamp(leadingOffset, -maxDist, maxDist);
		
		camera.position.x = body.getPosition().x + leadingOffset;
	}
}
