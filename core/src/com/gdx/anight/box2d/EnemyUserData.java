package com.gdx.anight.box2d;

import com.badlogic.gdx.physics.box2d.Body;

public class EnemyUserData extends UserDataEnemy{

	@SuppressWarnings("static-access")
	public EnemyUserData(Body body, float width, float height, float maxHealth, float health) {
		super(body, width, height, maxHealth, health);
		
		userDataType = userDataType.ENEMY_STRONG;
	}



}
