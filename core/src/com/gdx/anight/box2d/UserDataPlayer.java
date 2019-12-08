package com.gdx.anight.box2d;

import com.badlogic.gdx.physics.box2d.Body;
import com.gdx.anight.enums.UserDataType;

public abstract class UserDataPlayer {
	
	protected UserDataType userDataType;
	
	protected float width;
	protected float height;
	protected float maxHealth;
	protected float health;
	protected int damage_low, damage_medium, damage_high;
	
	protected Body body;
		
	public UserDataPlayer(Body body, float width, float height, float maxHealth, float health,
			int damage_low, int damage_medium, int damage_high) {	
		this.width = width;
		this.height = height;
		this.body = body;
		this.maxHealth = maxHealth;
		this.health = health;
		this.damage_low = damage_low;
		this.damage_medium = damage_medium;
		this.damage_high = damage_high;
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

	public float getDamage_low() {
		return damage_low;
	}
	
	public int getDamage_medium() {
		return damage_medium;
	}

	public float getDamage_high() {
		return damage_high;
	}
	
	
}
