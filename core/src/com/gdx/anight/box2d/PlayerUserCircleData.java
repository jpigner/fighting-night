package com.gdx.anight.box2d;

import com.badlogic.gdx.physics.box2d.Body;

public class PlayerUserCircleData extends UserDataCircle{
	
	@SuppressWarnings("static-access")
	public PlayerUserCircleData(Body body, float radius) {
		super(body, radius);
		userDataType = userDataType.PLAYER;
	}
	
}
