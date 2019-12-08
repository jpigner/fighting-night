package com.gdx.anight.box2d;

import com.badlogic.gdx.physics.box2d.Body;

public class BulletUserData extends UserDataBox{

	@SuppressWarnings("static-access")
	public BulletUserData(Body body, float width, float height) {
		super(body, width, height);
		userDataType = userDataType.BULLET;
	}

}
