package com.gdx.anight.actors;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gdx.anight.box2d.UserDataPlayer;

public abstract class GameActorPlayer extends Actor {

	protected Body body;
		
	protected UserDataPlayer userDataPlayer;

	protected Rectangle rect;
	
	public GameActorPlayer(Body body) {
		this.body = body;
		this.userDataPlayer = (UserDataPlayer) body.getUserData();
		
		rect = new Rectangle();
	
		rect.width = userDataPlayer.getWidth();
		rect.height = userDataPlayer.getHeight();
		
	}
		
	public abstract UserDataPlayer getUserData();

}
