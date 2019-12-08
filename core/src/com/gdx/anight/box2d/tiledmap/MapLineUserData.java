package com.gdx.anight.box2d.tiledmap;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;

public class MapLineUserData extends UserDataLineTiled{

	@SuppressWarnings("static-access")
	public MapLineUserData(Array<Body> bodies) {
		super(bodies);
		userDataType = userDataType.LINEOBJECT;
	}
	
}
