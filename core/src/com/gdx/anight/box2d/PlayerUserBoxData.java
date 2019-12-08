package com.gdx.anight.box2d;

import com.badlogic.gdx.physics.box2d.Body;

public class PlayerUserBoxData extends UserDataPlayer{

	@SuppressWarnings("static-access")
	public PlayerUserBoxData(Body body, float width, float height, float maxHealth, float health,
			int damage_low, int damage_medium, int damage_high) {
		
		super(body, width, height, maxHealth, health, damage_low, damage_medium, damage_high);
		userDataType = userDataType.PLAYER;
	}
	

	
}
