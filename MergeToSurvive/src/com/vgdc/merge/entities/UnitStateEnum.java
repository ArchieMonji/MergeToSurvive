package com.vgdc.merge.entities;

public enum UnitStateEnum {
	IDLE(0), MOVE(1), JUMP(2), ATTACK(3), DEATH(4);
	
	public final int value;
	
	private UnitStateEnum(int value)
	{
		this.value = value;
	}
}
