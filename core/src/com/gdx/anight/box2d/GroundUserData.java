package com.gdx.anight.box2d;

import com.badlogic.gdx.physics.box2d.Body;

public class GroundUserData extends UserDataBox{

	@SuppressWarnings("static-access")
	public GroundUserData(Body body, float width, float height) {
		super(body, width, height);
		userDataType = userDataType.GROUND;
	}

}
