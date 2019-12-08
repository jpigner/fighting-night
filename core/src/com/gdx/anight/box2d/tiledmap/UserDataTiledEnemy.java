package com.gdx.anight.box2d.tiledmap;

import com.badlogic.gdx.physics.box2d.Body;
import com.gdx.anight.enums.EnemyStates;
import com.gdx.anight.enums.UserDataType;

public abstract class UserDataTiledEnemy {
	
	protected UserDataType userDataType;
	
	protected Body body;
	protected float height;
	protected float width;
	protected float maxHealth;
	protected float health;
	protected float damage;
	protected boolean dead;
	protected EnemyStates currentState;
	
	public UserDataTiledEnemy(Body body, float width, float height,
			float maxHealth, float health, float damage, boolean dead, EnemyStates currentState) {
		this.body = body;
		this.height = height;
		this.width = width;
		this.maxHealth = maxHealth;
		this.health = health;
		this.damage = damage;
		this.dead = dead;
		this.currentState = currentState;
	}
	
	public Body getBody() {
		return body;
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
	
	public float getMaxHealth() {
		return maxHealth;
	}
	
	public float getHealth() {
		return health;
	}
	
	public float getDamage() {
		return damage;
	}
	
	public void setMaxHealth(float maxHealth) {
		this.maxHealth = maxHealth;
	}
	
	public void setHealth(float health) {
		this.health = health;
	}

	public void setDamage(float damage) {
		this.damage = damage;
	}
	
	public UserDataType getUserDataType() {
		return userDataType;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public EnemyStates getCurrentState() {
		return currentState;
	}

	public void setCurrentState(EnemyStates currentState) {
		this.currentState = currentState;
	}
		
}
