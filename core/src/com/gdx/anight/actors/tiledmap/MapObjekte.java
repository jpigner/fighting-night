package com.gdx.anight.actors.tiledmap;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.gdx.anight.box2d.tiledmap.MapObjekteUserData;

public class MapObjekte extends GameActorTiled{

	public MapObjekte(Array<Body> bodies) {
		super(bodies);
	}

	@Override
	public MapObjekteUserData getUserData() {
		return (MapObjekteUserData) userDataTiled;
	}
}
