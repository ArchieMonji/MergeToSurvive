package com.vgdc.merge.entities;

public enum EntityType {
	Platform(1), Entity(2), Item(3), Projectile(4);
	
	public final int mask;
	
	private EntityType(int bit){
		this.mask = 0x1 << bit;
	}
}
