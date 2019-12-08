package com.gdx.anight.box2d;

import com.badlogic.gdx.physics.box2d.Body;
import com.gdx.anight.enums.UserDataType;

public abstract class UserDataBox {
	
	protected UserDataType userDataType;
	
	protected float width;
	protected float height;
	
	protected Body body;
		
	public UserDataBox(Body body, float width, float height) {
		this.width = width;
		this.height = height;
		this.body = body;
	}
		
	public Body getBody() {
		return body;
	}
		
	public UserDataType getUserDataType() {
		return userDataType;
	}
	
	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	
}
