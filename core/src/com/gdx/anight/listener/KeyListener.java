package com.gdx.anight.listener;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.MathUtils;
import com.gdx.anight.actors.Player2;

public class KeyListener extends InputAdapter{
	private boolean[] keys;
	
	public KeyListener() {
		keys = new boolean[256];
	}
	
	@Override
	public boolean keyDown(int key) {
		keys[key] = true;
		
		return false;
	}

	@Override
	public boolean keyUp(int key) {
		keys[key] = false;
		
		Player2.getJoint().enableLimit(true);
		Player2.getJoint().enableMotor(false);
		Player2.getJoint().setLimits(Player2.getJoint().getJointAngle() - .1f * MathUtils.degRad, 
				Player2.getJoint().getJointAngle() + .1f * MathUtils.degRad); 
		
		return false;
	}

	public boolean[] getKeys() {
		return keys;
	}
	
}
