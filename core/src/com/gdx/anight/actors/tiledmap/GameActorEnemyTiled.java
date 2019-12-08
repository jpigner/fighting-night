package com.gdx.anight.actors.tiledmap;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gdx.anight.box2d.tiledmap.UserDataTiledEnemy;

public abstract class GameActorEnemyTiled extends Actor{
	
	protected Body body;
	protected UserDataTiledEnemy userDataTiledEnemy;

	protected Rectangle rect;
	
	public GameActorEnemyTiled(Body body) {
		this.body = body;
		this.userDataTiledEnemy = (UserDataTiledEnemy) body.getUserData(); 
		
		rect = new Rectangle();
		
		rect.width = userDataTiledEnemy.getWidth();
		rect.height = userDataTiledEnemy.getHeight();
	}
	
	public abstract UserDataTiledEnemy getUserData();
	
}
