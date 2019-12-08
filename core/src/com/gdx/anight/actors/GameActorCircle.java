package com.gdx.anight.actors;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.gdx.anight.box2d.UserDataCircle;

public abstract class GameActorCircle extends Actor {

	protected Body body;
	protected Body body2;
	protected Body body3;
	
	protected Array<Body> bodies;
	protected UserDataCircle userDataBox;

	protected Rectangle rect;
	
	public GameActorCircle(Body body) {
		this.body = body;
		this.userDataBox = (UserDataCircle) body.getUserData();
		
		rect = new Rectangle();
	}
		
 

}
