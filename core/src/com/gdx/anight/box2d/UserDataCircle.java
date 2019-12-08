package com.gdx.anight.box2d;

import com.badlogic.gdx.physics.box2d.Body;
import com.gdx.anight.enums.UserDataType;

public abstract class UserDataCircle {
	
	protected UserDataType userDataType;
	
	protected float radius;
	
	protected Body body;
		
	public UserDataCircle(Body body, float radius) {
		this.body = body;
		
	}
		
	public Body getBody() {
		return body;
	}
		
	public UserDataType getUserDataType() {
		return userDataType;
	}
	
	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}
	
}
