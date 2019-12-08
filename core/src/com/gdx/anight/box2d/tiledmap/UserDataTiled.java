package com.gdx.anight.box2d.tiledmap;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.gdx.anight.enums.UserDataType;

public abstract class UserDataTiled {
	
	protected UserDataType userDataType;
	
	protected Array<Body> bodies;
	protected float height;
	protected float width;
	
	public UserDataTiled(Array<Body> bodies, float width, float height) {
		this.bodies = bodies;
		this.height = height;
		this.width = width;
	}
	
	public Array<Body> getBodies() {
		return bodies;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public UserDataType getUserDataType() {
		return userDataType;
	}
	
	
	
}
