package com.gdx.anight.box2d;

import com.badlogic.gdx.physics.box2d.Body;
import com.gdx.anight.enums.UserDataType;

public abstract class UserDataEnemy {
	
	protected UserDataType userDataType;
	
	protected float width;
	protected float height;
	protected float maxHealth;
	protected float health;
	
	protected Body body;
		
	public UserDataEnemy(Body body, float width, float height, float maxHealth, float health) {
		
		this.width = width;
		this.height = height;
		this.body = body;
		this.maxHealth = maxHealth;
		this.health = health;
	
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

	public float getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(float maxHealth) {
		this.maxHealth = maxHealth;
	}

	public float getHealth() {
		return health;
	}

	public void setHealth(float health) {
		this.health = health;
	}
	
}
