package com.gdx.anight.actors;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gdx.anight.box2d.UserDataEnemy;

public abstract class GameActorEnemy extends Actor {

	protected Body body;
		
	protected UserDataEnemy userDataEnemy;

	protected Rectangle rect;
	
	public GameActorEnemy(Body body) {
		this.body = body;
		this.userDataEnemy = (UserDataEnemy) body.getUserData();
		
		rect = new Rectangle();
	
		rect.width = userDataEnemy.getWidth();
		rect.height = userDataEnemy.getHeight();
		
	}
		
	public abstract UserDataEnemy getUserData();

}
