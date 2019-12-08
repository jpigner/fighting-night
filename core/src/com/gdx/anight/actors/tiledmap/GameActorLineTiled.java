package com.gdx.anight.actors.tiledmap;

import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.gdx.anight.box2d.tiledmap.UserDataLineTiled;

public abstract class GameActorLineTiled extends Actor{
	
	protected Array<Body> bodies;
	protected UserDataLineTiled userDataLineTiled;

	protected Polyline line;
	protected float[] vertices;
	
	public GameActorLineTiled(Array<Body> bodies) {
		this.bodies = bodies;
		this.userDataLineTiled = (UserDataLineTiled) bodies.get(0).getUserData();
		
		line = new Polyline();
		vertices = line.getTransformedVertices();
		
	}
	
	public abstract UserDataLineTiled getUserData();
	
	
	
}
