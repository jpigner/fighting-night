package com.gdx.anight.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.gdx.anight.box2d.GroundUserData;

public class Ground extends GameActorBox{
		
	public Ground(Body body) {
		super(body);
		
	}

	@Override
	public GroundUserData getUserData() {
		return (GroundUserData) userDataBox;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
	}

}