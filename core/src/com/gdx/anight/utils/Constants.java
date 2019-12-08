package com.gdx.anight.utils;

import com.badlogic.gdx.math.Vector2;

public class Constants {
	
	// Fensterkonstanten
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	public static final String TITLE = "Adventurenights v0.1 betabloody";
	
	// Worldkonstanten
	public static final Vector2 GRAVITY = new Vector2(0, -9.81f*13);
	public static final float TIMESTEP = 1 / 60f;
	public static final float SCALE = 100;
	public static final float SCALE_TILED = 40;
	
	// Playerkonstanten
	public static final float X_VERSATZ = 1.3f;
	public static final float Y_VERSATZ = 1.35f;
	public static final int MAX_POINTS = 1000;
	
	// Enemykontanten
	public static final float SHOOTING_ENEMY_DAMAGE = 250;
	
}
