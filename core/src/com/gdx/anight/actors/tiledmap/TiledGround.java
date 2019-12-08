package com.gdx.anight.actors.tiledmap;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.gdx.anight.box2d.tiledmap.TiledGroundUserData;

public class TiledGround extends GameActorTiled{

	public TiledGround(Array<Body> bodies) {
		super(bodies);
	}

	@Override
	public TiledGroundUserData getUserData() {
		return (TiledGroundUserData) userDataTiled;
	}
}
