package com.gdx.anight.box2d.tiledmap;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;

public class MapObjekteUserData extends UserDataTiled{

	@SuppressWarnings("static-access")
	public MapObjekteUserData(Array<Body> bodies, float width, float height) {
		super(bodies, width, height);
		userDataType = userDataType.MAPOBJECT;
	}
	
}
