package com.gdx.anight.box2d.tiledmap;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.gdx.anight.enums.UserDataType;

public abstract class UserDataLineTiled {
	
	protected UserDataType userDataType;
	
	protected Array<Body> bodies;
	
	public UserDataLineTiled(Array<Body> bodies) {
		this.bodies = bodies;
	}
	
	public Array<Body> getBodies() {
		return bodies;
	}

	public UserDataType getUserDataType() {
		return userDataType;
	}
		
}
	
	
