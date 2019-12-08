package com.gdx.anight.box2d.tiledmap;

import com.badlogic.gdx.physics.box2d.Body;
import com.gdx.anight.enums.EnemyStates;

public class TiledEnemyUserData extends UserDataTiledEnemy{

	@SuppressWarnings("static-access")
	public TiledEnemyUserData(Body body, float width, float height, float maxHealth, float health,
			float damage, boolean dead, EnemyStates currentstate) {
		
		super(body, width, height, maxHealth, health, damage, dead, currentstate);
		userDataType = userDataType.TILED_ENEMY_STRONG;
	}



}
