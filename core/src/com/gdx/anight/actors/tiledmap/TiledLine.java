package com.gdx.anight.actors.tiledmap;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.gdx.anight.box2d.tiledmap.MapLineUserData;

public class TiledLine extends GameActorLineTiled{

	public TiledLine(Array<Body> bodies) {
		super(bodies);
	}

	@Override
	public MapLineUserData getUserData() {
		return (MapLineUserData) userDataLineTiled;
	}
}
