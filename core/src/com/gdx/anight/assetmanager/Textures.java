package com.gdx.anight.assetmanager;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Textures {
	
public static final String TEX_BADLOGIC = "textures/badlogic.jpg";
public static final String TEX_WEAPON = "textures/gfx/weapon.png";
public static final String TEX_BULLET = "textures/gfx/bullet.png";
public static final String TEX_ENEMY = "textures/enemy.png";

public static final String TEX_PLAYERSHEET = "textures/gfx/playersheet.png";
public static final int BPZ_PLAYERSHEET = 13;
public static final int BPS_PLAYERSHEET = 6;

public static final String TEX_PLAYERSHEET2 = "textures/gfx/playersheet3.png";
public static final int BPZ_PLAYERSHEET2 = 13;
public static final int BPS_PLAYERSHEET2 = 6;

public static final String ATLAS_PLAYERSHEET = "textures/gfx/player_pack.pack";
public static final String ATLAS_PLAYERSHEET2 = "textures/gfx/player2_pack.pack";
public static final int BREITE_ATLASPLAYER = 75;
public static final int HOEHE_ATLASPLAYER = 105;



public static final String TEX_FULLLIFE = "textures/fulllife.jpg";
public static final String TEX_EMPTYLIFE = "textures/emptylife.jpg";

	public static void loadTextures(AssetManager manager) {
		manager.load(TEX_BADLOGIC, Texture.class);
		manager.load(TEX_PLAYERSHEET, Texture.class);
		manager.load(ATLAS_PLAYERSHEET, TextureAtlas.class);
		manager.load(ATLAS_PLAYERSHEET2, TextureAtlas.class);
		manager.load(TEX_PLAYERSHEET2, Texture.class);
		manager.load(TEX_FULLLIFE, Texture.class);
		manager.load(TEX_EMPTYLIFE, Texture.class);
		manager.load(TEX_WEAPON, Texture.class);
		manager.load(TEX_BULLET, Texture.class);
		manager.load(TEX_ENEMY, Texture.class);
	}

}

