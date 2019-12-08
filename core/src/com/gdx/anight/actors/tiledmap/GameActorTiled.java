package com.gdx.anight.actors.tiledmap;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.gdx.anight.box2d.tiledmap.UserDataTiled;

public abstract class GameActorTiled extends Actor{
	
	protected Array<Body> bodies;
	protected UserDataTiled userDataTiled;

	protected Rectangle rect;
	
	public GameActorTiled(Array<Body> bodies) {
		this.bodies = bodies;
		this.userDataTiled = (UserDataTiled) bodies.get(0).getUserData();
		
		rect = new Rectangle();
		
		rect.width = userDataTiled.getWidth();
		rect.height = userDataTiled.getHeight();
	}
	
	public abstract UserDataTiled getUserData();
	
}
