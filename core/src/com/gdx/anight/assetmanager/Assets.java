package com.gdx.anight.assetmanager;

import com.badlogic.gdx.assets.AssetManager;

public class Assets {
	
	public static final AssetManager manager = new AssetManager();
	
	public static void load() {
		Textures.loadTextures(manager);
		Sounds.loadSounds(manager);	
	}
		
	public static void dispose() {
		manager.dispose();
	}

}
