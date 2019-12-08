package com.gdx.anight.actors;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gdx.anight.box2d.UserDataBox;

public abstract class GameActorBox extends Actor {

	protected Body body;
		
	protected UserDataBox userDataBox;

	protected Rectangle rect;
	
	public GameActorBox(Body body) {
		this.body = body;
		this.userDataBox = (UserDataBox) body.getUserData();
		
		rect = new Rectangle();
	
		rect.width = userDataBox.getWidth();
		rect.height = userDataBox.getHeight();
		
	}
		
	public abstract UserDataBox getUserData();

}
