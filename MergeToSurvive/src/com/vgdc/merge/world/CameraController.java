package com.vgdc.merge.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.vgdc.merge.entities.Entity;
import com.vgdc.merge.entities.physics.MovingBody;

public class CameraController {
	//i may split this class in two
	OrthographicCamera camera = new OrthographicCamera(800, 600);
	Entity player;
	//Vector2 worldDimensions;
	World world;
	
	public CameraController(World world)
	{
		this.world = world;
	}
	
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
		updateCamera(delta);
	}
	
	public void setCameraDimensions(float width, float height)
	{
		this.camera.viewportWidth = width;
		this.camera.viewportHeight = height;
		System.out.println(camera.viewportWidth + ", " + camera.viewportHeight);
	}
	
	private void updateCamera(float delta)
	{
		MovingBody body = player.getMovingBody();
		
//		if(body.getVelocity().x > player.getMoveSpeed() / 2){
//			leadingOffset += leadSpeed * delta;
//		}
//		else if(body.getVelocity().x < -player.getMoveSpeed() / 2){
//			leadingOffset -= leadSpeed * delta;
//		}
//		else{
//			if(leadingOffset > leadSpeed / 8){
//				
//				leadingOffset -= leadSpeed * delta;
//			}
//			else if(leadingOffset < -leadSpeed / 8){
//				leadingOffset += leadSpeed * delta;
//			}
//			else{
//				leadingOffset = 0;
//			}
//		}
//		
//		MathUtils.clamp(leadingOffset, -maxDist, maxDist);
//		
//		camera.position.x = body.getPosition().x + leadingOffset;
		camera.position.x = body.getPosition().x;
		camera.position.y = body.getPosition().y;
		camera.position.x = MathUtils.clamp(camera.position.x, camera.viewportWidth/2, world.getDimensions().x- camera.viewportWidth/2);
		camera.position.y = MathUtils.clamp(camera.position.y, camera.viewportHeight/2, world.getDimensions().y- camera.viewportHeight/2);
		camera.update();
	}
}
