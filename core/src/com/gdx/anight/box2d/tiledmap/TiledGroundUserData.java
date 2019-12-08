package com.gdx.anight.box2d.tiledmap;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;

public class TiledGroundUserData extends UserDataTiled{

	@SuppressWarnings("static-access")
	public TiledGroundUserData(Array<Body> bodies, float width, float height) {
		super(bodies, width, height);
		userDataType = userDataType.TILED_GROUND;
	}
	
}
