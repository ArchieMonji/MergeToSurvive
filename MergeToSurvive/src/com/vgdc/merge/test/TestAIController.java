package com.vgdc.merge.test;

import com.vgdc.merge.Controller;

public class TestAIController extends Controller {
	
	private static final float TIME = 2;
	
	private float moveTime = TIME;
	private boolean left = true;

	@Override
	public Controller copy() {
		return new TestAIController();
	}

	@Override
	public void onUpdate(float delta) {
		moveTime-=delta;
		if(moveTime<=0)
		{
			moveTime += TIME;
			left = !left;
		}
		if(left)
			getEntity().moveLeft();
		else
			getEntity().moveRight();
	}

}
